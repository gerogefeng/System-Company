package by.psu.gui.controllers.department_departure;

import by.psu.gui.Converter;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.Post;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.order.Order;
import by.psu.logical.model.transport.ModelAuto;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.service.action.DepartureService;
import by.psu.logical.service.action.EquipmentDeparture;
import by.psu.logical.service.employee_services.EmployeeService;
import by.psu.logical.service.employee_services.PostEmployee;
import by.psu.logical.service.employee_services.PostService;
import by.psu.logical.service.instrument_service.InstrumentService;
import by.psu.logical.service.order_services.OrderService;
import by.psu.logical.service.transport_services.TransportService;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.ListSelectionView;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CActionDeparture implements Initializable, ControllerFX {

    @FXML private JFXDatePicker startDatePicker;
    @FXML private JFXDatePicker endDatePicker;

    @FXML private JFXComboBox<Order> orderComboBox;
    @FXML private JFXComboBox<Transport> autoComboBox;
    @FXML private JFXComboBox<Employee> employeeComboBox;

    @FXML private JFXTextField describeEmployee;
    @FXML private ListSelectionView<Instrument> listSelected;

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
    private EquipmentDeparture equipmentDeparture = new EquipmentDeparture();
    private PostService postService = new PostService();

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
        listSelected.setDisable(true);

        autoComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(autoComboBox.getValue() != null){
                    listSelected.setDisable(false);
                }
            }
        });

        orderComboBox.setCellFactory(p -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getOrganization().getTitle() +
                            " " + item.getPlace().getTitle());
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
                loadEmployee();
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

        loadAuto();

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

        loadEmployee();

        listSelected.setCellFactory(p -> new ListCell<Instrument>() {
            @Override
            protected void updateItem(Instrument item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText("Название: " + item.getTitle() + " Вес: " + item.getWeight() + "кг.");
                } else {
                    setText(null);
                }
            }
        });

        listSelected.getTargetItems().addListener(new ListChangeListener<Instrument>() {
            @Override
            public void onChanged(Change<? extends Instrument> c) {
                final int[] val = {0};
                listSelected.getTargetItems().forEach(
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

        loadInstruments();
    }

    private void loadOrder(){
        List<Order> orders = orderService.readAllActiveOrder();
        for (Order ord : orders)
            orderComboBox.getItems().add(ord);
    }

    private void loadAuto(){
        List<Transport> transports = transportService.readALL();
        for (Transport transport: transports)
            if(transport.getCapacity() > Integer.parseInt(weight.getText()))
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
        List<Instrument> instruments = instrumentService.readALL();
        listSelected.setSourceItems(FXCollections.observableArrayList(instruments));
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    @FXML private void action(){
        try {
            Order order = orderComboBox.getValue();
            Employee employee = employeeComboBox.getValue();
            Post post = new Post(describeEmployee.getText());
            Transport transport = autoComboBox.getValue();

            postService.create(post);

            PostsEmployee pe = new PostsEmployee(post, employee,
                    Converter.localDateToDate(startDatePicker.getValue()),
                    Converter.localDateToDate(endDatePicker.getValue()));


            Departure departure = new Departure(order, pe);

            postEmployeeService.create(pe);
            departureService.create(departure);

            transport.setDepartures(new HashSet<>(Collections.singletonList(departure)));

            transportService.create(transport);

            for (int i = 0; i < listSelected.getTargetItems().size(); i++)
                equipmentDeparture.create(listSelected.getTargetItems().get(i));

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void message(String title) {
        if (message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPane);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer(stackPane));
    }

    protected void animationElement(Node node) {
        Task<Void> animation = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Platform.runLater(() -> node.setStyle("-jfx-unfocus-color: brown"));
                    Thread.sleep(2000);
                    Platform.runLater(() -> node.setStyle("-jfx-unfocus-color: #c3c3c3"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread thread = new Thread(animation);
        thread.start();
    }
}
