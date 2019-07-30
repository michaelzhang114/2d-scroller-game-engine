package FSM.presetFSMs;

import FSM.FSM;
import FSM.FSM_State;
import javafx.scene.input.KeyCode;
import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import makingGame.interactions.events.conditionals.*;

import java.util.HashSet;

import static makingGame.interactions.events.conditionals.PropertyCondition.makeConditional;


//Assume falling declared by jumpfsm
public class FallOnGoombaFSM extends FSM {
    public FallOnGoombaFSM(){
        FSM_State notfalling=new FSM_State("not falling");
        FSM_State falling=new FSM_State("falling");

        HashSet<Action> noactionlist=new HashSet<>();
        HashSet<Action> hitgoomba=new HashSet<>();
        hitgoomba.add(new NumericalAction("yVelocity","=","-70",""));

        Conditionals isFalling=PropertyCondition.makeConditional("falling","==",1);
        Conditionals isNotFalling=PropertyCondition.makeConditional("falling","<",1);
        Conditionals collisionbottomgoomba=CollisionCondition.makeConditional("Goomba",CollisionCondition.BOTTOM);
        Conditionals collisionbottomturtle=CollisionCondition.makeConditional("Turtle",CollisionCondition.BOTTOM);

        notfalling.connectwith(isFalling,noactionlist,falling);
        falling.connectwith(isNotFalling,noactionlist,notfalling);
        falling.connectwith(collisionbottomgoomba,hitgoomba,falling);
        falling.connectwith(collisionbottomturtle,hitgoomba,falling);

        currentstate=notfalling;
    }
}
