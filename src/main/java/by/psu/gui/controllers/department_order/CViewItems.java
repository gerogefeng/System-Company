package by.psu.gui.controllers.department_order;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.service.order_services.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CViewItems implements Initializable, ControllerFX {

    @FXML private VBox vBoxItemOrders;

    private OrderService os = new OrderService();
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
        loadItems();
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    public void deleteItem(Pane pane){
        vBoxItemOrders.getChildren().remove(pane);
    }

    private void loadItems(){
        System.out.println(os.readALL().size());
        os.readALL().forEach(order -> {
            if(!order.isStatus()) {
                LoaderFXML.loaderController(
                        "/gui.resources/department_order/anchor_pane_item_order.fxml",
                        vBoxItemOrders, this, order);
            }
        });
    }
}
