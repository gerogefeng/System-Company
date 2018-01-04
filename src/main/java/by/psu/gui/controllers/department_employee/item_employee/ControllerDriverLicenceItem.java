package by.psu.gui.controllers.department_employee.item_employee;

import by.psu.gui.controllers.PatternFX;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.service.employee_services.CategoryService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDriverLicenceItem extends AbstractItem implements Initializable, ControllerFXLoader {

    private static final Logger LOGGER = Logger.getLogger(ControllerDriverLicenceItem.class);

    @FXML private AnchorPane anchorPane;

    @FXML private JFXTextField authorityTextField;
    @FXML private JFXTextField numberTextField;
    @FXML private JFXDatePicker dateInDatePicker;
    @FXML private JFXDatePicker dateOutDatePicker;
    @FXML private JFXCheckBox amCheckBox;
    @FXML private JFXCheckBox a1CheckBox;
    @FXML private JFXCheckBox aCheckBox;
    @FXML private JFXCheckBox cCheckBox;
    @FXML private JFXCheckBox bCheckBox;
    @FXML private JFXCheckBox dCheckBox;
    @FXML private JFXCheckBox beCheckBox;
    @FXML private JFXCheckBox ceCheckBox;
    @FXML private JFXCheckBox deCheckBox;
    @FXML private JFXCheckBox iCheckBox;
    @FXML private JFXCheckBox fCheckBox;

    private JFXSnackbar message = null;
    private ControllerEmployeeItem cei = null;
    private PatternFX patternFX = new PatternFX();
    private CategoryService categoryService = new CategoryService();

    private ObservableList<CheckBox> category = null;

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
         category = FXCollections.observableArrayList(
                amCheckBox, a1CheckBox, aCheckBox, cCheckBox,
                bCheckBox, dCheckBox, beCheckBox,
                ceCheckBox, deCheckBox, iCheckBox, fCheckBox
         );
         message = new JFXSnackbar();
    }

    @FXML private void close(){
        if(!cei.getvBoxDriverLicences().getChildren().isEmpty()) {
            cei.getvBoxDriverLicences().getChildren().forEach(node -> {
                if(node.hashCode() == anchorPane.hashCode())
                    Platform.runLater(()-> cei.getvBoxDriverLicences().getChildren().remove(node));
            });
        }
    }

    @Override
    public Object[] getData() {
        int res = checkData();
        if(res != 1){
            display(res);
            return null;
        }
        return new Object[]{/*new Driver(
                authorityTextField.getText(),
                numberTextField.getText(),
                Converter.localDateToDate(dateInDatePicker.getValue()),
                Converter.localDateToDate(dateOutDatePicker.getValue()),
                getCategory()
            )*/
        };
    }

 /*   public List<Category> getCategory(){
        List<Category> categories =  new ArrayList<>();
        for (CheckBox aCategory : category)
            if (aCategory.isSelected())
                categories.add(categoryService.findByName(aCategory.getText()));
        return categories;
    }

*/
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
    }

    @Override
    protected int checkData() {
        if (!patternFX.checkTextField(authorityTextField.getText(), 5, 25))
            return 0;
        if (!patternFX.checkNumberTextField(numberTextField.getText(), 3, 15))
            return -1;
        if (dateInDatePicker.getValue() == null)
            return -2;
        if (dateOutDatePicker.getValue() == null)
            return -3;
        if(!isCheckBox())
            return -4;
        return 1;
    }

    private boolean isCheckBox(){
        for (CheckBox aCategory : category)
            if (aCategory.isSelected())
                return true;
        return false;
    }

    @Override
    protected void display(int valueWarning) {
        switch (valueWarning){
            case 0:
                animationElement(authorityTextField);
                message("Введите корректно отдел выдавший удостоверение.");
            break;
            case -1:
                animationElement(numberTextField);
                message("Введите корректно номер удостоверения.");
            break;
            case -2: animationElement(dateInDatePicker);
                message("Введите корректно номер удостоверения.");
            break;
            case -3:
                animationElement(dateInDatePicker);
                message("Введите корректно дату выдачи удостоверения.");
            break;
            case -4:
                message("Не выбрана водительская категория.");
            break;
        }
    }

    private void message(String title){
        if(message.getPopupContainer() == null)
            message.registerSnackbarContainer(cei.getStackPaneRoot());
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer( cei.getStackPaneRoot()));
    }
}
