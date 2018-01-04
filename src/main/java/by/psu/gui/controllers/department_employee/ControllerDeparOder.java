package by.psu.gui.controllers.department_employee;

import by.psu.gui.LoaderFXML;
import by.psu.gui.controllers.ControllerMain;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.service.employee_services.EmployeeService;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDeparOder implements Initializable, ControllerFX {

    private static final Logger LOGGER = Logger.getLogger(ControllerDeparOder.class);

    private ControllerMain controllerMain = null;

    private EmployeeService es = new EmployeeService();


    @FXML
    private JFXButton menu1;
    @FXML
    private JFXButton menu3;
    @FXML
    private JFXButton menu4;
    @FXML
    private JFXButton menu5;

    @FXML
    private AnchorPane workspace;

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
        actionGetAllEmployee();
    }

    @FXML
    private void actionAddEmployee() {
        LoaderFXML.loaderController("/gui.resources/personal_department/item_employee/stack_pane_employee_v2.fxml", workspace, this);
    }

    public void actionMessageSceneAddEmployee() {
        LoaderFXML.loaderController("/gui.resources/personal_department/stack_pane_employee_plus.fxml", workspace, this);
    }

    @FXML
    public void actionGetAllEmployee() {
        LoaderFXML.loaderController("/gui.resources/personal_department/stack_pane_employee_items.fxml", workspace, this);
    }

    @FXML
    private void actionFilter() {

    }

    public AnchorPane getWorkspace() {
        return workspace;
    }

    @Override
    public void setParentController(ControllerFX controller) {
        this.controllerMain = (ControllerMain) controller;
    }
}
