package Running;

import Screens.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the Main class for the program and sets up the stage and ScreenManager. Once the ScreenManager is created
 * and the initial screen to be displayed is shown, the main method has no further function as the rest of the program
 * flow is controlled form within each Screen class.
 *
 * @autor Eric Werbel
 */
public class Player extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Play Games");
        new ScreenManager(stage);
        stage.show();
    }
}
