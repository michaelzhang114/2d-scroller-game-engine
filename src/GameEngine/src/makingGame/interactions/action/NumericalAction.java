package makingGame.interactions.action;

public class NumericalAction extends Action {
    String myOperator;

    /**
     * constructor
     * numerical action is action that numerically changes certain property
     * @param property
     * @param operator
     * @param amount
     * @param entityToAffect
     */
    public NumericalAction(String property, String operator, String amount, String entityToAffect){
        super(property, amount, entityToAffect);
        myOperator = operator;
        if(!operator.equals("=")) {
            myOperator = operator + "=";
        }
    }

    /**
     *
     * @return the operator of the action
     */
    public String getMyOperator() {
        return myOperator;
    }
}
