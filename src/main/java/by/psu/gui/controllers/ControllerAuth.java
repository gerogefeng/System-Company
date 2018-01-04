package by.psu.gui.controllers;

import by.psu.Config;
import by.psu.gui.frames.Frame;
import by.psu.gui.frames.FrameWork;
import by.psu.logical.service.employee_services.UserService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAuth implements Initializable{

    @FXML private JFXTextField login;
    @FXML private JFXPasswordField password;
    @FXML private JFXCheckBox checkMember;

    private UserService userService = new UserService();
    private Config config = Config.getInstance();

    private static final Logger LOG = Logger.getLogger(ControllerAuth.class);


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
        String user = config.getProperty("pro.username");
        String pass = config.getProperty("pro.password");
        if(!(user.equals("") && pass.equals(""))){
            checkMember.setSelected(true);
            login.setText(user);
            password.setText(pass);
        }

    }

    @FXML private void signIn(){
        if(checkMember.isSelected()){
            config.setProperty("pro.username", login.getText());
            config.setProperty("pro.password", password.getText());
        }
        LOG.info("Нажата кнопка войти.");
        if(!login.getText().isEmpty() && !password.getText().isEmpty()) {
            new Thread(()->{
                if (userService.exists(login.getText(), password.getText())) {
                    new FrameWork();
                    Platform.runLater(this::actionClose);
                }
            }).start();
        }
    }

    @FXML private void actionClose(){
        Frame.getGlobalStage().close();
    }

    @FXML private void actionCostume(){
        Frame.getGlobalStage().setIconified(true);
    }

}
