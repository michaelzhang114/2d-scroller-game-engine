package Accounts.FriendsTab;

import Database.DBFriends;
import Database.Friend;
import Screens.AccTabs.AccTabs;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * This class displays a list of the current user's friends and allows them to view their profiles. Depends on getting the
 * correct active user data from the superclass and the correct data from the database API.
 *
 * @author Eric Werbel
 */
public class AccList extends AccTabs {

    public static final String NO_FRIENDS = "You do not have any friends.";
    public static final String VIEW_ACC_TEXT = "View Account";
    public static final String RETURN_TEXT = "Return to List";
    public static final String REMOVE_FRIEND = "Remove Friend";

    private VBox myBox;
    private DBFriends frndsDB = new DBFriends();

    /**
     * Initializes the VBox containing the list of friends
     */
    public AccList() {
        myBox = new VBox();
        buildVBox();
    }

    /**
     * @return the VBox containing the contents to be displayed by the tab
     */
    public VBox getBox() {
        return buildVBox();
    }

    private VBox buildVBox() {
        myBox.getChildren().clear();
        myBox.getChildren().add(createFriendList());
        return myBox;
    }

    private VBox createFriendList() {
        VBox list = new VBox();
        String username = getActiveUser().getUsername();
        List<Friend> myFriends = frndsDB.getFriends(username);
        if (myFriends.size() == 0) {
            Label noFriends = new Label(NO_FRIENDS);
            list.getChildren().add(noFriends);
        } else {
            for (Friend f : myFriends) {
                HBox friend = makeLRBHBox(f.getName(), VIEW_ACC_TEXT, e->loadFriend(f.getName()));
                list.getChildren().add(friend);
            }
        }
        return list;
    }

    private void loadFriend(String username) {
        myBox.getChildren().clear();
        HBox basicData = makeLRBHBox(username, REMOVE_FRIEND, e->frndsDB.removeFriendship(getActiveUser().getUsername(), username));
        VBox gameData = showAccData(username);
        Button backList = makeButton(RETURN_TEXT, e->  backToList());
        myBox.getChildren().addAll(basicData, gameData, backList);
    }

    private void backToList() {
        myBox.getChildren().clear();
        buildVBox();
    }

}
