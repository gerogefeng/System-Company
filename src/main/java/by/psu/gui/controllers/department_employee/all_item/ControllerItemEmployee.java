package by.psu.gui.controllers.department_employee.all_item;

import by.psu.gui.LoaderFXML;
import by.psu.gui.LoaderFilesFX;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.service.employee_services.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerItemEmployee implements Initializable, ControllerFXLoader{

    private EmployeeService es = new EmployeeService();
    private Employee employee = null;
    private ControllerItemPane parent = null;

    @FXML private Circle avatar;
    @FXML private AnchorPane anchorPane;
    @FXML private Label fullName;

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

    @FXML
    private void actionDelete() {
        es.delete(employee);
        parent.deleteItem(anchorPane);
    }

    @FXML
    private void actionEdit() {
        LoaderFXML.loaderController("/gui.resources/personal_department/item_employee/stack_pane_employee_v2.fxml", parent.getControllerDeparOder().getWorkspace(), parent.getControllerDeparOder(), employee);
    }

    @FXML
    private void actionView() {

    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        employee = (Employee) objects[0];
        fullName.setText(
                          employee.getName() + " "
                        + employee.getLastName() + " "
                        + employee.getPatronymic()
        );
    }

    @Override
    public void setParentController(ControllerFX controller) {
        parent = (ControllerItemPane) controller;
    }
}
