package by.psu.gui.controllers.department_employee.item_employee;

import by.psu.gui.Converter;
import by.psu.gui.controllers.PatternFX;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.employee.Passport;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPassportItem extends AbstractItem implements Initializable, ControllerFXLoader{

    private static final Logger LOGGER = Logger.getLogger(ControllerPassportItem.class);

    private ControllerEmployeeItem cei = null;
    private PatternFX patternFX = new PatternFX();

    @FXML private AnchorPane anchorPane;
    @FXML private JFXTextField nationalityTextField;
    @FXML private JFXTextField authorityTextField;
    @FXML private JFXTextField serialTextField;
    @FXML private JFXTextField numberTextField;
    @FXML private JFXDatePicker dateInDatePicker;
    @FXML private JFXDatePicker dateOutDatePicker;

    private JFXSnackbar message = null;
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
    protected int checkData() {
        if (!patternFX.checkTextField(nationalityTextField.getText(), 3, 25))
            return 0;
        if (!patternFX.checkTextField(authorityTextField.getText(), 3, 25))
            return -1;
        if (!patternFX.checkTextField(serialTextField.getText(), 2, 4))
            return -2;
        if (!patternFX.checkNumberTextField(numberTextField.getText(), 4, 10))
            return -3;
        if (dateInDatePicker.getValue() == null)
            return -4;
        if (dateOutDatePicker.getValue() == null)
            return -5;
        return 1;
    }

    @Override
    protected void display(int valueWarning) {
        switch (valueWarning){
            case 0: animationElement(nationalityTextField);
                message("Введите корректно национальнось");
                break;
            case -1: animationElement(authorityTextField);
                message("Введите корректно орган выдавший паспорт");
                break;
            case -2: animationElement(serialTextField);
                message("Введите корректно серию пасспорта");
                break;
            case -3: animationElement(numberTextField);
                message("Введите корректно номер паспорта");
                break;
            case -4: animationElement(dateInDatePicker);
                message("Дата выдачи пасспорта введена некорректно");
                break;
            case -5: animationElement(dateOutDatePicker);
                message("Дата действия пасспорта введена некорректно");
                break;
            case 1: break;
        }
    }

    @FXML private void close(){
        LOGGER.info(anchorPane.hashCode());
        cei.getvBoxPassports().getChildren().forEach(node -> {
            if(node.hashCode() == anchorPane.hashCode())
                Platform.runLater(()-> cei.getvBoxPassports().getChildren().remove(node));
        });
    }

    @Override
    public Object[] getData() {
        int result = checkData();
        if(result != 1){
            display(result);
            return null;
        }
        return new Object[]{new Passport(
                nationalityTextField.getText(),
                authorityTextField.getText(),
                serialTextField.getText(),
                numberTextField.getText(),
                Converter.localDateToDate(dateInDatePicker.getValue()),
                Converter.localDateToDate(dateOutDatePicker.getValue())
        )};
    }

    @Override
    public void setData(Object... objects) {
        if(objects == null){
            LOGGER.info("setData: CREATE mode");
        } else {
            LOGGER.info("setData: CHANGE mode");
        }
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cei = (ControllerEmployeeItem) controller;
        dateInDatePicker.setDialogParent(cei.getStackPaneRoot());
        dateOutDatePicker.setDialogParent(cei.getStackPaneRoot());
    }

    private void message(String title){
        if(message.getPopupContainer() == null)
            message.registerSnackbarContainer(cei.getStackPaneRoot());
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer( cei.getStackPaneRoot()));
    }
}
