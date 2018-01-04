package by.psu.gui.controllers.department_departure;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CDeparDeparture implements Initializable, ControllerFX{

    @FXML private JFXButton departureButton;
    @FXML private JFXButton plusDepartureButton;

    @FXML private StackPane stackPane;

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
    public void setParentController(ControllerFX controller) {

    }

    @FXML private void actionGetDepartures() {
        LoaderFXML.loaderController("/gui.resources/department_departure/stack_pane_view_departure.fxml", stackPane, this);
    }

    @FXML private void actionPlusDeparture() {
        LoaderFXML.loaderController("/gui.resources/department_departure/stack_pane_action_departure.fxml", stackPane, this);
    }
}
