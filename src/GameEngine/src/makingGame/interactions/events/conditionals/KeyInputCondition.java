package makingGame.interactions.events.conditionals;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;


//keyinput condition that handles just one condition
//right now, not worrying abt combination first
//fsm could potentially just take care of that
public class KeyInputCondition {

    private Conditionals conditional;

    //the signature of a keyinputcondition is
    //keyinput type, keycode and state
    public static final String type="KeyInput";
    private KeyCode myKeyCode;
    private String state="Nothing";


    public KeyInputCondition(KeyCode code) {

        myKeyCode = code;

        conditional=makeConditional(code,state);
//        conditional=new Conditionals(type);
//        conditional.addSignature(myKeyCode.getName());
//        conditional.addSignature(state);
        //since references are passed, conditional is always up to date,
        //i.e. no need to set the arraylist
    }

    /**
     * static method that makes a keyinput condition
     * assume keycode not null
     * @param code
     * @param state
     * @return
     */
    public static Conditionals makeConditional(KeyCode code,String state){
        Conditionals c=new Conditionals(type);
        c.addSignature(code.getName());
        c.addSignature(state);

        return c;
//        return new KeyInputCondition(code).conditional;
    }

    //state can only be pressed, released, nothing
    //to do: make this an enum
    public void changeState(String state){
        this.state=state;
    }

    public Conditionals getConditional(){
        return conditional;
    }



}
