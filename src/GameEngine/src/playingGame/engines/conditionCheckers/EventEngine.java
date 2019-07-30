package playingGame.engines.conditionCheckers;



import makingGame.interactions.events.Conditions.Condition;
import makingGame.interactions.events.EventPackage;
import playingGame.Instance;
import makingGame.interactions.action.Action;
import javafx.scene.input.KeyCode;
import playingGame.engines.actionEngine.ActionEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventEngine {
    Map<String, ConditionCheck> myChecks;
    HashMap<String, ActionEngine> myActions;

    /**
     * constructor
     * @param conditionTypes
     * @param actionTypes
     */
    public EventEngine(List<String> conditionTypes, List<String> actionTypes) {
        myChecks = new HashMap<>();
        myActions = new HashMap<>();
        for(String conditionType : conditionTypes){
            myChecks.put(conditionType, (ConditionCheck) classFactory("playingGame.engines.conditionCheckers."+conditionType+"Check"));
        }
        for(String actionType : actionTypes){
            myActions.put(actionType, (ActionEngine) classFactory("playingGame.engines.actionEngine."+actionType+"Engine"));
        }
    }

    /**
     * check a eventpackage for instance given certain keys are pressed
     * @param instance
     * @param eventPackage
     * @param keysPressed
     * @param otherInstances
     * @param myTotalTime
     */
    public void evaluateEventPackage(Instance instance, EventPackage eventPackage, List<KeyCode> keysPressed, List<Instance> otherInstances, double myTotalTime) {
        List<Condition> myConditions = eventPackage.getConditions();
        var myInputs = makeInputMap((Object)keysPressed, (Object)otherInstances, (Object) myTotalTime);
        boolean result = true;
        for(Condition condition : myConditions){
            String conditionType = condition.getClass().getSimpleName();
            result = result && myChecks.get(conditionType).evaluateConditions(instance, condition, myInputs.get(conditionType));
        }
        if(result){
            for(Action action : eventPackage.getActions()){
                List<Instance> instanceToAffect = new ArrayList<>();
                if(action.getEntityToAffect().equals("MYSELF")){
                    instanceToAffect.add(instance);
                }
                else {
                    instanceToAffect = getInstances(action.getEntityToAffect(), otherInstances);

                }
                executeAction(instanceToAffect, action);
            }
        }
    }

    private List<Instance> getInstances(String id, List<Instance> otherInstances){
        List<Instance> instances = new ArrayList<>();
        for(Instance myInstance : otherInstances){
            if(id.equals(myInstance.getEntityType())){
                instances.add(myInstance);
            }
        }
        return instances;
    }


    private Object classFactory(String path){
        try {
            Class myClass = Class.forName(path);
            return  myClass.getConstructor().newInstance();
        }
        catch (NoSuchMethodException e){
            System.out.println("no such method");
        }
        catch(ClassNotFoundException e){
            System.out.println("class not found");
        }
        catch(IllegalAccessException e){
            System.out.println("illegal Access exception");
        }
        catch(Exception e){
            System.out.println("i dont know why");
        }
        return null;
    }


    private Map<String, Object> makeInputMap(Object keysPressed, Object otherInstances, Object myTime){
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("KeyInputCondition", keysPressed);
        myMap.put("PropertyCondition", otherInstances);
        myMap.put("CollisionCondition", otherInstances);
        myMap.put("TimeCondition", myTime);
        return myMap;
    }
    private void executeAction(List<Instance> instances, Action toBeExecuted){
        myActions.get(toBeExecuted.getClass().getSimpleName()).execute(instances, toBeExecuted);
    }
}
