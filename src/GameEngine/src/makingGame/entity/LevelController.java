package makingGame.entity;

import exception.IllegalPropertyException;

public class LevelController extends GameObject {

    public LevelController(String entityType) {
        super(entityType);
        createProperty("xPosition", "-1000");
        createProperty("yPosition", "-1000");
    }

    /**
     * Sets the property value of an instance
     * @param instanceId
     * @param property
     * @param newValue
     */
    @Override
    public void setPropertyValueOfInstance(String instanceId, String property, String newValue){
        if(!getListOfProperties().contains(property)) {
            throw new IllegalPropertyException();
        }
        myInstances.get(instanceId).put(property, newValue);
    }

}
