package by.psu.gui;

import by.psu.logical.unit.SessionHibernate;
import javafx.application.Application;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

public class AuthorizationApp extends Application{

    private static Stage stage = null;
    private static final Logger LOG = Logger.getLogger(AuthorizationApp.class);

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     * @throws Exception if something goes wrong
     */

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui.resources/authorization.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = primaryStage;

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void init() throws Exception {
        LOG.info("Форма авторизации открыта");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SessionHibernate.getInstance();
                return null;
            }
        };
        new Thread(task).start();
    }

    @Override
    public void stop() throws Exception {
        LOG.info("Форма авторизации закрыта");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
