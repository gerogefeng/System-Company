package by.psu.gui.controllers.department_departure;

import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.service.action.DepartureService;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CViewDeparture implements Initializable, ControllerFX{

    private DepartureService departureService = new DepartureService();

    @FXML VBox vBoxItem;

    @FXML
    private JFXRadioButton currentRadioButton;

    @FXML private ToggleGroup RadioButtonsGroup;
    @FXML private JFXRadioButton futureRadioButton;
    @FXML private JFXRadioButton pastRadioButton;
    @FXML private JFXRadioButton abolishedRadioButton;

    private static CViewDeparture cViewDeparture = null;

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

        cViewDeparture = this;

        updateData();
    }

    void updateData(){
        long current = new Date().getTime();

        departureService.readALL().forEach(departure -> {
            if(!departure.isDelete()) {
                if ((departure.getOrder().getPlace().getDateBegin().getTime() < current &&
                        departure.getOrder().getPlace().getDateEnd().getTime() > current)) {
                    departure.setStatus(1);
                } else if (departure.getOrder().getPlace().getDateBegin().getTime() > current) {
                    departure.setStatus(2);
                } else if (departure.getOrder().getPlace().getDateEnd().getTime() < current) {
                    departure.setStatus(3);
                } else {
                    departure.setStatus(0);
                }
                departureService.create(departure);
            }
        });
    }
    @Override
    public void setParentController(ControllerFX controller) {

    }

    private interface DepartureSolution{
        boolean expressionSelect(Departure departure);
    }

    private void loadItem(DepartureSolution ds){

        departureService.readALL().forEach(departure ->{
            if(!departure.isDelete()) {
                if (ds.expressionSelect(departure)) {
                    LoaderFXML.loaderController("/gui.resources/department_departure/anchor_pane_item_departure.fxml",
                            vBoxItem, this, departure);
                }
            }
        });
    }


    private void loadItemDelete(){
        vBoxItem.getChildren().clear();
        departureService.readALL().forEach(departure ->{
            if(departure.isDelete()) {
                LoaderFXML.loaderController("/gui.resources/department_departure/anchor_pane_delete_departure.fxml",
                        vBoxItem, this, departure);
            }
        });
    }

    @FXML private void actionGet(Event event){
        vBoxItem.getChildren().clear();
        long current = new Date().getTime();
        if(event.getTarget().toString().contains("currentRadioButton")){
            loadItem((departure)-> departure.getStatus() == 1);
        } else if (event.getTarget().toString().contains("futureRadioButton")){
            loadItem((departure)-> departure.getStatus() == 2);
        } else if (event.getTarget().toString().contains("pastRadioButton")) {
            loadItem((departure)-> departure.getStatus() == 3);
        } else {
            loadItemDelete();
        }
    }

    public static CViewDeparture getcViewDeparture() {
        return cViewDeparture;
    }

    public static void setcViewDeparture(CViewDeparture cViewDeparture) {
        CViewDeparture.cViewDeparture = cViewDeparture;
    }
}
