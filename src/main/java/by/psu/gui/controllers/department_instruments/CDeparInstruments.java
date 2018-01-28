package by.psu.gui.controllers.department_instruments;

import by.psu.gui.LoaderFXML;
import by.psu.gui.controllers.ControllerMain;
import by.psu.gui.logicalGui.ControllerFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CDeparInstruments implements Initializable, ControllerFX {

    @FXML private StackPane stackPane;

    private static CDeparInstruments cDeparInstruments = null;

    @Override
    public void setParentController(ControllerFX controller) {

    }

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
        actionGetInstruments();
        cDeparInstruments = this;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    @FXML private void actionPlusInstument(){
        LoaderFXML.loaderController("/gui.resources/department_instruments/stack_pane_action_instrument.fxml", stackPane, this);
    }

    @FXML private void actionGetInstruments(){
        LoaderFXML.loaderController("/gui.resources/department_instruments/stack_pane_view_instruments.fxml", stackPane, this);
    }

    public static CDeparInstruments getcDeparInstruments() {
        return cDeparInstruments;
    }
}
