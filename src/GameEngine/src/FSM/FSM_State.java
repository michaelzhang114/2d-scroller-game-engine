package FSM;

import makingGame.interactions.action.Action;
import makingGame.interactions.events.conditionals.Conditionals;
import playingGame.engines.conditionCheckers.ConditionCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FSM_State {
    //public for now
    public String statename;
    public ArrayList<Conditionals> triggers;
    //    public HashSet<Conditionals> triggers;//event that triggers changes, labelled by names
    public HashMap<Conditionals,HashSet<Action>> actionmap;//map that links event trigger to actions to take
    public HashMap<Conditionals,FSM_State> transitionmap;//map that links event trigger to next states

    /**
     * constructor that just instantiates all references
     */
    public FSM_State(){
//        triggers=new HashSet<>();
        triggers=new ArrayList<>();
        actionmap=new HashMap<>();
        transitionmap=new HashMap<>();
    }

    /**
     * constructor that instantiates all references and give this state a name
     * @param statename
     */
    public FSM_State(String statename){
        this();
        this.statename=statename;
    }

    /**
     * connect a current state with the next state following a event and actions to take if this event is met
     * @param trigger
     * @param valueActions
     * @param nextstate
     */
    public void connectwith(Conditionals trigger, HashSet<Action> valueActions, FSM_State nextstate){
        triggers.add(trigger);
        actionmap.put(trigger, valueActions);
        transitionmap.put(trigger,nextstate);
    }

    //@deprecated
    public void connectwith(String trigger, HashSet<String> actionlist, FSM_State nextstate){
//        triggers.add(trigger);
//        actionmap.put(trigger,actionlist);
//        transitionmap.put(trigger,nextstate);
    }
}
