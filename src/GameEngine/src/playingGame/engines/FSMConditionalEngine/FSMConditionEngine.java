package playingGame.engines.FSMConditionalEngine;


import makingGame.entity.GameObject;
import makingGame.interactions.events.conditionals.*;
import playingGame.Instance;
import playingGame.engines.KeyInputHandler;
import playingGame.engines.conditionCheckers.CollisionConditionCheck;
import playingGame.manager.LevelManager;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

//engine that evaluate conditionals
//generic, the same for all games/levels/maps

/**
 * @author: yao yuan
 */
public class FSMConditionEngine {

    private ScriptEngine myScriptEngine;
    private KeyInputHandler myKeyInputHandler;
    private LevelManager myLevelManager;
    public FSMConditionEngine(){
        myScriptEngine=new ScriptEngineManager().getEngineByName("groovy");
    }

    /**
     *
     * @param keyhandler
     *
     * sets the keyInputHandler
     */
    public void setKeyInputHandler(KeyInputHandler keyhandler){
        myKeyInputHandler=keyhandler;
    }

    /**
     *
     * @param level manager
     *
     * sets the levelmanager
     */
    public void setLevelManager(LevelManager lm){
        myLevelManager=lm;
    }
    private boolean evaluatePropertyConditionFor(Instance entity, Conditionals propertycondition){
//        if (propertycondition==null||!propertycondition.Signatures.get(0).equals(ConditionType.PROPERTY_CHECKING.toString())){
////            System.out.println("not property condition");
//            return false;
//        }

        ArrayList<String> signatures=propertycondition.Signatures;
        String PropertyToCompare=signatures.get(1);
        String operator=signatures.get(2);
        Double value1 = Double.parseDouble(entity.getValueOfProperty(PropertyToCompare));
        Double value2 = Double.parseDouble(signatures.get(3));
        myScriptEngine.put("valOne", value1);
        myScriptEngine.put("valTwo", value2);
        try {
            myScriptEngine.eval("answer = valOne" + operator + "valTwo"); }
        catch (ScriptException conditionError) {
//        catch (Exception ex) {
            conditionError.printStackTrace();
            System.out.println("evaluate conditions failed");
            System.out.println(propertycondition);
        }
        Object answer = myScriptEngine.get("answer");
        boolean output = (Boolean) answer;
        return output;
    }

    private boolean evaluateKeyInputConditionFor(Instance entity, Conditionals keyinputcondition){
        return myKeyInputHandler.keycode_conditions.contains(keyinputcondition);
    }

    private boolean evaluateCollisionConditionFor(Instance entity, Conditionals collisioncondition){
        //assume level manager is not null
        //the other entity where the detection is abt
        String entitytype=collisioncondition.Signatures.get(1);
        String side=collisioncondition.Signatures.get(2);
        List<Instance> allInstances=myLevelManager.getInstancesOfCurrentLevel();
        return CollisionConditionCheck.collisionChecker(entity,entitytype,side,allInstances);
    }
    //    private static final String propertytype = ConditionType.PROPERTY_CHECKING.toString();

    /**
     *
     * @param entity
     * @param conditional
     * @return
     *
     *
     * evaluates the set of conditions for the entity
     */
    public boolean evaluateConditionFor(Instance entity, Conditionals conditional){
        String conditionType=conditional.Signatures.get(0);
        switch (conditionType){
            case PropertyCondition.type:
                return evaluatePropertyConditionFor(entity,conditional);
            case KeyInputCondition.type:
                return evaluateKeyInputConditionFor(entity,conditional);
            case AlwaysTrueCondition.type:
                return true;
            case CollisionCondition.type:
                return evaluateCollisionConditionFor(entity,conditional);
            default:
                return false;
        }
//        System.out.println("not implemented yet");
//        return false;
    }

    //might not be needed
//    public boolean evaluateConditionsFor(Entity entity, ArrayList<Conditionals> conditionals){
//        System.out.println("not implemented yet");
//        return false;
//    }

}