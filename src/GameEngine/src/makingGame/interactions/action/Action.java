package makingGame.interactions.action;



public abstract class Action {

    String entityToAffect;
    String propertyToBeModified;
    String valueToUse;


    /**
     * constructor
     * @param property
     * @param amount
     * @param toAffect
     */
    public Action(String property, String amount, String toAffect){
        propertyToBeModified = property;
        entityToAffect = toAffect;
        valueToUse = amount;
    }

    /**
     * @return the entity to affect
     */
    public String getEntityToAffect() {return entityToAffect;}

    /**
     *
     * @return value of the action
     */
    public String getValueToUse() {
        return valueToUse;
    }

    /**
     *
     * @return property to be modified
     */
    public String getPropertyToBeModified() {
        return propertyToBeModified;
    }

    /**
     *
     * @return a string that describes this action
     */
    public String toString(){
        return propertyToBeModified + " " + valueToUse;
    }

}
