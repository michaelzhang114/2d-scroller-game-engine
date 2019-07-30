package Accounts.FriendsTab;

import Screens.ScreenFunction;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * Did not have the time/get the chance to implement this class. Was going to come up with an algorithm to have a
 * suggested friends list for each account.
 */
public class SuggestedFriends extends ScreenFunction {

    private VBox myBox;

    public SuggestedFriends() {
        myBox = buildVBox();
    }

    /**
     * @return the VBox where a suggested friends list could be displayed
     */
    public VBox getBox() {
        return buildVBox();
    }

    private VBox buildVBox() {
        VBox box = new VBox();
        Label place = new Label("Suggested");
        box.getChildren().add(place);
        myBox = box;
        return box;
    }

}
