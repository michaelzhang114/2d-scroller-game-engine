package Screens.AccTabs;

import Accounts.FriendsTab.AccList;
import Accounts.FriendsTab.SearchAcc;
import Accounts.FriendsTab.SuggestedFriends;
import Screens.ScreenFunction;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class controls the display of the Friends tab within the account information section. From within this tab,
 * there are multiple buttons that can be pressed to take you to a different section (i.e. search for friends, friends
 * list, etc.) This class owns a consistent screen that will be displayed when the friends tab is selected. The screen
 * will store its previous state, so if the ser navigates away from the screen and returns, the screen's data will not
 * have changed. The screen's behavior is dependent on the current active user as this information effects what is shown
 * on the screen.
 *
 * @author Eric Werbel
 */
public class AccFriendsTab extends ScreenFunction {

    public static final String FRIENDS_TAB = "friends-tab";
    public static final String FRIENDS = "My Friends";
    public static final String SUGGESTED = "Suggested";
    public static final String SEARCH = "Search";

    private ScrollPane myRoot = new ScrollPane();
    private AccList myFriendsList = new AccList();
    private SuggestedFriends mySuggested = new SuggestedFriends();
    private SearchAcc myFriendSearch = new SearchAcc();

    private VBox myContainer = new VBox();
    private VBox currentBox;

    /**
     * Constructor for the screen displayed by the tab. Initializes the screen to avoid null pointers.
     */
    public AccFriendsTab() {
        HBox controlBar = makeControlBar();
        currentBox = myFriendsList.getBox();
        myContainer.getChildren().addAll(controlBar, currentBox);
        myRoot.setContent(myContainer);
    }

    /**
     * @return the ScrollPane that contains the display for the tab
     */
    public ScrollPane getScrollPane() {
        return myRoot;
    }

    /**
     * Makes the control bar at the top of the Friends tab that contains the different buttons to navigate to different
     * sections within the tab.
     *
     * @return an HBox containing the created buttons
     */
    public HBox makeControlBar() {
        HBox controlBar = new HBox();
        Button myFriends = makeButton(FRIENDS, e -> updateDisplay(myFriendsList.getBox()));
        Button suggested = makeButton(SUGGESTED, e -> updateDisplay(mySuggested.getBox()));
        Button search = makeButton(SEARCH, e->updateDisplay(myFriendSearch.getBox()));
        controlBar.getChildren().addAll(myFriends, suggested, search);
        controlBar.getStyleClass().add(FRIENDS_TAB);
        return controlBar;
    }

    private void updateDisplay(VBox newBox) {
        myContainer.getChildren().remove(currentBox);
        myContainer.getChildren().add(newBox);
        currentBox = newBox;
        myRoot.setContent(myContainer);
    }

}
