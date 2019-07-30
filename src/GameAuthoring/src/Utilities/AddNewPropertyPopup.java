package Utilities;

import Manager.Controller;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AddNewPropertyPopup extends GamePopup {
    private final static String TITLE = "Add New Property";
    private final static int PADDING = 20;
    private static final int SIZE = 300;

    private Controller myController;

    private String propertyType = "";
    private TextField propertyValueField;
    private TextField propertyNameField;
    private VBox layout;
    private HBox addPropertyValue;
    private Button confirm;


    public AddNewPropertyPopup(Controller controller){
        super();
        myController = controller;
        setUpScene();
        show();
    }

    @Override
    protected void setUpScene() {
        setTitle(TITLE);
        layout = new VBox(PADDING);
        layout.getChildren().addAll(addPropertyName(), makePropertyTypeOptions());
        addSceneToPopup(layout, SIZE, SIZE);
    }

    private HBox makePropertyTypeOptions(){
        Text propertyTypeText = new Text("Select Property Type:");
        ComboBox propertyTypes = new ComboBox();
        propertyTypes.getItems().addAll("Number", "Boolean", "State"); //FIXME: must read from GameEngine for actual property categories
        propertyTypes.valueProperty().addListener((observable, oldValue, newValue) -> { //FIXME: do not add new box each time user changes option value, only update type name
            addPropertyAndConfirm((String) newValue);
        });
        HBox propertyTypeOptions = new HBox(propertyTypeText, propertyTypes);
        formatHBox(propertyTypeOptions);
        return propertyTypeOptions;
    }

    private void addPropertyAndConfirm(String newValue){
        List<Node> cluster = new ArrayList<>();
        cluster.add(addPropertyValue(newValue));
        cluster.add(makeConfirmButton());
        layout.getChildren().addAll(cluster);
    }

    private HBox addPropertyValue(String newValue){
        propertyType = newValue;
        if (addPropertyValue != null){
            layout.getChildren().remove(addPropertyValue);
        }
        Text propertyTypeText = new Text(propertyType + " Value:");
        propertyValueField = new TextField();
        addPropertyValue = new HBox(propertyTypeText, propertyValueField);
        formatHBox(addPropertyValue);
        return addPropertyValue;
    }

    private HBox addPropertyName(){
        Text propertyNameText = new Text("Property Name");
        propertyNameField = new TextField();
        HBox addPropertyName = new HBox(propertyNameText, propertyNameField);
        formatHBox(addPropertyName);
        return addPropertyName;
    }

    private Button makeConfirmButton(){
        if (confirm != null){
            layout.getChildren().remove(confirm);
        }
        confirm = new Button("confirm");
        confirm.setOnMouseClicked(e -> {
            getStage().close();
            savePropertyInfo();
        });
        confirm.setAlignment(Pos.CENTER);
        return confirm;
    }

    private void savePropertyInfo(){
        String propertyName = propertyNameField.getText();
        String propertyValue = propertyValueField.getText();
        myController.saveNewProperty(propertyName, propertyValue);
    }

    @Override
    protected void show() {
        getStage().showAndWait();
    }
}
