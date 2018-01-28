package by.psu.gui.controllers.department_departure;

import by.psu.gui.Converter;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.order.Order;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.action.EquipmentDepartureService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CItemDeparture implements Initializable, ControllerFXLoader{

    @FXML
    private JFXTextField companyTextField;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDatePicker dateBeginDatePicker;

    @FXML
    private JFXDatePicker dateEndDatePicker;

    @FXML
    private JFXComboBox<Employee> employeesComboBox;

    @FXML
    private JFXComboBox<Transport> transportsComboBox;

    @FXML
    private JFXComboBox<Instrument> instrumentsComboBox;


    private Departure departure = null;
    private DepartureService departureService = new DepartureService();
    private Order order = null;
    private Transport transport = null;
    private PostsEmployee postsEmployee = null;
    private List<Employee> list = null;
    private List<Instrument> instruments = null;

    private EquipmentDepartureService eds = new EquipmentDepartureService();
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
        employeesComboBox.setCellFactory(p -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.toString());
                } else {
                    setText("Нет записей");
                }
            }
        });

        transportsComboBox.setCellFactory(p -> new ListCell<Transport>() {
            @Override
            protected void updateItem(Transport item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.toString());
                } else {
                    setText("Нет записей");
                }
            }
        });

        instrumentsComboBox.setCellFactory(p -> new ListCell<Instrument>() {
            @Override
            protected void updateItem(Instrument item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getTitle());
                } else {
                    setText("Нет записей");
                }
            }
        });
    }


    @Override
    public void setParentController(ControllerFX controller) {

    }

    @FXML private void actionDelete(){
        if(CViewDeparture.getcViewDeparture().vBoxItem.getChildren().remove(anchorPane)) {
            departureService.deleteDeparture(departure.getId());
            CViewDeparture.getcViewDeparture().updateData();
        }
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        if(objects != null) {
            departure = (Departure) objects[0];
            order = departure.getOrder();
            postsEmployee = departure.getPostsEmployee();

            if(!departure.getTransportRentals().isEmpty())
                transport = departure.getTransportRentals().get(0).getTransport();

            instruments = eds.readALLWithDeparture(departure);

            companyTextField.setText(order.getOrganization().getTitle() + " " + order.getPlace().getTitle());
            dateBeginDatePicker.setValue(Converter.dateToLocalDate(order.getPlace().getDateBegin()));
            dateEndDatePicker.setValue(Converter.dateToLocalDate(order.getPlace().getDateEnd()));
            employeesComboBox.getItems().add(departure.getPostsEmployee().getEmployee());
            employeesComboBox.setValue(employeesComboBox.getItems().get(0));

            transportsComboBox.getItems().add(transport);
            transportsComboBox.setValue(transportsComboBox.getItems().get(0));

            instrumentsComboBox.getItems().addAll(instruments);

            if(!instrumentsComboBox.getItems().isEmpty())
                instrumentsComboBox.setValue(instrumentsComboBox.getItems().get(0));
        }
    }
}
