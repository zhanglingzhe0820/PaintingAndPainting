package surevil.paintingandpainting.presentation.canvasui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import surevil.paintingandpainting.presentation.mainui.BorderlessStageHelper;
import surevil.paintingandpainting.presentation.mainui.StageManager;

public class CanvasUiController {
    private static final int DEPTH = 3;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private GridPane drawer;
    @FXML
    private GridPane titleBar;
    @FXML
    private GridPane bottomBar;
    @FXML
    private Pane contentPane;

    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXButton minimizeButton;
    @FXML
    private JFXButton maximizeButton;
    @FXML
    private MaterialIconView maximizeButtonGlyph;

    public void initialize() {
        JFXDepthManager.setDepth(drawer, DEPTH);
        JFXDepthManager.setDepth(titleBar, DEPTH);
        JFXDepthManager.setDepth(bottomBar, DEPTH);
        JFXDepthManager.setDepth(contentPane, DEPTH);

        Stage stage = StageManager.getStage();
        BorderlessStageHelper.makeDraggable(stage, rootPane);
        BorderlessStageHelper.makeResizeable(stage);

        closeButton.setOnMouseClicked(e -> StageManager.closeStage());
        minimizeButton.setOnMouseClicked(e -> stage.setIconified(true));
        maximizeButton.setOnMouseClicked(e -> {
            if (stage.isMaximized()) {
                stage.setMaximized(false);
                maximizeButtonGlyph.setGlyphName("KEYBOARD_ARROW_UP");
            } else {
                stage.setMaximized(true);
                maximizeButtonGlyph.setGlyphName("KEYBOARD_ARROW_DOWN");
            }

        });
    }
}
