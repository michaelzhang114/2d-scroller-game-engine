package playingGame.engines.conditionCheckers;


import javafx.scene.input.KeyCode;
import makingGame.interactions.events.Conditions.Condition;
import playingGame.Instance;

import java.util.ArrayList;
import java.util.List;

public class KeyInputConditionCheck extends ConditionCheck {
    public KeyInputConditionCheck() {

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
        List<Object> inputCodes = (List<Object>) otherObjects;
        List<KeyCode> codes = new ArrayList<>();
        List<String> stringCodes = (ArrayList<String>) condition.getCondition("KeyCodes");
        for (String key : stringCodes){
            KeyCode temp = KeyCode.getKeyCode(key);
            codes.add(temp);
        }
        return checkInputs(codes, (Boolean) condition.getCondition("Inverse"), inputCodes);
    }

    private boolean checkInputs(List<KeyCode> codesToCheck, Boolean inverse,  List<Object> inputCodes){
        if(inputCodes.size() == 0){ //empty that means that the this condition returns true, since no keys were pressed
            return inverse;
        }

        boolean result = true;
        for(KeyCode code : codesToCheck){
            result = result && inputCodes.contains(code);
        }

        if(inverse) return !result;
        return result;

    }

}
