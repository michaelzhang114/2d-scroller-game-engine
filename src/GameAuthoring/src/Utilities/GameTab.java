package Utilities;

import Screens.MapViewScreen.MapViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

abstract public class GameTab {

    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;
    private static final int PADDING = 20;

    private MapViewController myController;
    private BorderPane myContent;

    public GameTab(MapViewController controller){
        myController = controller;
        myContent = new BorderPane();
    }

    public BorderPane getContent(){
        return myContent;
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

    protected Button makeButton(String text, EventHandler<ActionEvent> handler) {
        var button = new Button(text);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setOnAction(handler);
        return button;
    }

    protected MapViewController getMyController(){
        return myController;
    }

    abstract public void update();
}
