package Screens.SplashScreen;

import Utilities.Screen;
import Manager.ScreenManager;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Class defines the Splash Screen within the Game Authoring Environment. Allows user to Make a New Game or Load
 * a Previous Game. Has Buttons that lead you to other screens once you click on a any specific button.
 */


public class SplashScreen extends Screen {
    private SplashScreenController myController;
    private File myFile;

    private static final String BUTTON1_TEXT = "New Game";
    private static final String BUTTON2_TEXT = "Load Game";


    private static final String WELCOME_TEXT = "Welcome to Mighty - \n Dux Game Maker!";


    public SplashScreen(ScreenManager manager, SplashScreenController controller) {
        super(manager.getStage());
        myController = controller;
        getRoot().setCenter(makeCenterPane());
        getRoot().setId("splash-screen-root");
    }


    private Node makeCenterPane() {
        VBox cp = new VBox();
        cp.setId("splash-screen-center-pane");
        Label t = new Label(WELCOME_TEXT);
        t.setId("welcome-label");
        cp.getChildren().add(t);
        cp.getChildren().addAll(makeButton(BUTTON1_TEXT, e -> myController.addGameDescription()),
            makeButton(BUTTON2_TEXT, e -> loadGameFromFile(getStage())));
        return cp;
    }

    private void loadGameFromFile(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load a Game to Continue Editing");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        myFile = chooser.showOpenDialog(stage);
        if(myFile == null) return;
        //TODO : ACTUALLY LOAD THE FILE INTO THE GAME. (For now, its only being stored in myFile Instance Variable)
    }

}
