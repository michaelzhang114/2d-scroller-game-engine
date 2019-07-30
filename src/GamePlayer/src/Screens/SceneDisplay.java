package Screens;

import javafx.scene.Scene;

import java.io.*;
import java.util.Properties;

/**
 * This class is abstract and contains methods that are shared by many of the screens in the project. This class contains
 * methods that can load and read from properties files as well as an abstract buildScene() method that all of its subclasses
 * must implement to initialize their displays.
 *
 * @author Connor Ghazaleh and Eric Werbel
 */
public abstract class SceneDisplay extends ScreenFunction {

    protected static final int SCENE_WIDTH = 500;
    protected static final int SCENE_HEIGHT = 500;

    /**
     * Load properties file from a file path
     * @param file - path to file to load
     * @return
     */
    public Properties loadPropsFile(String file){
        Properties properties = new Properties();
        try{
            InputStream propsFile = new FileInputStream(file);
            properties.load(propsFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Read text file from file path
     * @param filePath - path to file to read
     * @return
     */
    public String readFile(String filePath){
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file");
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        try{
            String line = buf.readLine();
            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }
        }catch(IOException e){
            System.out.println("Could not read from file");
        }
        return sb.toString();
    }

    protected abstract Scene buildScene();

}
