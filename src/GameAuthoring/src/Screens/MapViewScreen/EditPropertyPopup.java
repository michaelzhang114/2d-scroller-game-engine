package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Generates a popup that allows user to edit an instance property (e.g. xvelocity, health, points, etc.)
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EditPropertyPopup extends GamePopup {

    private MapViewController myController; //TODO: extend AddNewPropertyPopup possibly?
    private String myPropertyId;
    private TextField myField;
    private static final int spacing = 20;
    private static final int HEIGHT = 200;
    private static final int WIDTH = 300;


    public EditPropertyPopup(MapViewController controller, String property){
        super();
        myController = controller;
        myPropertyId = property;
        setUpScene();
    }

    @Override
    protected void setUpScene(){
        getStage().setMinWidth(WIDTH);
        getStage().setMinHeight(HEIGHT);
        myField = new TextField();
        var hbox = new HBox();
        hbox.getChildren().addAll(new Label(myPropertyId), myField);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(spacing);
        var vbox = new VBox();
        vbox.getChildren().addAll(hbox, addCloseButtons());
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(spacing);
        addSceneToPopup(vbox);
    }

    public HBox addCloseButtons() {
        var box = new HBox(makeButton("Close", e->getStage().close()),
                makeButton("Save", e-> {
                    getStage().close();
                    myController.editInstanceProperty(myPropertyId, myField.getText());
                }));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(spacing);
        return box;
    }

    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
