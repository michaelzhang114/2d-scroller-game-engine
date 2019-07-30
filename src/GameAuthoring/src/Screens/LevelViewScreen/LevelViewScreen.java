package Screens.LevelViewScreen;

import Utilities.*;
import Manager.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LevelViewScreen extends Screen {

    private static final int IMAGE_SIZE = 300;
    private static final int HBOX_SPACING = 100;
    private static final String PATH = "backgrounds";

    private Level myLevel;
    private Group myCenterImage;
    private LevelViewController myController;
    private ScreenManager myManager;

    public LevelViewScreen(Level level, ScreenManager manager, LevelViewController controller) {
        super(manager.getStage());
        myLevel = level;
        myManager = manager;
        myController = controller;
        myCenterImage = new Group();
        addNodes();
        getRoot().setId("level-view-root");
        getRoot().setCenter(makeCenterPane());
    }

    private Node makeCenterPane() {
        BorderPane main = new BorderPane();
        main.setBottom(makeCurrentLevels());
        main.setCenter(myCenterImage);
        return main;
    }


    private ScrollPane makeCurrentLevels()  {
        var scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        var h = new HBox(HBOX_SPACING);
        if (myManager.getCurrentLevel() != null){
            for (GameMap i : myController.getMapBackgrounds()){
                var b = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(PATH+ File.separator+i.getBackground()), IMAGE_SIZE, IMAGE_SIZE, true, true));
                b.setOnMouseClicked(e->{myCenterImage.getChildren().clear();
                    b.setFitHeight(400); b.setFitWidth(600);
                    myCenterImage.getChildren().addAll(
                            b, new HBox(
                                    makeButton("Edit Map", ee-> myController.setMap(i)),
                                    makeButton("Delete Map", ee-> {myManager.deleteMap(i); update(); myCenterImage.getChildren().clear();})));
                    update();});
                var v = new VBox(new Text(i.getMapID()), b);
                h.getChildren().add(v);
            }
        }
        scroll.setContent(h);
        return scroll;
    }

    private void addNodes() {
        addTopHBoxNodes();
        addBottomHBoxNodes();
        getRoot().setCenter(makeCenterPane());
    }

    private void addTopHBoxNodes(){
        HBox left = new HBox(makeButton("Game View", e ->  myManager.jumpToGameViewScreen()));
        HBox center = new HBox(new Text("Add and Select Map to Display Below"));
        HBox right = new HBox(makeButton("ADD MAP", e-> myController.addNewMapPopup()));
        //TODO: implement undo/redo by creating Undo class and create separate UndoButton and RedoButton classes in Utilities
        left.setId("hbox-left");
        center.setAlignment(Pos.CENTER);
        right.setId("hbox-right");
        getTopBox().setLeft(left);
        getTopBox().setRight(right);
        getTopBox().setCenter(center);
    }

    private void addBottomHBoxNodes(){
        HBox center = new HBox(makeButton("<-", e -> myManager.setLevel(getPrevLevel())),
                new Label(myLevel.getLevelID()),
                makeButton("->", e -> myManager.setLevel(getNextLevel())));
        center.setId("hbox-center");
        getBottomBox().setCenter(center);
    }

    private Level getNextLevel(){
        int size = myManager.getLevels().size();
        int index = myManager.getLevels().indexOf(myLevel);
        if (index == size - 1){
            return myManager.getLevels().get(0);
        }
        else {
            return myManager.getLevels().get(index+1);
        }
    }

    private Level getPrevLevel(){
        int size = myManager.getLevels().size();
        int index = myManager.getLevels().indexOf(myLevel);
        if (index == 0){
            return myManager.getLevels().get(size-1);
        }
        else {
            return myManager.getLevels().get(index-1);
        }
    }

    public void update(){
        getRoot().setCenter(makeCenterPane());
    }

    public Level getLevel() {
        return myLevel;
    }
}