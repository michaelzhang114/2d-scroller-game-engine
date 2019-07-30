package Screens;

import Database.Account;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * This class is the root of the entire inheritance hierarchy for the GamePlayer module and contains methods that are used in
 * almost every subclass. Many classes depend on the methods in this class and this class is also responsible for tracking
 * the active user within the program. If a user signs in, their data must be stored in order to display the correct
 * data as they transition between screens.
 *
 * @author Eric Werbel
 */
public abstract class ScreenFunction {

    private static Account activeUser = new Account();

    protected Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button b = new Button();
        b.setText(property);
        b.setOnAction(handler);
        return b;
    }

    protected void setActiveUser(Account account) {
        activeUser = account;
    }

    protected Account getActiveUser() {
        return activeUser;
    }

}
