package Screens.MapViewScreen;


import Utilities.GameTab;
import exception.ActionNotFoundException;
import exception.IllegalEventPackageException;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;

/**
 * Generates a library with conditions and actions that is updated real time. User can also add more conditions or actions
 * in this popup.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class ConditionsAndActionsTab extends GameTab {

    public ConditionsAndActionsTab(MapViewController controller) {
        super(controller);
        setUpScene();
    }

    private void setUpScene() {
        getContent().setTop(defineTop());
        getContent().setLeft(setUpConditions());
        getContent().setRight(setUpActions());
        setUpActions();
    }

    private Node setUpConditions(){
        var layout = makePane(makeButton("Add Condition", e-> getMyController().openAddNewCondition()));
        Accordion acc = new Accordion();
        layout.getChildren().add(acc);
        try {
            for (String condition: getMyController().getListOfAllConditions()){
                TitledPane conditionID = new TitledPane();
                conditionID.setText(condition);
                conditionID.setContent(setUpConditionContent(condition));
                acc.getPanes().add(conditionID);
            }
        }
        catch (IllegalEventPackageException e){
            //do nothing
        }
        return layout;
    }

    private Node setUpActions(){
        var layout = makePane(makeButton("Add Action", e -> getMyController().openAddAction()));
        Accordion acc = new Accordion();
        layout.getChildren().add(acc);
        try {
            for (String action: getMyController().getListOfAllActions()){
                TitledPane actionID = new TitledPane();
                actionID.setText(action);
                actionID.setContent(setUpActionContent(action));
                acc.getPanes().add(actionID);
            }
        }
        catch (ActionNotFoundException e){
            //do nothing
        }
        return layout;
    }

    private VBox makePane(Button button) {
        ScrollPane sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox layout = new VBox();
        sp.setContent(layout);
        layout.getChildren().add(button);
        return layout;
    }

    private Node setUpConditionContent(String conditionID) {
        VBox layout = new VBox();
        Map<String, String> conditionInfo = getMyController().getConditionInfo(conditionID);
        for (Map.Entry<String, String> entry: conditionInfo.entrySet()){
            Label info = new Label(entry.getKey() + ": " + entry.getValue());
            layout.getChildren().add(info);
        }
        return layout;
    }

    private Node setUpActionContent(String actionID) {
        VBox layout = new VBox();
        List<String> actionInfo = getMyController().getActionInfo(actionID);
        for (String info: actionInfo) {
            layout.getChildren().add(new Label(info));
        }
        return layout;
    }

    private BorderPane defineTop() {
        var left = new HBox(new Label("Conditions"));
        var right = new HBox(new Label("Actions"));
        var bp = new BorderPane();
        bp.setLeft(left);
        bp.setRight(right);
        return bp;
    }

    /**
     * Updates screen when called.
     */
    public void update() {
        setUpScene();

    }
}
