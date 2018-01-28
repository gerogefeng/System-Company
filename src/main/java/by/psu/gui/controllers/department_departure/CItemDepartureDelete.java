package by.psu.gui.controllers.department_departure;

import by.psu.gui.Converter;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.departure.Departure;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class CItemDepartureDelete implements Initializable, ControllerFXLoader {
    @FXML private JFXTextField companyTextField;

    @FXML private JFXTextArea reason;

    @FXML private DatePicker dateBeginDatePicker;
    @FXML private DatePicker dateEndDatePicker;

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        Departure departure = (Departure) objects[0];
        companyTextField.setText(departure.getOrder().getOrganization().getTitle() + " "
                + departure.getOrder().getPlace().getTitle());

        reason.setText("Выезд удалён администрацией компании.");

        dateBeginDatePicker.setValue(Converter.dateToLocalDate(departure.getOrder().getPlace().getDateBegin()));
        dateEndDatePicker.setValue(Converter.dateToLocalDate(departure.getOrder().getPlace().getDateEnd()));
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
