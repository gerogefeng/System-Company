package by.psu.gui.controllers.department_employee.item_employee;

import by.psu.gui.LoaderFXML;
import by.psu.gui.controllers.PatternFX;
import by.psu.gui.controllers.department_employee.ControllerDeparOder;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.service.employee_services.EmployeeService;
import by.psu.logical.service.employee_services.PassportService;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerEmployeeItem /*extends AbstractItem */implements Initializable, ControllerFX{

    private static final Logger LOGGER = Logger.getLogger(ControllerEmployeeItem.class);

    private ControllerDeparOder parent = null;

    @FXML private StackPane stackPaneRoot;

    @FXML private VBox vBoxDriverLicences;
    @FXML private VBox vBoxPassports;

    @FXML private PatternFX patternFX = new PatternFX();

    @FXML private Circle avatar;

    @FXML private JFXTextField nameTextField;
    @FXML private MaterialDesignIconView nameMDIV;

    @FXML private JFXTextField lastNameTextField;
    @FXML private MaterialDesignIconView lastNameMDIV;

    @FXML private JFXTextField patronymicTextField;
    @FXML private MaterialDesignIconView patronymicMDIV;

    @FXML private JFXDatePicker birthdayDatePicker;

    @FXML private JFXTextField mobilePhoneTextField;
    @FXML private MaterialDesignIconView mobilePhoneMDIV;

    @FXML private JFXTextField mailTextField;
    @FXML private MaterialDesignIconView mailMDIV;

    private JFXSnackbar message = null;

    private HashMap<Integer, ControllerFXLoader> passports = new HashMap<>();
    private HashMap<Integer, ControllerFXLoader> driverLicences = new HashMap<>();

    private EmployeeService es = new EmployeeService();
    private PassportService pasS = new PassportService();
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
        /*birthdayDatePicker.setDialogParent(stackPaneRoot);

        message = new JFXSnackbar();
        message.setAlignment(Pos.TOP_CENTER);
        message.setStyle("-fx-background-color: indigo;");

        nameTextField.setTooltip(getTooltipe("Иван"));
        lastNameTextField.setTooltip(getTooltipe("Иванов"));
        patronymicTextField.setTooltip(getTooltipe("Иванович"));
        mobilePhoneTextField.setTooltip(getTooltipe("+375(29)999-95-40"));
        mailTextField.setTooltip(getTooltipe("jsdeveloper@gmail.com"));

        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            iconEdit(newValue, nameMDIV, patternFX.checkTextField(newValue, 5, 20),
                    MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE,  MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
        });
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            iconEdit(newValue, lastNameMDIV, patternFX.checkTextField(newValue, 5, 20),
                    MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE,  MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
        });
        patronymicTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            iconEdit(newValue, patronymicMDIV, patternFX.checkTextField(newValue, 5, 20),
                    MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE,  MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
        });
        mobilePhoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.isEmpty()) {
                iconEdit(newValue, mobilePhoneMDIV, patternFX.checkNumberPhone(newValue), null,  null);
            }else{
                mobilePhoneMDIV.setStyle("-fx-fill:  #009688");
            }
        });
        mailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.isEmpty()) {
                iconEdit(newValue, mailMDIV, patternFX.checkEmail(newValue), null, null);
            }else{
                mailMDIV.setStyle("-fx-fill:  #009688");
            }
        });
*/
    }

    /*@Override
    protected int checkData() {
        if (!patternFX.checkTextField(nameTextField.getText(), 5, 25))
            return 0;
        if (!patternFX.checkTextField(lastNameTextField.getText(), 5, 25))
            return -1;
        if (!patternFX.checkTextField(patronymicTextField.getText(), 5, 25))
            return -2;
        if (!patternFX.checkNumberPhone(mobilePhoneTextField.getText()) && !mobilePhoneTextField.getText().isEmpty())
            return -3;
        if (!patternFX.checkEmail(mailTextField.getText()) && !mailTextField.getText().isEmpty())
            return -4;
        if (birthdayDatePicker.getValue() == null)
            return -5;
        return 1;
    }

    @Override
    protected void display(int valueWarning) {
        switch (valueWarning){
            case 0: animationElement(nameTextField);
                message("Введите имя сотрудника");
                break;
            case -1: animationElement(lastNameTextField);
                message("Введите фамилию сотрудника");
                break;
            case -2: animationElement(patronymicTextField);
                message("Введите отчество сотрудника");
                break;
            case -3: animationElement(mobilePhoneTextField);
                message("Неверный мобильный телефон сотрудника");
                break;
            case -4: animationElement(mailTextField);
                message("Неверный Email сотрудника");
                break;
            case -5: animationElement(birthdayDatePicker);
                message("Укажите дату рождения сотрудника");
                break;
            case 1: break;
        }
    }*/

   /* private void iconEdit(String newValue, MaterialDesignIconView iconEdit, boolean check,
                          MaterialDesignIcon errorIcon, MaterialDesignIcon fine){
        if(!newValue.isEmpty()) {
            if (check) {
                if(fine != null)
                    iconEdit.setIcon(errorIcon);
                iconEdit.setStyle("-fx-fill:  #009688");
            } else {
                if(errorIcon != null)
                    iconEdit.setIcon(fine);
                iconEdit.setStyle("-fx-fill: #ff2424");
            }
        }
    }

    private Tooltip getTooltipe(String text){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-radius: 2 2 2 2;\n -fx-background-color: #1e90ff; -fx-text-fill: white;");
        tooltip.setText(text);
        return tooltip;
    }*/

    @FXML private void actionAddPassport(){
        LOGGER.info("pane employee - ADD Passport");
        if(vBoxPassports.getChildren().size() < 2) {
            Object[] passports = LoaderFXML.loaderController("/gui.resources/personal_department/item_employee/anchor_pane_passport_item.fxml", vBoxPassports, this);
            this.passports.put((Integer) passports[1], (ControllerFXLoader) passports[0]);
        } else {
            LOGGER.info("actionAddPassport - LIMIT 2");
        }
    }

    @FXML private void actionAddDriverLicence(){
        LOGGER.info("pane employee - ADD DriverLicence");
        if(vBoxDriverLicences.getChildren().size() < 2) {
            Object[] driverlicence = LoaderFXML.loaderController("/gui.resources/personal_department/item_employee/stack_pane_driver_licence_item.fxml", vBoxDriverLicences, this);
            this.driverLicences.put((Integer) driverlicence[1], (ControllerFXLoader) driverlicence[0]);
        } else {
            LOGGER.info("actionAddDriverLicence - LIMIT 2");
        }
    }

    public StackPane getStackPaneRoot() {
        return stackPaneRoot;
    }

    public VBox getvBoxDriverLicences() {
        return vBoxDriverLicences;
    }

    public VBox getvBoxPassports() {
        return vBoxPassports;
    }

  /*  @Override
    public void setParentController(ControllerFX controller) {
        this.parent = (ControllerDeparOder) controller;
    }

    @FXML private void createEmployee(){
        int resultCheck = checkData();
        if(resultCheck == 1){
            List<Passport> passportsList = getPassportEntity();
            List<DriverLicence> driverLicencesList = getDriverLicenceEntity();



            try {
                *//*es.create(employee);

                privateCard.setEmployee(employee);
                pcs.create(privateCard);
                if (passportsList != null) {
                    for (Passport passport : passportsList){
                        passport.setEmployee(employee);
                        pasS.create(passport);
                    }
                }*//*

            } catch (Exception e) {
                message("Возникла ошибка " + e.getMessage());
            }
            message("Пользователь успешно добавлен");

        } else {
            display(resultCheck);
        }
    }

    private Employee buildEmployee(){
        return new Employee(
                nameTextField.getText(),
                lastNameTextField.getText(),
                patronymicTextField.getText(),
                null
        );
    }
    private PrivateCard buildPrivateCard(){
        return new PrivateCard(
                Converter.localDateToDate(birthdayDatePicker.getValue()),
                mobilePhoneTextField.getText(),
                mailTextField.getText()
        );
    }

    private List<Passport> getPassportEntity(){
        List<Passport> passportsList = null;
        if(passports.size() != 0) {
            passportsList = new ArrayList<>();
            ObservableList<Node> list = vBoxPassports.getChildren();
            for (Node aList : list) {
                try {
                    Passport passport = (Passport) passports.get(aList.hashCode()).getData()[0];
                    passportsList.add(passport);
                } catch (NullPointerException e) {
                    LOGGER.info("Passports == null");
                    return null;
                }
            }
        }
        return passportsList;
    }

    private List<Driver> getDriverLicenceEntity(){
        List<Driver> driverLicencesList = null;
        if(driverLicences.size() != 0) {
            driverLicencesList = new ArrayList<>();
            ObservableList<Node> list = vBoxDriverLicences.getChildren();
            for (Node aList : list) {
                try {
                    LOGGER.info("HashCode: " + aList.hashCode() + " DriverLicence: " + driverLicences.get(aList.hashCode()));
                    Driver driverLicence = (Driver) driverLicences.get(aList.hashCode()).getData()[0];
                    driverLicencesList.add(driverLicence);
                } catch (NullPointerException e) {
                    LOGGER.info("DriverLicence == null");
                    return null;
                }
            }

        }
        return driverLicencesList;
    }
*/
    private void message(String title){
        if(message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPaneRoot);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer( stackPaneRoot));
    }

    @Override
    public void setParentController(ControllerFX controller) {

    }
}
