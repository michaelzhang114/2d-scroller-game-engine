package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Class generated popup to add a new entity (Character) to the game.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddNewEntityPopup extends GamePopup {

    private static final String TITLE = "New Entity";
    private static final int padding = 20;

    private String myImgPath;
    private TextField myField;
    private MapViewController myController;

    public AddNewEntityPopup(MapViewController controller, String path) {
        super();
        myController = controller;
        myImgPath = path;
        setUpScene();
    }


    @Override
    protected void setUpScene(){
        setTitle(TITLE);
        VBox layout = new VBox(padding);
        layout.getChildren().addAll(addTextField(),
                addCloseButtons());
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }


    private HBox addTextField() {
        myField = new TextField();
        var box = new HBox(new Text("Name:  "), myField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox addCloseButtons() {
        var box = new HBox(makeButton("Close", e -> getStage().close()),
                makeButton("Save", e -> { getStage().close();
                        myController.addNewEntity(myField.getText(), myImgPath);
                        }));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(padding);
        return box;
    }

    /**
     * Shows popup when called. Waits for user response to save entity or close.
     */
    public void show() {
        getStage().showAndWait();
    }

}
