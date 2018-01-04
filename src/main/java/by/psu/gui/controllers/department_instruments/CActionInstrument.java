package by.psu.gui.controllers.department_instruments;

import by.psu.gui.Converter;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.service.instrument_service.InstrumentService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CActionInstrument implements ControllerFXLoader,Initializable {

    @FXML
    private Circle avatar;

    @FXML private JFXTextField title;
    @FXML private JFXTextField weight;

    @FXML private JFXDatePicker dateStart;
    @FXML private JFXDatePicker dateEnd;

    @FXML private JFXButton actionButton;

    private Instrument instrument = null;
    private InstrumentService instrumentService = new InstrumentService();
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
        instrument.getDateBuy();
        dateStart.setValue(Converter.dateToLocalDate(instrument.getDateBuy()));
        dateEnd.setValue(Converter.dateToLocalDate(instrument.getDateEnd()));
        weight.setText(String.valueOf(instrument.getWeight()));
        title.setText(instrument.getTitle());
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    @FXML private void action(){
        instrumentService.create(newInstanceInstrument((instrument==null)
                ? new Instrument()
                : instrument)
        );
    }

    private Instrument newInstanceInstrument(Instrument instrument){
        instrument.setTitle(title.getText());
        instrument.setWeight(Integer.parseInt(weight.getText()));
        instrument.setDateBuy(Converter.localDateToDate(dateStart.getValue()));
        instrument.setDateEnd(Converter.localDateToDate(dateEnd.getValue()));
        return instrument;
    }

}
