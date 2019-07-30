package playingGame.engines;

import playingGame.Instance;

import java.util.List;

/**
 * @author: Justin Kim
 */
public class PhysicsEngine {

    final String XLOC = "xPosition";
    final String YLOC = "yPosition";
    final String XVEL = "xVelocity";
    final String YVEL = "yVelocity";
    final String XACC = "xAcceleration";
    final String YACC = "yAcceleration";

    private double timeStep;

    public PhysicsEngine() {
    }

    /**
     *
     * @param elapsedTime
     * @param myInstances
     *
     * updates each instance according the laws of physics in our environment
     */
    public void update(double elapsedTime, List<Instance> myInstances){
        timeStep = elapsedTime;
        for (Instance entity: myInstances){
            updateEntity(entity);
        }
    }

    /*
    Because location is dependent on velocity and velocity is dependent on acceleration, we have to update velocity
    and location exclusively (since we update them one at a time) as if we were updating them simultaneously.
     */
    private void updateEntity(Instance entity){
        double tempXV = Double.parseDouble(entity.getValueOfProperty(XVEL));
        double tempYV = Double.parseDouble(entity.getValueOfProperty(YVEL));
        double tempXA = Double.parseDouble(entity.getValueOfProperty(XACC));
        double tempYA = Double.parseDouble(entity.getValueOfProperty(YACC));
        updateMutex(entity, tempXV, tempYV, tempXA, tempYA);

    }

    private void updateMutex(Instance entity, double xVel, double yVel, double xAcc, double yAcc){
        //doesn't make sense to use timestep here to calculate is this is already called regularly
//        double nextXL = xVel + Double.parseDouble(entity.getValueOfProperty(XLOC));
//        double nextYL = yVel + Double.parseDouble(entity.getValueOfProperty(YLOC));
        double nextXV = xAcc + Double.parseDouble(entity.getValueOfProperty(XVEL));
        double nextYV = yAcc + Double.parseDouble(entity.getValueOfProperty(YVEL));
        double nextXL = xVel * timeStep + Double.parseDouble(entity.getValueOfProperty(XLOC));
        double nextYL = yVel * timeStep + Double.parseDouble(entity.getValueOfProperty(YLOC));
//        double nextXV = xAcc * timeStep + Double.parseDouble(entity.getValueOfProperty(XVEL));
//        double nextYV = yAcc * timeStep + Double.parseDouble(entity.getValueOfProperty(YVEL));
        entity.setValueToProperty(XLOC, Double.toString(nextXL));
        entity.setValueToProperty(YLOC, Double.toString(nextYL));
        entity.setValueToProperty(XVEL, Double.toString(nextXV));
        entity.setValueToProperty(YVEL, Double.toString(nextYV));
    }

}
