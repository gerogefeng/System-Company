package by.psu.gui;

import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.io.IOException;


public class LoaderFXML {

    private static final Logger LOGGER = Logger.getLogger(LoaderFXML.class);

    public static Object[] loaderController(String fxml, Pane parent, ControllerFX cFX, Object ... objects) {
        Object[] metadata = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoaderFXML.class.getResource(fxml));
            Pane child = fxmlLoader.load();

            ControllerFX controller = fxmlLoader.getController();
            controller.setParentController(cFX);
            if(objects.length > 0){
                ControllerFXLoader controllerFXLoader = (ControllerFXLoader) controller;
                controllerFXLoader.setData(objects);
            }

            metadata = new Object[]{controller, child.hashCode()};
            
            AnchorPane.setLeftAnchor(child, 0.0);
            AnchorPane.setTopAnchor(child, 0.0);
            AnchorPane.setRightAnchor(child, 0.0);
            AnchorPane.setBottomAnchor(child, 0.0);

            parent.getChildren().add(child);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metadata;
    }
}
