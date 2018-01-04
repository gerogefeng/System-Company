package by.psu.gui.controllers.department_instruments;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.service.instrument_service.InstrumentService;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CItemInstrument implements Initializable, ControllerFXLoader{
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Circle avatar;

    @FXML private Label title;
    @FXML private Label price;
    @FXML private Label weight;

    @FXML private JFXButton edit;
    @FXML private JFXButton delete;

    private Instrument instrument = null;
    private CViewInstruments cViewInstruments = null;
    private InstrumentService is = new InstrumentService();
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
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        instrument = (Instrument) objects[0];
        title.setText(instrument.getTitle());
        weight.setText(String.valueOf(instrument.getWeight()));
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cViewInstruments = (CViewInstruments) controller;
    }

    @FXML private void actionDelete() {
        is.delete(instrument);
        cViewInstruments.deleteItem(anchorPane);
    }

    @FXML private void actionEdit() {
        LoaderFXML.loaderController("/gui.resources/department_instruments/stack_pane_action_instrument.fxml", CDeparInstruments.getcDeparInstruments().getStackPane(), CDeparInstruments.getcDeparInstruments(), instrument);
    }
}
