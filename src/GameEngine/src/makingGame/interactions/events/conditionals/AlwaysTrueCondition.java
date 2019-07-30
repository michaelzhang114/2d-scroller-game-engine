package makingGame.interactions.events.conditionals;

public class AlwaysTrueCondition {

    public static final String type="AlwaysTrueCondition";
    public static final Conditionals conditional=new Conditionals(type);

    /**
     * static method that makes a always true condition
     * @return the made condition
     */
    public static Conditionals makeConditional(){
        Conditionals conditional=new Conditionals(type);

        return conditional;
    }
}
