package by.psu.gui.frames;

import by.psu.logical.unit.SessionHibernate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

public class FrameWork extends Application {

    private static final Logger LOGGER = Logger.getLogger(FrameWork.class);
    private static Stage globalStage = null;

    public FrameWork(){
        Platform.runLater(() -> {
            try {
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
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
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1100, 700));
        primaryStage.setOnCloseRequest((event)-> SessionHibernate.getInstance().closeFactory());
        globalStage = primaryStage;
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        SessionHibernate.getInstance().closeFactory();
    }

    public static Stage getGlobalStage() {
        return globalStage;
    }
}
