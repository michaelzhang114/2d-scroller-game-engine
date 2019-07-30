package makingGame.interactions.events.Conditions;

import makingGame.interactions.events.Conditions.Condition;

import java.util.List;


public class KeyInputCondition extends Condition {

    /**
     * constructor of keyinputcondition
     * @param codes
     */
    public KeyInputCondition(List<String> codes) {
        myConditions.put("KeyCodes", codes);
        myConditions.put("Inverse", false);
    }

    /**
     * set a keycode condtiion to be inverse
     */
    public void setInverse(){
        myConditions.put("Inverse", true);
    }





}
