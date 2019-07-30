package Screens.MapViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.io.File;
import java.util.List;

/**
 * Generates a gallery of all the entities the user can pick from to add to the game.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class EntityGallery extends GamePopup {

    private static final int padding = 5;
    private static final int GALLERY_SIZE = 500;
    private static final int IMAGE_SIZE = 50;
    private static final String TITLE = "Entity Gallery";
    private static final String PATH = "characters" + File.separator;

    private MapViewController myController;

    public EntityGallery(MapViewController controller){
        super();
        myController = controller;
        setUpScene();
    }

    @Override
    protected void setUpScene() {
        setTitle(TITLE);
        var root = new ScrollPane();
        var tile  = new TilePane();
        tile.setPadding(new Insets(padding, padding, padding, padding));
        tile.setHgap(padding);
        loadEntities(tile);
        root.setContent(tile);
        addSceneToPopup(root, GALLERY_SIZE, GALLERY_SIZE);
    }

    private void loadEntities(TilePane tile) {
        List<String> paths = getFileFromJar(PATH);
        for (String f : paths){
            var x = f.split("/");
            String s = x[x.length-1];
            var entityImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(PATH + s), IMAGE_SIZE, IMAGE_SIZE, true, true));
            tile.getChildren().add(entityImg);
            entityImg.setOnMouseClicked(e -> {
                myController.openAddNewEntity(s);
                getStage().close();
            });
        }
    }

    @Override
    protected void show() {
        getStage().show();
    }
}
