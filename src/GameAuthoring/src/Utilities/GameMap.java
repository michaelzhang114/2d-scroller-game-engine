package Utilities;

import Manager.ScreenManager;
import Screens.MapViewScreen.MapViewController;
import Screens.MapViewScreen.MapViewScreen;

public class GameMap {

    private MapViewScreen myScreen;
    private String myMapID;
    private String myBackground;

    public GameMap(String id, String background, ScreenManager manager, MapViewController controller) {
        myMapID = id;
        myBackground = background;
        myScreen = new MapViewScreen(this, manager, controller);
    }

    public MapViewScreen getScreen() {return myScreen;}

    public String getMapID() {return myMapID;}

    public String getBackground() { return myBackground; }

}
