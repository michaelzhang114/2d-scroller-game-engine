package Screens.MapViewScreen;

import Utilities.LibraryPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Popup generated to add an action to the Game
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddActionPopup extends LibraryPopup {

    private static final int padding = 20;
    private static final int  WIDTH = 400;
    private static final int  HEIGHT = 200;
    private TextField myAction;
    private String myEntityID;
    private ComboBox myProperty;
    private ComboBox myOperator;
    private TextField myAmount;
    private ComboBox myEntityDropdown;

    public AddActionPopup(Stage stage, MapViewController controller) {
        super(stage, controller);
        setUpScene();
    }

    private void setUpScene() {
        VBox layout = new VBox(padding);
        layout.getChildren().addAll(addEventBox(), addEntityBox(), addOperationBox(), addCloseButtons());
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout, WIDTH, HEIGHT);
    }

    private HBox addEventBox() {
        myAction = new TextField();
        var box = new HBox(new Label("Action ID:  "), myAction);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox addEntityBox() {
        myEntityDropdown = new ComboBox();
        myEntityDropdown.getItems().addAll(getMyController().getListOfEntities());
        myEntityDropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            myEntityID = (String) myEntityDropdown.getSelectionModel().getSelectedItem();
            makePropertyDropdown();
        });
        var box = new HBox(new Label("Entity ID: "), myEntityDropdown);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void makePropertyDropdown(){
        myProperty.getItems().addAll(getMyController().getEntityProperties(myEntityID));
    }

    private HBox addOperationBox() {
        myProperty = new ComboBox();
        var property = new VBox(new Label("Property"), myProperty);
        property.setAlignment(Pos.CENTER);
        myOperator = new ComboBox();
        myOperator.getItems().addAll("+", "-", "*", "/", "=");
        var operator = new VBox(new Label("Operator"), myOperator);
        myAmount = new TextField();
        var amount = new VBox(new Label("Amount"), myAmount);
        amount.setAlignment(Pos.CENTER);
        HBox box = new HBox(property, operator, amount);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(padding);
        return box;
    }

    private HBox addCloseButtons() {
        var box = new HBox();
        box.getChildren().add(makeButton("Close", e -> getStage().close()));
        box.getChildren().add(makeButton("Save", e ->  { getStage().close();
            saveAction();
            } ));  //TODO: Save the entities with the GE API and not with Parameters
        //TODO: Add to Collision Event Panel (for display)
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void saveAction(){
        String actionID = myAction.getText();
        String entityID = (String) myEntityDropdown.getSelectionModel().getSelectedItem();
        String property = (String) myProperty.getSelectionModel().getSelectedItem();
        String operator = (String) myOperator.getSelectionModel().getSelectedItem();
        String amount = myAmount.getText();
        getMyController().createAction(actionID, entityID, property, operator, amount);
        getMyController().updateEventsPopup();
    }

    /**
     * When called, shows the popup
     */
    public void show(){ getStage().show(); }

    /**
     * If needed, this method will update the contents of the screen when called
     */
    @Override
    public void update() {
        setUpScene();
    }

}
