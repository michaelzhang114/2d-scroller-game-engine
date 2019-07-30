package Utilities;

import java.io.File;

public class MapEntity {

    private String myEntityID;
    private String myInstanceID;
    private String myPath;
    private int myXPos;
    private int myYPos;

    public MapEntity(String entityID, String instanceID, String ImgPath, int xPos, int yPos) {
        myEntityID = entityID;
        myInstanceID = instanceID;
        myPath = ImgPath;
        myXPos = xPos;
        myYPos = yPos;
    }

    public String getEntityID() { return myEntityID; }

    public String getInstanceID() { return myInstanceID; }

    public String getEntityPath() { return myPath; }

    public int getXPos() { return myXPos; }

    public int getYPos() { return myYPos; }
}
