package Screens.MapViewScreen;

import Utilities.AddNewPropertyPopup;
import Utilities.PropertiesPopup;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Generates the popup to edit Entity Properties
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EntityPropertiesPopup extends PropertiesPopup { //TODO: make extensible to work for level properties as well
    private final static String TITLE = "Properties Editor";
    private final static int PADDING = 20;
    private static final int HEIGHT = 400;
    private static final int WIDTH = 300;
    private MapViewController myController;

    private ArrayList<String> uneditableProperties = new ArrayList<>(Arrays.asList("xPosition", "yPosition", "imagePath", "onScreen", "level",
            "currentImageIndex", "xScale"));


    public EntityPropertiesPopup(MapViewController controller){ //TODO: use Controller superclass/interface in constructor and implement common methods
        super();
        myController = controller;
        setUpScene();
    }

    @Override
    protected void setUpScene() {
        setTitle(TITLE);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox content = new VBox();
        content.setSpacing(PADDING);
        content.getChildren().add(addCloseButtons());
        content.getChildren().addAll(existingProperties());
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        addSceneToPopup(scrollPane, WIDTH, HEIGHT);
    }

    protected HBox addCloseButtons(){
        var box = new HBox();
        box.getChildren().add(makeButton("Add Property", e -> new AddNewPropertyPopup(myController)));
        box.getChildren().add(makeButton("Close", e -> getStage().close()));
        box.setAlignment(Pos.CENTER);
        return box;
    }

    protected Collection<HBox> existingProperties(){
        Collection<String> properties = myController.getEntityProperties(myController.getCurrentEntityID());
        var boxes = new ArrayList<HBox>();
        for (String p : properties){
            if (!uneditableProperties.contains(p)) {
                var property = makePropertyBox(p, myController.getValueOfProperty(p));
                boxes.add(property);
            }
        }
        return boxes;
    }

    protected HBox makePropertyBox(String prop, String val) {
        HBox property = new HBox(new Text(prop), new Text(val));
        property.setAlignment(Pos.CENTER);
        property.setSpacing(PADDING);
        return property;
    }

    /**
     * Shows the screen when called
     */
    @Override
    public void show() {
        getStage().show();
    }

    /**
     * Updates the screen
     */
    public void update() {
        setUpScene();
    }

    /**
     * Returns the list of uneditable properties.
     * @return
     */
    public ArrayList<String> getUneditableProperties() { return uneditableProperties; }

    /**
     * Returns the controller of the specific screen
     */
    public MapViewController getController() { return myController; }

}
