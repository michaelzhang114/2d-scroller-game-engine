package playingGame.gamerunning;

import javafx.scene.input.KeyCode;
import java.util.List;
import java.util.ArrayList;

/**
 * author: Justin Kim
 */
public class KeyHolder {

    private List<KeyCode> keysPressed;

    /**
     * holds the key pressed in the current steps
     */
    public KeyHolder(){
        keysPressed = new ArrayList<>();
    }

    /**
     * adds key to list
     * @param key
     */

    public void addKey(KeyCode key){
        if(key != null) keysPressed.add(key);
    }

    /**
     * returns the keys pressed
     * @return
     */
    public List<KeyCode> getKeysPressed() {
        return keysPressed;
    }

    /**
     * resets the list of keys pressed
     */
    public void reset(){
        keysPressed.clear();
    }
}
