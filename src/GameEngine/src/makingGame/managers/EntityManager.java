package makingGame.managers;

import makingGame.entity.Entity;
import makingGame.entity.GameObject;
import makingGame.entity.LevelController;
import makingGame.interactions.events.EventPackage;
import javafx.util.Pair;

import java.util.*;

public class EntityManager {
    //made temperorily public
    public Map<String, GameObject> myEntities;
    private Map<String, Pair<String, String>> mainCharacterMap;
    private Map<String, List<EventPackage>> myEventPackages; //entityName -> list of event packages
    private Pair<Double, Double> myScreenSize;
    private Map<String, String> myBackgroundImagePath;

    public EntityManager() {
        myEntities = new HashMap<>();
        mainCharacterMap = new HashMap<>();
        myEventPackages = new HashMap<>();
        myBackgroundImagePath = new HashMap<>();
    }

    /**
     * Sets the background image for a level to the image at path
     * @param currentLevel
     * @param path
     */
    public void setMyBackgroundImagePath(String currentLevel, String path) {
        myBackgroundImagePath.put(currentLevel, path);
    }

    /**
     * Returns the background map
     * @return
     */
    public Map<String, String> getMyBackgroundMap() {
        return myBackgroundImagePath;
    }

    /**
     * Sets the screen size of the game
     * @param screenSize
     */
    public void setMyScreenSize(Pair<Double, Double> screenSize){
        myScreenSize = screenSize;
    }

    /**
     * Gets the screen size of the game
     * @return
     */
    public Pair<Double, Double> getMyScreenSize(){
        return myScreenSize;
    }

    /**
     * Creates a new entity
     * @param entityName
     */
    public void createEntity(String entityName) {
        myEntities.put(entityName, new Entity(entityName));
        myEventPackages.put(entityName, new ArrayList<>());
    }

    /**
     * Creates a level controller
     * @param entityName
     */
    public void createLevelEntity(String entityName) {
        myEntities.put(entityName, new LevelController(entityName));
        myEventPackages.put(entityName, new ArrayList<>());
    }

    /**
     * Adds an instance of an entity
     * @param entityName
     * @param instanceID
     */
    public void addInstance(String entityName, String instanceID){
        myEntities.get(entityName).createInstance(instanceID);
    }

    /**
     * Gets an entity based on id
     * @param id
     * @return
     */
    public GameObject getEntity(String id){
        return myEntities.get(id);
    }

    /**
     * Adds an already initialized event package to an entitiy
     * @param entityType
     * @param ep
     */
    public void addEventPackageToEntity(String entityType, EventPackage ep){
        myEventPackages.get(entityType).add(ep);
    }

    /**
     * Removes an event package from a specific entity
     * @param entityType
     * @param ep
     */
    public void removeEventPackageFromEntity(String entityType, EventPackage ep){
        myEventPackages.get(entityType).remove(ep);

    }

    /**
     * Adds a property with a default value to an entity
     * @param entityType
     * @param property
     * @param value
     */
    public void addPropertyToEntity(String entityType, String property, String value){
        myEntities.get(entityType).createProperty(property, value);

    }

    /**
     * Returns a map of event packages from entity:eventpackages
     * @return
     */
    public Map<String, List<EventPackage>> getMyEventPackages() {
        return myEventPackages;
    }

    /**
     * Sets the value of a property of an instance to a value
     * @param entityName
     * @param instanceId
     * @param property
     * @param newValue
     */
    public void setPropertyValueOfInstance(String entityName, String instanceId, String property, String newValue){
        myEntities.get(entityName).setPropertyValueOfInstance(instanceId, property, newValue);
    }

    /**
     * Gets a list of properties of the entitiy
     * @param entityType
     * @return
     */
    public Collection<String> getListOfProperties(String entityType){
        GameObject myEntity = myEntities.get(entityType);
        return myEntity.getListOfProperties();
    }

    /**
     * Sets an instance in a level to be the main character
     * @param entityType
     * @param instanceID
     * @param currentLevel
     */
    public void setMainCharacterID(String entityType, String instanceID, String currentLevel){
        mainCharacterMap.put(currentLevel, new Pair<>(entityType, instanceID));
    }

    /**
     * Get the main character map which indicates which instance is the main character in each level
     * @return
     */
    public Map<String, Pair<String, String>> getMainCharacterMap(){
        return Collections.unmodifiableMap(mainCharacterMap);
    }

    /**
     * Check if an entity exists
     */
    public boolean checkEntityExists(String entityName) {
        return (myEntities.get(entityName) != null);
    }

    /**
     * Deletes an entity
     * @param entityName
     */
    public void deleteEntity(String entityName){
        myEntities.remove(entityName);
    }

    /**
     * Gets a map of all EntityName: Entity
     * @return
     */
    public Map<String, GameObject> getMapOfEntities(){
        return Collections.unmodifiableMap(myEntities);
    }

    /**
     * Gets event packages' names that belong to the entity
     * @param entityName
     * @return
     */
    public List<String> getEventPackagesOfEntity(String entityName) {
        List<String> out = new ArrayList<>();
        List<EventPackage> eventPackages = myEventPackages.get(entityName);
        for (EventPackage e : eventPackages) {
            out.add(e.getMyId());
        }
        return out;
    }

    /**
     * Get a list of all entities
     * @return
     */
    private List<String> getAllEntityTypes() {
        List<String> myTypes = new ArrayList<>(myEntities.keySet());
        return Collections.unmodifiableList(myTypes);
    }


}
