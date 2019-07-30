package exception;

public class DuplicateLevelControllerException extends RuntimeException {
    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public DuplicateLevelControllerException() {
        super(String.format("duplicate level controller!"));
    }
}
