package exception;

public class IllegalLevelException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public IllegalLevelException() {
        super(String.format("Level not found!"));
    }
}
