package Screens.MapViewScreen;

import Utilities.GamePopup;
import Utilities.MapEntity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Generates a popup that allows user to edit an instance, whether it be delete the instance or edit one of its properties
 * (e.g. xVelocity, Health, Points, etc.)
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EditInstancePopup extends GamePopup{

    private ImageView myImage;
    private MapEntity myEntity;
    private MapViewController myController;
    private int padding = 20;


    public EditInstancePopup(MapEntity entity, ImageView finalImg, MapViewController controller) {
        super();
        myEntity = entity;
        myImage = finalImg;
        myController = controller; //FIXME: Delete this after API is fixed
        setUpScene();
    }

    @Override
    protected void setUpScene() {
        VBox layout = new VBox(padding);
        layout.getChildren().addAll(makeButton("Edit Properties", e-> {
                    getStage().close();
                    myController.openInstancePropertiesPopup();
                }),
                makeButton("Delete Entity", e-> {
                    getStage().close();
                    myController.deleteInstance(myImage, myEntity);
                }));
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));
        addSceneToPopup(layout);
    }

    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
