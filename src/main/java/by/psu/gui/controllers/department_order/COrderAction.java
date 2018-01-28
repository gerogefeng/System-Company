package by.psu.gui.controllers.department_order;

import by.psu.gui.Converter;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.order.Order;
import by.psu.logical.model.order.Organization;
import by.psu.logical.model.order.Place;
import by.psu.logical.model.order.Report;
import by.psu.logical.model.transport.MarkAuto;
import by.psu.logical.service.instrument_service.InstrumentService;
import by.psu.logical.service.order_services.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.controlsfx.control.ListSelectionView;

import java.net.URL;
import java.util.ResourceBundle;

public class COrderAction implements Initializable, ControllerFXLoader {

    @FXML
    private Circle avatar;

    @FXML private JFXTextField titleTextField;
    @FXML private JFXTextField placeTextField;
    @FXML private JFXTextField docTextField;

    @FXML private JFXButton enterButton;

    @FXML private StackPane stackPane;

    @FXML private JFXDatePicker beginDatePicker;
    @FXML private JFXDatePicker endDatePicker;

    @FXML private JFXTextField priceTextField;

    @FXML private JFXSnackbar message = null;
    @FXML
    private JFXButton actionButton;

    private OrderService orderService = new OrderService();
    private OrganizationService organizationService = new OrganizationService();
    private PlaceService placeService = new PlaceService();
    private ReportService reportService = new ReportService();

    private Organization organization = null;
    private Place place = null;
    private Order order = null;
    private Report report = null;

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
        message = new JFXSnackbar();
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        actionButton.setText("Сохранить");
        order = (Order) objects[0];
        titleTextField.setText(order.getOrganization().getTitle());
        placeTextField.setText(order.getPlace().getTitle());
        docTextField.setText(order.getReport().getResource());
        beginDatePicker.setValue(Converter.dateToLocalDate(order.getPlace().getDateBegin()));
        endDatePicker.setValue(Converter.dateToLocalDate(order.getPlace().getDateEnd()));

        place = order.getPlace();
        organization = order.getOrganization();
        report = order.getReport();
    }

    private int orderCheck() {
        if (titleTextField.getText().isEmpty()) {
            message("Введите название компании");
            return 0;
        }
        if (placeTextField.getText().isEmpty()) {
            message("Введите место проведения мероприятия");
            return -1;
        }
        if (beginDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            message("Ошибочная дата мероприятия " + beginDatePicker.getValue() + " > " + endDatePicker.getValue());
            return -2;
        }
        return 1;
    }

    private void message(final String title) {
        if (message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPane);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer(stackPane));
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    @FXML private void action(){
        if(orderCheck() == 1) {
            place = newInstancePlace(newInstancePlace((place == null) ? new Place() : place));
            organization = newInstanceOrganization((organization == null) ? new Organization() : organization);
            report = newInstanceReport((report == null) ? new Report() : report);
            order = newInstanceOrder((order == null) ? new Order() : order);

            System.out.println();

            placeService.create(place);
            organizationService.create(organization);
            reportService.create(report);
            orderService.create(order);
        }
    }

    private Organization newInstanceOrganization(Organization organization){
        organization.setTitle(titleTextField.getText());
        return organization;
    }

    private Place newInstancePlace(Place place){
        place.setTitle(placeTextField.getText());
        place.setDateBegin(Converter.localDateToDate(beginDatePicker.getValue()));
        place.setDateEnd(Converter.localDateToDate(endDatePicker.getValue()));
        return place;
    }

    private Report newInstanceReport(Report report){
        report.setResource(docTextField.getText());
        return report;
    }

    private Order newInstanceOrder(Order order){
        order.setPlace(place);
        order.setReport(report);
        order.setOrganization(organization);
        return order;
    }

    @FXML private void actionDocument(){

    }
}
