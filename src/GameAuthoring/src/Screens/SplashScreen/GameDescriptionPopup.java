package Screens.SplashScreen;

import Manager.ScreenManager;
import Utilities.GamePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class GameDescriptionPopup extends GamePopup {

    private static final String TITLE = "NEW GAME";
    private static final int spacing = 20;

    private ScreenManager myManager;
    private TextField myName;
    private VBox myLayout;

    public GameDescriptionPopup(ScreenManager manager){
        super();
        myManager = manager;
        setUpScene();
    }

    @Override
    protected void setUpScene() {
        setTitle(TITLE);
        myLayout = new VBox(spacing);
        //TODO: Add map of existing properties to this list
        myLayout.getChildren().addAll(addGameName(),
                addCloseButtons());
        myLayout.setAlignment(Pos.CENTER);
        myLayout.setPadding(new Insets(spacing));
        addSceneToPopup(myLayout);
    }

    private HBox addGameName() {
        var box = addHBox();
        box.getChildren().add(new Label("Game Name: "));
        myName = new TextField();
        myName.setPrefWidth(200);
        box.getChildren().add(myName);
        return box;
    }

    private HBox addCloseButtons() {
        var box = addHBox();
        box.getChildren().addAll(makeButton("Close", e-> getStage().close()),
                makeButton("Save", e-> {
                    if (!myName.getText().equals("")){
                        myManager.setName(myName.getText());
                        myManager.jumpToGameViewScreen();
                        getStage().close();
                    }}));
        return box;
    }

    private HBox addHBox() {
        var box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(spacing);
        box.setPadding(new Insets(spacing));
        return box;
    }

    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
