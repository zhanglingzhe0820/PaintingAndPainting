package surevil.paintingandpainting.presentation.frameworkui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrameworkUiController {
    private static final int DEPTH = 3;
    //全局布局
    @FXML
    private AnchorPane rootPane;

    //局部布局
    @FXML
    private GridPane drawer;
    @FXML
    private GridPane titleBar;
    @FXML
    private GridPane bottomBar;
    @FXML
    private Pane contentPane;

    //局部按钮及组件
    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXButton minimizeButton;
    @FXML
    private JFXButton maximizeButton;
    @FXML
    private MaterialIconView maximizeButtonGlyph;

    public void initializeUiConfig() {
        Stage stage = StageManager.getStage();
        BorderlessStageHelper.makeDraggable(stage, rootPane);
        BorderlessStageHelper.makeResizeable(stage);

        JFXDepthManager.setDepth(drawer, DEPTH);
        JFXDepthManager.setDepth(titleBar, DEPTH);
        JFXDepthManager.setDepth(bottomBar, DEPTH);
        JFXDepthManager.setDepth(contentPane, DEPTH);

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

    public void setContent(Node content) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(content);
    }
}
