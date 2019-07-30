package playingGame.engines;

import makingGame.entity.BasicProperties;
import playingGame.gamerunning.ActiveFrame;
import playingGame.Instance;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


/**
 * @author: yao yuan
 */
public class RenderingEngine {


    private Instance maincharacter;
    //probably not needed
    private ActiveFrame activeFrame;
    private Scene myScene;

    private double screenHeight;
    private double screenWidth;
    PriorityQueue<Instance> rightQueue;
    ArrayList<Instance> activeInstances;

    /**
     * Constructor of rendering engine. Needs to specify sizes of screen
     * @param screenWidth
     * @param screenHeight
     */
    public RenderingEngine(double screenWidth, double screenHeight){
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
        Comparator<Instance> rightmostComparator = new Comparator<Instance>() {
            @Override
            public int compare(Instance ei1, Instance ei2) {
                Double diff = Double.parseDouble(ei1.getValueOfProperty("xPosition"))-Double.parseDouble(ei2.getValueOfProperty("xPosition"));
                return diff.intValue();
//                return s1.length() - s2.length();
            }
        };
        rightQueue=new PriorityQueue<>(rightmostComparator);
        activeInstances=new ArrayList<>();
        myScene = new Scene(new Group(), screenWidth, screenHeight);
    }

    /**
     * Function to call when first setting or switching the levels, stuffs like that
     * @param myInstances
     * @param mainCharacter
     */
    public void setScreen(List<Instance> myInstances, Instance mainCharacter){

        activeFrame=new ActiveFrame(screenWidth,200,440); //TODO: yao fix this
        rightQueue.clear();
        activeInstances.clear();
        for (Instance ei: myInstances){
            if (Double.parseDouble(ei.getValueOfProperty("xPosition"))<activeFrame.rightx){
                activeInstances.add(ei);
            }else {
                rightQueue.add(ei);
            }
        }
        maincharacter = mainCharacter;
    }


    //need to get all views appended to root
    //assume active frame set up

    /**
     * updates all image imageviews in the screen that are within a range in the current frame
     * to be called every time graphics need to be updated
     */
    public void updateGraphics() {
        List<Instance> instanceList=getactiveinstances();
        for (Instance i:instanceList) {
            ImageView view=i.getImageView();
            //ImageView view = ImageViewManager.get(i.getID());
            double x = Double.parseDouble(i.getValueOfProperty("xPosition"))-activeFrame.leftx;
            //YAO: want y to be negated here
            double y = Double.parseDouble(i.getValueOfProperty("yPosition"));
            double xscale=Double.parseDouble(i.getValueOfProperty(BasicProperties.XSCALE));

            view.setX(x);
            view.setY(y);
            view.setScaleX(xscale);
        }
    }



    private List<Instance> getactiveinstances(){
        double charax = Double.parseDouble(maincharacter.getValueOfProperty("xPosition"));
        if (charax>activeFrame.freerightx){
            //then update is needed
            activeFrame.updatefreerightx(charax);
            while(!rightQueue.isEmpty()){
                Instance nextinstance=rightQueue.peek();
                if (Double.parseDouble(nextinstance.getValueOfProperty("xPosition"))<activeFrame.rightx){
                    activeInstances.add(nextinstance);
                    rightQueue.remove();
                }else {
                    break;
                }
            }
        }
        return activeInstances;
    }
}
