package Accounts;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class simply displays an alert with a specific error message every time an exception is thrown.
 * It assumes that the reason for the error will be known by the class that throws the error and takes this in as a string.
 * If the error handler class does not have information regarding the type of error thrown, a general error message is displayed.
 * A new instance of this class is created every time an error is thrown.
 *
 * @author Eric Werbel
 */
public class ErrorHandler {

    public static final String ERROR_TITLE = "Invalid Input!";
    public static final String DEFAULT_TYPE = "Unknown Error";
    public static final String DEFAULT_DESCRIPTION = "The source of your error is unknown.";

    private String myFile = "Errors.properties";

    public ErrorHandler() {

    }

    /**
     * Creates an Alert to let the user know that there was an error.
     * Provides feedback as to the type of error so it can be corrected by the user.
     *
     * @param errorType = String determining what type of error message to display
     */
    public void showError(String errorType) {
        Alert myError = new Alert(Alert.AlertType.ERROR);
        myError.setTitle(ERROR_TITLE);
        try {
            determineErrorText(myError, errorType);
        } catch (IOException e) {
            myError.setHeaderText(DEFAULT_TYPE);
            myError.setContentText(DEFAULT_DESCRIPTION);
        }
        myError.showAndWait();
    }

    private void determineErrorText(Alert myError, String errorType) throws IOException {
        Properties errorProperties = new Properties();
        InputStream ip = this.getClass().getClassLoader().getResourceAsStream(myFile);
        errorProperties.load(ip);
        String val = errorProperties.getProperty(errorType);
        String [] myMessage = val.split("-");
        myError.setHeaderText(myMessage[0]);
        myError.setContentText(myMessage[1]);
    }

}
