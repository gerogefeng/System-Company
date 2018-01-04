package by.psu.gui.controllers.department_transport;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.service.transport_services.TransportService;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CCarItem implements Initializable, ControllerFXLoader {

    @FXML private Label fullName;
    @FXML private Label valueWeight;
    @FXML private Label capacity;
    @FXML private Label statusCar;
    @FXML private JFXButton edit;
    @FXML private JFXButton delete;
    @FXML private AnchorPane anchorPane;

    private Transport transport = null;
    private TransportService ts = new TransportService();
    private CViewItem cViewItem = null;

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

    @Override
    public void setParentController(ControllerFX controller) {
        cViewItem = (CViewItem) controller;
    }

    @FXML private void actionDelete(){
        ts.delete(transport);
        cViewItem.deleteItem(anchorPane);
    }

    @FXML private void actionEdit(){
        LoaderFXML.loaderController(
                "/gui.resources/transport/stack_pane_action_car.fxml",
                CDeparTransport.getcDeparTransport().getWorkspace(),
                CDeparTransport.getcDeparTransport(),
                transport
        );
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        transport = (Transport) objects[0];
        fullName.setText(transport.getMarkAuto().getTitle() + " " + transport.getModelAuto().getTitle());
        valueWeight.setText(String.valueOf(transport.getCapacity()));
        statusCar.setText((transport.getStatus() == 1) ? "не нуждается в ремонте" : "нуждается в ремонте");
    }
}
