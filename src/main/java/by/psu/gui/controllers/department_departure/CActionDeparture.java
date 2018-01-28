package by.psu.gui.controllers.department_departure;

import by.psu.gui.Converter;
import by.psu.gui.LoaderFXML;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.Post;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.instrument.InstrumentDeparture;
import by.psu.logical.model.order.Order;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.model.transport.TransportRental;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.action.EquipmentDepartureService;
import by.psu.logical.service.action.TransportRentalService;
import by.psu.logical.service.employee_services.EmployeeService;
import by.psu.logical.service.employee_services.PostEmployee;
import by.psu.logical.service.employee_services.PostService;
import by.psu.logical.service.instrument_service.InstrumentService;
import by.psu.logical.service.order_services.OrderService;
import by.psu.logical.service.transport_services.TransportService;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.*;

public class CActionDeparture implements Initializable, ControllerFX {

    @FXML private JFXDatePicker startDatePicker;
    @FXML private JFXDatePicker endDatePicker;

    @FXML private JFXComboBox<Order> orderComboBox;
    @FXML private JFXComboBox<Transport> autoComboBox;
    @FXML private JFXComboBox<Employee> employeeComboBox;

    @FXML private JFXTextField describeEmployee;
    @FXML private CheckComboBox<Instrument> checkBoxInstrument;

    @FXML private Label weight;
    @FXML private JFXButton actionButton;


    @FXML private JFXSnackbar message;

    @FXML private StackPane stackPane;

    private OrderService orderService = new OrderService();
    private TransportService transportService = new TransportService();
    private InstrumentService instrumentService = new InstrumentService();
    private EmployeeService employeeService = new EmployeeService();
    private DepartureService departureService = new DepartureService();
    private PostEmployee postEmployeeService = new PostEmployee();
    private PostService postService = new PostService();

    private EquipmentDepartureService equipmentDepartureService = new EquipmentDepartureService();
    private TransportRentalService transportRentalService = new TransportRentalService();

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
        weight.setText("0");

        message = new JFXSnackbar();

        autoComboBox.setDisable(true);
        employeeComboBox.setDisable(true);
        describeEmployee.setDisable(true);

        autoComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(autoComboBox.getValue() != null){
                    checkBoxInstrument.setDisable(false);
                }
            }
        });

        orderComboBox.setCellFactory(p -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getOrganization().getTitle() + " " +
                            item.getPlace().getTitle());
                } else {
                    setText("Нет записей");
                }
            }
        });

        loadOrder();

        orderComboBox.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            Order order = orderComboBox.getSelectionModel().getSelectedItem();
            if(order!= null) {
                startDatePicker.setValue(Converter.dateToLocalDate(order.getPlace().getDateBegin()));
                endDatePicker.setValue(Converter.dateToLocalDate(order.getPlace().getDateEnd()));
                autoComboBox.setDisable(false);
                employeeComboBox.setDisable(false);
                autoComboBox.getItems().clear();
                employeeComboBox.getItems().clear();
                checkBoxInstrument.getItems().clear();
                loadEmployee();
                loadAuto();
                loadInstruments();
                startDatePicker.setDisable(true);
                endDatePicker.setDisable(true);
            }
        }));

        employeeComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                describeEmployee.setDisable(false);
            }
        });

        autoComboBox.setCellFactory(p -> new ListCell<Transport>() {
            @Override
            protected void updateItem(Transport item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getMarkAuto().getTitle() +
                    " " + item.getModelAuto().getTitle());
                } else {
                    setText("Нет записей");
                }
            }
        });

        employeeComboBox.setCellFactory(p -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName() + " " + item.getLastName() + " " + item.getPatronymic());
                } else {
                    setText("Нет записей");
                }
            }
        });

        final ObservableList<Instrument> instruments = FXCollections.observableArrayList(instrumentService.readALL());

        StringConverter<Instrument> stringConverter = new StringConverter<Instrument>() {
            @Override
            public String toString(Instrument object) {
                return "Название: " + object.getTitle() + " Вес: " +  object.getWeight() + " кг";
            }

            @Override
            public Instrument fromString(String string) {
                return null;
            }
        };

        checkBoxInstrument.setConverter(stringConverter);

        checkBoxInstrument.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Instrument>() {
            @Override
            public void onChanged(Change<? extends Instrument> c) {
                final int[] val = {0};
                checkBoxInstrument.getCheckModel().getCheckedItems().forEach(
                        instrument -> val[0] += instrument.getWeight()
                );
                weight.setText(String.valueOf(val[0]));
                Transport transport = autoComboBox.getValue();
                if(transport != null){
                    if(transport.getCapacity() + 50 < val[0]){
                        actionButton.setDisable(true);
                        message("Привышена грузоподъёмнасть транспорта.");
                    } else {
                        actionButton.setDisable(false);
                    }
                }
            }
        });


    }

    private void loadOrder(){
        List<Order> orders = orderService.readAllActiveOrder();
        for (Order ord : orders){
            if(!ord.isDelete())
                orderComboBox.getItems().add(ord);
        }

    }

    private void loadAuto(){

        List<Transport> transports = transportRentalService.readALLIntervalDate(
                Converter.localDateToDate(startDatePicker.getValue()),
                Converter.localDateToDate(endDatePicker.getValue()));

        for (Transport transport: transports)
            autoComboBox.getItems().add(transport);
    }

    private void loadEmployee(){
        if(startDatePicker.getValue() != null && endDatePicker.getValue() != null) {

            List<Employee> employees = employeeService.getEmployeeIntervalDate(
                            Converter.localDateToDate(startDatePicker.getValue()),
                            Converter.localDateToDate(endDatePicker.getValue()));

            for (Employee employee : employees)
               if(employee.getDriver().size() > 0)
                    employeeComboBox.getItems().add(employee);

        }
    }

    private void loadInstruments(){
        List<Instrument> instruments = equipmentDepartureService.readALLIntervalDate(
                Converter.localDateToDate(startDatePicker.getValue()),
                Converter.localDateToDate(endDatePicker.getValue()));
        checkBoxInstrument.getItems().addAll(instruments);
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    private int check() {
        if (orderComboBox.getValue() == null) {
            message("Необходимо выбрать заказчика");
            return 0;
        }
        if (employeeComboBox.getValue() == null) {
            message("Необходимо выбрать сотрудника для исполнения");
            return -1;
        }
        if (autoComboBox.getValue()== null) {
            message("Необходимо выбрать транспорт");
            return -2;
        }
        if (describeEmployee.getText().isEmpty()) {
            message("Кратко опишите действия сотрудника");
            return -3;
        }
        return 1;
    }

    @FXML private void action(){
        if (check() == 1) {
            try {
                Order order = orderComboBox.getValue();
                Employee employee = employeeComboBox.getValue();
                Post post = new Post(describeEmployee.getText());
                Transport transport = autoComboBox.getValue();

                postService.create(post);

                PostsEmployee pe = new PostsEmployee(post, employee,
                        Converter.localDateToDate(startDatePicker.getValue()),
                        Converter.localDateToDate(endDatePicker.getValue()));

                postEmployeeService.create(pe);

                Departure departure = new Departure(order, pe);

                departureService.create(departure);

                transportRentalService.create(new TransportRental(transport, departure,
                        Converter.localDateToDate(startDatePicker.getValue()),
                        Converter.localDateToDate(endDatePicker.getValue())));
                //transport.setDepartures(new HashSet<>(Collections.singletonList(departure)));

                List<Instrument> instruments = checkBoxInstrument.getCheckModel().getCheckedItems();

                for (Instrument instrument : instruments) {
                    equipmentDepartureService.create(new InstrumentDeparture(
                            Converter.localDateToDate(startDatePicker.getValue()),
                            Converter.localDateToDate(endDatePicker.getValue()), departure, instrument));
                }

                CDeparDeparture.getcDeparDeparture().actionGetDepartures();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void message(final String title) {
        if (message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPane);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer(stackPane));
    }
}
