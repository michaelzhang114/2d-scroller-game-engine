package exception;

public class IllegalEntityException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public IllegalEntityException() {
        super(String.format("Entity not found!"));
    }
}
