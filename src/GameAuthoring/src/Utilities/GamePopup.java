package Utilities;

import Manager.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class GamePopup {
    private Stage myStage;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;
    private static final int PADDING = 20;
    private static final Pos ALIGNMENT = Pos.CENTER;
    private static final int SCENE_SIZE = 500;

    public GamePopup() {
        myStage = new Stage();
        myStage.initModality(Modality.APPLICATION_MODAL);
    }

    public Stage getStage() {
        return myStage;
    }

    protected void setTitle(String t) {
        myStage.setTitle(t);
    }

    protected abstract void setUpScene() throws FileNotFoundException;

    protected void addSceneToPopup(Parent root) {
        Scene s = new Scene(root, SCENE_SIZE, SCENE_SIZE);
        s.getStylesheets().add("styling.css");
        getStage().setScene(s);
    }

    protected void addSceneToPopup(Parent root, int width, int height) {
        Scene s = new Scene(root, width, height);
        s.getStylesheets().add("styling.css");
        getStage().setScene(s);
    }

    protected abstract void show();

    protected Button makeButton(String text, EventHandler<ActionEvent> handler) {
        var button = new Button(text);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setOnAction(handler);
        return button;
    }

    protected Button makeButton(String text, EventHandler<ActionEvent> handler, int width, int height) {
        var button = new Button(text);
        button.setPrefSize(width, height);
        button.setOnAction(handler);
        return button;
    }

    protected void formatHBox(HBox box) {
        box.setSpacing(PADDING);
        box.setAlignment(ALIGNMENT);
    }

    protected String getSelectedItem(ComboBox options) {
        return (String) options.getSelectionModel().getSelectedItem();
    }

    protected List<String> getFileFromJar(String folder) { //From StackOverflow https://stackoverflow.com/questions/18055189/why-is-my-uri-not-hierarchical\
        ArrayList<String> files = null;
        File jarFile = null;
        try {
            files = new ArrayList<>();
            jarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            System.out.println("error");
        }
        String actualFile = jarFile.getParentFile().getAbsolutePath() + File.separator + "GameAuthoring.jar";
        try {
            JarFile jar = new JarFile(actualFile);
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.startsWith(folder)) { //filter according to the path
                    File scriptsFile = new File(name);
                    files.add(scriptsFile.getAbsolutePath());
                }
            }
            jar.close();
        } catch (IOException e) {
            System.out.println("ERROR"); // ERROR never reached!
        }
        files.remove(0);
        return files;
    }

}

