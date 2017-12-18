package by.psu.gui.frames;

import by.psu.gui.logicalGui.AbstractFrame;
import by.psu.gui.logicalGui.ApplicationFX;
import by.psu.gui.logicalGui.LoaderGUI;
import by.psu.gui.logicalGui.ControllerClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainApp extends AbstractFrame implements ApplicationFX {

    private static final Logger LOG = Logger.getLogger(MainApp.class);
    private static Stage stage = null;


    public MainApp(){
        try {
            this.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public FXMLLoader getFXMLLoader() {
        return LoaderGUI.getFxmlLoader("/gui.resources/mainframe.fxml");
    }

    @Override
    public void settingStage(FXMLLoader fxmlLoader, Stage stage) throws IOException {
        stage.setScene(new Scene(fxmlLoader.load(), 850, 600));
        MainApp.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        ControllerClass controllerClass = fxmlLoader.getController();
        controllerClass.setParent(this);

        stage.show();
    }

    @Override
    public Stage getStage() {
        return MainApp.stage;
    }
}
