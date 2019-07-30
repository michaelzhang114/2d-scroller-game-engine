package Manager;

/**
 * Main function that initializes the authoring backend. It creates a ScreenManager, which is the class that ~manages~
 * the entire GAE, by having control of the controllers and their respective screens and popups.
 * @author Alejandro Meza
 * @author Irene Qiao
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        ScreenManager myManager = new ScreenManager(stage);
        stage.setScene(myManager.getCurrentScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }



}
