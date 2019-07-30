package Manager;

/**
 * Abstract controller from which each screen's controllers are inherited. A controller ~controls~ the actions and events
 * of each screen, also tying the screen to the backend.\
 * @author: Irene Qiao
 * @author: Alejandro Meza
 */

import external.GameCreator;

public abstract class Controller {

    private GameCreator myCreator;
    private ScreenManager myManager;

    public Controller(ScreenManager manager, GameCreator creator) {
        myCreator = creator;
        myManager = manager;
    }

    /**
     * Returns the GameCreator, API with engine.
     * @return
     */
    protected GameCreator getGameCreator(){
        return myCreator;
    }

    /**
     * Returns the Screen Manager, the class that manages all of the controller and their sccreens and popups
     * @return
     */
    protected ScreenManager getScreenManager(){
        return myManager;
    }

    /**
     * Allows extensibility for the addition of new properties to any of the authoring screens. A property can have a name
     * and a value
     * @param propertyName: name of the property to add.
     * @param propertyValue: value of the property to add.
     */
    public abstract void saveNewProperty(String propertyName, String propertyValue);

}
