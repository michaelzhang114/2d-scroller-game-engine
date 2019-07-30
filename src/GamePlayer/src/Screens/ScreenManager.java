package Screens;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class controls the transitions between screens within the project. A ScreenManager is initialized at the start
 * of the program and immediately sets up the lambdas the control screen transitions. This class assumes that all screens
 * will have the required methods to correctly set up the lambdas within each screen. This class does not depend on any
 * other classes as it is the one to set up and initialize all screens required in the project.
 *
 * The ScreenManager allows all screens to be independent from one another and prevents a hierarchical structure from emerging
 * among screens where one screen owns an instance of another. The ScreenManager also makes it easier to have only one instance
 * of every screen as all transitions are controlled from this class. Additionally, having all transitions in the same place
 * increases the flexibility of the overall code because if a new screen needs to be added, the only thing that needs to change
 * is the scene passed into the desired lambda.
 *
 * @author Eric Werbel
 */
public class ScreenManager {

    public static final String DEFAULT_STYLE = "/Stylesheets/Red.css";

    private Stage myStage;

    // Screens are initialized outside the constructor to avoid order of method call dependencies
    private MainMenuScreen mainMenu = new MainMenuScreen(DEFAULT_STYLE);
    private CreateAccountScreen createAcc = new CreateAccountScreen(DEFAULT_STYLE);
    private LoginScreen login = new LoginScreen(DEFAULT_STYLE);
    private GameLobbyScreen games = new GameLobbyScreen(DEFAULT_STYLE);
    private GamePlayScreen gamePlay = new GamePlayScreen(DEFAULT_STYLE);
    private AccControlScreen accHome = new AccControlScreen(DEFAULT_STYLE);
    private GameLobbyUpdater gameLobbyUpdater = new GameLobbyUpdater(games, mainMenu);

    private Scene oldScene;

    /**
     * Constructor for the ScreenManager class. All work done by the class is done in the constructor as the purpose of
     * the class is only to initialize all screens and control transitions. Once the lambdas are passed into each screen,
     * the ScreenManager does not need to do anything. The constructor sets up all lambdas necessary to control screen
     * transitions.
     *
     * @param stage = stage where all screens are displayed for the project
     */
    public ScreenManager(Stage stage) {
        myStage = stage;
        addMenuLambdas();
        addCreateAccLambdas();
        addLoginLambdas();
        addGameLobbyLambda();
        addGamePlayLambda();
        addAccHomeLambda();
        myStage.setScene(mainMenu.getScene());
    }

    private void addMenuLambdas() {
        mainMenu.setLoginLambda(scene -> switchScene(scene, login.getScene()));
        mainMenu.setCreateAccLambda(scene -> switchScene(scene, createAcc.getScene()));
        mainMenu.setGameDescLambda(scene -> switchScene(scene, games.getScene()));
        mainMenu.setAccHomeLambda(scene -> switchScene(scene, accHome.getScene()));
    }

    private void addCreateAccLambdas() {
        createAcc.setBackLambda(scene -> switchScene(scene, oldScene));
        createAcc.setCreatedLambda(scene -> switchScene(scene, mainMenu.getScene()));
    }

    private void addLoginLambdas() {
        login.setLoginLambda(scene -> switchScene(scene, mainMenu.getScene()));
        login.setBackLambda(scene -> switchScene(scene, oldScene));
    }

    private void addGameLobbyLambda() {
        games.setPlayLambda(string -> gamePlay.runGame(string));
        games.setBackLambda(scene -> switchScene(scene, oldScene));
    }

    private void addGamePlayLambda() {
        gamePlay.setUpdateScreenLambda(scene -> myStage.setScene(scene));
        gamePlay.setQuitLambda(scene->myStage.setScene(games.getScene()));
    }

    private void addAccHomeLambda() {
        accHome.setBackLambda(scene -> myStage.setScene(oldScene));
    }

    /**
     * Switches the scene that is displayed on the stage and  stores the old scene as an instance variable.
     *
     * @param prevScene = scene to be stores as the scene to go to if "Back" is pressed
     * @param scene = new scene to be displayed on the stage
     */
    private void switchScene(Scene prevScene, Scene scene) {
        myStage.setScene(scene);
        oldScene = prevScene;
    }
}
