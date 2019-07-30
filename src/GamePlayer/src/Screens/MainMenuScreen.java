/**
 * This class constructs the main lobby area that extends out to all the other screens/menus in the game player module. It displays a list of games that can be played as well as corresponding descriptions for each game. It also displays buttons used for navigation through the various player panes and the accounts pane. This class extends SceneDisplay and depends on the methods in that superclass for reading files and making buttons.
 * @author Connor Ghazaleh
 */

package Screens;

import Accounts.MakeAlert;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.scene.image.ImageView;


import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class MainMenuScreen extends SceneDisplay {

    private Scene myScene;
    private BorderPane myRoot = new BorderPane();
    private MakeAlert myAlert = new MakeAlert();

    private Consumer<Scene> myLoginLambda;
    private Consumer<Scene> myCreateAccLambda;
    private Consumer<Scene> myGameDescLambda;
    private Consumer<String> myActiveGameLambda;
    private Consumer<Scene> myAccHomeLambda;

    private String gameImgsDirectory = "GameImages";
    private ArrayList<ImageView> gameImages = new ArrayList<>();
    private String myActiveGame = "";
    private String gamesDirectoryPath = "/data/games/";
    private String projectRoot = System.getProperty("user.dir");
    private String gamePropsFile = "games.properties";
    private String gameIcon = "/icon.png";
    private String gameDesc = "/description.txt";

    public static final String NO_LOGIN_TITLE = "Can't Access Data";
    public static final String NO_LOGIN_HEADER = "Not Logged In";
    public static final String NO_LOGIN_CONTENT = "Log in to view your account data.";
    public static final String SCREEN_TITLE = "GAMES";
    public static final String GAME_SELECT = "Select a game";
    public static final String MY_ACCOUNT_BUTTON = "My Account";
    public static final String TO_LOBBY_BUTTON = "Go To Lobby";

    private static final int TITLE_SIZE = 40;
    private static final int TOP_MENU_SPACING = 90;
    private static final int TOP_MENU_HEIGHT = 50;
    private static final int SELECT_GAME_SIZE = 17;
    private static final Insets INSETS = new Insets(20,20,20,20);
    private static final int CENTER_MENU_SPACING = 90;
    private static final int GAME_ICON_HEIGHT = 50;
    private static final int GAME_SELECT_MENU_WIDTH = 150;



    public MainMenuScreen(String style) {
        myScene = buildScene();
        myScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
    }

    @Override
    protected Scene buildScene() {
        MenuButton accts = createAccountsMenu();
        Label gamesLbl = makeLabel(SCREEN_TITLE,TITLE_SIZE);
        Button accHome = super.makeButton(MY_ACCOUNT_BUTTON, e -> checkLogin());
        HBox top = makeTop();
        top.getChildren().addAll(accHome,gamesLbl,accts);
        myRoot.setTop(top);
        Label gamesDesc = makeLabel(GAME_SELECT,SELECT_GAME_SIZE);
        gamesDesc.wrapTextProperty().setValue(true);
        VBox center = makeCenter();
        center.getChildren().addAll(gamesDesc);
        Button toLobby = makeLobbyButton();
        myRoot.setCenter(center);
        Properties gameNames = super.loadPropsFile(projectRoot+gamesDirectoryPath+gamePropsFile);
        Enumeration keys = gameNames.propertyNames();
        ArrayList<String> games = Collections.list(keys);
        getGameIcons(games);
        ListView gamesList = makeGamesList(gameImages);
        myRoot.setLeft(gamesList);
        createListListeners(gamesList, center, toLobby, games, gamesDesc);
        return new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private ListView makeGamesList(List icons){
        ListView gamesList = new ListView();
        gamesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        gamesList.getItems().addAll(icons);
        gamesList.setPrefWidth(GAME_SELECT_MENU_WIDTH);
        return gamesList;
    }

    private Button makeLobbyButton() {
        Button toLobby = new Button(TO_LOBBY_BUTTON);
        toLobby.setOnAction(actionEvent -> {
            myActiveGameLambda.accept(getActiveGame());
            myGameDescLambda.accept(myScene);
        });
        return toLobby;
    }

    private VBox makeCenter(){
        VBox center = new VBox();
        center.setPadding(INSETS);
        center.setSpacing(CENTER_MENU_SPACING);
        center.setAlignment(Pos.CENTER);
        return center;
    }

    private HBox makeTop(){
        HBox top = new HBox();
        top.setAlignment(Pos.TOP_RIGHT);
        top.setSpacing(TOP_MENU_SPACING);
        top.setPrefHeight(TOP_MENU_HEIGHT);
        return top;
    }

    private Label makeLabel(String text, int fontSize){
        Label label = new Label(text);
        label.setFont(new Font (fontSize));
        return label;
    }

    private MenuButton createAccountsMenu(){
        //accounts menu box
        MenuItem loginAccount = new MenuItem("Login");
        MenuItem createAccount = new MenuItem("Create Account");
        MenuButton accts = new MenuButton("Accounts", null, loginAccount, createAccount);
        loginAccount.setOnAction(actionEvent -> {
            myLoginLambda.accept(myScene);
        });
        createAccount.setOnAction(actionEvent -> {
            myCreateAccLambda.accept(myScene);
        });

        return accts;
    }

    private void getGameIcons(List<String> games) {
        for (String game : games){
            String imagePath = projectRoot+gamesDirectoryPath+game+gameIcon;
            try {
                ImageView icon = new ImageView(new Image(new FileInputStream(imagePath)));
                icon.setFitHeight(GAME_ICON_HEIGHT);
                icon.setPreserveRatio(true);
                gameImages.add(icon);
            } catch (FileNotFoundException e) {
                System.out.println("Could not retrieve game icon");
            }
        }
    }

    private void createListListeners(ListView gamesList, VBox center, Button toLobby, List<String> games, Label gamesDesc) {
        gamesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!center.getChildren().contains(toLobby)){
                    center.getChildren().addAll(toLobby);
                }
                int index = gamesList.getSelectionModel().getSelectedIndex();
                myActiveGame = games.get(index);
                String description = readFile(projectRoot+gamesDirectoryPath+myActiveGame+gameDesc);
                gamesDesc.setText(description);
            }
        });
    }

    private void checkLogin() {
        if (getActiveUser().getUsername().length() > 0) {
            myAccHomeLambda.accept(myScene);
        } else {
            myAlert.createAndShow(NO_LOGIN_TITLE, NO_LOGIN_HEADER, NO_LOGIN_CONTENT);
        }
    }

    /**
     * @return the scene that will display the main menu
     */
    public Scene getScene() {
        return myScene;
    }

    /**
     * Returns the string representing the game that is currently selected in the ListView of games
     *
     * @return String representing the active game name
     */
    public String getActiveGame() {
        return myActiveGame;
    }

    /**
     * Sets the lambda to display the CreateAccount screen
     *
     * @param createAccLambda = consumer that will update the window to display the CreateAccount screen
     */
    public void setCreateAccLambda(Consumer<Scene> createAccLambda) {
        myCreateAccLambda = createAccLambda;
    }

    /**
     * Sets the lambda to display the Login screen
     *
     * @param loginLambda = consumer that will update the window to display the Login screen
     */
    public void setLoginLambda(Consumer<Scene> loginLambda) {
        myLoginLambda = loginLambda;
    }

    /**
     * Sets the lambda to display the GameLobby screen
     *
     * @param gameLambda = consumer that will update the window to display the GameLobby screen
     */
    public void setGameDescLambda(Consumer<Scene> gameLambda) {
        myGameDescLambda = gameLambda;
    }

    /**
     * Sets the lambda to display the Account home screen
     *
     * @param accHomeLambda = consumer that will update the window to display the Account home screen
     */
    public void setAccHomeLambda(Consumer<Scene> accHomeLambda) {
        myAccHomeLambda = accHomeLambda;
    }

    /**
     * Sets the lambda to transfer the active String representing the active game
     *
     * @param activeGameLambda = consumer that will track the string representing the active game
     */
    public void setCurrentGame(Consumer<String> activeGameLambda){
        myActiveGameLambda = activeGameLambda;
    }

}
