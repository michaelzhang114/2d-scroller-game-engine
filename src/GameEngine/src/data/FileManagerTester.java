package data;

public class FileManagerTester {

    public static void main(String[] args){
        FileManager f = new FileManager();

        String game = "super mario";
        f.addGame(game);
        f.addDescriptionToGame(game, "play the og mario");
        f.addInstructionsToGame(game, "wasd");
        f.addIconToGame(game, "/Users/justinkim/Duke/sp19/CS308/voogasalad_mightydux/src/GameEngine/resources/goomba.png");

    }
}
