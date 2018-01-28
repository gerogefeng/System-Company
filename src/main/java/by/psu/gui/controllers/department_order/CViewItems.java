package by.psu.gui.controllers.department_order;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.service.order_services.OrderService;
import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CViewItems implements Initializable, ControllerFX {

    @FXML private VBox vBoxItemOrders;
    @FXML private StackPane stackPane;

    private JFXSnackbar message = new JFXSnackbar();
    private OrderService os = new OrderService();
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    private static CViewItems cViewItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cViewItems = this;

        loadItems();
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    public VBox getvBoxItemOrders() {
        return vBoxItemOrders;
    }

    private void loadItems(){
        os.readAllActiveOrder().forEach(order -> {
            System.out.println(order.isDelete() + " " + order.getOrganization().getTitle());
            LoaderFXML.loaderController("/gui.resources/department_order/anchor_pane_item_order.fxml",
                    vBoxItemOrders, this, order);
        });
    }

    void message(final String title) {
        if (message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPane);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer(stackPane));
    }

    public static CViewItems getcViewItems() {
        return cViewItems;
    }
}
