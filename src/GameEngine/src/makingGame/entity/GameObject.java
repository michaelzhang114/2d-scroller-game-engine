package makingGame.entity;

import FSM.FSM;
import exception.IllegalInstanceException;
import exception.IllegalPropertyException;
import javafx.util.Pair;

import java.util.*;

public abstract class GameObject {
    /**
     * This class houses all of the entities that are defined by
     */

    protected Map<String, Map<String, String>> myInstances;
    protected String entityType;
    private List<Pair> defaultProperties;

    protected ArrayList<FSM> FSMs;
    protected ArrayList<String> ImagePaths;


    public GameObject(String entityType) {
        this.entityType = entityType;
        myInstances = new HashMap<>();
        defaultProperties = new ArrayList<>();
        createDefaultProperties();
        FSMs=new ArrayList<FSM>();
    }

    protected void createDefaultProperties(){
        createProperty("xPosition", "0");
        createProperty("yPosition", "0");
        createProperty("xVelocity", "0");
        createProperty("yVelocity", "0");
        createProperty("xAcceleration", "0");
        createProperty("yAcceleration", "0");
        createProperty("imagePath", "mario.png");
        createProperty("onScreen", "0");
        createProperty("level", "0");
        createProperty(BasicProperties.IMAGE_INDEX, "0");
        createProperty(BasicProperties.XSCALE,"1");
    }

    /**
     * Creates an instance "template" of this entity. It does not initialize the instance, but just creates its property map that will later be used
     * to create an Instance
     * @param instanceID
     */
    public void createInstance(String instanceID){
        if(myInstances.containsKey(instanceID)){
            throw new IllegalInstanceException();
        }
        myInstances.put(instanceID, new HashMap<>());
        addDefaultProperties(instanceID);
    }

    /**
     * Deletes an instance
     * @param instanceID
     */
    public void deleteInstance(String instanceID){
        myInstances.remove(instanceID);
    }

    /**
     * adds a property with a default value to an instance
     * @param ID
     */
    private void addDefaultProperties(String ID){
        for(Pair<String, String> propertyValue : defaultProperties){
            myInstances.get(ID).put(propertyValue.getKey(),propertyValue.getValue());
        }
    }

    /**
     * Creates a property with a default value for an entity
     * @param propertyID
     * @param defaultValue
     */
    public void createProperty(String propertyID, String defaultValue){
        defaultProperties.add(new Pair<>(propertyID, defaultValue));
        for(String instanceID : myInstances.keySet()){
            myInstances.get(instanceID).put(propertyID, defaultValue);
        }
    }

    /**
     * Gets the default value of a property
     * @param property
     * @return
     */
    public String getValueOfProperty(String property){
        for(Pair<String, String> propertyValue : defaultProperties){
            if (propertyValue.getKey().equals(property)) {
                return propertyValue.getValue();
            }
        }
        throw new IllegalPropertyException();
    }

    /**
     * Gets the value of an instance's property
     * @param instanceID
     * @param property
     * @return
     */
    public String getValueOfProperty(String instanceID, String property){
        return myInstances.get(instanceID).get(property);
    }

    /**
     * Gets a list of all instance IDs
     * @return
     */
    public Collection<String> getListOfInstanceIDs() {
        return myInstances.keySet();
    }

    /**
     * Sets the value of a property for a specific instance
     * @param instanceId
     * @param property
     * @param newValue
     */
    public void setPropertyValueOfInstance(String instanceId, String property, String newValue){
        if(!getListOfProperties().contains(property)) {
            throw new IllegalPropertyException();
        }
        myInstances.get(instanceId).put(property, newValue);
    }

    /**
     * Gets a list of properties
     * @return
     */
    public Collection<String> getListOfProperties(){
        List<String> defPropStrings = new ArrayList<>();
        for(Pair<String, String> defaultProperty : defaultProperties){
            defPropStrings.add(defaultProperty.getKey());
        }
        return defPropStrings;
    }

    /**
     * Gets the property map for a single instance
     * @param instanceID
     * @return
     */
    public Map<String, String> getPropertyMapOfInstance(String instanceID){
        return myInstances.get(instanceID);
    }

    /**
     * Gets the name of the entitiy
     * @return
     */
    public String getEntityName(){
        return entityType;
    }

    /**
     * Adds a list of FSMs to an entity
     */
    public void addFSMs(ArrayList<FSM> fsms){
        FSMs.addAll(fsms);
    }

    /**
     * Adds a single FSM
     * @param fsm
     */
    public void addFSM(FSM fsm){
        FSMs.add(fsm);
    }

    /**
     * Get all of the FSMs for the entity
     * @return
     */
    public ArrayList<FSM> getFSMs(){
        return FSMs;
    }
}
