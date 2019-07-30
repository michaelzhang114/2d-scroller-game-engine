package Screens.MapViewScreen;

import Utilities.LibraryPopup;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Generates the events popup. This library contains the events, conditions and actions that the user has created.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EventsPopup extends LibraryPopup {
    private static final int HEIGHT = 700;
    private static final int WIDTH = 400;

    private Stage myStage;

    public EventsPopup(Stage stage, MapViewController controller){
        super(stage, controller);
        myStage = stage;
        setUpScene();
    }

    private void setUpScene() {
        TabPane tp = new TabPane();
        EventLibrary lib = new EventLibrary(getMyController());
        ConditionsAndActionsTab c = new ConditionsAndActionsTab(getMyController());
        Tab libTab = new Tab("Event Library", lib.getContent());
        Tab conditionsTab = new Tab("Conditions and Actions", c.getContent());
        tp.getTabs().addAll(libTab, conditionsTab);
        addSceneToPopup(tp);
    }

    protected void addSceneToPopup(Parent root) {
        Scene s = new Scene(root, WIDTH, HEIGHT);
        s.getStylesheets().add("styling.css");
        myStage.setScene(s);
    }

    @Override
    public void update() {
        setUpScene();
    }
}
