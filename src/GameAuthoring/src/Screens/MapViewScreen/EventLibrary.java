package Screens.MapViewScreen;

import Utilities.GameTab;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collection;

/**
 * Generates the event library, where all event packages are displayed real time
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EventLibrary extends GameTab {

    public EventLibrary (MapViewController controller){
        super(controller);
        setUpScene();
    }

    private void setUpScene() {
        getContent().setTop(setUpTopHBox());
        getContent().setCenter(setUpScrollPaneContent());
    }

    private Node setUpScrollPaneContent() {
        var pane = new ScrollPane();
        pane.setContent(fillPane());
        formatScrollPane(pane);
        return pane;
    }

    private Node fillPane(){
        var box = new VBox();
        box.getChildren().add(makeButton("Add Event", e -> getMyController().openAddNewEvent()));
        box.getChildren().add(makeAccordion());
        formatVBox(box);
        return box;
    }

    private Node makeAccordion(){
        Accordion accordion = new Accordion();
        for (String entityID: getMyController().getEntityMap().keySet()){
            accordion.getPanes().add(makeEntityEventsGroup(entityID));
        }
        return accordion;
    }

    private TitledPane makeEntityEventsGroup(String entityID){
        TitledPane tp = new TitledPane();
        tp.setText(entityID);
        VBox box = new VBox();
        tp.setContent(box);
        Collection<String> eventPackages = getMyController().getEventPackagesOfEntity(entityID);
        for (String eventID: eventPackages){
            Label id = new Label(eventID);
            HBox eventBox = new HBox(id); //TODO: implement delete Event button here
            box.getChildren().addAll(id, eventBox);
        }
        return tp;
    }

    private Node setUpTopHBox() {
        var box = new HBox();
        formatHBox(box);
        return box;
    }

    /**
     * Updates the screen when called
     */
    public void update() {
        setUpScene();
    }

}
