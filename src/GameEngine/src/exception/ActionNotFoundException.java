package exception;

public class ActionNotFoundException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public ActionNotFoundException() {
        super(String.format("Action not found!"));
    }
}
