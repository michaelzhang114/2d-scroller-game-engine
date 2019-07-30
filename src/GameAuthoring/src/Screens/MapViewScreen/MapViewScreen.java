package Screens.MapViewScreen;

import Utilities.GameMap;
import Utilities.MapEntity;
import Utilities.Screen;
import Manager.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MapViewScreen extends Screen {

    private static final String GAME_VIEW_BUTTON = "Game View";
    private static final String LEVEL_VIEW_BUTTON = "Level View";
    private int GRID_SIZE = 50;
    private int BLOCK_SIZE = 32;
    private int counter = 0;
    private int ROWS = 12;
    private int COLUMNS = 70;

    private GridPane myCanvas;
    private MapViewController myController;
    private ScreenManager myManager;
    private GameMap myMap;
    private List<MapEntity> myMapInstances;

    private boolean EDIT_MODE = false;
    private ComboBox mainCharacter;
    private static final String FOLDER = "characters"+ File.separator;

    public MapViewScreen(GameMap map, ScreenManager manager, MapViewController controller) {
        super(manager.getStage());
        myMap = map;
        myMapInstances = new ArrayList<>();
        myManager = manager;
        myController = controller;
        defineTopHBox();
        defineBottomHBox();
        getRoot().setCenter(makeCenterPane(COLUMNS));
    }

    private ScrollPane makeCenterPane(int col) {
        COLUMNS = COLUMNS + 30;
        myCanvas = new GridPane();
        myCanvas.setGridLinesVisible(true);
        for (int i = 0; i <= ROWS; i++) {
            RowConstraints rowCon = new RowConstraints();
            rowCon.setPrefHeight(GRID_SIZE);
            myCanvas.getRowConstraints().add(rowCon);
        }
        for (int i = 0; i <=  col; i++) {
            ColumnConstraints colCon = new ColumnConstraints();
            colCon.setPrefWidth(GRID_SIZE);
            myCanvas.getColumnConstraints().add(colCon);
        }
        myCanvas.setOnMouseClicked(this::mouseEvents);
        myCanvas.setOnMouseDragged(this::mouseEvents);
        for (MapEntity i : myMapInstances)
            addImage(i.getEntityPath(), i.getXPos(), i.getYPos(), false);
        var pane = new ScrollPane(myCanvas);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return pane;
    }


    private void mouseEvents(MouseEvent e) {
        if (myController.getEntityID()!=null && !EDIT_MODE){
            if (!myMapInstances.isEmpty()){
                for (MapEntity i : myMapInstances){
                    if (i.getXPos() == (int) (e.getX()/GRID_SIZE)*GRID_SIZE && i.getYPos() == (int) (e.getY()/GRID_SIZE)*GRID_SIZE){
                        return;
                    }
                }
            }
            addImage(myController.getImgPath(), e.getX(), e.getY(), true);
        }
    }

    private void defineTopHBox() {
        HBox leftBox = new HBox(makeButton(GAME_VIEW_BUTTON, event -> myManager.jumpToGameViewScreen()),
                makeButton(LEVEL_VIEW_BUTTON, event -> myManager.jumpToLevelViewScreen()));
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox centerBox = new HBox(new Label(myMap.getMapID()));
        Button editButton;
        if (EDIT_MODE){
            editButton = makeButton("EDIT ON", e-> editModeOff());
            editButton.setId("edit-button");
        }
        else editButton = makeButton("EDIT OFF", e-> editModeOn());
        HBox rightBox = new HBox(editButton);
        leftBox.setId("hbox-left");
        centerBox.setId("hbox-center");
        rightBox.setId("hbox-right");
        getTopBox().setLeft(leftBox);
        getTopBox().setCenter(centerBox);
        getTopBox().setRight(rightBox);
    }

    private void defineBottomHBox() {
        HBox leftBox = new HBox(makeButton("Entity Library", e-> myController.openEntityLibrary()),
                makeButton("Events, Conditions, Actions", e -> myController.openEventsPopup()));
        HBox centerBox = new HBox(makeButton("<-- PREV MAP", event -> myManager.setMap(getPreviousMap())),
                makeButton("NEXT MAP -->", e -> myManager.setMap(getNextMap())));
        mainCharacter = new ComboBox();
        mainCharacter.getItems().addAll(myController.getListOfEntities());
        mainCharacter.getItems().remove("levelController");
        HBox rightBox = new HBox(new HBox(new Label("MainCharacter  "), mainCharacter),
                makeButton("Add Cells", e -> update(COLUMNS)));
        leftBox.setId("hbox-left");
        centerBox.setId("hbox-center");
        rightBox.setId("hbox-right");
        getBottomBox().setRight(rightBox);
        getBottomBox().setLeft(leftBox);
        getBottomBox().setCenter(centerBox);
    }

    private GameMap getNextMap() {
        int size = myManager.getMapsOfLevel().size();
        int index = myManager.getMapsOfLevel().indexOf(myMap);
        GameMap nextMap;
        if (index == size-1) nextMap = myManager.getMapsOfLevel().get(0);
        else nextMap = myManager.getMapsOfLevel().get(index + 1);
        return nextMap;
    }

    private GameMap getPreviousMap() {
        int size = myManager.getMapsOfLevel().size();
        int index = myManager.getMapsOfLevel().indexOf(myMap);
        GameMap prevMap;
        if (index == 0) prevMap = myManager.getMapsOfLevel().get(size - 1);
        else prevMap = myManager.getMapsOfLevel().get(index -1);
        return prevMap;
    }

    private void addImage(String path, double xPos, double yPos, boolean bool) {
        var img = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(FOLDER+path), GRID_SIZE, GRID_SIZE, true, true));
        int x = (int) (xPos / GRID_SIZE);
        int y = (int) (yPos / GRID_SIZE);

        myController.createNewEntityInstance(Integer.toString(counter),x*BLOCK_SIZE, y*BLOCK_SIZE);
        defineBottomHBox();
        var mapEntity = new MapEntity(myController.getEntityID(), Integer.toString(counter), myController.getImgPath(), x*GRID_SIZE, y*GRID_SIZE);
        if (bool) myMapInstances.add(mapEntity);
        counter++;
        myCanvas.add(img, x, y, 1, 1);
        ImageView finalImg = img;
        img.setOnMouseClicked(e-> {
            if (EDIT_MODE)
                myController.openEditOptionsPopup(mapEntity, finalImg);
        });
    }

    public void update(int cols) {
        getRoot().setCenter(makeCenterPane(cols));
    }

    public void editModeOn(){
        EDIT_MODE = true;
        defineTopHBox();
    }

    private void editModeOff() {
        EDIT_MODE = false;
        defineTopHBox();
    }

    public void deleteInstance(ImageView img, MapEntity mapEntity) {
        myCanvas.getChildren().remove(img);
        myMapInstances.remove(mapEntity);
    }

    public String getMainCharacter() {
        return (String) mainCharacter.getSelectionModel().getSelectedItem();
    }

}
