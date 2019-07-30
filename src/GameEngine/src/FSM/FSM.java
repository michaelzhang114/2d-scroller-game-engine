package FSM;

import makingGame.interactions.action.Action;
import makingGame.interactions.events.conditionals.Conditionals;
import playingGame.engines.conditionCheckers.ConditionCheck;

import java.util.HashSet;

public abstract class FSM {
    public FSM_State currentstate;

    /**
     * super class constructor of a fsm
     * specific fsm just has to change the constructor to fill in the content
     * nothing else since all interfaces are defined here
     */
    public FSM(){

    }

    /**
     * given a event, returns the actions corresponding to it
     * assumes that the event to be called through this has already been checked
     * @param event
     * @return
     */
    public HashSet<Action> getaction(Conditionals event){
        return currentstate.actionmap.get(event);
    }

    /**
     * change the state of this finite state machine along the path a event has specified
     * @param event
     */
    public void transitstate(Conditionals event){
        currentstate=currentstate.transitionmap.get(event);
    }
}
