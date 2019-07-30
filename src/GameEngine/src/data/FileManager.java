package data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class FileManager {

    /**
     * @Author Justin
     * This class serves as the manager that keeps track of where games are saved and where to find them. The games are saved into
     * and loaded from /root/games/data. Each game has its own folder with 4 default files: icon.png, description.txt, instructions.txt,
     *  and game.xml. All of these files need to be created through game authoring so that game player can play the games.
     */
    private final String GAMES_DIRECTORY = "games";
    private final String DATA_DIRECTORY = "data";
    private final String GAMES_PROPERTIES_FILE_NAME = "games.properties";
    private final String SAVE_FILE_DIRECTORY_NAME = "save_files";

    private final String ICON_FILE_NAME = "icon.png";
    private final String DESCRIPTION_FILE_NAME = "description.txt";
    private final String INSTRUCTION_FILE_NAME = "instructions.txt";
    private final String XML_FILE_NAME = "game.xml";

    private final String WHITE_SPACE = "\\s+";
    private final String UNDERSCORE = "_";

    private String gamesDirectoryPath;
    private String gamesPropertiesFilePath;

    public FileManager (){
        String projectRoot = System.getProperty("user.dir");
        gamesDirectoryPath = concatenatePaths(projectRoot, DATA_DIRECTORY, GAMES_DIRECTORY);
        var gamesDirectory = new File(gamesDirectoryPath);
        if(!gamesDirectory.exists()) {
            gamesDirectory.mkdir();
        }
        gamesPropertiesFilePath = concatenatePaths(gamesDirectoryPath, GAMES_PROPERTIES_FILE_NAME);
    }

    /**
     * Creates a file directory for a new game named gameName
     * @param gameName
     */
    public void addGame(String gameName) {
        gameName = cleanGameName(gameName);
        addGameToProperties(gameName);
        var gameDir = new File(concatenatePaths(gamesDirectoryPath, gameName));
        if(!gameDir.exists()) {
            gameDir.mkdir();
        }
    }

    /**
     * deletes the directory for a specific game
     * @param gameName
     */
    public void deleteGame(String gameName){
        gameName = cleanGameName(gameName);

        Properties properties = getProperties();
        if (properties.contains(gameName)){
            properties.remove(gameName);
        }
    }

    /**
     * Copies the image path of an image and places it in the folder of a specific game. This is the display icon for that
     * game and will be used for game player and is saved by game authoring.
     * @param gameName
     * @param pathToImage
     */
    public void addIconToGame(String gameName, String pathToImage){
        gameName = cleanGameName(gameName);
        String gamePath = concatenatePaths(gamesDirectoryPath, gameName);
        String iconPath = concatenatePaths(gamePath, ICON_FILE_NAME);

        copyFile(pathToImage, iconPath);
    }


    /**
     * Writes the description of the game into a file for a specific game.
     * @param gameName
     * @param description
     */
    public void addDescriptionToGame(String gameName, String description){
        gameName = cleanGameName(gameName);
        String descriptionPath = concatenatePaths(gamesDirectoryPath, gameName, DESCRIPTION_FILE_NAME);
        writeToFile(descriptionPath, description);
    }

    /**
     * Writes the instructions for the game into a file for a specific game.
     * @param gameName
     * @param instructions
     */
    public void addInstructionsToGame(String gameName, String instructions){
        gameName = cleanGameName(gameName);
        String instructionsPath = concatenatePaths(gamesDirectoryPath, gameName, INSTRUCTION_FILE_NAME);
        writeToFile(instructionsPath, instructions);
    }

    /**
     * Saves the original XML file for a specific game. This file should only be edited through the game authroing environment
     * @param gameName
     * @param xml
     */
    public void saveOriginalGame(String gameName, String xml){ // ONLY FOR GAME AUTHORING
        gameName = cleanGameName(gameName);
        String xmlPath = concatenatePaths(gamesDirectoryPath, gameName, XML_FILE_NAME);
        writeToFile(xmlPath, xml);
    }

    /**
     * Returns the XML string for a specific game's original state
     * @param gameName
     * @return
     */
    public String loadOriginalGameXML(String gameName){
        gameName = cleanGameName(gameName);
        String xmlPath = concatenatePaths(gamesDirectoryPath, gameName, XML_FILE_NAME);
        try {
            return new Scanner(new File(xmlPath)).useDelimiter("\\Z").next();
        }
        catch (FileNotFoundException e){
            return "";
        }
    }

    /**
     * Saves the state of a game at a specific point specified by the user into a file called saveFile
     * @param gameName
     * @param saveFile
     * @param xml
     */
    public void saveGameState(String gameName, String saveFile, String xml){
        createSaveFileDirectory(gameName);
        String saveFilePath = concatenatePaths(gamesDirectoryPath, gameName, SAVE_FILE_DIRECTORY_NAME, saveFile + ".xml");
        writeToFile(saveFilePath, xml);
    }

    // write contents into filepath
    private boolean writeToFile(String filePath, String contents){
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(contents);
            writer.close();
            return true;
        } catch (IOException e){
            return false;
        }
    }

    // Checks if a game has been created
    private boolean doesGameExists(String gameName){

        Properties availableGames = getProperties();
        return availableGames.contains(gameName);

    }

    // copies a file from fromPath to toPath
    // https://www.javadevjournal.com/java/java-copy-file-directory/
    private boolean copyFile(String fromPath, String toPath){
        Path src = Paths.get(fromPath);
        Path dest = Paths.get(toPath);
        try {
            Files.copy(src, dest);
            return true;
        } catch (IOException e){
            return false;
        }

    }

    // Add a gamename to the properties file
    private boolean addGameToProperties(String gameName){
        gameName = cleanGameName(gameName);
        Properties prop = getProperties();
        try (OutputStream output = new FileOutputStream(gamesPropertiesFilePath)) {
            prop.setProperty(gameName, gameName);
            prop.store(output, null);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Load in the properties files
    private Properties getProperties(){
        var temp = new File(gamesPropertiesFilePath);
        Properties properties = new Properties();

        if (temp.exists()) {
            try {
                InputStream input = new FileInputStream(gamesPropertiesFilePath);
                properties.load(input);

            } catch (IOException e){
                return null;
            }
        }
        return properties;
    }

    // concatenate paths with varying length of parameters
    private String concatenatePaths(String... paths){

        File file = new File(paths[0]);
        for (int i = 1; i < paths.length ; i++) {
            file = new File(file, paths[i]);
        }
        return file.getPath();
    }

    // Create a directory that will house the save files for saved states of the game
    private void createSaveFileDirectory(String gameName){
        gameName = cleanGameName(gameName);
        String gamePath = concatenatePaths(GAMES_DIRECTORY, gameName);
        String savedDirPath = concatenatePaths(gamePath, SAVE_FILE_DIRECTORY_NAME);
        var savedDir = new File(savedDirPath);
        if(!savedDir.exists()) {
            savedDir.mkdir();
        }
    }

    // remove whitespace and replace with an underscore
    private String cleanGameName(String gameName){
        return gameName.replaceAll(WHITE_SPACE, UNDERSCORE);
    }
}
