package Screens.LevelViewScreen;

import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.List;


public class BackgroundGallery extends GamePopup {

    private static final int padding = 5;
    private static final int GALLERY_SIZE = 500;
    private static final int IMAGE_SIZE = 200;
    private static final String TITLE = "Entity Gallery";
    private static final String FOLDER = "backgrounds"+ File.separator;

    private LevelViewController myController;

    public BackgroundGallery(LevelViewController controller)  {
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
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setContent(tile);
        addSceneToPopup(root, GALLERY_SIZE, GALLERY_SIZE);
    }

    private void loadEntities(TilePane tile) {
        List<String> paths = getFileFromJar(FOLDER);
        for (String f : paths){
            var s = f.split("/");
            String p = s[s.length-1];
            var entityImg =  new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(FOLDER + p), IMAGE_SIZE, IMAGE_SIZE, true, true));
            tile.getChildren().add(entityImg);
            entityImg.setOnMouseClicked(e -> {
                myController.addBackgroundToMap(p);
                getStage().close();
            });
        }
    }

    @Override
    protected void show() {
        getStage().show();
    }
}