package Accounts.FriendsTab;

import Database.*;
import Screens.AccTabs.AccTabs;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class allows the user to search through a list of current accounts stored by the database. The list of results will
 * dynamically update as the user types in new letters/deletes letters form the search bar. From this list, the user will be
 * able to pull up the account information for the given account if they are friends. If they are not friends, the user
 * can choose to add them as a friend. This class also depends on the API from the database to return current and correct
 * information.
 *
 * @author Eric Werbel
 */
public class SearchAcc extends AccTabs {

    public static final String VIEW_ACC_TEXT = "View Account";
    public static final String SEARCH_PROMPT = "Search Account Names";
    public static final String RETURN_TEXT = "Return to List";
    public static final String ADD_FRIEND_TEXT = "Add Friend";
    public static final String REMOVE_FRIEND_TEXT = "Remove Friend";

    private VBox myBox;
    private VBox accContainer = new VBox();
    private DBAccounts acctsDB = new DBAccounts();
    private DBFriends frndsDB = new DBFriends();
    private DBGameStats statsDB = new DBGameStats();
    private Map<String, Account> myAccounts;

    public SearchAcc() {
        myBox = buildVBox();
    }

    /**
     * @return the VBox containing the search bar and information to be displayed
     */
    public VBox getBox() {
        return buildVBox();
    }

    /**
     * Builds a VBox to be added to the root and displayed when the tab is clicked. Constructs the overall format
     * of the window to be displayed and initializes all components.
     *
     * @return a VBox containing all the information to be displayed in the tab
     */
    private VBox buildVBox() {
        VBox box = new VBox();
        myAccounts = getAllUsernames();
        TextField search = new TextField();
        search.setPromptText(SEARCH_PROMPT);
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateList(search);
            }
        });
        box.getChildren().addAll(search, accContainer);
        myBox = box;
        return myBox;
    }

    /**
     * Gets the username associated with every account stored in the database.
     *
     * @return a map where the key is the username and the value is the account object associated with the username
     */
    private Map<String, Account> getAllUsernames() {
        Map<String, Account> myAccMap = new HashMap<>();
        List<Account> myAccList = acctsDB.retrieveAllAccounts();
        for (Account a : myAccList) {
            myAccMap.put(a.getUsername(), a);
        }
        return myAccMap;
    }

    /**
     * Updates the list of accounts that match the search term from the user. This method is called every time the text
     * in the search TextField changes, so the list will dynamically update as the user types in more characters.
     *
     * @param search = current text in the user TextField
     */
    private void updateList(TextField search) {
        accContainer.getChildren().clear();
        for (String name : myAccounts.keySet()) {
            if (checkAddToList(name, search.getText())) {
                HBox curAccount = makeLRBHBox(name, VIEW_ACC_TEXT, e->loadAccInfo(name));
                accContainer.getChildren().add(curAccount);
            }
        }
    }

    private boolean checkAddToList(String accName, String search) {
        return search.length() > 0 && ! accName.equals(getActiveUser().getUsername())
                && accName.startsWith(search);
    }

    private void loadAccInfo(String username) {
        myBox.getChildren().clear();
        Button backList = makeButton(RETURN_TEXT, e->  backToList());
        HBox basicInfo;
        if (frndsDB.checkIfFriendshipExists(getActiveUser().getUsername(), username)) {
            basicInfo = makeLRBHBox(username, REMOVE_FRIEND_TEXT, e->frndsDB.removeFriendship(getActiveUser().getUsername(), username));
            VBox data = showAccData(username);
            myBox.getChildren().addAll(basicInfo, data, backList);
        } else {
            basicInfo = makeLRBHBox(username, ADD_FRIEND_TEXT, e->frndsDB.addFriendship(getActiveUser().getUsername(), username));
            myBox.getChildren().addAll(basicInfo, backList);
        }
    }

    private void backToList() {
        myBox.getChildren().clear();
        myBox.getChildren().addAll(buildVBox().getChildren());
    }

}
