package by.psu.gui.controllers.department_employee.item_employee;

import by.psu.gui.Converter;
import by.psu.gui.LoaderFilesFX;
import by.psu.gui.controllers.PatternFX;
import by.psu.gui.controllers.department_employee.ControllerDeparOder;
import by.psu.gui.logicalGui.ControllerFX;
import by.psu.gui.logicalGui.ControllerFXLoader;
import by.psu.logical.model.employee.*;
import by.psu.logical.service.employee_services.*;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class CEmployee implements Initializable, ControllerFXLoader {

    private static final Logger LOGGER = Logger.getLogger(CEmployee.class);

    @FXML
    JFXButton addEmployeeButton;
    @FXML
    private VBox vBox;
    @FXML
    private VBox vBoxPassports;
    @FXML
    private VBox vBoxDriver;

    @FXML
    private StackPane stackPaneRoot;

    private JFXSnackbar message = null;

    @FXML
    private JFXTextField nameTextField;
    @FXML
    private MaterialDesignIconView nameMDIV;

    @FXML
    private JFXTextField lastNameTextField;
    @FXML
    private MaterialDesignIconView lastNameMDIV;

    @FXML
    private JFXTextField patronymicTextField;
    @FXML
    private MaterialDesignIconView patronymicMDIV;

    @FXML
    private Circle avatar;

    @FXML
    private JFXDatePicker birthdayDatePicker;
    @FXML
    private JFXTextField mobilePhoneTextField;
    @FXML
    private MaterialDesignIconView mobilePhoneMDIV;

    @FXML
    private JFXTextField mailTextField;
    @FXML
    private MaterialDesignIconView mailMDIV;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField nationalityTextField;
    @FXML
    private JFXTextField authorityTextField;
    @FXML
    private JFXTextField serialTextField;
    @FXML
    private JFXTextField numberTextField;
    @FXML
    private JFXDatePicker dateInDatePicker;
    @FXML
    private JFXDatePicker dateOutDatePicker;

    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private JFXTextField authorityTextField1;
    @FXML
    private JFXTextField numberTextField1;
    @FXML
    private JFXDatePicker dateInDatePicker1;
    @FXML
    private JFXDatePicker dateOutDatePicker1;

    @FXML
    private JFXCheckBox amCheckBox;
    @FXML
    private JFXCheckBox a1CheckBox;
    @FXML
    private JFXCheckBox aCheckBox;
    @FXML
    private JFXCheckBox cCheckBox;
    @FXML
    private JFXCheckBox bCheckBox;
    @FXML
    private JFXCheckBox dCheckBox;
    @FXML
    private JFXCheckBox beCheckBox;
    @FXML
    private JFXCheckBox ceCheckBox;
    @FXML
    private JFXCheckBox deCheckBox;
    @FXML
    private JFXCheckBox iCheckBox;
    @FXML
    private JFXCheckBox fCheckBox;

    @FXML
    private JFXButton buttonPassport;
    @FXML
    private JFXButton buttonDriver;

    private static boolean isEdit = false;

    private static ControllerDeparOder cdo = null;

    private DriverService ds = new DriverService();
    private PassportService ps = new PassportService();
    private EmployeeService es = new EmployeeService();
    private CardService cardService = new CardService();
    private CategoryService cs = new CategoryService();

    private PatternFX patternFX = new PatternFX();
    private ObservableList<CheckBox> category = null;

    private Driver driver = null;
    private Passport passport = null;
    private Card card = null;
    private Employee employee = null;

    private LoaderFilesFX filesFX = new LoaderFilesFX();
    private File avr = null;
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
        avatar.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            File file = LoaderFilesFX.configureFileImageChooser();
            if(file != null) {
                avr = file;
                System.out.println(file.getAbsolutePath());
                Image image = null;
                try {
                    image = new Image(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                avatar.setFill(new ImagePattern(image));
            }
        });

        birthdayDatePicker.setDialogParent(stackPaneRoot);

        vBoxPassports.getChildren().remove(anchorPane);
        vBoxDriver.getChildren().remove(anchorPane1);

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
                    MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE, MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
        });
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            iconEdit(newValue, lastNameMDIV, patternFX.checkTextField(newValue, 5, 20),
                    MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE, MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
        });
        patronymicTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            iconEdit(newValue, patronymicMDIV, patternFX.checkTextField(newValue, 5, 20),
                    MaterialDesignIcon.CHECKBOX_MARKED_CIRCLE, MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
        });
        mobilePhoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                iconEdit(newValue, mobilePhoneMDIV, patternFX.checkNumberPhone(newValue), null, null);
            } else {
                mobilePhoneMDIV.setStyle("-fx-fill:  #009688");
            }
        });
        mailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                iconEdit(newValue, mailMDIV, patternFX.checkEmail(newValue), null, null);
            } else {
                mailMDIV.setStyle("-fx-fill:  #009688");
            }
        });

        category = FXCollections.observableArrayList(
                amCheckBox, a1CheckBox, aCheckBox, cCheckBox,
                bCheckBox, dCheckBox, beCheckBox,
                ceCheckBox, deCheckBox, iCheckBox, fCheckBox
        );
    }

    private void iconEdit(String newValue, MaterialDesignIconView iconEdit, boolean check,
                          MaterialDesignIcon errorIcon, MaterialDesignIcon fine) {
        if (!newValue.isEmpty()) {
            if (check) {
                if (fine != null)
                    iconEdit.setIcon(errorIcon);
                iconEdit.setStyle("-fx-fill:  #009688");
            } else {
                if (errorIcon != null)
                    iconEdit.setIcon(fine);
                iconEdit.setStyle("-fx-fill: #ff2424");
            }
        }
    }

    private Tooltip getTooltipe(String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-radius: 2 2 2 2;\n -fx-background-color: #1e90ff; -fx-text-fill: white;");
        tooltip.setText(text);
        return tooltip;
    }


    protected int employeeCheck() {
        if (!patternFX.checkTextField(nameTextField.getText(), 5, 25)) {
            animationElement(nameTextField);
            message("Введите имя сотрудника");
            return 0;
        }
        if (!patternFX.checkTextField(lastNameTextField.getText(), 5, 25)) {
            animationElement(lastNameTextField);
            message("Введите фамилию сотрудника");
            return -1;
        }
        if (!patternFX.checkTextField(patronymicTextField.getText(), 5, 25)) {
            animationElement(patronymicTextField);
            message("Введите отчество сотрудника");
            return -2;
        }
        if (!patternFX.checkNumberPhone(mobilePhoneTextField.getText()) && !mobilePhoneTextField.getText().isEmpty()) {
            animationElement(mobilePhoneTextField);
            message("Неверный мобильный телефон сотрудника");
            return -3;
        }
        if (!patternFX.checkEmail(mailTextField.getText()) && !mailTextField.getText().isEmpty()) {
            animationElement(mailTextField);
            message("Неверный Email сотрудника");
            return -4;
        }
        if (birthdayDatePicker.getValue() == null) {
            animationElement(birthdayDatePicker);
            message("Укажите дату рождения сотрудника");
            return -5;
        }
        return 1;
    }

    protected int checkPassport() {
        if (!patternFX.checkTextField(nationalityTextField.getText(), 3, 25)) {
            animationElement(nationalityTextField);
            message("Введите корректно национальнось");
            return 0;
        }
        if (!patternFX.checkTextField(authorityTextField.getText(), 3, 25)) {
            animationElement(authorityTextField);
            message("Введите корректно орган выдавший паспорт");
            return -1;
        }
        if (!patternFX.checkTextField(serialTextField.getText(), 2, 4)) {
            animationElement(serialTextField);
            message("Введите корректно серию пасспорта");
            return -2;
        }
        if (!patternFX.checkNumberTextField(numberTextField.getText(), 4, 10)) {
            animationElement(numberTextField);
            message("Введите корректно номер паспорта");
            return -3;
        }
        if (dateInDatePicker.getValue() == null) {
            animationElement(dateInDatePicker);
            message("Дата выдачи пасспорта введена некорректно");
            return -4;
        }
        if (dateOutDatePicker.getValue() == null) {
            animationElement(dateOutDatePicker);
            message("Дата действия пасспорта введена некорректно");
            return -5;
        }
        return 1;
    }

    protected int checkDriver() {
        if (!patternFX.checkTextField(authorityTextField1.getText(), 5, 25)) {
            animationElement(authorityTextField);
            message("Введите корректно отдел выдавший удостоверение.");
            return 0;
        }
        if (!patternFX.checkNumberTextField(numberTextField1.getText(), 3, 15)) {
            animationElement(numberTextField);
            message("Введите корректно номер удостоверения.");
            return -1;
        }
        if (dateInDatePicker1.getValue() == null) {
            animationElement(dateInDatePicker);
            message("Введите корректно дату выдачи удостоверения.");
            return -2;
        }
        if (dateOutDatePicker1.getValue() == null) {
            animationElement(dateInDatePicker);
            message("Введите корректно дату срока истечения действия удостоверения.");
            return -3;
        }
        if (!isCheckBox()) {
            message("Не выбрана водительская категория.");
            return -4;
        }
        return 1;
    }

    private boolean isCheckBox() {
        for (CheckBox aCategory : category)
            if (aCategory.isSelected())
                return true;
        return false;
    }

    @Override
    public Object[] getData() {
        return new Object[0];
    }

    @Override
    public void setData(Object... objects) {
        isEdit = true;
        LOGGER.info("MODE EDIT");
        addEmployeeButton.setText("Сохранить информацию");
        employee = (Employee) objects[0];
        nameTextField.setText(employee.getName());
        lastNameTextField.setText(employee.getLastName());
        patronymicTextField.setText(employee.getPatronymic());
        card = employee.getCard();
        birthdayDatePicker.setValue(Converter.dateToLocalDate(card.getBirthday()));
        mobilePhoneTextField.setText(card.getNumberPhone());
        mailTextField.setText(card.getEmail());
        if (employee.getAvatar() != null){
            try {

                Image image = new Image(new FileInputStream(employee.getAvatar()));
                avatar.setFill(new ImagePattern(image));
            } catch (FileNotFoundException e) {
                employee.setAvatar(null);
            }
        }
        for (Passport item : employee.getPassport()) {
            actionAddPassport();
            passport = item;
            nationalityTextField.setText(passport.getNationality());
            authorityTextField.setText(passport.getDepartment());
            serialTextField.setText(passport.getSerialPassport());
            numberTextField.setText(passport.getNumberPassport());
            dateInDatePicker.setValue(Converter.dateToLocalDate(passport.getDateIn()));
            dateOutDatePicker.setValue(Converter.dateToLocalDate(passport.getDateOut()));
        }
        for (Driver item : employee.getDriver()) {
            actionAddDriver();
            driver = item;
            authorityTextField1.setText(driver.getDepartment());
            numberTextField1.setText(driver.getNumber());
            dateInDatePicker1.setValue(Converter.dateToLocalDate(driver.getDateIn()));
            dateOutDatePicker1.setValue(Converter.dateToLocalDate(driver.getDateOut()));
            installCategory(driver.getCategory());
        }
    }

    private void installCategory(List<Category> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < category.size(); j++) {
                if (list.get(i).getTitle().equalsIgnoreCase(category.get(j).getText())) {
                    category.get(j).setSelected(true);
                    break;
                }
            }
        }
    }

    @Override
    public void setParentController(ControllerFX controller) {
        cdo = (ControllerDeparOder) controller;
        LOGGER.info("ControllerDeparOder = " + cdo);
    }

    @FXML
    private void createEmployee() {
        int checkEmployee = employeeCheck();
        if (checkEmployee != 1)
            return;
        boolean passportAnchP = vBoxPassports.getChildren().contains(anchorPane);
        boolean driverAnchP = vBoxDriver.getChildren().contains(anchorPane1);
        boolean resPassp = false;
        boolean resDriver = false;
        if (passportAnchP) {
            if (checkPassport() != 1)
                return;
            resPassp = true;
        }
        if (driverAnchP) {
            if (checkDriver() != 1)
                return;
            resDriver = true;
        }
        if (employee == null) {

            employee = new Employee(
                    nameTextField.getText(),
                    lastNameTextField.getText(),
                    patronymicTextField.getText(),
                    (avr == null) ? null : avr.getAbsolutePath(),
                    card
            );

            card = newInstanceCard(new Card(), employee);
            employee.setCard(card);
            es.create(employee);

            Set<Passport> passportSet = new HashSet<>();
            Set<Driver> driverSet = new HashSet<>();

            if (resPassp) {
                LOGGER.info("Строю права");
                passport = newInstancePassport(new Passport(), employee);
                passport.setEmployee(employee);
                passportSet.add(passport);
                ps.create(passport);
            }

            if (resDriver) {
                LOGGER.info("Строю пасспорт");
                driver = newInstanceDrive(new Driver(), employee);
                driver.setEmployee(employee);
                driverSet.add(driver);
                ds.create(driver);
            }
            cdo.actionMessageSceneAddEmployee();
        } else {
            LOGGER.info("mode EDIT");
            employee.setName(nameTextField.getText());
            employee.setLastName(lastNameTextField.getText());
            employee.setPatronymic(patronymicTextField.getText());
            employee.setAvatar((avr == null) ? null : avr.getAbsolutePath());
            employee.setCard(newInstanceCard(card, employee));


            if (driverAnchP && employee.getDriver().size() != 0) {
                LOGGER.info("права нужно обгрэйдить");
                for (Driver item : employee.getDriver())
                    ds.update(newInstanceDrive(item, employee));
            } else if (!driverAnchP && employee.getDriver().size() != 0) {
                LOGGER.info("права нужно удалить.");
                for (Driver item : employee.getDriver())
                    ds.delete(newInstanceDrive(item, employee));
            } else if (driverAnchP && employee.getDriver().size() == 0) {
                LOGGER.info("права нужно добавить, их не было.");
                ds.create(newInstanceDrive(new Driver(), employee));
            }

            if (passportAnchP && employee.getPassport().size() != 0) {
                LOGGER.info("паспорт нужно обгрэйдить");
                for (Passport item : employee.getPassport())
                    ps.update(newInstancePassport(item, employee));
            } else if (!passportAnchP && employee.getPassport().size() != 0) {
                LOGGER.info("паспорт нужно нужно удалить.");
                for (Passport item : employee.getPassport())
                    ps.delete(newInstancePassport(item, employee));
            } else if (passportAnchP && employee.getPassport().size() == 0) {
                LOGGER.info("права нужно добавить, их не было.");
                ps.create(newInstancePassport(new Passport(), employee));
            }

            es.create(employee);

            cdo.actionGetAllEmployee();
        }
    }

    private Card newInstanceCard(Card card, Employee employee) {
        card.setBirthday(Converter.localDateToDate(birthdayDatePicker.getValue()));
        card.setEmail(mailTextField.getText());
        card.setNumberPhone(mobilePhoneTextField.getText());
        card.setEmployee(employee);
        return card;
    }

    private Driver newInstanceDrive(Driver driver, Employee employee) {
        driver.setEmployee(employee);
        driver.setDepartment(authorityTextField1.getText());
        driver.setNumber(numberTextField1.getText());
        driver.setCategory(getCategory());
        LOGGER.info(getCategory().size() + " CATEGORY");
        driver.setDateOut(Converter.localDateToDate(dateOutDatePicker1.getValue()));
        driver.setDateIn(Converter.localDateToDate(dateInDatePicker1.getValue()));
        return driver;
    }

    private Passport newInstancePassport(Passport passport, Employee employee) {
        passport.setEmployee(employee);
        passport.setNumberPassport(numberTextField.getText());
        passport.setNationality(nationalityTextField.getText());
        passport.setDepartment(authorityTextField.getText());
        passport.setSerialPassport(serialTextField.getText());
        passport.setDateOut(Converter.localDateToDate(dateOutDatePicker.getValue()));
        passport.setDateIn(Converter.localDateToDate(dateInDatePicker.getValue()));
        return passport;
    }

    private boolean resultcheck(boolean isVisible, int check) {
        if (isVisible)
            if (check != 1)
                return false;
        return true;
    }

    public List<Category> getCategory() {
        List<Category> categories = new CategoryService().readALL();
        List<Category> selectCategory = new ArrayList<>();
        for (CheckBox aCategory : category) {
            if (aCategory.isSelected()) {
                for (int i = 0; i < categories.size(); i++) {
                    if (aCategory.getText().equalsIgnoreCase(categories.get(i).getTitle())) {
                        selectCategory.add(categories.get(i));
                        break;
                    }
                }
            }
        }
        return selectCategory;
    }

    @FXML
    private void actionAddPassport() {
        vBoxPassports.getChildren().remove(buttonPassport);
        vBoxPassports.getChildren().add(anchorPane);
    }

    @FXML
    private void actionAddDriver() {
        vBoxDriver.getChildren().remove(buttonDriver);
        vBoxDriver.getChildren().add(anchorPane1);
    }

    @FXML
    private void closePassport() {
        vBoxPassports.getChildren().remove(anchorPane);
        vBoxPassports.getChildren().add(buttonPassport);
    }

    @FXML
    private void closeDriver() {
        vBoxDriver.getChildren().remove(anchorPane1);
        vBoxDriver.getChildren().add(buttonDriver);
    }

    private void message(String title) {
        if (message.getPopupContainer() == null)
            message.registerSnackbarContainer(stackPaneRoot);
        message.show(title, "Закрыть", 2000, event -> message.unregisterSnackbarContainer(stackPaneRoot));
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
