package FSM.presetFSMs;

import FSM.FSM;
import FSM.FSM_State;
import javafx.scene.input.KeyCode;
import makingGame.entity.BasicProperties;
import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import makingGame.interactions.events.conditionals.*;

import java.util.HashSet;


public class TurtleMoveFSM extends FSM{

    public TurtleMoveFSM(){
        FSM_State movingslow=new FSM_State("turtle moving slow");
        FSM_State ducking=new FSM_State("turtle ducking");
        FSM_State ducked=new FSM_State("turtle ducked");
        FSM_State movingfast=new FSM_State("turtle moving fast");
        FSM_State movingfastready=new FSM_State("turtle moving fast ready for another step");
//        FSM_State movingrightfast=new FSM_State("turtle moving right first");
//        FSM_State movingleftfast=new FSM_State("turtle moving left first");

        HashSet<Action> actionlist_moveleftslow=new HashSet<>();
        HashSet<Action> actionlist_moverightslow=new HashSet<>();
        HashSet<Action> actionlist_moveleftfast=new HashSet<>();
        HashSet<Action> actionlist_moverightfast=new HashSet<>();

        HashSet<Action> actionlist_startduck=new HashSet<>();
        HashSet<Action> actionlist_continueduck=new HashSet<>();
        HashSet<Action> actionlist_unduck=new HashSet<>();

        actionlist_moveleftfast.add(new NumericalAction(BasicProperties.XVEL,"=","-100",""));
        actionlist_moveleftfast.add(new NumericalAction(BasicProperties.XSCALE,"=","-1",""));
        actionlist_moveleftslow.add(new NumericalAction(BasicProperties.XVEL,"=","-30",""));
        actionlist_moveleftslow.add(new NumericalAction(BasicProperties.XSCALE,"=","-1",""));
        actionlist_moverightfast.add(new NumericalAction(BasicProperties.XVEL,"=","100",""));
        actionlist_moverightfast.add(new NumericalAction(BasicProperties.XSCALE,"=","1",""));
        actionlist_moverightslow.add(new NumericalAction(BasicProperties.XVEL,"=","30",""));
        actionlist_moverightslow.add(new NumericalAction(BasicProperties.XSCALE,"=","1",""));

        actionlist_startduck.add(new NumericalAction(BasicProperties.XVEL,"=","0",""));
        actionlist_startduck.add(new NumericalAction("timer","=","0",""));
        actionlist_continueduck.add(new NumericalAction("timer","+","1",""));
        actionlist_unduck.add(new NumericalAction(BasicProperties.XVEL,"=","-30",""));

        Conditionals mariotopcollision= CollisionCondition.makeConditional("Mario",CollisionCondition.TOP);
        Conditionals marioleftcollision= CollisionCondition.makeConditional("Mario",CollisionCondition.LEFT);
        Conditionals mariorightcollision= CollisionCondition.makeConditional("Mario",CollisionCondition.RIGHT);
        Conditionals brickrightcollision= CollisionCondition.makeConditional("Brick",CollisionCondition.RIGHT);
        Conditionals brickleftcollision= CollisionCondition.makeConditional("Brick",CollisionCondition.LEFT);

        Conditionals waitedlongenough=PropertyCondition.makeConditional("timer",">",15);
        Conditionals duckedlongenough=PropertyCondition.makeConditional("timer",">",300);
        Conditionals alwaystrue= AlwaysTrueCondition.makeConditional();


        movingslow.connectwith(brickleftcollision,actionlist_moverightslow,movingslow);
        movingslow.connectwith(brickrightcollision,actionlist_moveleftslow,movingslow);
        movingslow.connectwith(mariotopcollision,actionlist_startduck,ducking);

        ducking.connectwith(waitedlongenough,actionlist_startduck,ducked);
        ducking.connectwith(alwaystrue,actionlist_continueduck,ducking);
//        ducking.connectwith(marioleftcollision,actionlist_moverightfast,movingfast);
//        ducking.connectwith(mariotopcollision,actionlist_moverightfast,movingfast);
//        ducking.connectwith(mariorightcollision,actionlist_moveleftfast,movingfast);
//        ducking.connectwith(alwaystrue,actionlist_continueduck,ducking);
        ducked.connectwith(duckedlongenough,actionlist_unduck,movingslow);
        ducked.connectwith(marioleftcollision,actionlist_moverightfast,movingfast);
        ducked.connectwith(mariotopcollision,actionlist_moverightfast,movingfast);
        ducked.connectwith(mariorightcollision,actionlist_moveleftfast,movingfast);
        ducked.connectwith(alwaystrue,actionlist_continueduck,ducked);

        movingfast.connectwith(brickleftcollision,actionlist_moverightfast,movingfast);
        movingfast.connectwith(brickrightcollision,actionlist_moveleftfast,movingfast);
        movingfast.connectwith(mariotopcollision,actionlist_startduck,ducking);

        currentstate=movingslow;
    }
}
