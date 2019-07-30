package Screens.MapViewScreen;

import Manager.Controller;
import Utilities.MapEntity;
import Utilities.Screen;
import Manager.ScreenManager;
import exception.ConditionNotFoundException;
import external.GameCreator;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.*;

/**
 * The mapview controller controls the map view screen as well as all of the popups that the map view screen triggers.
 * It also ties the screen and its popups to game creator api, or the backend.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

public class MapViewController extends Controller {

    private EntityLibrary myEntityLibrary;
    private MapViewScreen myScreen;
    private String myCurrentEntityID;
    private String myCurrentInstanceID;
    private String myCurrentImgPath;
    private Map<String, String> myEntityMap;
    private EventsPopup myEventsPopup;
    private EntityPropertiesPopup myEntityPropertyPopup;
    private InstancePropertiesPopup myInstancePropertyPopup;


    public MapViewController(ScreenManager manager, GameCreator creator) {
        super(manager, creator);
        myEntityMap = new HashMap<>();
        myEventsPopup = new EventsPopup(new Stage(), this);
        myEntityLibrary = new EntityLibrary(new Stage(), this);
    }


    /**
     * Sets the screen of the map
     * @param screen: screen to set
     */
    public void setScreen(MapViewScreen screen) {
        myScreen = screen;
    }

    /**
     * Gets the screen of the map
     */
    public Screen getScreen() { return myScreen; }

    /**
     * Opens popup to add a new entity with path
     * @param path of entity
     */
    public void openAddNewEntity(String path) {
        var s = new AddNewEntityPopup(this, path);
        s.show();
    }

    /**
     * Opens entity gallery
     */
    public void openEntityGallery() {
        var s = new EntityGallery(this);
        s.show();
    }

    /**
     * Opens entity Library
     */
    public void openEntityLibrary() {
        myEntityLibrary.show();
    }

    /**
     * Opens Events Popup
     */
    public void openEventsPopup() {
        myEventsPopup.show();
    }

    /**
     * OPens popup to add a new event
     */
    public void openAddNewEvent() {
        var s = new AddNewEventPopup(new Stage(), this);
        s.show();
    }

    /**
     * Opens popup to edit the game environment
     */
    public void openEditEnvironment() {
        var s = new AddEnvironmentPropertyPopup();
        s.show();
    }

    /**
     * Opens popup to add a new condition to the game
     */
    public void openAddNewCondition() {
        var s = new AddNewConditionPopup(new Stage(), this);
        s.show();
    }

    /**
     * Opens popup to add a new collision condition
     */
    public void openAddCollisionEvent() {
        var s = new AddCollisionConditionPopup(this);
        s.show();
    }

    /**
     * Opens popup to add a new key-input condition
     */
    public void openAddKeyInputEvent() {
        var s = new AddKeyInputConditionPopup(this);
        s.show();
    }

    /**
     * Opens popup to add a new property condition
     */
    public void openAddPropertyEvent() {
        var s = new AddPropertyConditionPopup(this);
        s.show();
    }

    /**
     * Opens popup to add a new action
     */
    public void openAddAction() {
        var s = new AddActionPopup(new Stage(), this);
        s.show();
    }

    /**
     * Opens popup to edit the instance properties
     */
    public void openEditOptionsPopup(MapEntity entity, ImageView finalImg) {
        myCurrentEntityID = entity.getEntityID();
        myCurrentInstanceID = entity.getInstanceID();
        var s = new EditInstancePopup(entity, finalImg, this);
        s.show();
    }

    /**
     * Opens popup to edit or add properties to the game
     */
    public void openPropertiesPopup(){
        myEntityPropertyPopup =  new EntityPropertiesPopup(this);
        myEntityPropertyPopup.show();
    }

    /**
     * Opens the list of entity instance properties that exist
     */
    public void openInstancePropertiesPopup() {
        myInstancePropertyPopup = new InstancePropertiesPopup(this);
        myInstancePropertyPopup.show();
    }

    /**
     * Opens popup to edit a property of ID propertyID
     * @param propertyId: id of the property to be edited
     */
    public void editPropertyPopup(String propertyId) {
        var s = new EditPropertyPopup(this, propertyId);
        s.show();
    }


    /**
     * Updates event library
     */
    public void updateEventLibrary() { myEventsPopup.update();
    }

    /**
     * Updates event popup
     */
    public void updateEventsPopup(){
        myEventsPopup.update();
    }

    /**
     * Prepares drag and drop - used when a entity is selected to be added to the map
     * @param ID of the instance
     * @param path of the instance
     */
    public void prepareDragDrop(String ID, String path) { //TODO: add this method to PropertyPopups in library to assign entity
        myCurrentEntityID = ID;
        myCurrentImgPath = path;
    }

    /**
     * Returns the map of all the instances. Key - ID, value- path
     * @return
     */
    public Map<String, String> getEntityMap() {
        return myEntityMap;
    }

    /**
     * returns the current entity's id
     * @return
     */
    public String getEntityID() { return myCurrentEntityID; }

    /**
     * returns the current entity's image path
     * @return
     */
    public String getImgPath() { return myCurrentImgPath; }


    /**
     * adds a new entity to the game - ties to backend
     * @param name of entity
     * @param path of entity
     */
    public void addNewEntity(String name, String path){
        getGameCreator().createEntity(name);
        myEntityMap.put(name, path);
        myEntityLibrary.update();
    }

    /**
     * adds a new instance to the game - ties to backend
     * @param instanceID of entity
     * @param x position in map
     * @param y position in map
     */
    public void createNewEntityInstance(String instanceID, double x, double y){
        getGameCreator().createEntityInstance(myCurrentEntityID, instanceID, myCurrentImgPath);
        getGameCreator().setPropertyToEntityInstance(myCurrentEntityID, instanceID, "xPosition", "" + x);
        getGameCreator().setPropertyToEntityInstance(myCurrentEntityID, instanceID, "yPosition", "" + y);
    }

    /**
     * Saves a new property to an instance
     * @param propertyName: name of the property to add.
     * @param propertyValue: value of the property to add.
     */
    @Override
    public void saveNewProperty(String propertyName, String propertyValue) {
        getGameCreator().createPropertyInEntity(myCurrentEntityID, propertyName, propertyValue);
        myEntityPropertyPopup.update();
    }

    /**
     * Edits an instance property
     * @param propertyName: name of property
     * @param propertyValue: new value of property
     */
    public void editInstanceProperty(String propertyName, String propertyValue) {
        getGameCreator().setPropertyToEntityInstance(myCurrentEntityID, myCurrentInstanceID, propertyName, propertyValue);
        myInstancePropertyPopup.update();
    }

    /**
     * Creates a collision in the game
     * @param collisionID: id of collision
     * @param entityID: ID of entity affected
     * @param collisionType: type of collision
     */
    public void createCollisionCondition(String collisionID, String entityID, String collisionType){
        getGameCreator().createCollisionCondition(collisionID, entityID, collisionType);
    }

    /**
     * Creates a new key input condition in the game
     * @param keyEventID: id of condition
     * @param keys: key to trigger condition
     */
    public void createKeyInputCondition(String keyEventID, List<String> keys) {
        getGameCreator().createKeyInputCondition(keyEventID, keys);
    }

    /**
     * creates new property condition
     * @param conditionID: id of condition
     * @param entityID: entity affected
     * @param propertyID: property affected
     * @param value: by how much is the property affected
     * @param operator: how is the property affected
     */
    public void createPropertyCondition(String conditionID, String entityID, String propertyID, String value, String operator){
        getGameCreator().createPropertyCondition(conditionID, entityID, propertyID, value, operator);
    }

    /**
     * Creates action in the game
     * @param actionId
     * @param entityID: entity affected
     * @param property: property affected
     * @param operator: how is it affected
     * @param amount: by how much is it affected
     */
    public void createAction(String actionId, String entityID, String property, String operator, String amount){
        getGameCreator().createAction(actionId, entityID, property, operator, amount);
    }

    /**
     * Save new event packaged
     * @param eventID: event id
     * @param conditions: list of conditions to trigger event, all must be true
     * @param actions: actions triggered by conditions
     */
    public void saveNewEvent(String eventID, List<String> conditions, List<String> actions) {
        getGameCreator().createEventPackage(eventID);
        for (String conditionID: conditions){
            getGameCreator().addConditionToPackage(eventID, conditionID);
        }
        for (String actionID: actions){
            getGameCreator().addActionToEventPackage(eventID, actionID);
        }
    }

    /**
     * Adds an event package to an entity
     * @param entityID: entity to add package to
     * @param eventID: name of event
     */
    public void addEventPackageToEntity(String entityID, String eventID) {
        getGameCreator().addEventPackageToEntity(entityID, eventID);
    }

    /**
     * returns the current entity's ID
     * @return
     */
    public String getCurrentEntityID(){
        return myCurrentEntityID;
    }


    /**
     * Returns all entity properties
     * @param entityID: entity we want properties from
     * @return
     */
    public Collection<String> getEntityProperties(String entityID) {
        return getGameCreator().getEntityProperties(entityID);
    }

    /**
     * Return all instance properties of current entity
     * @return
     */
    public Collection<String> getInstanceProperties() {
        return getGameCreator().getInstanceProperties(myCurrentEntityID, myCurrentInstanceID);
    }

    /**
     * Return all event packages of entity
     * @param entityID: entity we want packages from
     * @return
     */
    public Collection<String> getEventPackagesOfEntity (String entityID){
        return getGameCreator().getEventPackagesOfEntity(entityID);
    }

    /**
     * Get the value of a property of current entity
     * @param propertyID: id of propertu we want value from
     * @return
     */
    public String getValueOfProperty(String propertyID) {
        return getGameCreator().getDefaultPropertyValue(myCurrentEntityID, propertyID);
    }

    /**
     * Get value of property of current instance
     * @param propertyID: property we want value from
     * @return
     */
    public String getInstanceValueOfProperty(String propertyID){
        return getGameCreator().getPropertyValue(myCurrentEntityID, myCurrentInstanceID, propertyID);
    }

    /**
     * return list of all entitites created in game
     * @return
     */
    public Collection<String> getListOfEntities(){
        return getGameCreator().getListOfEntities();
    }

    /**
     * return list of all conditions created in game
     * @return
     */
    public Collection<String> getListOfAllConditions () {
        try {
            return getGameCreator().getAllConditions();
        }
        catch (ConditionNotFoundException e){
            return new ArrayList<>();
        }
    }

    /**
     * return list of all actions created in game
     * @return
     */
    public Collection<String> getListOfAllActions(){
        return getGameCreator().getAllActions();
    }

    /**
     * return the info of a condition
     * @param conditionID: condition we want info from
     * @return
     */
    public Map<String, String> getConditionInfo(String conditionID){
        return getGameCreator().getCondition(conditionID);
    }

    /**
     * return info of action
     * @param actionID: action we want info from
     * @return
     */
    public List<String> getActionInfo(String actionID){
        return getGameCreator().getAction(actionID);
    }

    /**
     * delete an instance from a game
     * @param img: image view we want to delete
     * @param mapEntity: instance we want to delete
     */
    public void deleteInstance(ImageView img, MapEntity mapEntity) {
        myScreen.deleteInstance(img, mapEntity);
        getGameCreator().deleteInstance(mapEntity.getEntityID(), mapEntity.getInstanceID());
    }

}
