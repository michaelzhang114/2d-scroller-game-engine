package playingGame.gamerunning;

import FSM.FSM;
import external.GameCreator;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import makingGame.entity.BasicProperties;
import makingGame.interactions.action.Action;
import makingGame.interactions.events.conditionals.Conditionals;
import playingGame.Instance;
import playingGame.engines.FSMConditionalEngine.FSMConditionEngine;
import playingGame.engines.KeyInputHandler;
import playingGame.engines.actionEngine.NumericalActionEngine;
import playingGame.engines.actionEngine.StringActionEngine;
import playingGame.manager.LevelManager;
import playingGame.engines.conditionCheckers.EventEngine;
import makingGame.interactions.events.EventPackage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import javafx.util.Pair;
import playingGame.engines.PhysicsEngine;
import playingGame.engines.RenderingEngine;

import java.util.*;

/**
 * @author: Diego Chamorro, Michael Zhang, Justin Kim, yao yuan
 */
public class GameEnvironment {
    private final String LEVEL_CONTROLLER_INSTANCE_ID = "lc";
    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final String[] MYCONDITIONTYPES = { "KeyInputCondition", "CollisionCondition", "PropertyCondition", "TimeCondition" };
    private final String[] MYACTIONTYPES = { "NumericalAction", "StringAction"};

    //private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;
    private  final String STARTING_LEVEL_ID = "0";

    //engines
    private PhysicsEngine myPhysicsEngine;
    private RenderingEngine myRenderingEngine;
    private EventEngine myEventEngine;

    private double myTotalTime;

    private KeyHolder keyHolder;
    private List<Instance> myInstances;

    private Map<String, List<EventPackage>> myEventPackages;
    private LevelManager myLevelManager;
    private String myCurrentLevel;
    private Scene myScene;
    private Map<String, Image> backgroundMap;

    private KeyInputHandler myKeyInputHandler;
    private FSMConditionEngine myFSMConditionEngine;
    private NumericalActionEngine myNumericalActionEngine;
    private Timeline animation;
    private boolean paused;
    private StringActionEngine myStringActionEngine;
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    public GameEnvironment(LevelManager levelManager, Map<String, List<EventPackage>> myEvents, Pair<Double, Double> screenSize, Map<String, String> background){
        myNumericalActionEngine=new NumericalActionEngine();
        myStringActionEngine = new StringActionEngine();
        myKeyInputHandler=new KeyInputHandler();

        myLevelManager = levelManager;
        myFSMConditionEngine=new FSMConditionEngine();
        myFSMConditionEngine.setKeyInputHandler(myKeyInputHandler);
        myFSMConditionEngine.setLevelManager(myLevelManager);
        myCurrentLevel = STARTING_LEVEL_ID;
        myRenderingEngine = new RenderingEngine(screenSize.getKey(), screenSize.getValue());
        myPhysicsEngine = new PhysicsEngine();
        myEventPackages = myEvents;
        myTotalTime = 0;
        myEventEngine = new EventEngine(Arrays.asList(MYCONDITIONTYPES), Arrays.asList(MYACTIONTYPES));
        keyHolder = new KeyHolder();
        backgroundMap =  makeBackGroundImages(background);
        myScene = new Scene(new Group(), screenSize.getKey(), screenSize.getValue());
        myScene.setOnKeyPressed(e -> handleKeyPressedInput(e.getCode()));
        myScene.setOnKeyReleased(e -> myKeyInputHandler.handleKeyReleasedInput(e.getCode()));
        initializeLevel();
    }

    private Map<String, Image> makeBackGroundImages(Map<String, String>  backgroundPaths){
        Map<String, Image> tempMap = new HashMap<>();
        for(String level : backgroundPaths.keySet()){
//            System.out.println(level);
//            System.out.println(backgroundPaths.get(level));
            System.out.println(level+""+backgroundPaths.get(level));
            //System.out.println(this.getClass().getClassLoader().getResourceAsStream(backgroundPaths.get(level)));
            tempMap.put(level, new Image(this.getClass().getClassLoader().getResourceAsStream(backgroundPaths.get(level))));
        }
        return tempMap;
    }

