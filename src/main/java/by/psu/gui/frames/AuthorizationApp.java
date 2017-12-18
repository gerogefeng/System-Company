package by.psu.gui.frames;

import by.psu.gui.logicalGui.AbstractFrame;
import by.psu.gui.logicalGui.LoaderGUI;
import by.psu.gui.logicalGui.ControllerClass;
import by.psu.logical.unit.SessionHibernate;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AuthorizationApp extends AbstractFrame {
    private static Stage stageMove;
    private static Stage stage = null;
    private double mouseDragDeltaX = 0;
    private double mouseDragDeltaY = 0;

    @Override
    public FXMLLoader getFXMLLoader() {
        return LoaderGUI.getFxmlLoader("/gui.resources/authorization.fxml");
    }

    @Override
    public void settingStage(FXMLLoader fxmlLoader, Stage stage) throws IOException {
        Scene scene = new Scene(fxmlLoader.load(), 457, 500);
        scene.setFill(Color.TRANSPARENT);
        stageMove = stage;

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        AuthorizationApp.stage = stage;

        ControllerClass controllerClass = fxmlLoader.getController();
        controllerClass.setParent(this);

        stage.show();
    }

    @Override
    public Stage getStage() {
        return AuthorizationApp.stage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        new Thread(SessionHibernate::getInstance).start();
    }

    @Override
    public void stop() throws Exception {
        SessionHibernate.getInstance().closeFactory();
    }
}
