package exception;

public class DuplicateEntityInstanceIdException extends RuntimeException {
    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public DuplicateEntityInstanceIdException() {
        super(String.format("Duplicate instance id!"));
    }
}
