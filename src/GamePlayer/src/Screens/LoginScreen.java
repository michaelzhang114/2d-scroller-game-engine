package Screens;

import Accounts.EmailBot;
import Accounts.ErrorHandler;
import Accounts.MakeAlert;
import Database.DBAccounts;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * This class displays the user where they can add their username and password to login to their account. This class relies
 * on the database to validate the user's input to check that it corresponds to a valid account. Additionally, if the user
 * forget their password, he or she can click the forgot password button and enter their username. This will retrieve the
 * password associated with the user's account and send it to the email associated with their account.
 *
 * @author Eric Werbel
 */
public class LoginScreen extends SceneDisplay {

    public static final String USERNAME = "Username: ";
    public static final String PASSWORD = "Password: ";

    public static final String FORGOT_PASS_TITLE = "Forgot Password";
    public static final String FORGOT_PASS_HEADER = "Enter username";
    public static final String FORGOT_PASS_CONTENT = "Please enter your username: ";

    public static final String LOGIN_SUCC_TITLE = "Success!";
    public static final String LOGIN_SUCC_HEAD = "Login Successful";
    public static final String LOGIN_SUCC_CONTENT = "You have successfully logged in.";

    private Scene myScene;
    private BorderPane myRoot = new BorderPane();
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private EmailBot myBot = new EmailBot();
    private DBAccounts acctsDB = new DBAccounts();
    private MakeAlert myAlert = new MakeAlert();

    public static final String LOGIN_ERROR = "invalidLogin";
    private ErrorHandler myErrorHandler = new ErrorHandler();

    private Consumer<Scene> myLoginLambda;
    private Consumer<Scene> myBackLambda;

    /**
     * Constructs the class and initializes the login screen
     *
     * @param style = path to the desired stylesheet for this screen
     */
    public LoginScreen(String style) {
        myScene = buildScene();
        myScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
    }

    @Override
    protected Scene buildScene() {
        HBox buttons = createButtons();
        VBox fields = createFields();
        fields.getChildren().add(buttons);
        myRoot.setCenter(fields);
        Button back = makeButton("Back", event -> myBackLambda.accept(myScene));
        myRoot.setBottom(back);
        return new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT);
    }

    /**
     * @return the scene that displays the login screen
     */
    public Scene getScene() {
        return myScene;
    }

    private VBox createFields() {
        VBox fields = new VBox();
        fields.getChildren().addAll(new Label(USERNAME), username, new Label(PASSWORD), password);
        return fields;
    }

    private HBox createButtons() {
        HBox buttons = new HBox();
        Button forgotPass = makeButton("Forgot Password", event -> forgotPassDialog());
        Button login = makeButton("Login", event -> tryLogin());
        buttons.getChildren().addAll(forgotPass, login);
        return buttons;
    }

    private void tryLogin() {
        String userTxt = username.getText();
        String passTxt = password.getText();
        if (acctsDB.authenticate(userTxt, passTxt)) {
            setActiveUser(acctsDB.retrieveAccount(userTxt));
            username.clear();
            password.clear();
            myAlert.createAndShow(LOGIN_SUCC_TITLE, LOGIN_SUCC_HEAD, LOGIN_SUCC_CONTENT);
            myLoginLambda.accept(myScene);
        } else {
            myErrorHandler.showError(LOGIN_ERROR);
        }
    }

    private void forgotPassDialog() {
        TextInputDialog myDialog = new TextInputDialog();
        myDialog.setTitle(FORGOT_PASS_TITLE);
        myDialog.setHeaderText(FORGOT_PASS_HEADER);
        myDialog.setContentText(FORGOT_PASS_CONTENT);
        Optional<String> email = myDialog.showAndWait();
        email.ifPresent(event -> myBot.sendPassword(email.get()));
    }

    /**
     * Sets the lambda to be called after a successful login that will take the user back to the main menu
     *
     * @param loginLambda = consumer that will update the window to display the main menu screen
     */
    public void setLoginLambda(Consumer<Scene> loginLambda) {
        myLoginLambda = loginLambda;
    }

    /**
     * Sets the lambda to take the user back to the previous screen.
     *
     * @param backLambda = consumer that will take the user back to the previous screen
     */
    public void setBackLambda(Consumer<Scene> backLambda) {
        myBackLambda = backLambda;
    }

}

