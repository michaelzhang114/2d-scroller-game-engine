/**
 * This class constructs the lobby area where games can be played from. It displays information relevant to each game such as the title and instructions. It also displays buttons used for navigation through the various player panes and one button to play the game for the current lobby. This class extends SceneDisplay and depends on the methods in that superclass for reading files and making buttons.
 * @author Connor Ghazaleh
 */

package Screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class GameLobbyScreen extends SceneDisplay {

    private Scene myScene;
    private BorderPane myRoot = new BorderPane();
    private String myGame;
    private ImageView myGameImage;
    private String myGameTitle;
    private String myGameInstructions;
    private String projectRoot = System.getProperty("user.dir");
    private String gamesDirectoryPath = "/data/games/";
    private String gameIcon = "/icon.png";
    private String gameInst = "/instructions.txt";

    private static final int BACKPLAYSPACING = 368;
    private static final int TITLEFONT = 30;
    private static final Insets INSETS = new Insets(20,20,20,20);
    private static final int GAME_ICON_HEIGHT = 200;

    private Consumer<Scene> myBackLambda;
    private Consumer<String> myPlayLambda;

    public GameLobbyScreen(String style) {
        myScene = buildScene();
        myScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
    }

    @Override
    protected Scene buildScene() {
        Button back = makeButton("Back", event -> myBackLambda.accept(myScene));
        Button play = makeButton("Play", event -> myPlayLambda.accept(myGame));
        HBox backAndPlay = new HBox();
        backAndPlay.setSpacing(BACKPLAYSPACING);
        backAndPlay.setPadding(INSETS);
        backAndPlay.getChildren().addAll(back,play);
        myRoot.setBottom(backAndPlay);
        return new Scene(myRoot, SCENE_WIDTH,SCENE_HEIGHT);
    }

    /**
     * Returns the current scene
     * @return
     */
    public Scene getScene() {
        return myScene;
    }

    /**
     * Sets lambda function to transition to playing the game when play button is clicked
     * @param playLambda
     */
    public void setPlayLambda(Consumer<String> playLambda) {
        myPlayLambda = playLambda;
    }

    /**
     * Sets lambda function to transition to go back to the previous screen when back button is clicked
     * @param backLambda
     */
    public void setBackLambda(Consumer<Scene> backLambda) {
        myBackLambda = backLambda;
    }

    /**
     * Updates the lobby scene with information specific to game identified by gameName parameter
     * @param gameName
     */
    public void updateGameLobby(String gameName){
        myGame = gameName;
        System.out.println(myGame);
        getImage(myGame);
        getInstructions(myGame);
        getTitle();
        Label gameTitle = new Label(myGameTitle);
        gameTitle.setFont(new Font(TITLEFONT));
        VBox titleAndImage = new VBox();
        titleAndImage.setPadding(INSETS);
        titleAndImage.setSpacing(20);
        titleAndImage.setAlignment(Pos.CENTER_LEFT);
        titleAndImage.getChildren().addAll(myGameImage,gameTitle);
        myRoot.setTop(titleAndImage);
        Label gameInstructions = new Label(myGameInstructions);
        gameInstructions.setFont(new Font(17));
        gameInstructions.wrapTextProperty().setValue(true);
        VBox descriptionAndPlayButton = new VBox();
        descriptionAndPlayButton.getChildren().addAll(gameInstructions);
        descriptionAndPlayButton.setPadding(INSETS);
        myRoot.setCenter(descriptionAndPlayButton);
    }

    private void getImage(String game){
        String imagePath = projectRoot+gamesDirectoryPath+game+gameIcon;
        try {
            myGameImage = new ImageView(new Image(new FileInputStream(imagePath)));
            myGameImage.setFitHeight(GAME_ICON_HEIGHT);
            myGameImage.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            System.out.println("Could not retrieve game icon");
        }

    }

    private void getInstructions(String game){
        myGameInstructions = super.readFile(projectRoot+gamesDirectoryPath+game+gameInst);
    }


    private void getTitle(){
        myGameTitle = myGame.replaceAll("_"," ");
    }


}
