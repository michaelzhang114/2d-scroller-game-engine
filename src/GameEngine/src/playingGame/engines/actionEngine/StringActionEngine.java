package playingGame.engines.actionEngine;

import makingGame.interactions.action.Action;
import playingGame.Instance;

import java.util.List;


/**
 * @author michael zhang, Diego Chamorro
 */

public class StringActionEngine extends ActionEngine {
    @Override

    /**
     *  This engine sets the instances property equal to the the specific string the action specifies.
     */
    public void execute(List<Instance> instances, Action myAction) {
        for(Instance affected : instances){
            String propertyToBeModified = myAction.getPropertyToBeModified();
            affected.setValueToProperty(propertyToBeModified, myAction.getValueToUse());
        }
    }
}
