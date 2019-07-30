package playingGame.engines.conditionCheckers;

import makingGame.interactions.events.Conditions.Condition;
import playingGame.Instance;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

public class PropertyConditionCheck extends ConditionCheck {
     private ScriptEngine engine;


    /**
     * constructor
     */
    public PropertyConditionCheck() {
        engine = new ScriptEngineManager().getEngineByName("groovy");
    }

    @Override
    /**
     *
     * @param mySelf
     * @param condition
     * @param otherObjects
     * @return
     *
     * Evaluates whatever condition passed into this engine.
     */
    public boolean evaluateConditions(Instance mySelf, Condition condition, Object otherObjects) {
        List<Instance> otherInstances = (List<Instance>) otherObjects;
        if(condition.getCondition("EntityID").equals("MYSELF")){
            return checkPropertyCondition(mySelf, condition);
        }
        else {
            for (Instance otherInstance : otherInstances) {
                String otherEntityId = otherInstance.getEntityType();
                if (otherEntityId.equals(condition.getCondition("EntityID"))) {
                    return checkPropertyCondition(otherInstance, condition);
                }
            }
        }
        return false;
    }

    /**
     * Check property condition for myinstance
     * @param myInstance
     * @param propertyCondition
     * @return
     */
    public boolean checkPropertyCondition(Instance myInstance, Condition propertyCondition) {
        String myPropertyToCompare = (String) propertyCondition.getCondition("PropertyToCompare");
        if((Boolean) propertyCondition.getCondition("StringType")){
            System.out.println(myInstance.getValueOfProperty(myPropertyToCompare));
            return (myInstance.getValueOfProperty(myPropertyToCompare) == propertyCondition.getCondition("Amount"));

        }


        Double value1 = Double.parseDouble(myInstance.getValueOfProperty(myPropertyToCompare));
        Double value2 = (Double) propertyCondition.getCondition("Amount");
        String myOperator = (String) propertyCondition.getCondition("Operator");
        engine.put("valOne", value1);
        engine.put("valTwo", value2);
        try {engine.eval("answer = valOne" + myOperator + "valTwo"); }
        catch (ScriptException conditionError) {
            System.out.println("evaluate conditions failed");
        }
        Object answer = engine.get("answer");
        boolean output = (Boolean) answer;
        return output;
    }
}
