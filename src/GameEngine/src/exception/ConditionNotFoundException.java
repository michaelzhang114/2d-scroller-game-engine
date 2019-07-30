package exception;

public class ConditionNotFoundException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public ConditionNotFoundException() {
        super(String.format("Condition not found!"));
    }
}
