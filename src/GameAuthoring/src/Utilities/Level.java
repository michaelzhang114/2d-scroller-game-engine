package Utilities;

import Manager.ScreenManager;
import Screens.LevelViewScreen.LevelViewController;
import Screens.LevelViewScreen.LevelViewScreen;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private List<GameMap> myMaps;
    private String myLevelID;
    private Screen myLevelScreen;

    public Level(String ID, ScreenManager manager, LevelViewController controller){
        myLevelID = ID;
        myMaps = new ArrayList<>();
        myLevelScreen = new LevelViewScreen(this, manager, controller);
    }

    public List<GameMap> getMaps() { return myMaps; }

    public String getLevelID() { return myLevelID; }

    public Screen getScreen() { return myLevelScreen; }

    public void clearMaps() {
        for (GameMap i :  myMaps){
            myMaps.remove(i);
        }
    }
}
