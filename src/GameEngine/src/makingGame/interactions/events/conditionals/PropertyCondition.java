package makingGame.interactions.events.conditionals;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class PropertyCondition {

//    private EntityInstance myEntityAffected;



    //want to separate out the engine later
    //so that there's not so many copy of it
    //could just make it static
    private static ScriptEngine engine=new ScriptEngineManager().getEngineByName("groovy");

    private Conditionals conditional;

    //the signature of a property condition is
    //property condition type, property to compare, operator, amount
    public static final String type="PropertyCondition";
    //might want to be enum
    private String myPropertyToCompare;
    private String myOperator;
    private double myAmount;

    /**
     * constructor but not really needed
     * @param property1
     * @param operator
     * @param num
     */
    public PropertyCondition(String property1, String operator, double num) {
        super();
        myPropertyToCompare = property1;
        myOperator = operator;
        myAmount = num;
//        engine = new ScriptEngineManager().getEngineByName("groovy");

        conditional=makeConditional(property1,operator,num);
    }

    /**
     * static method that makes a property condition
     * @param property1
     * @param operator
     * @param num
     * @return
     */
    public static Conditionals makeConditional(String property1, String operator, double num){
        Conditionals conditional=new Conditionals(type);
        conditional.addSignature(property1);
        conditional.addSignature(operator);
        conditional.addSignature(Double.toString(num));

        return conditional;
    }

    public Conditionals getConditional(){
        return conditional;
    }

}
