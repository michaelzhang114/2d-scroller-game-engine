package exception;

public class MainCharacterNotFoundException extends RuntimeException{

    /**
     * Create an exception based on an issue in our code.
     * @param
     */
    public MainCharacterNotFoundException() {
        super(String.format("main character not found!"));
    }
}
