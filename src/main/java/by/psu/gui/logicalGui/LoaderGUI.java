package by.psu.gui.logicalGui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;

public class LoaderGUI {

    public static FXMLLoader getFxmlLoader(String controller, Object ... object){
        return new FXMLLoader(LoaderGUI.class.getResource(controller));
    }

    public static void installScene(AnchorPane anchorPane,
                                    String controller,
                                    ControllerClass parentController,
                                    Object ... objects){
        FXMLLoader fxmlLoader = new FXMLLoader(LoaderGUI.class.getResource(controller));
        Node node = null;
        try {
            node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoadControllerClass loadControllerClass = fxmlLoader.getController();
        loadControllerClass.setData(objects);
        loadControllerClass.setParentController(parentController);

        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);

        anchorPane.getChildren().add(node);
    }

    public static void loaderDynamicItem(String pathFXML,
                                         VBox vBox,
                                         ControllerClass controllerClass,
                                         Object ... objects ) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoaderGUI.class.getResource(pathFXML));
        AnchorPane anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();

            LoadControllerClass loadControllerClass = fxmlLoader.getController();
            loadControllerClass.setData(objects[0]);
            loadControllerClass.setParentController(controllerClass);

            vBox.getChildren().add(anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
