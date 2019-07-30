package playingGame.engines.conditionCheckers;

import makingGame.interactions.events.Conditions.Condition;
import playingGame.Instance;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CollisionConditionCheck extends ConditionCheck {

    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String RIGHT = "right";
    public static final String LEFT = "left";
    public static final String ANY = "any";

    public static final double OFFSET = 10.0;
    public static final double HIT_BOX_WIDTH = 10.0;

    /**
     * evaluate a condition for myself
     * @param mySelf
     * @param condition
     * @param otherObjects
     * @return
     */
    @Override
    public boolean evaluateConditions(Instance mySelf, Condition condition, Object otherObjects){
        List<Instance> otherInstances = (List<Instance>) otherObjects;
        return collisionChecker(mySelf, (String) condition.getCondition("otherEntity"), condition.getCondition("side"), otherInstances);
    }

    /**
     * checks if myinstance is in collision with other instances with entityID with specified side
     * @param myInstance
     * @param entityID
     * @param side
     * @param otherInstances
     * @return
     */
    public static boolean collisionChecker(Instance myInstance, String entityID, Object side, List<Instance> otherInstances) {
        List<Instance> myChecks = new ArrayList<>();
        for(Instance otherInstance : otherInstances){
            if(otherInstance.getEntityType().equals(entityID) && myInstance.getImageView().intersects(otherInstance.getImageView().getBoundsInParent())) {
                myChecks.add(otherInstance);
            }
        }
        return checkHitBoxes(myInstance, myChecks, (String) side);
    }

    private static boolean checkHitBoxes(Instance me, List<Instance> others, String collisionSide) {
        ImageView myImage = me.getImageView();

        for (Instance other : others) {
            ImageView otherImage = other.getImageView();
            double myHeight = myImage.getBoundsInLocal().getHeight();
            double myWidth = myImage.getBoundsInLocal().getWidth();

            if (collisionSide.equals(ANY)) {
                return true;
            }

            // top collision
            if (otherImage.intersects(myImage.getX() + OFFSET, myImage.getY() - OFFSET, myWidth - 2 * OFFSET, HIT_BOX_WIDTH)
                    && collisionSide.equals(TOP)) {
                return true;
            }

            // right collision
            if (otherImage.intersects(myImage.getX() + myWidth + OFFSET - HIT_BOX_WIDTH, myImage.getY() + OFFSET, HIT_BOX_WIDTH, myHeight - 2 * OFFSET)
                    && collisionSide.equals(RIGHT)) {
                return true;
            }

            // bottom collision
            if (otherImage.intersects(myImage.getX() + OFFSET, myImage.getY() + myHeight + OFFSET - HIT_BOX_WIDTH, myWidth - 2 * OFFSET, HIT_BOX_WIDTH)
                    && collisionSide.equals(BOTTOM)) {
                return true;
            }

            // left collision
            if (otherImage.intersects(myImage.getX() - OFFSET, myImage.getY() + OFFSET, HIT_BOX_WIDTH, myHeight - 2 * OFFSET)
                    && collisionSide.equals(LEFT)) {
                return true;
            }
        }
        return false;
    }
}
