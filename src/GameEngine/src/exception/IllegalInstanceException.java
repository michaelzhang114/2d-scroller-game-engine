package exception;

public class IllegalInstanceException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public IllegalInstanceException() {
        super(String.format("Instance not found!"));
    }
}
