package makingGame.managers;

import exception.ActionNotFoundException;
import exception.ConditionNotFoundException;
import exception.IllegalEventPackageException;
import makingGame.interactions.action.Action;
import makingGame.interactions.action.NumericalAction;
import makingGame.interactions.action.StringAction;
import makingGame.interactions.events.Conditions.CollisionCondition;
import makingGame.interactions.events.Conditions.Condition;
import makingGame.interactions.events.Conditions.KeyInputCondition;
import makingGame.interactions.events.Conditions.PropertyCondition;
import makingGame.interactions.events.EventPackage;
import java.util.*;


public class EventPackageManager {
    private Map<String, EventPackage> myEventPackages;
    private Map<String, Condition> myConditionMap;
    private Map<String, Action> myActionMap;

    public EventPackageManager(){
        myEventPackages = new HashMap<>();
        myConditionMap = new HashMap<>();
        myActionMap = new HashMap<>();
    }

    /**
     * Adds an event package with an ID
     * @param packageID
     * @param myPackage
     */
    public void addEventPackage(String packageID, EventPackage myPackage){
        myEventPackages.put(packageID, myPackage);
    }

    /**
     * Deletes an event package
     * @param packageID
     */
    public void deleteEventPackage(String packageID){
        myEventPackages.remove(packageID);
    }

    /**
     * Creates a property condition with a specific property, comparator, and value
     * @param propertyEventID
     * @param property1
     * @param operator
     * @param num
     */
    public void createPropertyCondition(String propertyEventID, String property1, String operator, String num){
        PropertyCondition propertyToAdd = new PropertyCondition(property1, operator, Double.parseDouble(num));
        myConditionMap.put(propertyEventID, propertyToAdd);
    }

    /**
     * Creates a property condition that does not depend on numbers and deals with strings
     * @param propertyEventID
     * @param property1
     * @param value
     */
    public void createPropertyCondition(String propertyEventID, String property1, String value){
        PropertyCondition propertyToAdd = new PropertyCondition(property1, value);
        myConditionMap.put(propertyEventID, propertyToAdd);
    }

    /**
     * Creates a property condition that is specific to an entity
     * @param propertyEventID
     * @param property1
     * @param entity
     * @param operator
     * @param num
     */
    public void createPropertyCondition(String propertyEventID, String property1, String entity,  String operator, String num){
        PropertyCondition propertyToAdd = new PropertyCondition(entity, property1, operator, Double.parseDouble(num));
        myConditionMap.put(propertyEventID, propertyToAdd);
    }

    /**
     * Gets a key input condition based on ID
     * @param id
     * @return
     */
    public KeyInputCondition getKeyInputCondition(String id){
        return (KeyInputCondition) myConditionMap.get(id);
    }

    /**
     * Gets an event package based on ID
     * @param eventPackageID
     * @return
     */
    public EventPackage getEventPackage(String eventPackageID){
        return myEventPackages.get(eventPackageID);
    }

    /**
     * Creates a key input condition
     * @param keyInputID
     * @param keys
     */
    public void createKeyInputCondition(String keyInputID, List<String> keys){
        KeyInputCondition keyEvent = new KeyInputCondition(keys);
        myConditionMap.put(keyInputID, keyEvent);
    }

    /**
     * Adds a condition to an event package based on IDs
     * @param eventPackageID
     * @param conditionID
     */
    public void addCondition(String eventPackageID, String conditionID){
        myEventPackages.get(eventPackageID).addCondition(conditionID, myConditionMap.get(conditionID));
    }

    /**
     * Creates a collision condition with the entity type and the side of collision
     * @param collisionEventID
     * @param entity
     * @param collisionType
     */
    public void createCollision(String collisionEventID, String entity, String collisionType){
        CollisionCondition collisionCondition = new CollisionCondition(entity, collisionType);
        myConditionMap.put(collisionEventID, collisionCondition);
    }

