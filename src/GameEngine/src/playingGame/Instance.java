package playingGame;

import FSM.FSM;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import makingGame.entity.GameObject;

import java.util.*;

public class Instance {

    private Boolean changedView;
    private Map<String, String> propertiesMap;
    private String entityType;
    private String instanceID;
    private ImageView imageView;
    protected ArrayList<FSM> FSMs;

    /**
     *
     * @param entityType
     * @param insID
     * @param propertiesMap
     *
     * author: Diego Chamorro, Michael zhang, Justin Kim
     */
    public Instance(String entityType, String insID, Map<String, String> propertiesMap){
        this.entityType = entityType;
        this.instanceID = insID;
        this.propertiesMap = propertiesMap;
        imageView = new ImageView();
        createImageView();
        FSMs=new ArrayList<>();
    }

    /**
     * constructor
     * @param entityType
     * @param insID
     * @param masterEntity
     */
    public Instance(String entityType, String insID, GameObject masterEntity){
        this(entityType, insID, masterEntity.getPropertyMapOfInstance(insID));
        //System.out.println("making instance "+entityType+insID+" with "+masterEntity.getFSMs());
        this.addFSMs(masterEntity.getFSMs());
    }

    /**
     *
     * @return the id of this instance
     */
    public String getInstanceID() {
        return instanceID;
    }

    /**
     * add a FSM to this instance
     * @param fsms
     */
    public void addFSMs(ArrayList<FSM> fsms){
        FSMs.addAll(fsms);
    }

    /**
     *
     * @return all fsms of this instanec
     */
    public ArrayList<FSM> getFSMs(){
        return FSMs;
    }


    /**
     *
     * @return entity type of the instance
     */
    public String getEntityType(){
        return entityType;
    }

    /**
     *
     * @param property
     * @return value of the property
     */
    public String getValueOfProperty(String property) {
        return propertiesMap.get(property);
    }

    /**
     * set a property of a instance to certain value
     * @param property
     * @param amount
     * @return
     */
    public String setValueToProperty(String property, String amount) {
        if(property.equals("imagePath")){
            changedView = true;
        }
        return propertiesMap.put(property, amount);
    }

    /**
     *
     * @return this instance's imageview
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * check if view is changed for this instance
     * @return if view is changed
     */
    public Boolean getChangedView() {
        return changedView;
    }

    /**
     * create a imageview for an instance
     */
    public void createImageView(){
        changedView = false;
        System.out.println(propertiesMap.get("imagePath"));
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(propertiesMap.get("imagePath")));
        imageView.setImage(image);
        imageView.setX(Double.parseDouble(propertiesMap.get("xPosition")));
        imageView.setY(Double.parseDouble(propertiesMap.get("yPosition")));
    }

}
