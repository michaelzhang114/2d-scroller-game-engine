package Accounts;

import Database.DBAccounts;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class handles the backend error checking for when a user tries to create an account. It takes the user input from
 * the CreateAccountScreen class and checks it against a set of given parameters to ensure the data is valid. This class
 * represents the backend word from the create account screen class to error check. This class depends on the user data
 * that is input into the CreateAccountScreen class.
 *
 * @author Eric Werbel
 */
public class CreateValidator {

    public static final String NO_USERNAME_ERROR = "noUsername";
    public static final String USERNAME_EXISTS_ERROR = "usernameExists";
    public static final String NO_EMAIL_ERROR = "noEmail";
    public static final String INVALID_EMAIL_ERROR = "invalidEmail";
    public static final String SHORT_PASSWORD = "shortPass";
    public static final String MISMATCH_PASSWORD = "mismatchPass";

    public static final String CONFIRM_TITLE = "Account Created";
    public static final String CONFIRM_HEADER = "Success!";
    public static final String CONFIRM_MESSAGE = "Your account has been successfully created.";

    public static final int MIN_PASSWORD_LEN = 7;

    private ErrorHandler myErrorHandler = new ErrorHandler();
    private DBAccounts acctsDB = new DBAccounts();

    private MakeAlert myAlert = new MakeAlert();
    private boolean valid;

    public CreateValidator() {

    }

    /**
     * Checks if the user input is all valid for creating an account
     *
     * @param username = TextField where user inputs their username
     * @param displayName = TextField where user can input their display name
     * @param email = TextField where user can input their email address
     * @param password = TextField where user can input their password
     * @param confirmPassword = TextField where user can confirm their password
     * @return a boolean value indicating whether the account data is valid or not
     */
    public boolean isValid(TextField username, TextField displayName, TextField email, PasswordField password, PasswordField confirmPassword) {
        valid = true;
        checkUsername(username);
        checkEmail(email);
        checkPassword(password, confirmPassword);
        if (valid) {
            myAlert.createAndShow(CONFIRM_TITLE, CONFIRM_HEADER, CONFIRM_MESSAGE);
        }
        return valid;
    }

    private void checkUsername(TextField username) {
        String usernameTxt = username.getText();
        if (usernameTxt.length() == 0) {
            myErrorHandler.showError(NO_USERNAME_ERROR);
            valid = false;
        }else{
            valid = ! acctsDB.checkForAccount(usernameTxt);
            if (!valid){
                myErrorHandler.showError(USERNAME_EXISTS_ERROR);
            }
        }
    }

    private void checkEmail(TextField email) {
        String emailTxt = email.getText();
        if (emailTxt.length() == 0) {
            myErrorHandler.showError(NO_EMAIL_ERROR);
            valid = false;
        } else if (emailTxt.indexOf("@") == -1 || emailTxt.indexOf("@") == emailTxt.length()-1) {
            myErrorHandler.showError(INVALID_EMAIL_ERROR);
            email.clear();
            valid = false;
        }
    }

    private void checkPassword(PasswordField password, PasswordField confirmPassword) {
        String passwordTxt = password.getText();
        String confirmPasswordTxt = confirmPassword.getText();
        if (passwordTxt.length() < MIN_PASSWORD_LEN) {
            myErrorHandler.showError(SHORT_PASSWORD);
            password.clear();
            confirmPassword.clear();
            valid = false;
        } else if (! confirmPasswordTxt.equals(passwordTxt)) {
            myErrorHandler.showError(MISMATCH_PASSWORD);
            password.clear();
            confirmPassword.clear();
            valid = false;
        }
    }

}
