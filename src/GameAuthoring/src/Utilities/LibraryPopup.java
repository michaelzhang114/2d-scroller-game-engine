package Utilities;

import Screens.MapViewScreen.MapViewController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

abstract public class LibraryPopup {

    private static final int IMAGE_SIZE = 50;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;
    private static final int PADDING = 20;

    private Stage myStage;
    private MapViewController myController;

    private String boxStyle = "-fx-border-color: gray;\n -fx-border-width: 2;\n";
    private static final String FOLDER = "characters"+ File.separator;

    public LibraryPopup(Stage stage, MapViewController controller) {
        myStage = stage;
        myController = controller;
    }

    protected void formatHBox(HBox box){
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(PADDING));
    }

    protected void formatVBox(VBox box){
        box.setAlignment(Pos.CENTER);
        box.setSpacing(PADDING);
    }

    protected void formatScrollPane(ScrollPane pane){
        pane.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        pane.setFitToWidth(true);
    }

    protected ImageView createImage(String imgPath){
        return new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(FOLDER +imgPath), IMAGE_SIZE, IMAGE_SIZE, true, true));
    }

    protected Button makeButton(String text, EventHandler<ActionEvent> handler) {
        var button = new Button(text);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setOnAction(handler);
        return button;
    }

    protected Button makeButton(String text, EventHandler<ActionEvent> handler, int width, int height) {
        var button = new Button(text);
        button.setPrefSize(width, height);
        button.setOnAction(handler);
        return button;
    }

    abstract public void update();

    protected Stage getStage(){
        return myStage;
    }

    public void show() {
        myStage.toFront();
        myStage.show();
    }

    protected MapViewController getMyController(){
        return myController;
    }

    protected void addSceneToPopup(Parent root, int width, int height) {
        Scene s = new Scene(root, width, height);
        s.getStylesheets().add("styling.css");
        myStage.setScene(s);
    }

    protected void addSceneToPopup(Parent root) {
        Scene s = new Scene(root);
        s.getStylesheets().add("styling.css");
        myStage.setScene(s);
    }
}

