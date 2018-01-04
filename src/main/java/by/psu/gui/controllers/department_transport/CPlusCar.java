package by.psu.gui.controllers.department_transport;

import by.psu.gui.controllers.PatternFX;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.transport.MarkAuto;
import by.psu.logical.model.transport.ModelAuto;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.service.transport_services.MarkAutoService;
import by.psu.logical.service.transport_services.ModelAutoService;
import by.psu.logical.service.transport_services.TransportService;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class CPlusCar implements Initializable, ControllerFXLoader {
    @FXML private Circle avatar;

    @FXML private JFXComboBox<MarkAuto> markAutoComboBox;
    @FXML private JFXComboBox<ModelAuto> modelAutoComboBox;

    @FXML private JFXTextField typeEngineTextField;
    @FXML private JFXTextField capacityTextField;
    @FXML private JFXTextField countSeats;

    @FXML private JFXCheckBox status;
    @FXML private JFXButton actionButton;
    private JFXSnackbar message = new JFXSnackbar();

    private MarkAutoService markAutoService = new MarkAutoService();
    private ModelAutoService modelAutoService = new ModelAutoService();
    private TransportService transportService = new TransportService();

    private Transport transport = null;
    private boolean isEdit = false;

    private PatternFX patternFX = new PatternFX();

    private StackPane stackPane = CDeparTransport.getcDeparTransport().getStackPane();

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
        markAutoComboBox.setCellFactory(p -> new ListCell<MarkAuto>() {
            @Override
            protected void updateItem(MarkAuto item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getTitle());
                } else {
                    setText(null);
                }
            }
        });

        modelAutoComboBox.setCellFactory(p -> new ListCell<ModelAuto>() {
            @Override
            protected void updateItem(ModelAuto item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getTitle());
                } else {
                    setText(null);
                }
            }
        });
        markAutoComboBox.getItems().addAll(markAutoService.readALL());

        markAutoComboBox.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            modelAutoComboBox.getItems().clear();
            modelAutoComboBox.getItems().addAll(markAutoComboBox.getSelectionModel().getSelectedItem().getModelAutos());
        }));

        countSeats.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.equals("")) {
                if (Character.isLetter(newValue.charAt(newValue.length() - 1)))
                    countSeats.setText(oldValue);
            }
        }));

        capacityTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.equals("")) {
                if (Character.isLetter(newValue.charAt(newValue.length() - 1)))
                    capacityTextField.setText(oldValue);
            }
        }));
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        isEdit = true;
        actionButton.setText("Сохранить");
        transport = (Transport) objects[0];
        markAutoComboBox.getItems().addAll(markAutoService.readALL());
        markAutoComboBox.setValue(transport.getMarkAuto());

        modelAutoComboBox.getItems().addAll(modelAutoService.readALL());
        modelAutoComboBox.setValue(transport.getModelAuto());

        typeEngineTextField.setText(transport.getEngineType());
        capacityTextField.setText(String.valueOf(transport.getCapacity()));
        countSeats.setText(String.valueOf(transport.getSeats()));
        status.setSelected(transport.getStatus() == 1);
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }

    private int checkData(){
        if(markAutoComboBox.getValue() == null){
            message("Не выбрана марка автомобиля");
            return 0;
        } else if(modelAutoComboBox.getValue() == null){
            message("Не выбрана модель автомобиля");
            return -1;
        } else if(typeEngineTextField.getText().equals("")){
            message("Не введена информация о типе двигателя.");
            return -2;
        } else if(!patternFX.checkNumberTextField(capacityTextField.getText(), 1, 5)){
            message("Грузоподъёмнасть должна быть в интервале от 1 - до 10000");
            return -3;
        } else if(!patternFX.checkNumberTextField(countSeats.getText(), 1, 2)){
            message("Вместительность должна быть в интервале от 1 - до 99");
            return -4;
        }
        return 1;
    }

    @FXML private void action(){
        if(checkData() == 1) {
            transport = newInstanceTransport((!isEdit) ? new Transport() : transport);
            try {
                transportService.create(transport);
                message((isEdit) ? "Сохранена информация" : "Автомобиль добавлен");
            } catch (Exception e){
                message(e.getMessage());
            }
        }
    }

    private Transport newInstanceTransport(Transport transport){
        transport.setMarkAuto(markAutoComboBox.getValue());
        transport.setModelAuto(modelAutoComboBox.getValue());
        transport.setEngineType(typeEngineTextField.getText());
        transport.setCapacity(Integer.parseInt(capacityTextField.getText()));
        transport.setSeats(Integer.parseInt(countSeats.getText()));
        transport.setStatus((status.isSelected()) ? 1 : 0);
        return transport;
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
