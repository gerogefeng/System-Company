package by.psu.gui.controllers.department_order;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.instrument.InstrumentDeparture;
import by.psu.logical.model.order.Order;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.order_services.OrderService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class COrderItem implements Initializable, ControllerFXLoader{

    @FXML private AnchorPane anchorPane;

    @FXML private JFXTextField titleTextField;

    @FXML private MaterialDesignIconView statusOrganization;

    @FXML private CViewItems cViewItems;

    private Order order = null;
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
        cViewItems = CViewItems.getcViewItems();
    }

    private boolean isAccessOrder(){
        List<Departure> departures = departureService.readALL();
        for (Departure d : departures) {
            if (!d.isDelete() && d.getStatus() != 3) {
                if(d.getOrder().getId() == order.getId())
                    return false;
            }
        }
        return true;
    }

    @FXML
    void actionDelete() {
        cViewItems.getvBoxItemOrders().getChildren().remove(anchorPane);
        new OrderService().deleteOrder(order.getId());
    }

    @FXML
    void actionEdit() {
        if(isAccessOrder()) {
            LoaderFXML.loaderController("/gui.resources/department_order/stack_pane_action_order.fxml", CDepartamentOder.getDepartamentOder().getStackPane(), CDepartamentOder.getDepartamentOder(), order);
        } else {
            CViewItems.getcViewItems().message("Действие заморожено. Данный заказ выполняется или будет ещё выполнен.");
        }
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        order = (Order) objects[0];
        titleTextField.setText(order.getOrganization().getTitle() + " " + order.getPlace().getTitle());
        statusOrganization.setFill(!isAccessOrder() ? Color.GREEN : Color.RED);
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cViewItems = (CViewItems) controller;
    }
}
