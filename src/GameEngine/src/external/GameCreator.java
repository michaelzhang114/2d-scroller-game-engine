package external;


import FSM.FSM;
import data.FileManager;
import data.Packager;
import exception.*;
import javafx.util.Pair;
import makingGame.entity.GameObject;
import makingGame.interactions.events.EventPackage;
import makingGame.managers.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GameCreator implements AuthoringAPI{

    /**
     * The GameCreator class houses the communication between the the Authoring environment and Engine. It has the functionality
     * to define conditions and actions and tie them to event packages, create entities and tie event packages to entities, create instances
     * of entities, and modify all previously mentioned components that make up a game. This is used by the game authoring environment to
     * edit the game engine.
     */
    private final String LEVEL_CONTROLLER_IMAGE_PATH = "mario.png";
    private final String LEVEL_CONTROLLER_ENTITY_NAME = "levelController";
    private final String LEVEL_CONTROLLER_INSTANCE_ID = "lc";

    private EntityManager myEntityManager;
    private EventPackageManager myEventPackages;
    private List<String> myLevels; //XML'd
    private String myCurrentLevel;
    private Pair<String, String> myScreenSize;
    private Packager myPackager;
    private FileManager myFileManager;
    private String myGameName;

    private int levelChangeId;

    public GameCreator() {
        myEventPackages = new EventPackageManager();
        myEntityManager = new EntityManager();
        myLevels = new ArrayList<>();
        myPackager = new Packager();
        myFileManager = new FileManager();

        // defaults
        levelChangeId = 0;
        myCurrentLevel = "0";
        myLevels.add(myCurrentLevel);
        myScreenSize = new Pair<>("1280","1000");
        myEntityManager.setMyScreenSize(new Pair<>(Double.parseDouble(myScreenSize.getKey()), Double.parseDouble(myScreenSize.getValue())));

        createLevelControllerEntity();
    }

    /**
     * Sets the name of the game
     * @param gameName
     */
    public void setGameName(String gameName){
        myGameName = gameName;
        myFileManager.addGame(myGameName);
    }

    /**
     * Saves the state of the game during authoring into an XML file handled by the file manager
     */
    public void saveGame(){
        try {
            String xmlString = serializeEntityManager();
            myFileManager.saveOriginalGame(myGameName, xmlString);
        }
        catch (IOException e){
            //TODO catch error
        }
    }

    /**
     * Authoring can load a game's XML file to continue editing.
     */
    public void loadGame(){
        String xmlString = myFileManager.loadOriginalGameXML(myGameName);
        myEntityManager = loadEntityManager(xmlString);
    }

    /**
     * Sets the icon of the game that will later be seen in game playing
     * @param iconFilePath
     */
    public void setIcon(String iconFilePath){
        myFileManager.addIconToGame(myGameName, iconFilePath);
    }

    /**
     * Sets the instructions of the game that will give users instructions during game playing
     * @param instructions
     */
    public void setInstructions(String instructions){
        myFileManager.addInstructionsToGame(myGameName, instructions);
    }

    /**
     * Sets the description of the game that will give users a description of the game during game playing
     * @param description
     */
    public void setDescription(String description){
        myFileManager.addDescriptionToGame(myGameName, description);
    }

    private void createLevelControllerEntity() {
        if(myEntityManager.checkEntityExists(LEVEL_CONTROLLER_ENTITY_NAME)){
            throw new IllegalEntityException();
        }
        myEntityManager.createLevelEntity(LEVEL_CONTROLLER_ENTITY_NAME);
        createEntityInstance(LEVEL_CONTROLLER_ENTITY_NAME, LEVEL_CONTROLLER_INSTANCE_ID, LEVEL_CONTROLLER_IMAGE_PATH);
    }

    /**
     * sets the background image of the game for the current level
     * @param imagePath
     */
    public void setBackgroundImage(String imagePath) {
        myEntityManager.setMyBackgroundImagePath(myCurrentLevel, imagePath);
    }

    /**
     * Maps an already initialized condition to an already initialized level change action
     * @param levelEventPackageId
     * @param conditionId
     */
    public void addConditionToLevelChangeEventPackage(String levelEventPackageId, String conditionId) {
        myEventPackages.addCondition(levelEventPackageId, conditionId);
    }

    /**
     * Initializes a level change event package
     * @param eventPackageID
     * @param nextLevel
     */
    public void createLevelChangeEventPackage(String eventPackageID, String nextLevel){
        createEventPackage(eventPackageID);
        addEventPackageToEntity(LEVEL_CONTROLLER_ENTITY_NAME, eventPackageID);
        createPropertyCondition("levelChange"+levelChangeId, "level", "==", myCurrentLevel);
        addConditionToPackage(eventPackageID, "levelChange"+levelChangeId);
        // different action IDs each time
        createAction("levelAction"+levelChangeId, "level", "=", nextLevel);
        addActionToEventPackage(eventPackageID, "levelAction"+levelChangeId);
        levelChangeId++;
    }

    /**
     * Sets the current level of the game to edit
     * @param levelId
     */
    public void setCurrentLevel(String levelId) {
        myCurrentLevel = levelId;
        myLevels.add(myCurrentLevel);
    }

    /**
     * Sets the screen size for the specific game
     * @param width
     * @param height
     */
    public void setGameScreenSize(String width, String height) {
        myEntityManager.setMyScreenSize(new Pair<>(Double.parseDouble(width), Double.parseDouble(height)));
    }

    /**
     * Sets the main character of the game to the instance with instanceId. This is mainly for following this character
     * around the screen.
     * @param entityName
     * @param instanceID
     */
    public void setMainCharacter(String entityName, String instanceID){
        myEntityManager.setMainCharacterID(entityName, instanceID, myCurrentLevel);
    }

    /**
     * Initializes an entity
     * @param entityName
     */
    public void createEntity(String entityName) {
        if(myEntityManager.checkEntityExists(entityName)){
            throw new IllegalEntityException();
        }

        myEntityManager.createEntity(entityName);
    }

    /**
     * Initializes an instance of an entity and gives it an ID and an imagePath
     * @param entityName
     * @param instanceId
     * @param imagePath
     */
    public void createEntityInstance(String entityName, String instanceId, String imagePath) {
        myEntityManager.addInstance(entityName, instanceId);
        myEntityManager.setPropertyValueOfInstance(entityName, instanceId, "imagePath", imagePath);
        myEntityManager.setPropertyValueOfInstance(entityName, instanceId, "level", myCurrentLevel);
    }

    /**
     * Deletes an entity
     * @param entityID
     */
    public void deleteEntity(String entityID) {
        myEntityManager.deleteEntity(entityID);
    }

    /**
     * Ties an already defined event package to a specific entity
     * @param entityID
     * @param eventPackageID
     */
    public void addEventPackageToEntity(String entityID, String eventPackageID){
        myEntityManager.addEventPackageToEntity(entityID, myEventPackages.getEventPackage(eventPackageID));
    }

    /**
     * Initializes an event package
     * @param eventPackageID
     */
    public void createEventPackage(String eventPackageID){
        myEventPackages.addEventPackage(eventPackageID, new EventPackage(eventPackageID));
    }

    /**
     * adds an already defined condition to an event package
     * @param eventPackageID
     * @param conditionID
     */
    public void addConditionToPackage(String eventPackageID, String conditionID) {
        myEventPackages.addCondition(eventPackageID, conditionID);
    }

    /**
     * Creates a property condition. This property condition if a property of an entity is satisfied. Operator is a boolean operator
     * and num is the number an instance's property value is being compared to
     * @param propertyEventID
     * @param property1
     * @param operator
     * @param num
     */
    public void createPropertyCondition(String propertyEventID, String property1, String operator, String num){
        myEventPackages.createPropertyCondition(propertyEventID, property1, operator, num);
    }

    /**
     * Creates a property condition but is limited to having a value of type string.
     * @param propertyEventID
     * @param property1
     * @param value
     */
    public void createPropertyConditionString(String propertyEventID, String property1, String value){
        myEventPackages.createPropertyCondition(propertyEventID, property1, value);
    }

    /**
     * Creates a property condition for a specific entity.
     * @param propertyEventID
     * @param entity
     * @param property1
     * @param operator
     * @param num
     */
    public void createPropertyCondition(String propertyEventID, String entity, String property1, String operator, String num){
        myEventPackages.createPropertyCondition(propertyEventID, property1, entity, operator, num);
    }

    /**
     * Creates a key input condition. If all of the keys in keys are being pressed then the condition evaluates true
     * @param keyEventID
     * @param keys
     */
    public void createKeyInputCondition(String keyEventID, List<String> keys){
        myEventPackages.createKeyInputCondition(keyEventID, keys);
    }

    /**
     * Creates a collision condition where any entity that holds this condition has this condition evaluate to true if
     * if is colliding with entityID in a specific way defined by collisionType (top, bottom, left, right, any).
     * @param collisionID
     * @param entityID
     * @param collisionType
     */
    public void createCollisionCondition(String collisionID, String entityID, String collisionType) {
        myEventPackages.createCollision(collisionID, entityID, collisionType);
    }

    /**
     * deletes a property condition from a package using their IDs
     * @param eventPackageID
     * @param propertyEventID
     */
    public void deletePropertyConditionFromPackage(String eventPackageID, String propertyEventID){
        myEventPackages.deleteConditionFromPackage(eventPackageID, propertyEventID);
    }

    /**
     * Deletes a key input condition from an event package using their IDs
     * @param eventPackageID
     * @param keyEventID
     */
    public void deleteKeyInputConditionFromPackage(String eventPackageID, String keyEventID){
        myEventPackages.deleteConditionFromPackage(eventPackageID, keyEventID);
    }

    /**
     * Deletes a collision condition from a package using IDs.
     * @param eventPackageID
     * @param collisionID
     */
    public void deleteCollisionCondtionFromPackage(String eventPackageID, String collisionID) {
        myEventPackages.deleteConditionFromPackage(eventPackageID, collisionID);
    }

    /**
     * Sets a priority for an event package to be executed. If there are competing event packages whose
     * execution orders cause different outcomes, priorities can be set.
     * @param eventPackageID
     * @param priority
     */
    public void setPriorityToEventPackages(String eventPackageID, String priority){
        myEventPackages.getEventPackage(eventPackageID).setPriority(priority);
    }

    /**
     * Creates an action which sets an entity's property to a certain value
     * @param actionID
     * @param property1
     * @param operator
     * @param amount
     */
    public void createAction(String actionID, String property1, String operator, String amount){
        myEventPackages.createAction(actionID, property1, operator, amount);
    }

    /**
     * Creates an action which edits a specific entity's instance property to a certain value
     * @param actionID
     * @param entityID
     * @param property1
     * @param operator
     * @param amount
     */
    public void createAction(String actionID, String entityID,  String property1, String operator, String amount){
        myEventPackages.createAction(actionID, entityID, property1, operator, amount);
    }

    /**
     * Creates an action that modifies a string property of an entity
     * @param actionID
     * @param property1
     * @param amount
     */
    public void createActionString(String actionID, String property1, String amount){
        myEventPackages.createStringAction(actionID, property1, amount);
    }

    /**
     * Creates an action that modifies a string property of an entity's specific instance
     * @param actionID
     * @param entityID
     * @param property1
     * @param amount
     */
    public void createActionString(String actionID, String entityID, String property1, String amount){
        myEventPackages.createStringAction(actionID, entityID, property1, amount);
    }

    /**
     * Ties an already defined action to an already initialized event package
     * @param eventPackageID
     * @param actionID
     */
    public void addActionToEventPackage(String eventPackageID, String actionID) {
        myEventPackages.addAction(eventPackageID, actionID);
    }

    /**
     * Creates a new property in an entity with a default valuec
     * @param entityName
     * @param propertyName
     * @param defaultValue
     */
    public void createPropertyInEntity(String entityName, String propertyName, String defaultValue) {
        GameObject currentEntity = myEntityManager.getEntity(entityName);
        currentEntity.createProperty(propertyName, defaultValue);
    }

    /**
     * Sets the value of a property of a specific entity instance
     * @param entityName
     * @param instanceId
     * @param propertyID
     * @param value
     */
    public void setPropertyToEntityInstance(String entityName, String instanceId, String propertyID, String value){
        myEntityManager.setPropertyValueOfInstance(entityName, instanceId, propertyID, value);
    }
    public static final String fsmpath="FSM.presetFSMs.";

    /**
     * Adds am FSM to a specific entity instance
     * @param entityName
     * @param instanceId
     * @param FSMName
     */
    public void addFSMToEntityInstance(String entityName, String instanceId, String FSMName){
        //should use reflection here to make the class
        FSM fsmtoadd=null;

        fsmtoadd=reflectpresetFSMFromName(FSMName);

        if (fsmtoadd==null){
            throw new RuntimeException("trying to add a fsm that hasn't been created");
        }else{
            GameObject entityinstance=myEntityManager.myEntities.get(entityName);
//                    getEntity(instanceId);
            entityinstance.addFSM(fsmtoadd);
        }
    }


    /**
     * Sets a key input condition to now represent an inverse hold. (From pressing A to releasing A key)
     * @param keyInputID
     */
    public void setInverseKeyInputCondition(String keyInputID){
        myEventPackages.getKeyInputCondition(keyInputID).setInverse();
    }


    /**
     * Returns a list of all initialized entities
     * @return
     */
    public Collection<String> getListOfEntities(){
        return myEntityManager.getMapOfEntities().keySet();
    }

    /**
     * Returns a list of all defined properties for an entity
     * @param entityName
     * @return
     */
    public Collection<String> getEntityProperties(String entityName) {
        GameObject currentEntity = myEntityManager.getEntity(entityName);
        return currentEntity.getListOfProperties();
    }

    /**
     * Returns the default property value for an entity
     * @param entityName
     * @param property
     * @return
     */
    public String getDefaultPropertyValue(String entityName, String property) {
        GameObject currentEntity = myEntityManager.getEntity(entityName);
        return currentEntity.getValueOfProperty(property);
    }

    /**
     * Returns the property map of an instance
     * @param entityName
     * @param instanceId
     * @return
     */
    public Collection<String> getInstanceProperties(String entityName, String instanceId) {
        GameObject currentEntity = myEntityManager.getEntity(entityName);
        return currentEntity.getPropertyMapOfInstance(instanceId).keySet();
    }

    /**
     * Gets the value of a property for a specific instance
     * @param entityName
     * @param instanceId
     * @param property
     * @return
     */
    public String getPropertyValue(String entityName, String instanceId, String property) {
        GameObject currentEntity = myEntityManager.getEntity(entityName);
        return currentEntity.getValueOfProperty(instanceId, property);
    }

    /**
     * Returns a list of all of the event packages for an entity
     * @param entityName
     * @return
     */
    public Collection<String> getEventPackagesOfEntity(String entityName) {
        return myEntityManager.getEventPackagesOfEntity(entityName);
    }

    /**
     * Returns a list of all event packages defined
     * @return
     */
    public Collection<String> getAllEventPackages() {
        return myEventPackages.getAllEventPackagesIds();
    }

    /**
     * Returns a list of conditions tied to an event package
     * @param eventPackageId
     * @return
     * @throws IllegalEventPackageException
     */
    public Collection<String> getConditionIds(String eventPackageId) throws IllegalEventPackageException {
        try {
            return myEventPackages.getConditionIdsFromEventPackageId(eventPackageId);
        }
        catch (IllegalEventPackageException e){
            throw new IllegalEventPackageException();
        }
    }

    /**
     * Returns a list of actions tied to an event package
     * @param eventPackageId
     * @return
     */
    public Collection<String> getActionIds(String eventPackageId) {
        return myEventPackages.getActionIdsFromEventPackageId(eventPackageId);
    }

    /**
     * Returns a list of all defined conditions
     * @return
     * @throws ConditionNotFoundException
     */
    public Collection<String> getAllConditions() throws ConditionNotFoundException{
        try {
            return myEventPackages.getAllConditions();
        }
        catch (ConditionNotFoundException e){
            throw new ConditionNotFoundException();
        }
    }

    /**
     * Returns a list of all of the defined actions
     * @return
     */
    public Collection<String> getAllActions() {
        return myEventPackages.getAllActions();
    }

    /**
     * Returns an action name tied to an action ID
     * @param actionId
     * @return
     */
    public List<String> getAction(String actionId) {
        return myEventPackages.getActionFromActionId(actionId);
    }

    /**
     * Returns a condition map from a specific condition id
     * @param conditionId
     * @return
     */
    public Map<String, String> getCondition(String conditionId) {
        return myEventPackages.getConditionFromConditionId(conditionId);
    }

    /**
     * Returns all instances
     * @param entityName
     * @return
     */
    public Collection<String> getEntityInstances(String entityName) {
        return myEntityManager.getEntity(entityName).getListOfInstanceIDs();
    }

    /**
     * deletes an instance
     * @param entity
     * @param instanceID
     */
    public void deleteInstance(String entity, String instanceID){
        myEntityManager.getEntity(entity).deleteInstance(instanceID);
    }


    private String serializeEntityManager() throws IOException {
        return myPackager.saveEntityManager(myEntityManager);
    }

    private EntityManager loadEntityManager(String xmlString){
        return myPackager.loadEntityManager(xmlString);
    }


    private FSM reflectpresetFSMFromName(String FSMName){
        String classname=fsmpath+FSMName;
        Constructor<FSM> constructor=(Constructor<FSM>) makeCtorFromClassName(classname);
        try{
            return constructor.newInstance();
        }catch (Exception e){
            //e.printStackTrace();
            throw new RuntimeException("Can't find fsm "+classname);
        }
    }

    private Constructor<?> makeCtorFromClassName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            System.out.println(clazz.getName());
            return clazz.getConstructor();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}