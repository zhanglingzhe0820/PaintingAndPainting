package surevil.paintingandpainting.presentation.frameworkui;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrameworkUiController {
    //局部布局
    @FXML
    private Pane contentPane;
    @FXML
    private Pane titleBar;

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
        BorderlessStageHelper.makeDraggable(stage, titleBar);
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

    public void setContent(Node content) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(content);
    }
}
