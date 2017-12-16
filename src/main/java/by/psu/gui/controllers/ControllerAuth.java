package by.psu.gui.controllers;

import by.psu.gui.AuthorizationApp;
import by.psu.gui.MainApp;
import by.psu.logical.Main;
import by.psu.logical.service.UserService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAuth implements Initializable{

    @FXML private JFXTextField login;
    @FXML private JFXPasswordField password;

    private UserService userService = new UserService();
    private Logger LOG = Logger.getLogger(ControllerAuth.class);

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

    }

    @FXML private void signIn(){
        LOG.info("Нажата кнопка войти.");
        if(!login.getText().isEmpty() && !password.getText().isEmpty()) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (userService.exists(login.getText(), password.getText())) {
                        Platform.runLater(()->{
                            try {
                                new MainApp().start(new Stage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    return null;
                }
            };
            new Thread(task).start();
        }
    }

    @FXML private void actionClose(){
        AuthorizationApp.getStage().close();
    }
}
