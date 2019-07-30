package playingGame.engines.conditionCheckers;


import makingGame.interactions.events.Conditions.Condition;
import playingGame.Instance;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TimeConditionCheck extends ConditionCheck {
    private ScriptEngine engine;

    public TimeConditionCheck(){
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
    public boolean evaluateConditions(Instance mySelf, Condition condition, Object otherObjects){
        String operator = (String) condition.getCondition("operator");
        Integer conditionTime = (Integer) condition.getCondition("TimeAmount");
        return checkTimeCondition(operator, conditionTime, (Integer) otherObjects);
    }

    private boolean checkTimeCondition(String operator, Integer conditionTime, Integer totalTime){
        String myOperator =  operator;
        Integer myConditionTime =  conditionTime;
        engine.put("totalTime", totalTime);
        engine.put("conditionTime", conditionTime);
        if(myOperator.equals("%")){
            try {
                engine.eval("answer = totalTime" + myOperator + "conditionTime");
                engine.eval("answer = (answer == 0)");

            }
            catch (ScriptException conditionError) {
                return false;
            }
        }
        else {
            try {engine.eval("answer = conditionTime" + myOperator + "totalTime"); }
            catch (ScriptException conditionError) {
                return false;
            }
        }

        Object answer = engine.get("answer");
        boolean output = (Boolean) answer;
        return output;
    }



}
