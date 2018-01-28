package by.psu.gui.controllers.department_transport;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.service.transport_services.TransportService;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CViewItem implements Initializable, ControllerFX {

    @FXML private VBox vBoxItemCars;
    @FXML private JFXTextField searchTextField;
    @FXML private StackPane stackPane;

    private JFXSnackbar message = new JFXSnackbar();
    private static CViewItem cViewItem = null;

    private TransportService transportService = new TransportService();
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
        cViewItem = this;
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            vBoxItemCars.getChildren().clear();
            if(!newValue.equals("")){
                transportService.readALLActive().forEach( transport -> {
                    String title = transport.getMarkAuto().getTitle() + " " + transport.getModelAuto().getTitle();
                    if (title.toUpperCase().contains(newValue.toUpperCase())) {
                        LoaderFXML.loaderController(
                                "/gui.resources/transport/anchor_pane_item_car.fxml",
                                vBoxItemCars, this, transport
                        );
                    }
                });
            } else {
                transportService.readALLActive().forEach(transport ->
                    LoaderFXML.loaderController(
                            "/gui.resources/transport/anchor_pane_item_car.fxml",
                            vBoxItemCars, this, transport
                    )
                );
            }
        });
    }

    private void loadItem(){
        transportService.readALLActive().forEach(transport -> {
            LoaderFXML.loaderController("/gui.resources/transport/anchor_pane_item_car.fxml", vBoxItemCars, this, transport);
        });
    }

    void deleteItem(AnchorPane anchorPane){
        List<Node> list = vBoxItemCars.getChildren();
        for (Node node : list) {
            if(node.hashCode()==anchorPane.hashCode()) {
                vBoxItemCars.getChildren().remove(node);
                break;
            }
        }
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    void message(final String title) {
        if (message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPane);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer(stackPane));
    }

    public static CViewItem getcViewItem() {
        return cViewItem;
    }
}
