package makingGame.interactions.events;

import exception.ActionNotFoundException;
import exception.ConditionNotFoundException;
import makingGame.interactions.action.Action;
import makingGame.interactions.events.Conditions.Condition;


import java.util.*;

public class EventPackage {

    Map<String, Condition> myConditions;
    Map<String, Action> myActions;
    String myId;
    int priority;

    /**
     * constructor
     * @param id
     */
    public EventPackage(String id) {
        this.priority = 0;
        myId = id;
        myConditions = new HashMap<>();
        myActions = new HashMap<>();
    }

    /**
     * add a action with action id
     * @param actionId
     * @param a
     */
    public void addAction(String actionId, Action a){
        //System.out.println(myId + " "+ a.toString());

        myActions.put(actionId, a);

    }

    /**
     * add a condition with condition id
     * @param conditionId
     * @param e
     */
    public void addCondition(String conditionId, Condition e){
        myConditions.put(conditionId, e);
    }

    /**
     * remove condition with condition id
     * @param conditionId
     */
    public void deleteCondition(String conditionId) {
        myConditions.remove(conditionId);
    }

    /**
     * get all conditions
     * @return
     */
    public List<Condition> getConditions(){
        List<Condition> conditions = new ArrayList<>();
        for (String s : myConditions.keySet()) {
            conditions.add(myConditions.get(s));
        }
        return conditions;
    }

    /**
     * get all actions
     * @return
     */
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        for (String s : myActions.keySet()) {
            actions.add(myActions.get(s));
        }
        return actions;
    }

    /**
     * set priority of the event package
     * @param priority
     */
    public void setPriority(String priority){
        this.priority = Integer.parseInt(priority);
    }

    public int getPriority() {
        return priority;
    }

    /**
     * @return all condition id of conditions in this package
     */
    public Collection<String> getConditionIds() {
        return myConditions.keySet();
    }

    /**
     *
     * @return all action id of actions in this package
     */
    public Collection<String> getActionIds() {
        return myActions.keySet();
    }

    /**
     * @return this package's id
     */
    public String getMyId() {
        return myId;
    }

    /**
     *
     * @return a text description of this package which is just its id
     */
    public String toString(){
        return myId;
    }
}

