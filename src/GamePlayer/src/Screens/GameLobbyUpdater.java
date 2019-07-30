package Screens;

public class GameLobbyUpdater {

    GameLobbyScreen myGameLobbyScreen;
    MainMenuScreen myMainMenu;

    // TODO : Move the setUpdateGameLambda() function to the ScreenManager class

    public GameLobbyUpdater(GameLobbyScreen gameLobbyScreen, MainMenuScreen mainMenu){
        myGameLobbyScreen = gameLobbyScreen;
        myMainMenu = mainMenu;
        setUpdateGameLambda();
    }


    public void updateActiveGame(String activeGame){
        myGameLobbyScreen.updateGameLobby(activeGame);
    }

    public void setUpdateGameLambda(){
        myMainMenu.setCurrentGame(event -> myGameLobbyScreen.updateGameLobby(myMainMenu.getActiveGame()));
    }


}
