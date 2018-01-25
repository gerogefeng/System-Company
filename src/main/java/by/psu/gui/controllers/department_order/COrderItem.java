package by.psu.gui.controllers.department_order;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.order.Order;
import by.psu.logical.service.order_services.OrderService;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class COrderItem implements Initializable, ControllerFXLoader{

    @FXML private AnchorPane anchorPane;

    @FXML private Circle avatar;

    @FXML private JFXButton edit;
    @FXML private JFXButton delete;

    @FXML private Label place;
    @FXML private Label organization;

    @FXML private CViewItems cViewItems = null;

    private Order order = null;

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
    void actionDelete() {
        cViewItems.deleteItem(anchorPane);
        order.setStatus(true);
        new OrderService().create(order);
    }

    @FXML
    void actionEdit() {
        LoaderFXML.loaderController("/gui.resources/department_order/stack_pane_action_order.fxml", CDepartamentOder.getDepartamentOder().getStackPane(), CDepartamentOder.getDepartamentOder(), order);
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        order = (Order) objects[0];
        organization.setText(order.getOrganization().getTitle());
        place.setText(order.getPlace().getTitle());
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cViewItems = (CViewItems) controller;
    }
}
