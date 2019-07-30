package playingGame.engines.conditionCheckers;

import makingGame.interactions.events.Conditions.Condition;
import playingGame.Instance;

public abstract class ConditionCheck {

    /**
     *
     * @param mySelf
     * @param condition
     * @param otherObjects
     * @return
     *
     * Evaluates whatever condition passed into this engine.
     */
    public abstract boolean evaluateConditions(Instance mySelf, Condition condition, Object otherObjects);
}
