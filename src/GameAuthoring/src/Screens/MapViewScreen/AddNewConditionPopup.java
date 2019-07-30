package Screens.MapViewScreen;

import Utilities.LibraryPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Generates a popup to add a new condition to the game. Afterwards, conditions are tied with actions to create events.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddNewConditionPopup extends LibraryPopup {

    private MapViewController myController;

    private static final int padding = 20;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;

    public AddNewConditionPopup(Stage stage, MapViewController controller){
        super(stage, controller);
        myController = controller;
        setUpScene();
    }

    /**
     * Updates the screen when called, perhaps to show new content.
     */
    @Override
    public void update() {
        setUpScene();
    }

    private void setUpScene() {
        VBox layout = new VBox(padding);
        layout.getChildren().addAll(makeButton("Collision Condition", e -> {
            myController.openAddCollisionEvent();
            getStage().close();
                }, BUTTON_WIDTH,BUTTON_HEIGHT),
                makeButton("Key-Input Condition", e -> {
                    myController.openAddKeyInputEvent();
                    getStage().close();
                }, BUTTON_WIDTH,BUTTON_HEIGHT),
                makeButton("Property Condition", e -> {
                    myController.openAddPropertyEvent();
                    getStage().close();
                }, BUTTON_WIDTH,BUTTON_HEIGHT),
                makeButton("Level-Change Condition", e -> {
                    System.out.println("Levelchange"); //TODO: implement level change condition
                    getStage().close();
                }),
                makeButton("Close", e -> getStage().close()));
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }
}
