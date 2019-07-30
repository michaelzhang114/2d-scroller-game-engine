package exception;

public class IllegalScreenSizeException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public IllegalScreenSizeException() {
        super(String.format("width and height are zero!!"));
    }
}
