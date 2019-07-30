package FSM.presetFSMs;

import FSM.FSM;
import FSM.FSM_State;
import javafx.scene.input.KeyCode;
import makingGame.entity.BasicProperties;
import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import makingGame.interactions.events.conditionals.AlwaysTrueCondition;
import makingGame.interactions.events.conditionals.Conditionals;
import makingGame.interactions.events.conditionals.PropertyCondition;
import playingGame.engines.conditionCheckers.ConditionCheck;
import makingGame.interactions.events.conditionals.KeyInputCondition;

import java.util.HashSet;

public class JumpFSM extends FSM{
    public JumpFSM(){
        FSM_State rest=new FSM_State("rest");
        FSM_State inair=new FSM_State("in air");
        FSM_State inair_tojump=new FSM_State("in air to jump");
        FSM_State doublejump=new FSM_State("double jump");

        HashSet<Action> jumpactionlist=new HashSet<>();
        jumpactionlist.add(new NumericalAction("yPosition","-","5",""));
        jumpactionlist.add(new NumericalAction("yAcceleration","=","6",""));
        jumpactionlist.add(new NumericalAction("yVelocity","=","-180",""));
//        jumpactionlist.add(new NumericalAction("yAcceleration","=",".1",""));
//        jumpactionlist.add(new NumericalAction("yVelocity","=","-3",""));
//        jumpactionlist.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","2",""));

        HashSet<Action> noactionlist=new HashSet<>();
//        noactionlist.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","2",""));

        HashSet<Action> maintainjumpactionlist=new HashSet<>();
//        maintainjumpactionlist.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","2",""));
//        HashSet<Action> actionlistdoublejump=new HashSet<>();
//        jumpactionlist.add(new Action("yAcceleration","=",-10));
//        jumpactionlist.add(new Action("yVelocity","=",10));
//        actionlistdoublejump.add("set a to -10");
//        actionlistdoublejump.add("set yv to 10");

        HashSet<Action> actionlistinairtorest=new HashSet<>();
        actionlistinairtorest.add(new NumericalAction("yAcceleration","=","0",""));
        actionlistinairtorest.add(new NumericalAction("yVelocity","=","0",""));
//        actionlistinairtorest.add(new NumericalAction(BasicProperties.IMAGE_INDEX,"=","0",""));
//        actionlistinairtorest.add("set a to 0");
//        actionlistinairtorest.add("set yv to 0");

        Conditionals jump_pressed= KeyInputCondition.makeConditional(KeyCode.UP,"pressed");
        Conditionals jump_released= KeyInputCondition.makeConditional(KeyCode.UP,"released");

        //to be changed to collision at the bottom
        Conditionals yeq0= PropertyCondition.makeConditional("yPosition",">",245);
        //needs to be added last
        Conditionals alwaystrue= AlwaysTrueCondition.makeConditional();


        //need to have a conditional that always evaluate true

//        rest.connectwith(yeq0,actionlistinairtorest,rest);

        rest.connectwith(jump_pressed,jumpactionlist,inair);


        inair.connectwith(jump_released,noactionlist,inair_tojump);
        inair.connectwith(yeq0,actionlistinairtorest,rest);
        inair.connectwith(alwaystrue,maintainjumpactionlist,inair);

        inair_tojump.connectwith(jump_pressed,jumpactionlist,doublejump);
        inair_tojump.connectwith(yeq0,actionlistinairtorest,rest);
        inair_tojump.connectwith(alwaystrue,maintainjumpactionlist,inair_tojump);

        doublejump.connectwith(yeq0,actionlistinairtorest,rest);
        doublejump.connectwith(alwaystrue,maintainjumpactionlist,doublejump);

        currentstate=rest;
    }
}
