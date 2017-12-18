package by.psu.gui.controllers.departmentEmployee;

import by.psu.gui.logicalGui.ApplicationFX;
import by.psu.gui.logicalGui.ControllerClass;
import by.psu.gui.logicalGui.LoadControllerClass;
import by.psu.logical.LoaderImage;
import by.psu.logical.model.Employee;
import by.psu.logical.service.DriverLicenceService;
import by.psu.logical.service.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerItemEmployee implements Initializable, LoadControllerClass {

    private EmployeeService es = new EmployeeService();
    private DriverLicenceService dls = new DriverLicenceService();

    private Employee employee = new Employee();
    private LoadControllerClass controller = null;

    @FXML
    private Circle avatar;

    @FXML
    private Label fullName;

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
        //es.delete(employee);
    }

    @FXML
    private void actionEdit() {

    }

    @FXML
    private void actionView() {

    }

    @Override
    public void setParentController(ControllerClass controller) {
        this.controller = (LoadControllerClass) controller;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setData(Object... objects) {
        this.employee = (Employee) objects[0];
        this.fullName.setText(employee.getName() + " " + employee.getLastName() + " " + employee.getPatronymic());
        /*List<Category> list = employee.getDriverLicences().get(0).getCategories();
        for (Category aList : list)
            stringBuilder.append(aList.getCategory()).append(" ");

        this.category.setText(stringBuilder.toString());
*/
        LoaderImage loaderImage = new LoaderImage(employee);
        this.avatar.setFill(new ImagePattern(loaderImage.getImage()));
    }

    @Override
    public void setParent(ApplicationFX applicationFX) {

    }
}
