package makingGame.interactions.events.Conditions;

import makingGame.interactions.events.Conditions.Condition;

public class PropertyCondition extends Condition {


    /**
     * constructor of a property condition
     * @param property1
     * @param operator
     * @param num
     */
    public PropertyCondition(String property1, String operator, double num) {
        myConditions.put("PropertyToCompare", property1);
        myConditions.put("Operator", operator);
        myConditions.put("EntityID", "MYSELF");
        myConditions.put("Amount", num);
        myConditions.put("StringType", false);
    }

    /**
     * constructor
     * @param entity
     * @param property1
     * @param operator
     * @param num
     */
    public PropertyCondition(String entity,String property1, String operator, double num) {
        myConditions.put("PropertyToCompare", property1);
        myConditions.put("Operator", operator);
        myConditions.put("EntityID", entity);
        myConditions.put("Amount", num);
        myConditions.put("StringType", false);

    }

    /**
     * constructor
     * @param property1
     * @param value
     */
    public PropertyCondition(String property1, String value){
        myConditions.put("PropertyToCompare", property1);
        myConditions.put("EntityID", "MYSELF");
        myConditions.put("Amount", value);
        myConditions.put("StringType", true);
    }

}
