package playingGame.manager;

import exception.MainCharacterNotFoundException;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import makingGame.entity.GameObject;
import playingGame.Instance;


import java.util.*;

/**
 * author: Diego Chamorro, Michael Zhang
 */
public class LevelManager {

    private List<Instance> myLevelInstances;
    private Map<String, Pair<String, String>> myMainCharacters;
    private List<Instance> myInstances;

    /**
     * constructor
     * @param myEntities
     * @param mainCharacters
     */
    public LevelManager(Map<String, GameObject> myEntities, Map<String, Pair<String, String>> mainCharacters) {
        myLevelInstances = new ArrayList<>();
        myMainCharacters = mainCharacters;
        myInstances = new ArrayList<>();
        createInstances(myEntities);
    }


    /**
     *
     * @param currentLevel
     * @return
     *
     * gets the main character of the current level
     */
    public Instance getMainCharacter(String currentLevel){

        //System.out.println(currentLevelId);
        String mainEntityName = myMainCharacters.get(currentLevel).getKey();
        //System.out.println(mainEntityName);
        String mainInstanceId = myMainCharacters.get(currentLevel).getValue();
        //System.out.println(mainEntityName + "|" + mainInstanceId);
        for(Instance instance : myLevelInstances){
            //System.out.println("thisLevel: " + instance.getEntityType() + "|" + instance.getInstanceID());
            if(instance.getEntityType().equals(mainEntityName) && instance.getInstanceID().equals(mainInstanceId)){


                return instance;
            }
        }
        throw new MainCharacterNotFoundException();
    }

    /**
     * @return
     * gets the instances of the current level
     */
    public List<Instance> getInstancesOfCurrentLevel(){
        return myLevelInstances;
    }


    /**
     * @param myCurrentLevelID
     * @return
     *
     * creates the current level
     */
    public Group createLevel(String myCurrentLevelID){
        String myCurrentLevel = Integer.toString((int) Double.parseDouble(myCurrentLevelID));
        myLevelInstances = new ArrayList<>();
        for(Instance instance : myInstances){
            if(instance.getValueOfProperty("level").equals(myCurrentLevel) || instance.getInstanceID().equals("lc")){
                myLevelInstances.add(instance);
            }
        }
        Group myRoot = addAllInstancesToRoot(myLevelInstances);
        return myRoot;
    }

    private void createInstances(Map<String, GameObject> myEntities){
        for(String entityID : myEntities.keySet()){
            GameObject masterEntity = myEntities.get(entityID);
            for(String instanceName : masterEntity.getListOfInstanceIDs()){

                Instance temp = new Instance(entityID, instanceName,masterEntity);
//                Instance temp = new Instance(entityID, instanceName, masterEntity.getPropertyMapOfInstance(instanceName));
                myInstances.add(temp);
            }
        }
    }



    private Group addAllInstancesToRoot(List<Instance> instances){
        Group root = new Group();
        for (Instance i: instances) {
            ImageView view = getImageViewOfInstance(i);
            root.getChildren().add(view);
        }
        return root;
    }

    private ImageView getImageViewOfInstance(Instance i) {
        ImageView view = i.getImageView();
        double x = Double.parseDouble(i.getValueOfProperty("xPosition"));
        double y = Double.parseDouble(i.getValueOfProperty("yPosition"));
        view.setX(x);
        view.setY(y);
        return view;
    }

}
