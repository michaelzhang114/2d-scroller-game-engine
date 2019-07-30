package Screens.AccTabs;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * This class displays the information of the current account including the username and game stats associated with the
 * given account in the database. This class depends on the connection to the database to get the required information
 * to display. Methods from the database API are called to get the correct and current data to be displayed.
 *
 * @author Eric Werbel
 */
public class AccInfoTab extends AccTabs {

    private ScrollPane myRoot = new ScrollPane();
    private VBox myContainer = new VBox();

    /**
     * The constructor initializes the content to be displayed by the ScrollPane.
     */
    public AccInfoTab() {
        myRoot.setContent(myContainer);
    }

    /**
     * Updates the ScrollPane to keep information current and correct
     *
     * @return the new version of the ScrollPane to be displayed
     */
    public ScrollPane getScrollPane() {
        updateDisplay();
        myRoot.setContent(myContainer);
        return myRoot;
    }

    private void updateDisplay() {
        myContainer.getChildren().clear();
        String username = getActiveUser().getUsername();
        VBox gameData = showAccData(username);
        myContainer.getChildren().addAll(new Label(username), gameData);
    }

}
