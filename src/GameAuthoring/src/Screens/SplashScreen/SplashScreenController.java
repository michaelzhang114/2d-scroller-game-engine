package Screens.SplashScreen;

import Utilities.Screen;
import Manager.ScreenManager;
import external.GameCreator;

public class SplashScreenController {

    private ScreenManager myManager;
    private SplashScreen myScreen;

    public SplashScreenController(ScreenManager manager, GameCreator creator) {
        myScreen = new SplashScreen(manager, this);
        myManager = manager;
    }

    public Screen getScreen() { return myScreen; }

    public void addGameDescription() {
        var s = new GameDescriptionPopup(myManager);
        s.show();
    }
}
