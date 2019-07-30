package playingGame.engines.actionEngine;

import playingGame.Instance;
import makingGame.interactions.action.Action;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

public abstract class ActionEngine {

    /**
     * abstract interface of executing certain actions for each children class
     * @param instances
     * @param myAction
     */
    public abstract void execute(List<Instance> instances, Action myAction);
}
