package Screens.MapViewScreen;

import Utilities.LibraryPopup;
import exception.ActionNotFoundException;
import exception.ConditionNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Generates a popup to add an event to the game. Events need conditions and actions to be fulfilled.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddNewEventPopup extends LibraryPopup {
    private Stage myStage;

    private MapViewController myController;
    private TextField eventIDField;
    private ComboBox entityID;
    private List<CheckBox> myConditions;
    private List<CheckBox> myActions;


    public AddNewEventPopup (Stage stage, MapViewController controller){
        super(stage, controller);
        myController = controller;
        myStage = stage;
        myConditions = new ArrayList<>();
        myActions = new ArrayList<>();
        setUpScene();
    }

    private void setUpScene() {
        BorderPane layout = new BorderPane();
        layout.setTop(setUpTop());
        layout.setCenter(setUpCenter());
        layout.setBottom(setUpBottom());
        ScrollPane sp = new ScrollPane(layout);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        addSceneToPopup(sp);
    }

    private Node setUpTop() {
        VBox top = new VBox();
        Label instructions = new Label("Select Conditions and Actions to add to Event Package");
        Label promptEventID = new Label("Event ID:");
        eventIDField = new TextField();
        entityID = new ComboBox();
        entityID.getItems().addAll(myController.getListOfEntities());
        HBox field = new HBox(promptEventID, eventIDField);
        HBox entity = new HBox(new Label("Entity Affected"), entityID);
        top.getChildren().addAll(instructions, field, entity);
        return top;
    }

    private Node setUpCenter() {
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(setUpConditions());
        borderPane.setRight(setUpActions());
        return borderPane;
    }

    private Node setUpConditions(){
        VBox box = new VBox();
        box.getChildren().add(new Label("Conditions"));
        try {
            for (String id : getMyController().getListOfAllConditions()) {
                var cb = new CheckBox(id);
                box.getChildren().add(cb);
                myConditions.add(cb);
            }
        } catch (ConditionNotFoundException e) {
            //FIXME: DO SOMETHING
        }
        return box;
    }

    private Node setUpActions(){
        VBox box = new VBox();
        box.getChildren().add(new Label("Actions"));
        try {
            for (String id : getMyController().getListOfAllActions()) {
               var cb = new CheckBox(id);
               box.getChildren().add(cb);
               myActions.add(cb);
            }
        } catch (ActionNotFoundException e){
            //FIXME: DO SOMETHING
        }
        return box;
    }

    private Node setUpBottom() {
        return new HBox(makeButton("Close",  e -> myStage.close()),
                makeButton("Confirm", e -> {
                    if (entityID.getSelectionModel().getSelectedItem() != null && !eventIDField.getText().equals("")) {
                        saveEvent();
                        myController.updateEventsPopup();
                        getStage().close();
                    }
                }));
    }

    private void saveEvent() {
        var actions = new ArrayList<String>();
        var conditions = new ArrayList<String>();
        for (CheckBox action : myActions){
            if (action.isSelected()){
                actions.add(action.getText());
            }
        }
        for (CheckBox condition : myConditions){
            if (condition.isSelected()){
                conditions.add(condition.getText());
            }
        }
        String eventID = eventIDField.getText();
        myController.saveNewEvent(eventID, conditions, actions);
        myController.addEventPackageToEntity((String) entityID.getSelectionModel().getSelectedItem(), eventID);
    }

    /**
     * Updates the screen when called to show edits made
     */
    public void update(){
        setUpScene();
    }
}
