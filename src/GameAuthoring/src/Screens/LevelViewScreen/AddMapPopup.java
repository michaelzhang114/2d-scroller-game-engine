package Screens.LevelViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.FileNotFoundException;

public class AddMapPopup extends GamePopup {
    private static final String TITLE = "Add Map";
    private LevelViewController myController;
    private TextField myField;
    private static final int padding = 20;
    private static final int BUTTON_SIZE = 150;


    public AddMapPopup(LevelViewController controller){
        super();
        myController = controller;
        setUpScene();
    }

    @Override
    protected void setUpScene() {
        VBox layout = new VBox();
        setTitle(TITLE);
        var background = makeButton("Add Background", e->
                myController.openBackgroundGallery());
        background.setMinWidth(BUTTON_SIZE);
        layout.getChildren().addAll(addMapName(),
                background,
                addCloseButtons());
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }

    private HBox addCloseButtons() {
        var box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(makeButton("Cancel", e-> getStage().close()));
        box.getChildren().add(makeButton("Save", e-> {
            if (!myField.getText().equals("") && myController.getBackgroundPath() != null) {
                myController.addNewMap(myField.getText());
                myController.updateScreen();
                getStage().close();
            }
        }));
        return box;
    }

    private HBox addMapName(){
        HBox addMapName = new HBox();
        Label mapNameText = new Label("Map Name: ");
        myField = new TextField();
        addMapName.getChildren().addAll(mapNameText, myField);
        formatHBox(addMapName);
        return addMapName;
    }

    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
