package FSM.presetFSMs;

import FSM.FSM;
import FSM.FSM_State;
import javafx.scene.input.KeyCode;
import makingGame.entity.BasicProperties;
import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import makingGame.interactions.events.conditionals.*;

import java.util.HashSet;

public class TurtleFallFSM extends FSM {
    public TurtleFallFSM(){
        FSM_State notfalling=new FSM_State("not falling");
        FSM_State falling=new FSM_State("falling");

        HashSet<Action> felloffedge=new HashSet<>();
        felloffedge.add(new NumericalAction(BasicProperties.YVEL,"=","400",""));

        HashSet<Action> actionlist_stop=new HashSet<>();
        actionlist_stop.add(new NumericalAction("yAcceleration","=","0",""));
        actionlist_stop.add(new NumericalAction("yVelocity","=","0",""));

        Conditionals brickbottomcollision= CollisionCondition.makeConditional("Brick",CollisionCondition.BOTTOM);
        Conditionals alwaystrue= AlwaysTrueCondition.makeConditional();


        notfalling.connectwith(brickbottomcollision,actionlist_stop,notfalling);
        notfalling.connectwith(alwaystrue,felloffedge,falling);
        falling.connectwith(brickbottomcollision,actionlist_stop,notfalling);
        falling.connectwith(alwaystrue,felloffedge,notfalling);

        currentstate=notfalling;

    }
}
