package by.psu.gui.controllers.department_transport;

import Test.Main;
import by.psu.gui.LoaderFXML;
import by.psu.gui.controllers.ControllerMain;
import by.psu.gui.logicalGui.ControllerFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CDeparTransport implements Initializable, ControllerFX{

    private ControllerMain controllerMain = null;

    @FXML private AnchorPane workspace;
    @FXML private StackPane stackPane;

    private static CDeparTransport cDeparTransport;
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
        cDeparTransport = this;
        actionGetCars();
    }

    @FXML private void actionPlusCar(){
        LoaderFXML.loaderController("/gui.resources/transport/stack_pane_action_car.fxml", workspace, this);
    }

    @FXML private void actionGetCars(){
        LoaderFXML.loaderController("/gui.resources/transport/anchor_pane_view_car.fxml", workspace, this);
    }

    public static CDeparTransport getcDeparTransport() {
        return cDeparTransport;
    }

    @Override
    public void setParentController(ControllerFX controller) {
        controllerMain = (ControllerMain) controller;
    }

    public AnchorPane getWorkspace() {
        return workspace;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setControllerMain(ControllerMain controllerMain) {
        this.controllerMain = controllerMain;
    }
}
