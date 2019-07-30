package Accounts;

import javafx.scene.control.Alert;

/**
 * This class simply creates alerts with a given title, header, and content. The purpose of this class is to consolidate
 * the functionality of creating an alert in one place. Instead of having methods in each class that needs an alert, creating
 * a separate class allows each class to simply contain an instance of this class to display any general alert.
 *
 * @author Eric Werbel
 */
public class MakeAlert {

    public MakeAlert() {

    }

    /**
     * Creates a general alert with the input title, heading, and content
     *
     * @param title = title of the alert to be created
     * @param header = header of the alert to be created
     * @param content = content of the alert to be created
     */
    public void createAndShow(String title, String header, String content) {
        Alert confirmAccount = new Alert(Alert.AlertType.INFORMATION);
        confirmAccount.setTitle(title);
        confirmAccount.setHeaderText(header);
        confirmAccount.setContentText(content);
        confirmAccount.showAndWait();
    }

}
