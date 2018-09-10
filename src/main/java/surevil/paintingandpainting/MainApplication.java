package surevil.paintingandpainting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import surevil.paintingandpainting.presentation.mainui.StageManager;
import surevil.paintingandpainting.util.PathUtil;

public class MainApplication extends Application {

    static {
        PathUtil.initDatabase();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFcaX applications.
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
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/canvasui/CanvasUi.fxml"));
        Scene newScene = new Scene(loader.load());

        primaryStage.setScene(newScene);
        StageManager.setStage(primaryStage);
        primaryStage.show();
    }
}