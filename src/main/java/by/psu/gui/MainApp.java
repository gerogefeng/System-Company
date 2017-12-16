package by.psu.gui;

import by.psu.logical.unit.SessionHibernate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

public class MainApp extends Application{

    private static final Logger LOG = Logger.getLogger(MainApp.class);

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
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui.resources/mainframe.fxml"));
        primaryStage.setScene(new Scene(fxmlLoader.load(), 950, 600));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setOnCloseRequest((e)-> SessionHibernate.getInstance().closeFactory());
        primaryStage.show();
    }


    @Override
    public void init() throws Exception {
        LOG.info("Открыто главное окно.");
    }

    @Override
    public void stop() throws Exception {
        LOG.info("Закрыто главное окно.");
        SessionHibernate.getInstance().closeFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
