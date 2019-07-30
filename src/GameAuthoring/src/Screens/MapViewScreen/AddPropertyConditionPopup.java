package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Arrays;
import java.util.Collection;

/**
 * Generates a popup to add a new Property condition to the game. Conditions are tied with actions to create events.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddPropertyConditionPopup extends GamePopup {

    private MapViewController myController;
    private static final String TITLE = "Property Condition";
    private Collection<String> operators;
    private ComboBox myProperty;
    private TextField propertyValue;
    private ComboBox myOperator;
    private ComboBox myEntityDropdown;
    private TextField myConditionID;
    private VBox content;
    private VBox otherOptions;


    public AddPropertyConditionPopup(MapViewController controller) {
        super();
        myController = controller;
        makeOperationsList();
        setTitle(TITLE);
        setUpScene();
    }

    private void makeOperationsList(){
        String[] operatorArray = new String[]{"==", "!=", "<", ">", "<=", ">="};
        operators = Arrays.asList(operatorArray); //TODO: get list of operators from GameEngine
    }
    
    @Override
    protected void setUpScene(){
        content = new VBox();
        content.getChildren().addAll(makeConditionIDBox(), makeEntityField());
        addSceneToPopup(content);
    }

    private Node makeConditionIDBox(){
        Label text = new Label("Property Condition ID: ");
        myConditionID = new TextField();
        HBox box = new HBox(text, myConditionID);
        return box;
    }

    private Node makeEntityField(){
        Label text = new Label("Entity: ");
        myEntityDropdown = new ComboBox();
        myEntityDropdown.getItems().addAll(myController.getListOfEntities());
        myEntityDropdown.valueProperty().addListener((observable, oldValue, newValue) ->
            addOtherFields());
        HBox box = new HBox(text, myEntityDropdown);
        return box;
    }

    private void addOtherFields(){
        if (otherOptions != null){
            content.getChildren().remove(otherOptions);
            otherOptions.getChildren().removeAll();
        }
        otherOptions = new VBox();
        otherOptions.getChildren().addAll(makePropertyBox(), addEqualityBox(), makeValueBox(), makeConfirm());
        content.getChildren().add(otherOptions);
    }

    private HBox makePropertyBox(){
        Label text = new Label("Select Property: ");
        myProperty = new ComboBox();
        HBox box = new HBox(text, myProperty);
        String entity = (String) myEntityDropdown.getSelectionModel().getSelectedItem();
        if (entity != null){
            myProperty.getItems().addAll(myController.getEntityProperties(entity));
        }
        return box;
    }

    private Node makeValueBox(){
        Label text = new Label("Value : ");
        propertyValue = new TextField();
        HBox box = new HBox(text, propertyValue);
        return box;
    }

    private HBox addEqualityBox() {
        Label text = new Label("Operator");
        myOperator = new ComboBox();
        myOperator.getItems().addAll(operators);
        HBox box = new HBox(text, myOperator);
        return box;
    }

    private Node makeConfirm(){
        HBox box = new HBox();
        box.getChildren().addAll(makeButton("Close", e -> getStage().close()), makeButton("Save", e -> savePropertyCondition()));
        return box;
    }

    private void savePropertyCondition() {
        String conditionID = myConditionID.getText();
        String entityID = getSelectedItem(myEntityDropdown);
        String propertyID = getSelectedItem(myProperty);
        String operator = getSelectedItem(myOperator);
        String value = propertyValue.getText();
        myController.createPropertyCondition(conditionID, entityID, propertyID, operator, value);
        myController.updateEventsPopup();
        getStage().close();
    }

    /**
     * When called, shows stage.
     */
    public void show(){
        getStage().show();
    }

}


