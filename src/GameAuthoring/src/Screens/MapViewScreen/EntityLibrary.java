package Screens.MapViewScreen;

import Utilities.LibraryPopup;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Generates the library of created instances. User is allowed to see the properties for each user and add new ones if he or
 * she desires.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EntityLibrary extends LibraryPopup {

    private static final int spacing = 20;
    private final String BOX_STYLE = "-fx-border-color: gray;\n -fx-border-width: 2;\n";


    public EntityLibrary (Stage stage, MapViewController controller){
        super(stage, controller);
        setUpScene();
    }

    private void setUpScene() {
        BorderPane content = new BorderPane();
        content.setTop(setUpTopHbox());
        content.setCenter(setUpScrollPaneContent());
        addSceneToPopup(content);
    }

    private HBox setUpTopHbox(){
        var text = new Label("Click to add Entities to Map");
        HBox box = new HBox(text);
         //TODO: open gallery after entity popup
        formatHBox(box);
        return box;
    }

    private Node setUpScrollPaneContent() {
        var pane = new ScrollPane();
            pane.setContent(fillPane());
        formatScrollPane(pane);
        return pane;
    }

    private Node fillPane()  {
        var box = new VBox();
        //FIXME: fix next couple of lines to load from API, after backend communication is done
        box.getChildren().add(makeButton("Add Entity", e -> getMyController().openEntityGallery()));
        var map = getMyController().getEntityMap();
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                box.getChildren().add(addEntityBox(key, map.get(key)));
            }
        }
        formatVBox(box);
        return box;
    }

    private Node addEntityBox(String name, String path) {
        var box = new HBox();
        formatHBox(box);
        box.setStyle(BOX_STYLE);
        ImageView entityImg = createImage(path);
        box.getChildren().addAll(new Text(name), entityImg, makeButton("!", e -> {
                    getMyController().prepareDragDrop(name, path);
                    getMyController().openPropertiesPopup();
                }));
        box.setOnMouseClicked(e -> getMyController().prepareDragDrop(name, path));
        box.setSpacing(spacing);
        return box;
    }

    @Override
    public void update() {
        setUpScene();
    }

}
