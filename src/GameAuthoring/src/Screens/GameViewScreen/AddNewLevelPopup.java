package Screens.GameViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddNewLevelPopup extends GamePopup {

    private GameViewController myController;

    private TextField myField;
    private static final int padding = 20;

    public AddNewLevelPopup(GameViewController controller) {
        super();
        myController = controller;
        setUpScene();
    }

    @Override
    protected void setUpScene() {
        VBox layout = new VBox();
        layout.getChildren().addAll(addContent(), addCloseButtons());
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }

    private HBox addContent() {
        var box = new HBox();
        myField = new TextField();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(new Label("Level ID: "));
        box.getChildren().add(myField);
        return box;
    }

    private HBox addCloseButtons() {
        var box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(makeButton("Cancel", e-> getStage().close()));
        box.getChildren().add(makeButton("Save", e->{
            myController.addNewLevel(myField.getText());
            getStage().close();}));
        return box;
    }

    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
