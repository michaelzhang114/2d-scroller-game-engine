package makingGame.interactions.events.conditionals;


public class CollisionCondition {

    private Conditionals conditional;

    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String RIGHT = "right";
    public static final String LEFT = "left";
    public static final String ANY = "any";
    //the signature of a keyinputcondition is
    //keyinput type, keycode and state
    public static final String type="Collision";

    public CollisionCondition(){

    }

    /**
     * static method that makes a collision condition
     * @param entityID
     * @param side
     * @return
     */
    public static Conditionals makeConditional(String entityID, String side){
        Conditionals c=new Conditionals(type);
        c.addSignature(entityID);
        c.addSignature(side);

        return c;
    }
}
