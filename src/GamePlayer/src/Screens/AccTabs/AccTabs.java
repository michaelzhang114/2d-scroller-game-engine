package Screens.AccTabs;

import Database.DBGameStats;
import Database.GameStats;
import Screens.ScreenFunction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * This is an abstract superclass that contains methods that will be commonly used among the tabs within the account
 * section of the UI. The class contains both formatting methods and methods that get data from the database. This class
 * assumes that the screen size is never changed and depends on the methods from the database API to return the correct
 * values.
 *
 * @author Eric Werbel
 */
public abstract class AccTabs extends ScreenFunction {

    public static final String TIMES_PLAYED = "   Number of Times Played";
    public static final String HIGH_SCORE = "   High Score";
    public static final String AVERAGE_SCORE = "   Average Score";
    public static final String COLON = ": ";

    public static final int IMG_WIDTH = 45;
    public static final int REGION_WIDTH = 40;
    public static final int REGION_HEIGHT = 20;

    private String ROOT = System.getProperty("user.dir");
    private String GAME_DIRECTORY = "/data/games/";
    private String ICON = "/icon.png";

    private DBGameStats statsDB = new DBGameStats();

    protected HBox makeLRBHBox(String labelTxt, String buttonTxt, EventHandler<ActionEvent> handler) {
        HBox box = new HBox();
        Label l = new Label(labelTxt);
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        Button b = makeButton(buttonTxt, handler);
        box.getChildren().addAll(l, r, b);
        return box;
    }

    protected VBox showAccData(String username) {
        VBox dataBox = new VBox();
        List<GameStats> myGames = statsDB.getGamesPlayed(username);
        for (GameStats gs : myGames) {
            HBox gameBox = new HBox();
            VBox info = new VBox();
            String game = gs.getGame() + COLON;
            ImageView icon = getGameImage(gs.getGame());
            String timesPlayed = TIMES_PLAYED + COLON + gs.getNumTimesPlayed();
            String highScore = HIGH_SCORE + COLON + gs.getHighScore();
            String avgScore = AVERAGE_SCORE + COLON + gs.getAvgScore();
            info.getChildren().addAll(new Label(game), new Label(timesPlayed), new Label(highScore), new Label(avgScore));
            Region r = new Region();
            r.setPrefWidth(REGION_WIDTH);
            gameBox.getChildren().add(info);
            Region r2 = new Region();
            r2.setPrefHeight(REGION_HEIGHT);
            dataBox.getChildren().addAll(gameBox, r2);
        }
        return dataBox;
    }

    private ImageView getGameImage(String game) {
        String imagePath = ROOT + GAME_DIRECTORY + game + ICON;
        try {
            ImageView myImage = new ImageView(new Image(new FileInputStream(imagePath)));
            myImage.setFitWidth(IMG_WIDTH);
            myImage.setPreserveRatio(true);
            return myImage;
        } catch (FileNotFoundException e) {
            // TODO add default image
            return null;
        }
    }

}
