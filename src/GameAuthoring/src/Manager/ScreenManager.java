package Manager;

import Screens.GameViewScreen.GameViewController;
import Screens.LevelViewScreen.LevelViewController;
import Screens.MapViewScreen.MapViewController;
import Screens.SplashScreen.SplashScreenController;
import Utilities.Level;
import Utilities.GameMap;
import external.GameCreator;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * This is perhaps the most important Class of the GAE. It is the main Manager of this part of the project. It initiates
 * all of the screen controllers. It initializes the GameCreator class that has access to all the methods from the backend
 * (Engine). It performs switches between the different screens and performs some tying with the GameCreator classes.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class ScreenManager {
    private final Stage myStage;
    private Scene myCurrentScene;

    private GameCreator myCreator;
    private SplashScreenController mySplashController;
    private GameViewController myGameViewController;
    private LevelViewController myLevelViewController;
    private MapViewController myMapViewController;

    private List<Level> myLevels;
    private Level myCurrentLevel;
    private String myGameName;
    private List<Level> undoLevel;
    private List<Level> redoLevel;

    public ScreenManager(Stage stage) {
        myStage = stage;
        myCreator = new GameCreator();
        myLevels = new ArrayList<>();
        undoLevel = new ArrayList<>();
        redoLevel = new ArrayList<>();
        mySplashController = new SplashScreenController(this, myCreator);
        myGameViewController = new GameViewController(this, myCreator);
        myLevelViewController = new LevelViewController(this, myCreator);
        myMapViewController = new MapViewController(this, myCreator);
        myCreator.setGameScreenSize("1040" , "650"); //values permanent
        jumpToSplashScreen();
    }

    private void jumpToSplashScreen() {
        myStage.setScene(mySplashController.getScreen().getScene());
        myCurrentScene = mySplashController.getScreen().getScene();
    }

    /**
     * Display the GameView Screen
     */
    public void jumpToGameViewScreen() {
        myGameViewController.getScreen().update();
        myStage.setScene(myGameViewController.getScreen().getScene());
        myCurrentScene = myGameViewController.getScreen().getScene();
    }

    /**
     * Display the LevelView Screen
     */
    public void jumpToLevelViewScreen(){
        myStage.setScene(myLevelViewController.getScreen().getScene());
        myCurrentScene = myLevelViewController.getScreen().getScene();
    }

    /**
     * Add new Level to the Game
     * @param ID: Name of the Level
     */
    public void addNewLevel(String ID) {
        myCurrentLevel = null;
        var level = new Level(ID, this, myLevelViewController);
        myLevels.add(level);
        myGameViewController.getScreen().update();
        undoLevel.add(level);
    }

    /**
     * Jump to the level screen selected
     * @param level: the level the user wants to open
     */
    public void setLevel(Level level){
        myCurrentLevel = level;
        myLevelViewController.setScreen(level);
        myStage.setScene(myLevelViewController.getScreen().getScene());
        myCurrentScene = myLevelViewController.getScreen().getScene();
    }

    /**
     * Undo the creation of a Level (aka. delete level just created).
     */
    public void undoLevel() {
        if (undoLevel.size() == 0) return;
        redoLevel.add(undoLevel.get(undoLevel.size() - 1));
        undoLevel.remove(undoLevel.size() - 1);
        myLevels.remove(myLevels.size()-1);
    }

    /**
     * Redo the creation of a level (aka. remake the level deleted).
     */
    public void redoLevel() {
        if (redoLevel.size() == 0) return;
        undoLevel.add(redoLevel.get(redoLevel.size() -1));
        myLevels.add(redoLevel.get(redoLevel.size()-1));
        redoLevel.remove(redoLevel.size()-1);
    }

    /**
     * Add new map to a specific Level in the Game
     * @param ID: name of the map
     * @param background: background image for the specific map
     */
    public void addNewMap(String ID, String background)  {
        var map = new GameMap(ID, background, this, myMapViewController);
        myCurrentLevel.getMaps().add(map);
    }

    /**
     * Jump to the map view screen selected
     * @param map: map the user wants to jump to
     */
    public void setMap(GameMap map){
        myCreator.setCurrentLevel(map.getMapID());
        myCreator.setBackgroundImage(map.getBackground());
        myMapViewController.setScreen(map.getScreen());
        myStage.setScene(myMapViewController.getScreen().getScene());
        myCurrentScene = myLevelViewController.getScreen().getScene();
    }

    /**
     * Returns the current stage of the GUI
     */
    public Stage getStage() { return myStage; }

    /**
     * Returns the list of levels in existence.
     */
    public List<Level> getLevels() {return myLevels; }

    /**
     * Retuns the list of maps in the current level.
     */
    public List<GameMap> getMapsOfLevel() { return myCurrentLevel.getMaps(); }

    /**
     * Returns the list of maps in level l
     * @param l: level
     */
    public List<GameMap> getMapsOfLevel(Level l) { return l.getMaps(); }

    /**
     * Returns the current level
     * @return
     */
    public Level getCurrentLevel() {return myCurrentLevel; }

    /**
     * Sets the name of the Game
     * @param name: name of the game
     */
    public void setName(String name) { myGameName = name;}

    /**
     * Returns the name of the game for display
     */
    public String getGameName() { return myGameName; }

    /**
     * Returns the current scene showing. (Only one scene can be shown at a time).
     */
    public Scene getCurrentScene() { return myCurrentScene; }

    /**
     * Deletes a map
     * @param i: map to be deleted
     */
    public void deleteMap(GameMap i) {
        getMapsOfLevel().remove(i);
    }

}