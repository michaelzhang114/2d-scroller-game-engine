package Utilities;

import Manager.ScreenManager;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;

public class DeletePopup extends GamePopup{

    private Object objectToDelete;
    private ScreenManager myManager;

    public DeletePopup(Object o, ScreenManager manager){
        super();
        myManager = manager;
        objectToDelete = o;
    }



    @Override
    protected void setUpScene() {
        var box = new HBox();
        box.getChildren().addAll(makeButton("Cancel", e->getStage().close()),
                makeButton("Delete", e->System.out.println("delete")));
    }

    @Override
    protected void show() {

    }
}
