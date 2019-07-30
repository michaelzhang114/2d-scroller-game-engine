package makingGame.interactions.events.Conditions;

import makingGame.interactions.events.Conditions.Condition;

public class CollisionCondition extends Condition {

    /**
     * constructor of a collision condition
     * @param entityID
     * @param side
     */
    public CollisionCondition(String entityID, String side) {
        myConditions.put("otherEntity", entityID);
        myConditions.put("side", side);
    }

}
