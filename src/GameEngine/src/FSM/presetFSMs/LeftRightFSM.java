package FSM.presetFSMs;
import FSM.FSM;
import FSM.FSM_State;
import javafx.scene.input.KeyCode;
import makingGame.entity.BasicProperties;
import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import makingGame.interactions.events.conditionals.*;
import playingGame.engines.conditionCheckers.ConditionCheck;

import java.util.HashSet;

public class LeftRightFSM extends FSM{
    public LeftRightFSM(){
        FSM_State rest=new FSM_State("rest");
        FSM_State left=new FSM_State("left");
        FSM_State right=new FSM_State("right");
        FSM_State leftright=new FSM_State("leftright");

        HashSet<Action> actionlist_moveleft=new HashSet<>();
        actionlist_moveleft.add(new NumericalAction("xVelocity","=","-100",""));
//        actionlist_moveleft.add(new NumericalAction("xVelocity","=","-1.5",""));
        actionlist_moveleft.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","1",""));
        actionlist_moveleft.add(new NumericalAction(BasicProperties.XSCALE,"=","-1",""));


        HashSet<Action> actionlist_moveright=new HashSet<>();
        actionlist_moveright.add(new NumericalAction("xVelocity","=","100",""));
//        actionlist_moveright.add(new NumericalAction("xVelocity","=","1.5",""));
        actionlist_moveright.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","1",""));
        actionlist_moveright.add(new NumericalAction(BasicProperties.XSCALE,"=","1",""));

        HashSet<Action> actionlist_stop=new HashSet<>();
        actionlist_stop.add(new NumericalAction("xVelocity","=","0",""));
        actionlist_stop.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","0",""));
//        actionlist_moveleft.add("move left");
//        HashSet<String> actionlist_moveright=new HashSet<>();
//        actionlist_moveright.add("move right");
//        HashSet<String> actionlist_stop=new HashSet<>();
//        actionlist_stop.add("stop leftright");

        HashSet<Action> actionlist_maintainleft=new HashSet<>();
        actionlist_maintainleft.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","1",""));
        actionlist_maintainleft.add(new NumericalAction(BasicProperties.XSCALE,"=","-1",""));

        actionlist_maintainleft.addAll(actionlist_moveleft);

        HashSet<Action> actionlist_maintainright=new HashSet<>();
        actionlist_maintainright.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","1",""));
        actionlist_maintainright.add(new NumericalAction(BasicProperties.XSCALE,"=","1",""));
        actionlist_maintainright.addAll(actionlist_moveright);

        HashSet<Action> actionlist_maintainmoving=new HashSet<>();
        actionlist_maintainmoving.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","1",""));

        Conditionals left_pressed= KeyInputCondition.makeConditional(KeyCode.LEFT,"pressed");
        Conditionals left_released= KeyInputCondition.makeConditional(KeyCode.LEFT,"released");
        Conditionals right_pressed= KeyInputCondition.makeConditional(KeyCode.RIGHT,"pressed");
        Conditionals right_released= KeyInputCondition.makeConditional(KeyCode.RIGHT,"released");

        Conditionals alwaystruecondition= AlwaysTrueCondition.conditional;

        Conditionals collisionrightbrick= CollisionCondition.makeConditional("Brick",CollisionCondition.RIGHT);
        Conditionals collisionleftbrick= CollisionCondition.makeConditional("Brick",CollisionCondition.LEFT);

        rest.connectwith(left_pressed,actionlist_moveleft,left);
        rest.connectwith(right_pressed,actionlist_moveright,right);

        left.connectwith(right_pressed,actionlist_moveright,leftright);
        left.connectwith(left_released,actionlist_stop,rest);
        left.connectwith(collisionleftbrick,actionlist_stop,left);
        left.connectwith(alwaystruecondition,actionlist_maintainleft,left);


        right.connectwith(left_pressed,actionlist_moveleft,leftright);
        right.connectwith(right_released,actionlist_stop,rest);
        right.connectwith(collisionrightbrick,actionlist_stop,right);
        right.connectwith(alwaystruecondition,actionlist_maintainright,right);

        leftright.connectwith(left_released,actionlist_moveright,right);
        leftright.connectwith(right_released,actionlist_moveleft,left);
        leftright.connectwith(collisionrightbrick,actionlist_stop,leftright);
        leftright.connectwith(collisionleftbrick,actionlist_stop,leftright);
//        leftright.connectwith(alwaystruecondition,actionlist_maintainmoving,leftright);
//        current.connectwith("left",actionlist_moveleft,current);


        currentstate=rest;
    }
}