    /**
     * starts running the game
     */
    public void run() {
        paused = false;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e-> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     *
     * @return
     *
     * gets the Scene to the GamePlayer can run it.
     */
    public Scene getScene(){
        return myScene;
    }

    private void initializeLevel() {
        Group root = myLevelManager.createLevel(myCurrentLevel);
        System.out.println("level: " + myCurrentLevel);
        System.out.println(backgroundMap.get(myCurrentLevel));
        ImagePattern pattern = new ImagePattern(backgroundMap.get(myCurrentLevel));
        myScene.setFill(pattern);
        Instance mainCharacter = myLevelManager.getMainCharacter(myCurrentLevel);
        myInstances = myLevelManager.getInstancesOfCurrentLevel();
        myScene.setRoot(root);
        myRenderingEngine.setScreen(myInstances, mainCharacter);
    }

    private void handleKeyPressedInput (KeyCode code) {
        if(!paused){
            keyHolder.addKey(code);
            myKeyInputHandler.handleKeyPressedInput(code);
        }
    }

    /**
     * pauses the game
     */
    public void pause(){
        paused = true;
        animation.stop();
    }

    /**
     * starts the game after being paused
     */
    public void start(){
        paused = false;
        animation.play();
    }
    private void step (double elapsedTime){
        myTotalTime += elapsedTime;
        for (Instance instance : myInstances) {
            String entityType = instance.getEntityType();
            for (EventPackage eventPackage : myEventPackages.get(entityType)) {
                myEventEngine.evaluateEventPackage(instance, eventPackage, keyHolder.getKeysPressed(), myInstances, myTotalTime);
            }

            if (instance.getInstanceID().equals(LEVEL_CONTROLLER_INSTANCE_ID) && !instance.getValueOfProperty("level").equals(myCurrentLevel)) {
                myCurrentLevel = instance.getValueOfProperty("level");
                myCurrentLevel = Integer.toString((int) Double.parseDouble(myCurrentLevel));
                initializeLevel();
            }
            if(instance.getChangedView()){
                instance.createImageView();
            }
        }
        handleFSM();
        myPhysicsEngine.update(elapsedTime, myInstances);
        myRenderingEngine.updateGraphics();
        keyHolder.reset();
        myKeyInputHandler.keycode_conditions.clear();
    }

    //to be moved to a dedicated fsm handler
    private void handleFSM(){
        ArrayList<Action> actions_toexe = new ArrayList<>();
        for (Instance instance : myInstances) {
//            System.out.println("checking fsm for instance "+instance.getEntityType()+instance.getInstanceID());
            actions_toexe.clear();
//            System.out.println(instance.getEntityType());
            List<Instance> listInstance = new ArrayList<>();
            listInstance.add(instance);
            if (instance.getEntityType().equals("Mario")){
                System.out.println("checking fsm for instance "+instance.getEntityType()+instance.getInstanceID());
                System.out.println("yloc: "+instance.getValueOfProperty(BasicProperties.YLOC));
                System.out.println("yacc: "+instance.getValueOfProperty(BasicProperties.YACC));
                System.out.println("yvel: "+instance.getValueOfProperty(BasicProperties.YVEL));
                System.out.println("xvel: "+instance.getValueOfProperty(BasicProperties.XVEL));
            }
            for(FSM fsm:instance.getFSMs()){
                if (instance.getEntityType().equals("Mario")) {
                    System.out.println("checking " + fsm.currentstate.statename);
                }
                for(Conditionals c:fsm.currentstate.triggers){
                    if(myFSMConditionEngine.evaluateConditionFor(instance,c)){
                        actions_toexe.addAll(fsm.getaction(c));
                        fsm.transitstate(c);
                        break;
                    }
                }
            }
            for(Action a : actions_toexe){
                if(a.getClass().getSimpleName().equals("StringAction")) {
                    myStringActionEngine.execute(listInstance,a);
                }
                else {
                    myNumericalActionEngine.execute(listInstance,a);
                }
            }
        }
    }
}

