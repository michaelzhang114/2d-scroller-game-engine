package makingGame.interactions.events.Conditions;

import java.util.HashMap;
import java.util.Map;

public abstract class Condition {
    protected Map<String, Object> myConditions;

    /**
     * constructor
     */
    public Condition(){
        myConditions = new HashMap<>();
    }

    /**
     * returns a condition with conditionID
     * assume conditionID exist in the map
     * @param conditionID
     * @return
     */
    public Object getCondition(String conditionID){
        return myConditions.get(conditionID);
    }

    /**
     *
     * @return the map structure of all conditions
     */
    public Map<String, String> getAllConditions() {
        Map<String, String> out = new HashMap<>();
        for (String s : myConditions.keySet()) {
            out.put(s, myConditions.get(s).toString());
        }
        return out;
    }

}
