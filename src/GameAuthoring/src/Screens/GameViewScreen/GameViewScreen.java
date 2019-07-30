package Screens.GameViewScreen;

import Utilities.AddNewPropertyPopup;
import Utilities.Level;
import Utilities.Screen;
import Manager.ScreenManager;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class GameViewScreen extends Screen {

    private static final int TABPANE_WIDTH = 250;
    private static final int FLOWPANE_VGAP = 15;
    private static final int FLOWPANE_HEIGHT = 1000;
    private static final int FLOWPANE_WIDTH = 100;
    private static final int TEXTFIELD_WIDTH = 200;

    private GameViewController myController;
    private ScreenManager myManager;
    private File myFile;
    private TextField myDescription;
    private TextField myInstructions;

    public GameViewScreen(ScreenManager manager, GameViewController controller) {
        super(manager.getStage());
        myManager = manager;
        myController = controller;
        defineTopBorderPane();
        defineLeftPane();
        defineCenterPane();
        getRoot().setId("game-view-root");
    }

    private void defineTopBorderPane() {
        Label t = new Label(myManager.getGameName());
        t.setId("top-label");
        HBox rightBox = new HBox(makeButton("Undo", event -> myController.undoAction()),
                makeButton("Redo", event -> myController.redoAction()),
                makeButton("Make Game", event -> saveGame()));
        getTopBox().setRight(rightBox);
        getTopBox().setLeft(t); //TODO: set title as exact center

    }

    private void saveGame() {
        String gameName = myManager.getGameName();
        String gameIconPath = myFile.getAbsolutePath();
        String gameDescription = myDescription.getText();
        String gameInstructions = myInstructions.getText();
        if (myFile == null){
            //display error to user
            return;
        }
        if (gameDescription == null){
            //display error to user
            return;
        }
        if (gameInstructions == null){
            //display error to user
            return;
        }
        myController.saveGame(gameName, gameIconPath, gameDescription, gameInstructions);
    }

    private String getFilePath(String file){
        for (int i = file.length() - 1; i >= 0; i--){
            if (file.substring(i).contains("/")){
                return file.substring(i + 1);
            }
        }
        return null;
    }

    private void defineCenterPane(){
        ScrollPane center = new ScrollPane();
        center.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(FLOWPANE_HEIGHT);
        flowPane.setPrefWidth(FLOWPANE_WIDTH);
        flowPane.getChildren().addAll(addGameImg(), addGameDescription(), addGameInstructions());
        flowPane.setOrientation(Orientation.VERTICAL); //TODO: get data from backend for game name and other info
        flowPane.setVgap(FLOWPANE_VGAP); //TODO: move styling to CSS
        flowPane.setAlignment(Pos.TOP_CENTER); //FIXME: figure out how to put in center
        center.setContent(flowPane);
        getRoot().setCenter(center);
    }

    private HBox addGameImg(){
        var box = new HBox();
        box.getChildren().add(makeButton("Load Icon", e-> loadImgFromFile(getStage())));
        return box;
    }

    private void loadImgFromFile(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Game Icon");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png"));
        myFile = chooser.showOpenDialog(stage);
        if (myFile != null) {
            setBackground();
        }
    }

    private void setBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image(myFile.toURI().toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        //iconPane.setBackground(new Background(myBI));
    }

    private HBox addGameDescription() {
        var box = new HBox();
        box.getChildren().add(new Label("Game Description: "));
        myDescription = setUpField();
        box.getChildren().add(myDescription);
        return box;
    }

    private HBox addGameInstructions() {
        var box = new HBox();
        box.getChildren().add(new Label("Game Instructions: "));
        myInstructions = setUpField();
        box.getChildren().add(myInstructions);
        return box;
    }

    private TextField setUpField(){ //TODO: delete this method after moving formatting to CSS
        var field = new TextField();
        field.setPrefWidth(TEXTFIELD_WIDTH);
        field.setPrefHeight(TEXTFIELD_WIDTH);
        field.setAlignment(Pos.TOP_LEFT);
        return field;
    }

    private void defineLeftPane(){
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(TABPANE_WIDTH);
        tabPane.getTabs().addAll(defineLevelsTab(), defineGamePropertiesTab());
        getRoot().setLeft(tabPane);
    }

    private Tab defineLevelsTab(){
        Tab levelsTab = new Tab("Levels");
        levelsTab.setClosable(false);
        FlowPane elements = new FlowPane();
        elements.setOrientation(Orientation.VERTICAL);
        elements.setAlignment(Pos.TOP_CENTER);
        elements.setVgap(FLOWPANE_VGAP);
        elements.getChildren().add(makeButton("Add Level", e -> myController.addNewLevelPopup()));
        for (Level l: myManager.getLevels()){ //TODO: connect to game engine level manager
            elements.getChildren().add(new HBox(makeButton(l.getLevelID(), e -> myController.setLevel(l))));
        }
        levelsTab.setContent(makeScrollPane(elements));
        return levelsTab;
    }

    private ScrollPane makeScrollPane(Node content){ //TODO: move styling to CSS
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(content);
        return scrollPane;
    }

    private Tab defineGamePropertiesTab(){
        Tab gamePropertiesTab = new Tab("Game Properties");
        FlowPane elements = new FlowPane();
        elements.setOrientation(Orientation.VERTICAL);
        elements.setVgap(FLOWPANE_VGAP);
        elements.getChildren().add(makeButton("Add Property", e -> new AddNewPropertyPopup(myController)));
        gamePropertiesTab.setContent(makeScrollPane(elements));
        return gamePropertiesTab;
    }

    public void update() {
        defineTopBorderPane();
        defineLeftPane();
        updateGamePropertiesTab();
        updateGameDescriptionPane();
    }

    private void updateGamePropertiesTab(){
        //TODO: connect to backend/controller
    }

    private void updateGameDescriptionPane(){
        //TODO: connect to backend/controller
    }


}
