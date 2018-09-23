package surevil.paintingandpainting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import surevil.paintingandpainting.presentation.frameworkui.FrameworkUiController;
import surevil.paintingandpainting.presentation.frameworkui.StageManager;
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
        FXMLLoader frameworkLoader = new FXMLLoader();
        frameworkLoader.setLocation(getClass().getResource("/fxml/frameworkui/FrameworkUi.fxml"));
        Scene scene = new Scene(frameworkLoader.load());

        FrameworkUiController controller = frameworkLoader.getController();

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setScene(scene);

        primaryStage.setHeight(900);
        primaryStage.setWidth(1600);

        FXMLLoader contentLoader = new FXMLLoader();
        contentLoader.setLocation(getClass().getResource("/fxml/canvasui/CanvasUi.fxml"));

        controller.setContent(contentLoader.load());

        StageManager.setStage(primaryStage);
        controller.initializeUiConfig();
        primaryStage.show();
        scene.getRoot().requestFocus();
    }
}