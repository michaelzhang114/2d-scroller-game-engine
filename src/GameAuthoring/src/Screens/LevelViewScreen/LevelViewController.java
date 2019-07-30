package Screens.LevelViewScreen;

import Manager.Controller;
import Utilities.GameMap;
import Utilities.Level;
import Utilities.Screen;
import Manager.ScreenManager;
import external.GameCreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class LevelViewController extends Controller {

    private LevelViewScreen myScreen;
    private String myBackgroundPath;

    public LevelViewController(ScreenManager manager, GameCreator creator) {
        super(manager, creator);
    }

    @Override
    public void saveNewProperty(String propertyName, String propertyValue) {

    }

    public void setScreen(Level level) {
        myScreen = (LevelViewScreen) level.getScreen();
    }

    public void setLevel(String id) {
        myScreen = (LevelViewScreen) getScreenManager().getLevels().get(getScreenManager().getLevels().indexOf(id)).getScreen();
    } //TODO: get levels from GameCreator

    public void addNewMap(String text) {
        getScreenManager().addNewMap(text, myBackgroundPath); //TODO: call GameCreator add map method here, set background img as map background
    }


    public void setMap(GameMap i) { getScreenManager().setMap(i); }

    public Screen getScreen() {
        return myScreen;
    }

    public void show() {
        myScreen.getStage().show();
    }


    public void addNewMapPopup() {
        myBackgroundPath = null;
        var s = new AddMapPopup(this);
        s.show();
    }

    public void openBackgroundGallery()  {
        var s = new BackgroundGallery(this);
        s.show();
    }


    public void addBackgroundToMap(String path) {
        myBackgroundPath = path;
    }

    public String getBackgroundPath() { return myBackgroundPath; }

    public void updateScreen() {
        myScreen.update();
    }

    public List<GameMap> getMapBackgrounds() { return getScreenManager().getMapsOfLevel();}

}
