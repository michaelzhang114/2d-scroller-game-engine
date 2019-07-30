package external;

import makingGame.managers.EntityManager;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AuthoringAPI {

    // Game setters
    void setGameScreenSize(String width, String height);
    void setCurrentLevel(String levelId);
    void setMainCharacter(String entityName, String instanceID);
    void setBackgroundImage(String imagePath);

    // level change
    void addConditionToLevelChangeEventPackage(String levelEventPackageId, String conditionId);
    void createLevelChangeEventPackage(String eventPackageID, String nextLevel);

    // entity/instance/eventpackage creation
    void createEntity(String entityName);
    void createEntityInstance(String entityName, String instanceId, String imagePath);
    void createEventPackage(String eventPackageID);

    // conditions
    void createPropertyCondition(String propertyEventID, String property1, String operator, String num);
    void createPropertyConditionString(String propertyEventID, String property1, String value);
    void createPropertyCondition(String propertyEventID, String entity, String property1, String operator, String num);
    void createKeyInputCondition(String keyEventID, List<String> keys);
    void setInverseKeyInputCondition(String keyInputID);
    void createCollisionCondition(String collisionID, String entityID, String collisionType);

    // actions
    void createAction(String actionID, String property1, String operator, String amount);
    void createAction(String actionID, String entityID,  String property1, String operator, String amount);
    void createActionString(String actionID, String property1, String amount);
    void createActionString(String actionID, String entityID, String property1, String amount);

    // linkage to event package
    void addActionToEventPackage(String eventPackageID, String actionID);
    void addConditionToPackage(String eventPackageID, String conditionID);
    void addEventPackageToEntity(String entityID, String eventPackageID);

    // property manipulation
    void createPropertyInEntity(String entityName, String propertyName, String defaultValue);
    void setPropertyToEntityInstance(String entityName, String instanceId, String propertyID, String value);

    // deletes
    void deleteEntity(String entityID);
    void deletePropertyConditionFromPackage(String eventPackageID, String propertyConditionID);
    void deleteKeyInputConditionFromPackage(String eventPackageID, String keyConditionID);
    void deleteCollisionCondtionFromPackage(String eventPackageID, String collisionConditionID);

    // getters
    Collection<String> getListOfEntities();
    Collection<String> getEntityProperties(String entityName);
    String getDefaultPropertyValue(String entityName, String property);
    Collection<String> getInstanceProperties(String entityName, String instanceId);
    String getPropertyValue(String entityName, String instanceId, String property);
    Collection<String> getEventPackagesOfEntity(String entityName);
    Collection<String> getAllEventPackages();
    Collection<String> getConditionIds(String eventPackageId);
    Collection<String> getActionIds(String eventPackageId);
    Collection<String> getAllConditions();
    Collection<String> getAllActions();
    List<String> getAction(String actionId);
    Map<String, String> getCondition(String conditionId);
    Collection<String> getEntityInstances(String entityName);

    // save
    void setGameName(String gameName);
    void saveGame();
    void loadGame();
    void setIcon(String iconFilePath);
    void setInstructions(String instructions);
    void setDescription(String description);

    //String serializeEntityManager() throws IOException;
    //EntityManager loadEntityManager(String xmlString);
}
