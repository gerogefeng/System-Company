package by.psu.gui.controllers.departmentEmployee;

import by.psu.gui.logicalGui.ApplicationFX;
import by.psu.gui.logicalGui.ControllerClass;
import by.psu.gui.logicalGui.LoadControllerClass;
import by.psu.gui.logicalGui.LoaderGUI;
import by.psu.logical.model.Employee;
import by.psu.logical.service.EmployeeService;
import by.psu.logical.service.UserService;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerDeparOder implements Initializable, LoadControllerClass {

    private EmployeeService es = new EmployeeService();
    private static ControllerClass controllerClass = null;

    private boolean isFilter = true;

    //menu

    @FXML private JFXButton menu1;
    @FXML private JFXButton menu2;
    @FXML private JFXButton menu3;
    @FXML private JFXButton menu4;
    @FXML private JFXButton menu5;

    @FXML private ScrollPane scrollPane;
    @FXML private VBox vBox;

    @FXML private AnchorPane anchorPaneAllEmployee;


    @FXML private StackPane stackPaneAddEmployee;

    @FXML private JFXDialog dialog;
    @FXML private JFXTextField nameEmployee;
    @FXML private JFXTextField lastNameEmployee;
    @FXML private JFXTextField patronymicEmployee;
    @FXML private JFXDatePicker birthdayEmployee;
    @FXML private JFXTextField mobilePhoneEmployee;
    @FXML private JFXTextField emailEmployee;
    @FXML private JFXButton addEmployeeButton;

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
        setVisiblePane(true);
        List<Employee> list = es.readALL();
        list.forEach(employee ->
            LoaderGUI.loaderDynamicItem(
                    "/gui.resources/personal_department/anchorItemEmployee.fxml",
                    vBox,
                    this,
                    employee
            )
        );
    }

    @FXML private void action(Event event){
        setVisiblePane(true);
        if (event.getTarget() == menu1){
            stackPaneAddEmployee.setVisible(true);
        } else if(event.getTarget() == menu2){

        } else if(event.getTarget() == menu3){
            anchorPaneAllEmployee.setVisible(true);
        }
    }

    @Override
    public void setParentController(ControllerClass controller) {
        controllerClass = controller;
    }

    @Override
    public Object getData() {
        return null;
    }


    @Override
    public void setData(Object... objects) {

    }

    @Override
    public void setParent(ApplicationFX applicationFX) {

    }

    @FXML private void actionFilter(){

    }

    private void setVisiblePane(boolean isVisiblePane){
        anchorPaneAllEmployee.setVisible(!isVisiblePane);
        stackPaneAddEmployee.setVisible(!isVisiblePane);
    }
}
