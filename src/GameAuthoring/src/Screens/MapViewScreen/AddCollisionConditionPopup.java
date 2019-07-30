package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class generates popup to create a new Collision Condition
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddCollisionConditionPopup extends GamePopup {

    private MapViewController myController;
    private static final int padding = 20;
    private static final String TITLE = "Collision Condition";
    private TextField myField;
    private ComboBox myCollisionType;
    private ComboBox myEntityAffected;

    public AddCollisionConditionPopup(MapViewController controller) {
        super();
        myController = controller;
        setUpScene();
    }

    /**
     * Sets up the Popup
     */
    @Override
    protected void setUpScene() {
        setTitle(TITLE);
        BorderPane layout = new BorderPane();
        layout.setTop(addTextField());
        layout.setCenter(addContent());
        layout.setBottom(addCloseButtons());
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }

    /**
     * Adds the content to the Popup
     */
    protected VBox addContent(){
        var entities = new HBox(addEntityBox("Entity Affected"));
        entities.setAlignment(Pos.CENTER);
        entities.setSpacing(padding);
        var box = new VBox(entities, addCollisionTypeBox());
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox addCollisionTypeBox(){
        myCollisionType = new ComboBox();
        myCollisionType.getItems().addAll("top", "bottom", "left", "right", "any");
        HBox box = new HBox(new Label("My Collision Type"), myCollisionType);
        formatHBox(box);
        return box;
    }

    private HBox addEntityBox(String name){
        var box = new HBox(new Label(name));
        box.setPadding(new Insets(padding));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(padding);
        myEntityAffected = new ComboBox();
        myEntityAffected.getItems().addAll(myController.getListOfEntities());
        box.getChildren().addAll(myEntityAffected);
        return box;
    }


    private HBox addCloseButtons() {
        var box = new HBox();
        box.getChildren().add(makeButton("Close", e -> getStage().close()));
        box.getChildren().add(makeButton("Save", e ->  {
            if (!(myField.getText().equals("") || myCollisionType.getValue().equals(""))) {
                myController.createCollisionCondition(myField.getText(), (String) myEntityAffected.getValue() , (String) myCollisionType.getValue());
                myController.updateEventsPopup();
                getStage().close();
            }
        }));
        return box;
    }

    private HBox addTextField() {
        myField = new TextField();
        var box = new HBox(new Label("Collision ID: "), myField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * When called, shows the stage. It waits for user input to either continue or close.
     */
    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
