package playingGame.engines;

import makingGame.interactions.events.conditionals.Conditionals;
import makingGame.interactions.events.conditionals.KeyInputCondition;
//import interactions.events.KeyInputCondition;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * @author: yao yuan
 */

public class KeyInputHandler {
    //really want to have a map of keycodes with states as their condition
    //and a map to corresponding conditionals
    //other wise there could be concurrency
    public HashSet<Conditionals> keycode_conditions;

    public KeyInputHandler(){
        keycode_conditions=new HashSet<>();
    }

    //the new part is bad
    //to be changed later

    /**
     *
     * @param code
     * handles keycodes
     */
    public void handleKeyPressedInput (KeyCode code) {
        keycode_conditions.add(KeyInputCondition.makeConditional(code,"pressed"));

    }

    /**
     *
     * @param code
     * handles when the keys are released.
     */
    public void handleKeyReleasedInput (KeyCode code) {
        keycode_conditions.add(KeyInputCondition.makeConditional(code,"released"));

    }
}
