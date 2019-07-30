package Screens.GameViewScreen;

import Manager.Controller;
import Utilities.GameMap;
import Utilities.Level;
import Utilities.Screen;
import Manager.ScreenManager;
import external.GameCreator;

import java.util.ArrayList;

public class GameViewController extends Controller {

    private GameViewScreen myScreen;
    private ScreenManager myManager;

    public GameViewController(ScreenManager manager, GameCreator creator){
        super(manager, creator);
        myScreen = new GameViewScreen(manager, this);
        myManager = manager;
    }

    @Override
    public void saveNewProperty(String propertyName, String propertyValue) {

    }

    public Screen getScreen() { return myScreen; }


    public void addNewLevel(String id) {
        getScreenManager().addNewLevel(id);
    }

    public void setLevel(Level level) {
        getScreenManager().setLevel(level);
    }

    public void addNewLevelPopup(){
        var s = new AddNewLevelPopup(this);
        s.show();
    }

    public void undoAction() {
        getScreenManager().undoLevel();
        myScreen.update();
    }

    public void redoAction() {
        getScreenManager().redoLevel();
        myScreen.update();
    }

    public void saveGame(String gameName, String iconFilePath, String description, String instructions){
        getGameCreator().setGameName(gameName);
        getGameCreator().setIcon(iconFilePath);
        getGameCreator().setDescription(description);
        getGameCreator().setInstructions(instructions);
        for (Level l : myManager.getLevels()){
            for (GameMap m : myManager.getMapsOfLevel(l)){
                if (m.getScreen().getMainCharacter() != null) {
                    getGameCreator().setCurrentLevel(m.getMapID());
                    var main = m.getScreen().getMainCharacter();
                    ArrayList<String> instance = new ArrayList<>(getGameCreator().getEntityInstances(main));
                    getGameCreator().setMainCharacter(main, instance.get(0));
                }
            }
        }
        getGameCreator().saveGame();
    }
}
