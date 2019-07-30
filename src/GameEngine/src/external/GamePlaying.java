package external;

import data.FileManager;
import data.Packager;
import makingGame.entity.GameObject;
import makingGame.interactions.events.EventPackage;
import javafx.scene.Scene;
import javafx.util.Pair;
import makingGame.managers.EntityManager;
import playingGame.gamerunning.GameEnvironment;
import playingGame.manager.LevelManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GamePlaying {
    /**
     * API for the game player to use. Allows the Game PLayer to load, save, pause, and continue a game.
     */
    private FileManager myFileManager;
    GameEnvironment game;
    private String myGameName;
    private Packager myPackager;
    private EntityManager myEntityManager;

    public GamePlaying(String gameName){
        myGameName = gameName;
        myPackager = new Packager();
    }

    /**
     * Loads the XML file for a game and initializes all of the image views for each object
     */
    public void loadGame(){
        myFileManager = new FileManager();
        Packager myPackager = new Packager();
        String xml = myFileManager.loadOriginalGameXML(myGameName);
        myEntityManager = myPackager.loadEntityManager(xml); //this is what should be loaded here


        // here we get info to make the game environment
        Map<String, String> backgroundImageMap = myEntityManager.getMyBackgroundMap();

        Map<String, GameObject> myEntities = myEntityManager.getMapOfEntities();


        Map<String, List<EventPackage>> myEvents = myEntityManager.getMyEventPackages();



        Pair<Double, Double> myScreenSize = myEntityManager.getMyScreenSize();

        Map<String, Pair<String, String>> mainCharacterMap = myEntityManager.getMainCharacterMap(); //i'm passing this to LevelManager so in the future it could be a map and main character can change
        // here we make the game environment
        //System.out.println(Arrays.asList(mainCharacterMap));

        LevelManager myLevels = new LevelManager(myEntities, mainCharacterMap);
        game = new GameEnvironment(myLevels, myEvents, myScreenSize, backgroundImageMap);
    }

    /**
     * Runs the game
     */
    public void runGame(){
        game.run();
    }

    /**
     * Returns the scene of the initalized game
     * @return
     */
    public Scene getScene(){
        return game.getScene();
    }

    /**
     * pauses the game
     */
    public void pauseGame() {
        game.pause();
    }

    /**
     * unpauses the game
     */
    public void continueGame() {
        game.run();
    }

    public boolean saveGame() {
        try {
            String xmlString = myPackager.saveEntityManager(myEntityManager);
            myFileManager.saveOriginalGame(myGameName, xmlString);
            return true;
        }
        catch (IOException e){
            return false;
        }
    }
}
