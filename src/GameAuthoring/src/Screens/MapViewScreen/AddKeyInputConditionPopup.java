package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class generates a popup to add a key-input condition to the game.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddKeyInputConditionPopup extends GamePopup {

    private MapViewController myController;
    private static final int padding = 20;
    private static final String TITLE = "KeyInput Condition";
    private TextField myKey;
    private TextField myConditionID;


    public AddKeyInputConditionPopup(MapViewController controller) {
        super();
        myController = controller;
        setTitle(TITLE);
        setUpScene();
    }

    private VBox addContent(){
        var entities = new HBox(addKeyInputBox());
        entities.setAlignment(Pos.CENTER);
        entities.setSpacing(padding);
        var box = new VBox(entities, addConditionIDBox(), closeAndConfirm());
        box.setSpacing(padding);
        //TODO: Add the Respective Actions to the Map
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox addKeyInputBox(){
        myKey = new TextField();
        HBox box = new HBox(new Label("Key"), myKey);
        formatHBox(box);
        return box;
    }

    private HBox addConditionIDBox(){
        myConditionID = new TextField();
        HBox box = new HBox(new Label("Condition ID: "), myConditionID);
        formatHBox(box);
        return box;
    }

    private HBox closeAndConfirm(){
        HBox box = new HBox(makeButton("Close", e -> getStage().close()), makeButton("Save", e -> saveValues()));
        return box;
    }

    private void saveValues() {
        String keyEventID = myConditionID.getText();
        List<String> keys = new ArrayList<>();
        keys.add(myKey.getText());
        myController.createKeyInputCondition(keyEventID, keys);
        myController.updateEventsPopup();
        getStage().close();
    }

    /**
     * Sets up the scene.
     */
    @Override
    protected void setUpScene() {
        addSceneToPopup(addContent());
    }

    /**
     * WHen called, shows the popup
     */
    @Override
    protected void show() {
        getStage().show();
    }
}
