package Screens;

import Accounts.MakeAlert;
import external.GamePlaying;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * The class controls the screen that is pulled up when the user wants to run a game from the GameEngine. This class
 * depends on the GameEngine module and the API that it exports to the Player module. The main purpose of this class is
 * to display a game that is being rendered by the GameEngine. Additionally, a pause button was added that allows users to
 * stop the game and continue, save, or quit.
 *
 * @author Eric Werbel
 */
public class GamePlayScreen extends SceneDisplay {

    public static final String LOADING = "Loading Game...";
    public static final String PAUSE = "Pause";
    public static final String CONTINUE = "Continue";
    public static final String SAVE = "Save";
    public static final String QUIT = "Quit";

    public static final String SAVED_TITLE = "Saved";
    public static final String SAVED_HEADING = "Save Successful";
    public static final String SAVED_CONTENT = "Your game has been saved successfully.";

    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";

    private BorderPane myRoot;
    private String myStyle;
    private Scene gameScene;

    private Consumer<Scene> myUpdateLambda;
    private Consumer<Scene> myQuitLambda;

    /**
     * Constructor for the game lobby screen.
     *
     * @param style = path to the stylesheet (required for the pause menu)
     */
    public GamePlayScreen(String style) {
        myStyle = style;
    }

    @Override
    protected Scene buildScene() {
        myRoot = new BorderPane();
        Label loading = new Label(LOADING);
        myRoot.setCenter(loading);
        return new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT);
    }

    /**
     * Calls various methods from the API passed into the Player module from Engine to render and display a game on the
     * stage. Additionally, a pause button is added that will enable users to pause a game and quit/restart.
     *
     * @param game = the name of the game the user wants to play
     */
    public void runGame(String game) {
        myUpdateLambda.accept(buildScene());
        game.replaceAll(SPACE, UNDERSCORE);
        GamePlaying myEngine = new GamePlaying(game);
        myEngine.loadGame();
        gameScene = myEngine.getScene();
        Group g = (Group) gameScene.getRoot();
        Button pause = makeButton(PAUSE, e->pause(myEngine));
        g.getChildren().add(pause);
        myUpdateLambda.accept(gameScene);
        myEngine.runGame();
        myEngine.continueGame();
    }

    private void pause(GamePlaying myEngine) {
        myEngine.pauseGame();
        Stage pauseStage = new Stage();
        BorderPane pauseRoot = new BorderPane();
        VBox options = makePauseOptions(pauseStage, myEngine);
        pauseRoot.setCenter(options);
        Scene pauseScene = new Scene(pauseRoot);
        pauseScene.getStylesheets().add(getClass().getResource(myStyle).toExternalForm());
        pauseStage.setScene(pauseScene);
        pauseStage.show();
    }

    private VBox makePauseOptions(Stage s, GamePlaying myEngine) {
        VBox options = new VBox();
        Button continueGame = makeButton(CONTINUE, e->continueGame(s, myEngine));
        Button saveGame = makeButton(SAVE, e->saveGame(s, myEngine));
        Button quitGame = makeButton(QUIT, e->quitGame(s, myEngine));
        options.getChildren().addAll(continueGame, saveGame, quitGame);
        return options;
    }

    private void continueGame(Stage s, GamePlaying myEngine) {
        s.close();
        myEngine.continueGame();
    }

    private void saveGame(Stage s, GamePlaying myEngine) {
        myEngine.saveGame();
        MakeAlert myAlert = new MakeAlert();
        myAlert.createAndShow(SAVED_TITLE, SAVED_HEADING, SAVED_CONTENT);
    }

    private void quitGame(Stage s, GamePlaying myEngine) {
        s.close();
        myQuitLambda.accept(myEngine.getScene());
    }

    /**
     * Sets the lambda to initialize the GamePlay screen.
     *
     * @param updateLambda = consumer that will set the current screen to a placeholder "Loading" screen while the game loads
     */
    public void setUpdateScreenLambda(Consumer<Scene> updateLambda) {
        myUpdateLambda = updateLambda;
    }

    /**
     * Sets up the lambda that allows users to quit the game and return to the main menu of the project where they
     * can select a new game to play.
     *
     * @param quitLambda = consumer that will update the window to display the main menu screen
     */
    public void setQuitLambda(Consumer<Scene> quitLambda) { myQuitLambda = quitLambda; }
}
