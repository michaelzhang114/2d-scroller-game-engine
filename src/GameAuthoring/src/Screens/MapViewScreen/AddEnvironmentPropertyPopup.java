package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class generates a popup to add a new Game Environment Property.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class AddEnvironmentPropertyPopup extends GamePopup {

    private static final String TITLE = "Edit Environment";
    private static final int padding = 20;
    private TextField myField;
    private String myGravity;
    private String myChosenDirection;
    private ComboBox myMenu;

    public AddEnvironmentPropertyPopup() {
        super();
        setUpScene();
    }

    /**
     * Sets up the scene
     */
    @Override
    protected void setUpScene() {
        setTitle(TITLE);
        VBox layout = new VBox(padding);
        //TODO: Add map of existing properties to this list
        layout.getChildren().addAll(addGravity(),
                addDropDown(),
                addCloseButtons());
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }


    private HBox addGravity() { //TODO : Generalize for extensibility.
        myField = new TextField();
        var box = new HBox(new Text("Gravity:  "), myField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox addDropDown() { //TODO: Generalize for extensibility
        myMenu = new ComboBox();
        myMenu.getItems().addAll("Up", "Down", "Right", "Left");
        var box = new HBox(new Text("Scroll Direction:  "), myMenu);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox addCloseButtons() {
        var box = new HBox();
        box.getChildren().add(makeButton("Save", e -> {getStage().close();
                myGravity = myField.getText();
                myChosenDirection = myMenu.getValue().toString();
                System.out.println(myGravity); //TODO: SAVE SOMEWHERE USEFUL
                System.out.println(myChosenDirection);}));  //TODO: SAVE SOMEWHERE USEFUL
        box.getChildren().add(makeButton("Close", e-> getStage().close()));
        box.setAlignment(Pos.CENTER);
        return box;
    }


    /**
     * WHen called, shows the popup.
     */
    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
