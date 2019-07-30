package Screens;

import Accounts.CreateValidator;
import Database.DBAccounts;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * The class controls the display of the screen that allows users to create accounts to be stored in the database. This
 * class provides fields for user input and, as a result, must have some error checking to ensure user input is valid.
 * This is done via an external class (CreateValidator) that does the backend work of checking user input to ensure it
 * is valid. This class simply controls the display and provides a place for the user to provide their account information.
 *
 * @author Eric Werbel
 */
public class CreateAccountScreen extends SceneDisplay {

    public static final String USERNAME_LABEL = "Username: ";
    public static final String DISPLAYNAME_LABEL = "Display Name (optional): ";
    public static final String EMAIL_LABEL = "Email: ";
    public static final String PASSWORD_LABEL = "Password: ";
    public static final String CONFIRMPASSWORD_LABEL = "Confirm Password: ";

    private Scene myScene;
    private BorderPane myRoot = new BorderPane();
    private TextField username = new TextField();
    private TextField displayName = new TextField();
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField confirmPassword = new PasswordField();
    private CreateValidator myValidator = new CreateValidator();

    private Consumer<Scene> myBackLambda;
    private Consumer<Scene> myCreatedLambda;

    private DBAccounts acctsDB = new DBAccounts();

    /**
     * Initializes the screen with the fields allowing for user input.
     *
     * @param style = a String corresponding to the file path of the desired stylesheet
     */
    public CreateAccountScreen(String style) {
        myScene = buildScene();
        myScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
    }

    @Override
    protected Scene buildScene() {
        VBox createAcc = createFields();
        HBox buttonArea = makeButtonArea();
        myRoot.setCenter(createAcc);
        myRoot.setBottom(buttonArea);
        return new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT);
    }

    /**
     * @return the create account screen
     */
    public Scene getScene() {
        return myScene;
    }

    private VBox createFields() {
        VBox fields = new VBox();
        fields.getChildren().addAll(
                new Label(USERNAME_LABEL), username,
                new Label(DISPLAYNAME_LABEL), displayName,
                new Label(EMAIL_LABEL), email,
                new Label(PASSWORD_LABEL), password,
                new Label(CONFIRMPASSWORD_LABEL), confirmPassword);
        return fields;
    }

    private HBox makeButtonArea() {
        HBox buttons = new HBox();
        Button back = makeButton("Back", event -> myBackLambda.accept(myScene));
        Button create = makeButton("Create Account", event -> createAccount());
        buttons.getChildren().addAll(back, create);
        return buttons;
    }

    private void createAccount() {
        if (myValidator.isValid(username, displayName, email, password, confirmPassword)) {
            acctsDB.createAccount(username.getText(),password.getText(),email.getText(),displayName.getText());
            myCreatedLambda.accept(myScene);
        }
    }

    /**
     * Sets up the lambda to control what happens when the back button is selected.
     *
     * @param backLambda = a consumer that will set the scene of the current window to the previous scene
     */
    public void setBackLambda(Consumer<Scene> backLambda) {
        myBackLambda = backLambda;
    }

    /**
     * Sets the lambda that controls what happens when an account is created successfully
     *
     * @param createdLambda = a consumer that will set the scene of the window to the MainMenu screen
     */
    public void setCreatedLambda(Consumer<Scene> createdLambda) {
        myCreatedLambda = createdLambda;
    }

}
