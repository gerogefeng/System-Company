package by.psu.gui.controllers.department_transport;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.model.transport.TransportRental;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.transport_services.TransportService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CCarItem implements Initializable, ControllerFXLoader {

    @FXML private JFXTextField titleTransportTextField;
    @FXML private MaterialDesignIconView statusTransport;
    @FXML private MaterialDesignIconView statusUsed;

    @FXML
    private AnchorPane anchorPane;

    private Transport transport = null;
    private TransportService ts = new TransportService();
    private CViewItem cViewItem = null;

    private DepartureService departureService = new DepartureService();

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

    @FXML
    private void actionDelete() {
        if (isAccessTransport()) {
            ts.deleteTransport(transport.getId());
            cViewItem.deleteItem(anchorPane);
        } else {
            CViewItem.getcViewItem().message("Действие заморожено. Транспорт зарезервирован.");
        }
    }

    private boolean isAccessTransport() {
        List<Departure> list = departureService.readALL();
        for (Departure departure : list) {
            if (!departure.isDelete()) {
                List<TransportRental> transportRentals = departure.getTransportRentals();
                for (TransportRental transportRental : transportRentals) {
                    if (transportRental.getTransport().getId() == transport.getId()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @FXML
    private void actionEdit() {
        if (isAccessTransport()) {
            LoaderFXML.loaderController(
                    "/gui.resources/transport/stack_pane_action_car.fxml",
                    CDeparTransport.getcDeparTransport().getWorkspace(),
                    CDeparTransport.getcDeparTransport(),
                    transport);
        } else {
            CViewItem.getcViewItem().message("Действие заморожено. Транспорт зарезервирован.");
        }
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        transport = (Transport) objects[0];

        titleTransportTextField.setText(transport.getMarkAuto().getTitle() + " " + transport.getModelAuto().getTitle());
        statusTransport.setFill((transport.getStatus()==1) ? Color.GREEN : Color.RED);
        statusUsed.setFill(!isAccessTransport() ? Color.GREEN : Color.RED);
    }
}
