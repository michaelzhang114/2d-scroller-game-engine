package playingGame.engines.actionEngine;

import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import playingGame.Instance;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author michael zhang, Diego Chamorro
 */

public class NumericalActionEngine extends ActionEngine {
    private ScriptEngine engine;

    /**
     * constructor
     */
    public NumericalActionEngine(){
        engine = new ScriptEngineManager().getEngineByName("groovy");
    }


    /**
     * @param instances
     * @param myAction
     * This executes an action on a list of instances
     *
     */
    public void execute(List<Instance> instances, Action myAction) {

        for(Instance affected : instances){
            executeActionOn(affected,myAction);
        }

    }

    public void executeActionsOn(Instance affected, ArrayList<Action> actions){
        for(Action a:actions){
            executeActionOn(affected,a);
        }

    }

    /**
     * @param affected
     * @param myAction
     *
     * Using groovy, we run the the action on an instance.
     */
    public void executeActionOn(Instance affected, Action myAction){
        NumericalAction numAction = (NumericalAction) myAction;
        String propertyToBeModified = myAction.getPropertyToBeModified();
        String stringPropertyToBeModifiedValue = affected.getValueOfProperty(propertyToBeModified); //MAP CONTAINS STRINGS
        double propertyToBeModifiedValue = Double.parseDouble(stringPropertyToBeModifiedValue);
        engine.put("propertyModified", propertyToBeModifiedValue);
        engine.put("amount", Double.parseDouble(myAction.getValueToUse()));
        try {
            engine.eval("propertyModified" + numAction.getMyOperator() + "amount");
        } catch (ScriptException actionFail) {
            System.out.println("failed in action");
        }
        Object newProperty = engine.get("propertyModified");
        String newPropertyValue = newProperty.toString();
        affected.setValueToProperty(propertyToBeModified, newPropertyValue);
    }


}
