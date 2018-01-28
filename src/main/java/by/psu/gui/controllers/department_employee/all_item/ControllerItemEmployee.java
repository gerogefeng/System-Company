package by.psu.gui.controllers.department_employee.all_item;

import by.psu.gui.LoaderFXML;
import by.psu.gui.LoaderFilesFX;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.employee_services.EmployeeService;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerItemEmployee implements Initializable, ControllerFXLoader{

    private EmployeeService es = new EmployeeService();
    private Employee employee = null;
    private ControllerItemPane parent = null;
    private DepartureService departureService = new DepartureService();

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

    private boolean isAccessEmployee(){
        List<Departure> departures = departureService.readALL();
        for (Departure d : departures) {
            if (!d.isDelete() && d.getStatus() != 3) {
                if(d.getPostsEmployee().getEmployee().getId() == employee.getId())
                    return false;
            }
        }
        return true;
    }

    @FXML
    private void actionDelete() {
        if(isAccessEmployee()) {
            es.deleteEmployee(employee.getId());
            parent.deleteItem(anchorPane);
        } else {
            ControllerItemPane.getControllerItemPane().message("Действие заморожено. Сотрудник имеет выезды.");
        }
    }

    @FXML
    private void actionEdit() {
        if(isAccessEmployee()) {
            LoaderFXML.loaderController("/gui.resources/personal_department/item_employee/stack_pane_employee_v2.fxml", parent.getControllerDeparOder().getWorkspace(), parent.getControllerDeparOder(), employee);
        } else {
            ControllerItemPane.getControllerItemPane().message("Действие заморожено. Сотрудник имеет выезды.");
        }
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
        if(employee.getAvatar()!=null){
            try {
                Image image = new Image(new FileInputStream(employee.getAvatar()));
                avatar.setFill(new ImagePattern(image));
            } catch (FileNotFoundException ignored) {}
        }
    }

    @Override
    public void setParentController(ControllerFX controller) {
        parent = (ControllerItemPane) controller;
    }
}
