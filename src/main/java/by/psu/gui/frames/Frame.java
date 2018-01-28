package by.psu.gui.frames;

import by.psu.logical.unit.SessionHibernate;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

public class Frame extends Application{

    private static final Logger LOGGER = Logger.getLogger(Frame.class);

    private static Stage globalStage = null;
    
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui.resources/authorization.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        primaryStage.onCloseRequestProperty().addListener(new ChangeListener<EventHandler<WindowEvent>>() {
            @Override
            public void changed(ObservableValue<? extends EventHandler<WindowEvent>> observable, EventHandler<WindowEvent> oldValue, EventHandler<WindowEvent> newValue) {
                System.out.println(454);
            }
        });
        primaryStage.setOnCloseRequest(event -> {

                    SessionHibernate.getInstance().closeFactory();
                }
        );
        globalStage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        SessionHibernate.getInstance();
        LOGGER.info("init auth main frame.");
        super.init();
    }

    @Override
    public void stop() throws Exception {
        SessionHibernate.getInstance().closeFactory();
    }

    public static Stage getGlobalStage() {
        return globalStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
