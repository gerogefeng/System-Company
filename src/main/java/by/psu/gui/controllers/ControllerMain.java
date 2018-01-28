package by.psu.gui.controllers;

import by.psu.gui.LoaderFXML;
import by.psu.gui.frames.Frame;
import by.psu.gui.frames.FrameWork;
import by.psu.gui.logicalGui.ControllerFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable, ControllerFX {

    private boolean winFullScreen = false;

    private static final Logger LOG = Logger.getLogger(ControllerMain.class);

    @FXML private Label statusRow;
    @FXML private AnchorPane workSpace;

    private static ControllerMain controllerMain;

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
        controllerMain = this;
        actionEmployees();

    }

    public static ControllerMain getControllerMain() {
        return controllerMain;
    }

    public void setRowStatus(String message){
        statusRow.setText(message);
    }

    @FXML private void actionEmployees(){
        setRowStatus("/сотрудники");
        LoaderFXML.loaderController(
                "/gui.resources/personal_department/anchorPaneMain.fxml",
                workSpace,
                this
        );
    }

    @FXML private void actionCloseWindow(){
        LOG.info("actionCloseWindow");
        FrameWork.getGlobalStage().close();
    }

    @FXML public void actionFullScreen(){
        FrameWork.getGlobalStage().setFullScreen(!(winFullScreen));
        winFullScreen = !winFullScreen;
        LOG.info("actionFullScreen");
    }

    @FXML private void consumeStage(){
        LOG.info("consumeStage");
        FrameWork.getGlobalStage().setIconified(true);
    }

    @FXML private void actionDepartmentCar() {
        setRowStatus("/автопарк");
        LoaderFXML.loaderController(
                "/gui.resources/transport/stack_pane_main_scene.fxml",
                workSpace,
                this
        );
    }
    @FXML private void actionDepartmentOrder() {
        setRowStatus("/компании-заказчики");
        LoaderFXML.loaderController(
                "/gui.resources/department_order/stack_pane_main_scene.fxml",
                workSpace,
                this
        );
    }
    @FXML private void actionDepartmentInstrument() {
        setRowStatus("/аттракционы");
        LoaderFXML.loaderController(
                "/gui.resources/department_instruments/stack_pane_main_instrument.fxml",
                workSpace,
                this
        );
    }

    @FXML private void actionDepartmentDeparture() {
        setRowStatus("/выезды");
        LoaderFXML.loaderController(
                "/gui.resources/department_departure/stack_pane_main_departure.fxml",
                workSpace,
                this
        );
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    @FXML private void goToAuthorization(){
        FrameWork.getGlobalStage().close();
        Frame.getGlobalStage().show();
    }
}
