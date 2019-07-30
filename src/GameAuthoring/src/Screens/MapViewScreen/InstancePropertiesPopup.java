package Screens.MapViewScreen;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Generates popup to edit instance properties like Acceleration, position or health
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class InstancePropertiesPopup extends EntityPropertiesPopup{

    private static final int spacing = 25;

    public InstancePropertiesPopup(MapViewController controller) {
        super(controller);
    }

    @Override
    protected Collection<HBox> existingProperties(){
        Collection<String> properties = getController().getInstanceProperties();
        var boxes = new ArrayList<HBox>();
        for (String p : properties){
            if (!getUneditableProperties().contains(p)) {
                var property = makePropertyBox(p, getController().getInstanceValueOfProperty(p));
                boxes.add(property);
            }
        }
        return boxes;
    }

    @Override
    protected HBox makePropertyBox(String prop, String val) {
        HBox property = new HBox(new Text(prop), new Text(val), makeButton("Edit", e -> getController().editPropertyPopup(prop)));
        property.setAlignment(Pos.CENTER);
        property.setSpacing(spacing);
        return property;
    }

    @Override
    protected HBox addCloseButtons(){
        var box = new HBox();
        box.getChildren().add(makeButton("Close", e -> getStage().close()));
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
