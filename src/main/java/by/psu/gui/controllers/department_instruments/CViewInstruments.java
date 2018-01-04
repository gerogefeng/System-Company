package by.psu.gui.controllers.department_instruments;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.service.instrument_service.InstrumentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CViewInstruments implements Initializable, ControllerFX {

    @FXML private VBox vBoxItem;

    private CDeparInstruments cdi = null;
    private InstrumentService is= new InstrumentService();
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
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cdi = (CDeparInstruments) controller;
    }

    private void loadItem(){
        is.readALL().forEach(instrument -> {
            LoaderFXML.loaderController("/gui.resources/department_instruments/anchor_pane_item_instrument.fxml", vBoxItem, this, instrument);
        });
    }

    public void deleteItem(Pane pane){
        for (Node item: vBoxItem.getChildren()) {
            if(item.hashCode() == pane.hashCode())
                vBoxItem.getChildren().remove(item);
        }
    }
}
