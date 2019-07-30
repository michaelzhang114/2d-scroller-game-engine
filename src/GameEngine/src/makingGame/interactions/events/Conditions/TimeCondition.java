package makingGame.interactions.events.Conditions;

import makingGame.interactions.events.Conditions.Condition;

public class TimeCondition extends Condition {

    /**
     * constructor of a time condition
     * @param operator
     * @param num
     */
    public TimeCondition(String operator, Integer num){
            super();
            myConditions.put("operator", operator);
            myConditions.put("TimeAmount", num);
    }
}
