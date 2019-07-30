package Utilities;

import Manager.ScreenManager;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ResourceBundle;

public class DeleteButton extends Button {
    private static final int size = 100;
    private ResourceBundle myResource = ResourceBundle.getBundle("Utilities.ResourceFiles.button_names");
    private String title;
    private ImageView icon;
    private Object deletableObject; //TODO: possibly create interface for Deletable objects kept in storage class in Manager

    public DeleteButton(Object deletableObject, ScreenManager manager){
        super();
        title = myResource.getString("DeleteButtonText");
        icon = new ImageView(new Image(getClass().getResourceAsStream(myResource.getString("DeleteButtonImage"))));
        setText(title);
        setGraphic(icon);

        setOnMouseClicked(e -> new DeletePopup(deletableObject, manager));
    }
}