    /**
     * Creates an action to set the property of an instance to a certain value based on the operator
     * @param actionID
     * @param propertyToModify
     * @param operator
     * @param amount
     */
    public void createAction(String actionID, String propertyToModify, String operator, String amount){
        myActionMap.put(actionID, new NumericalAction(propertyToModify, operator, amount, "MYSELF"));
    }

    /**
     * Creates an action to set the property of an entity to a certain value based on the operator
     * @param actionID
     * @param entityID
     * @param propertyToModify
     * @param operator
     * @param amount
     */
    public void createAction(String actionID, String entityID, String propertyToModify, String operator, String amount){
        myActionMap.put(actionID, new NumericalAction(propertyToModify, operator, amount, entityID));
    }

    /**
     * Creates an action to set a string value property to a certain value for an instance
     * @param actionID
     * @param propertyToModify
     * @param amount
     */
    public void createStringAction(String actionID, String propertyToModify,String amount){
        myActionMap.put(actionID, new StringAction(propertyToModify, amount, "MYSELF"));
    }

    /**
     * Creates an action to set a string value property to  a certain value for an entity
     * @param actionID
     * @param entityID
     * @param propertyToModify
     * @param amount
     */
    public void createStringAction(String actionID,String entityID, String propertyToModify, String amount){
        myActionMap.put(actionID, new StringAction(propertyToModify, amount, entityID));
}

    /**
     * Adds an action to an event package based on ID
     * @param eventPackageID
     * @param actionID
     */
    public void addAction(String eventPackageID, String actionID){
        myEventPackages.get(eventPackageID).addAction(actionID, myActionMap.get(actionID));
    }

    /**
     * Deletes a condition from an event package
     * @param eventPackageID
     * @param conditionID
     */
    public void deleteConditionFromPackage(String eventPackageID, String conditionID) {
        myEventPackages.get(eventPackageID).deleteCondition(conditionID);
    }

    /**
     * Returns the IDs of all event packages
     * @return
     */
    public Collection<String> getAllEventPackagesIds() {
        return myEventPackages.keySet();
    }

    /**
     * Get all condition IDs belonging to an event package
     * @param eventPackageId
     * @return
     */
    public Collection<String> getConditionIdsFromEventPackageId(String eventPackageId) {
        if (myEventPackages.get(eventPackageId) == null) {
            throw new IllegalEventPackageException();
        }
        return myEventPackages.get(eventPackageId).getConditionIds();
    }

    /**
     * Get a list of all of the action IDs belonging to an event package
     * @param eventPackageId
     * @return
     */
    public Collection<String> getActionIdsFromEventPackageId(String eventPackageId) {
        return myEventPackages.get(eventPackageId).getActionIds();
    }

    /**
     * Get action linked to an action ID
     * @param actionId
     * @return
     */
    public List<String> getActionFromActionId(String actionId) {
        Action a = myActionMap.get(actionId);
        List<String> out = new ArrayList<>();
        out.add(a.getEntityToAffect());
        out.add(a.getPropertyToBeModified());
        out.add(a.getValueToUse());
        return out;
    }

    /**
     * Get a condition linked to a condition ID
     * @param conditionId
     * @return
     */
    public Map<String, String> getConditionFromConditionId(String conditionId) {
        Condition c = myConditionMap.get(conditionId);
        return c.getAllConditions();
    }

    /**
     * Return all conditions by name
     * @return
     */
    public Collection<String> getAllConditions() {
        if (myConditionMap.keySet().isEmpty()) {
            throw new ConditionNotFoundException();
        }
        return Collections.unmodifiableCollection(myConditionMap.keySet());
    }

    /**
     * Return all actions by name
     * @return
     */
    public Collection<String> getAllActions() {
        if (myActionMap.keySet().isEmpty()) {
            throw new ActionNotFoundException();
        }
        return Collections.unmodifiableCollection(myActionMap.keySet());
    }





}
