package by.psu.gui.controllers.department_order;

import by.psu.gui.LoaderFXML;
import by.psu.gui.controllers.ControllerMain;
import by.psu.gui.logicalGui.ControllerFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CDepartamentOder implements Initializable, ControllerFX{


    @FXML private StackPane stackPane;


    private ControllerMain cm = null;
    private static CDepartamentOder departamentOder = null;
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
        departamentOder = this;
        actionGetOrder();
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cm = (ControllerMain) controller;
    }

    @FXML
    private void actionGetOrder(){
        LoaderFXML.loaderController("/gui.resources/department_order/stack_pane_view_order.fxml", stackPane, this);
    }

    @FXML private void actionPlusOrder(){
        LoaderFXML.loaderController("/gui.resources/department_order/stack_pane_action_order.fxml", stackPane, this);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public static CDepartamentOder getDepartamentOder() {
        return departamentOder;
    }
}
