package exception;

public class IllegalEventPackageException extends RuntimeException{
    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public IllegalEventPackageException() {
        super(String.format("no event package!"));
    }
}
