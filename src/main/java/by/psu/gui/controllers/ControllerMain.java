package by.psu.gui.controllers;

import by.psu.gui.logicalGui.ApplicationFX;
import by.psu.gui.logicalGui.ControllerClass;
import by.psu.gui.logicalGui.LoaderGUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable, ControllerClass {

    private ApplicationFX applicationFX = null;
    private boolean winFullScreen = false;
    private static final Logger LOG = Logger.getLogger(ControllerMain.class);

    @FXML private AnchorPane workSpace;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML private void actionEmployees(){
        workSpace.getChildren().clear();
        LoaderGUI.installScene(workSpace, "/gui.resources/personal_department/anchorPaneMain.fxml", this);
    }

    @FXML private void actionCloseWindow(){
        LOG.info("actionCloseWindow");
        applicationFX.getStage().close();
    }

    @FXML public void actionFullScreen(){
        if(winFullScreen)
            applicationFX.getStage().setFullScreen(false);
        else
            applicationFX.getStage().setFullScreen(true);
        winFullScreen = !winFullScreen;
        LOG.info("actionFullScreen");
    }

    @FXML private void consumeStage(){
        LOG.info("consumeStage");
        applicationFX.getStage().setIconified(true);
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setData(Object... objects) {}

    @Override
    public void setParent(ApplicationFX applicationFX) {
        this.applicationFX = applicationFX;
    }
}
