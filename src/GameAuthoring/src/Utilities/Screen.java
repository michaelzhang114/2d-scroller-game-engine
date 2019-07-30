package Utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

abstract public class Screen {
    private BorderPane myRoot;
    private Stage myStage;
    private Scene myScene;
    private static final String TITLE = "Mighty-Dux Game Maker";
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1280;
    private static final Color BACKGROUND_COLOR = Color.GRAY;

    private BorderPane myTopHBox;
    private BorderPane myBottomHBox;

    public Screen(Stage stage){
        myRoot = new BorderPane();
        myStage = stage;
        myScene = new Scene(myRoot, WIDTH, HEIGHT, BACKGROUND_COLOR);
        myScene.getStylesheets().add("styling.css");
        myStage.setTitle(TITLE);
        myTopHBox = makeHBox();
        myTopHBox.setId("top-hbox");
        myBottomHBox = makeHBox();
        myBottomHBox.setId("bottom-hbox");
        makePanes();
    }


    protected void makePanes(){
        myRoot.setTop(myTopHBox);
        myRoot.setBottom(myBottomHBox);
    }

    protected BorderPane makeHBox(){
        BorderPane box = new BorderPane();
        return box;
    }

    protected Button makeButton(String text, EventHandler<ActionEvent> handler) {
        var button = new Button(text);
        button.setOnAction(handler);
        return button;
    }

    protected BorderPane getTopBox(){
        return myTopHBox;
    }

    protected BorderPane getBottomBox(){
        return myBottomHBox;
    }

    public Scene getScene() { return myScene; }

    public Stage getStage() { return myStage; } //FIXME: DELETE?

    //public void setScene() { myStage.setScene(myScene);}

    protected BorderPane getRoot() { return myRoot; }

    public void update(){}

}
