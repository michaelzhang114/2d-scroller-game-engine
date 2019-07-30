package exception;

public class IllegalPropertyException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public IllegalPropertyException() {
        super(String.format("Property not found!"));
    }
}
