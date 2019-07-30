package test;

import Manager.ScreenManager;
import Screens.MapViewScreen.AddPropertyConditionPopup;
import Screens.MapViewScreen.MapViewController;
import Screens.MapViewScreen.MapViewScreen;
import Utilities.GameMap;
import external.GameCreator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class ScreenTest {

    private MapViewController myController;
    private GameCreator myCreator;
    private ScreenManager myManager;
    private Stage myStage;
    private MapViewScreen myMapViewScreen;
    private Robot myRobot;

    @BeforeAll
    public static void setUpClass () {
        // explicitly use the most stable robot implementation to avoid some older versions
        //   https://stackoverflow.com/questions/52605298/simple-testfx-example-fails
        System.getProperties().put("testfx.robot", "glass");
    }


    @BeforeEach
    protected void setUp(){
//        myStage = new Stage();
//        myManager = new ScreenManager(myStage);
//        myCreator = new GameCreator();
//        myController = new MapViewController(myManager, myCreator);
////        try {
////            String mapID = "map";
////            GameMap myGameMap = new GameMap(mapID, new File(getClass().getClassLoader().getResource("backgrounds").toURI()), myManager, myController);
////            myMapViewScreen = new MapViewScreen(myGameMap, myManager, myController);
////        }
////        catch (URISyntaxException u) {
////            //do nothing
////        }
//        myRobot = new Robot();

    }

    @Test
    public void testAddPropertyConditionPopup(){
        AddPropertyConditionPopup myPropertyConditionPopup = new AddPropertyConditionPopup(myController);
    }

    @Test
    public void testSaveGame(){

    }
}
