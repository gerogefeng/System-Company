package by.psu.gui.controllers.department_employee.all_item;

import by.psu.gui.LoaderFXML;
import by.psu.gui.controllers.department_employee.ControllerDeparOder;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.model.employee.Driver;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.Passport;
import by.psu.logical.service.employee_services.EmployeeService;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerItemPane implements Initializable, ControllerFX {

    @FXML
    private VBox vBoxItemEmployee;
    @FXML
    private JFXTextField searchTextField;

    @FXML StackPane stackPane;

    @FXML
    private JFXRadioButton name;
    @FXML
    private JFXRadioButton searchPassport;
    @FXML
    private JFXRadioButton searchDriver;

    private ControllerDeparOder controllerDeparOder = null;

    @FXML
    private HBox hBoxRadioButton;

    private JFXSnackbar messager = new JFXSnackbar();

    private final EmployeeService employeeService = new EmployeeService();

    private static ControllerItemPane controllerItemPane = null;
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
        loadItem();
        controllerItemPane = this;
        List<Employee> list = employeeService.readALL();
        searchTextField.textProperty().addListener((obs, oV, nV) -> {
            vBoxItemEmployee.getChildren().clear();
            if(!nV.equalsIgnoreCase("")) {
                if(name.isSelected()){
                    for (int i = 0; i < list.size(); i++) {
                        String fullName = list.get(i).getLastName() + list.get(i).getPatronymic() + list.get(i).getName();
                        if(fullName.toUpperCase().contains(nV.toUpperCase()))
                            filterLoadItem(list.get(i));
                    }
                }
                if(searchPassport.isSelected()){
                    for (int i = 0; i < list.size(); i++) {
                        Employee employee = list.get(i);
                        final Passport passport = list.get(i).getPassport().iterator().next();
                        if(passport == null) continue;
                        final String dataPassport = passport.getNumberPassport() + " "
                                + passport.getDepartment() +  " "
                                + passport.getSerialPassport() + " "
                                + passport.getNationality() + " "
                                + passport.getDateIn().toString() + " "
                                + passport.getDateOut().toString();
                        if(dataPassport.toUpperCase().contains(nV.toUpperCase())){
                            filterLoadItem(employee);
                        }
                    }
                }

                if(searchDriver.isSelected()){
                    for (int i = 0; i < list.size(); i++) {
                        Employee employee = list.get(i);
                        final Driver driver = list.get(i).getDriver().iterator().next();
                        if(driver == null) continue;
                        final String dataDriver = driver.getDepartment() + " " + driver.getNumber() + " " + driver.getDateIn().toString() + " " + driver.getDateOut().toString();
                        if(dataDriver.toUpperCase().contains(nV.toUpperCase())){
                            filterLoadItem(employee);
                        }
                    }
                }
            } else {
                loadItem();
            }
        });

        searchTextField.focusedProperty().addListener((obs, oV, nV) ->
                hBoxRadioButton.setVisible(!searchTextField.isFocused())
        );

        final ToggleGroup group = new ToggleGroup();
        name.setToggleGroup(group);
        name.setSelected(true);
        searchPassport.setToggleGroup(group);
        searchDriver.setToggleGroup(group);
    }

    private void filterLoadItem(Employee employee){
        Platform.runLater(() ->
                LoaderFXML.loaderController(
                        "/gui.resources/personal_department/anchorItemEmployee.fxml",
                        vBoxItemEmployee, this, employee)
        );
    }

    private void loadItem(){
        vBoxItemEmployee.getChildren().clear();
        employeeService.readAllEmployees().forEach(employee -> {
            Platform.runLater(() ->
                    LoaderFXML.loaderController(
                            "/gui.resources/personal_department/anchorItemEmployee.fxml",
                            vBoxItemEmployee, this, employee)
            );
        });
    }

    @Override
    public void setParentController(ControllerFX controller) {
        controllerDeparOder = (ControllerDeparOder) controller;
    }

    public ControllerDeparOder getControllerDeparOder() {
        return controllerDeparOder;
    }

    protected void deleteItem(AnchorPane anchorPane) {
        for (Node node : vBoxItemEmployee.getChildren()) {
            if (node.hashCode() == anchorPane.hashCode()) {
                vBoxItemEmployee.getChildren().remove(anchorPane);
                break;
            }
        }
    }

    void message(final String title) {
        if (messager.getPopupContainer() == null)
            messager.registerSnackbarContainer(stackPane);
        messager.show(title, "Закрыть",
                2000, event -> messager.unregisterSnackbarContainer(stackPane));
    }

    public static ControllerItemPane getControllerItemPane() {
        return controllerItemPane;
    }
}
