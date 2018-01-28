package by.psu.gui.controllers.department_instruments;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.instrument.InstrumentDeparture;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.instrument_service.InstrumentService;
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

public class CItemInstrument implements Initializable, ControllerFXLoader{
    @FXML
    private AnchorPane anchorPane;

    @FXML private JFXTextField titleInstrument;
    @FXML private MaterialDesignIconView statusInstrument;

    private Instrument instrument = null;
    private CViewInstruments cViewInstruments = null;
    private InstrumentService is = new InstrumentService();
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
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        instrument = (Instrument) objects[0];
        titleInstrument.setText(instrument.getTitle());
        statusInstrument.setFill(!isAccessInstrument() ? Color.GREEN : Color.RED);
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cViewInstruments = (CViewInstruments) controller;
    }

    private boolean isAccessInstrument(){
        List<Departure> departures = departureService.readALL();
        for (Departure d : departures) {
            if (!d.isDelete() && d.getStatus() != 3) {
                List<InstrumentDeparture> instDep = d.getInstrumentDepartures();
                for (InstrumentDeparture anInstDep : instDep) {
                    if (anInstDep.getInstrument().getId() == instrument.getId()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    @FXML private void actionDelete() {
        if(isAccessInstrument()) {
            is.deleteInstrument(instrument.getId());
            CViewInstruments.getcViewInstruments().getvBoxItem().getChildren().remove(anchorPane);
        } else {
            CViewInstruments.getcViewInstruments().message("Действие заморожено. Атрибут используется или будет использован.");
        }
    }

    @FXML private void actionEdit() {
        if(isAccessInstrument()) {
            LoaderFXML.loaderController("/gui.resources/department_instruments/stack_pane_action_instrument.fxml", CDeparInstruments.getcDeparInstruments().getStackPane(), CDeparInstruments.getcDeparInstruments(), instrument);
        } else {
            CViewInstruments.getcViewInstruments().message("Действие заморожено. Атрибут используется или будет использован.");
        }
    }
}
