package surevil.paintingandpainting.presentation.frameworkui;

import javafx.stage.Stage;

public class StageManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        StageManager.stage = stage;
    }

    public static void closeStage() {
        stage.close();
    }

    public static Stage getStage() {
        return stage;
    }

}
