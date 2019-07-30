package Screens;

import Screens.AccTabs.AccFriendsTab;
import Screens.AccTabs.AccInfoTab;
import Screens.AccTabs.AccMessagesTab;
import Screens.AccTabs.MyTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * This class controls the overall screen that is created when the user clicks on the My Accounts button. This includes
 * setting up the TabPane that will be displayed at the top of the screen and creating a container where each tab's
 * content can be displayed.
 *
 * @author Eric Werbel
 */
public class AccControlScreen extends SceneDisplay{

    public static final String MY_INFO = "My Info";
    public static final String FRIENDS = "Friends";
    public static final String MESSAGES = "Messages";

    private Scene myScene;
    private ScrollPane myRoot = new ScrollPane();
    private ScrollPane currentPane;
    private VBox myContainer = new VBox();
    private Button myBackButton;

    private AccInfoTab myAccInfo = new AccInfoTab();
    private AccFriendsTab myAccFriends = new AccFriendsTab();
    private AccMessagesTab myAccMessages = new AccMessagesTab();

    private Consumer<Scene> myBackLambda;

    /**
     * Constructor that initializes the general scene and builds the format for the TabPane and its contents
     *
     * @param style = a string representing the path to the desired stylesheet
     */
    public AccControlScreen(String style) {
        myScene = buildScene();
        myScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
    }

    @Override
    protected Scene buildScene() {
        myBackButton = makeButton("Back", e -> myBackLambda.accept(myScene));
        TabPane tabs = makeTabPane();
        currentPane = myAccInfo.getScrollPane();
        myContainer.getChildren().clear();
        myContainer.getChildren().addAll(tabs, currentPane, myBackButton);
        myRoot.setContent(myContainer);
        return new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT);
    }

    /**
     * @return the current scene displaying the TabPane and its contents
     */
    public Scene getScene() {
        return myScene;
    }

    private TabPane makeTabPane() {
        TabPane tp = new TabPane();
        MyTab myInfo = createTab(MY_INFO, e->updateDisplay(myAccInfo.getScrollPane()));
        MyTab friends = createTab(FRIENDS, e->updateDisplay(myAccFriends.getScrollPane()));
        MyTab messages = createTab(MESSAGES, e->updateDisplay(myAccMessages.getScrollPane()));
        tp.getTabs().addAll(myInfo, friends, messages);
        tp.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                MyTab t = (MyTab) tp.getTabs().get((int) newValue);
                t.selected();
        }
        });
        return tp;
    }

    private MyTab createTab(String content, Consumer<Tab> consumer) {
        MyTab t = new MyTab();
        t.setText(content);
        t.setClosable(false);
        t.setSelectionLambda(consumer);
        return t;
    }

    private void updateDisplay(ScrollPane pane) {
        myContainer.getChildren().remove(currentPane);
        myContainer.getChildren().add(pane);
        myContainer.getChildren().remove(myBackButton);
        myContainer.getChildren().add(myBackButton);
        currentPane = pane;
    }

    /**
     * Sets the lambda telling the screen what to do when the back button is clicked
     *
     * @param backLambda = a function that will set the scene of the window to the previous scene
     */
    public void setBackLambda(Consumer<Scene> backLambda) {
        myBackLambda = backLambda;
    }

}
