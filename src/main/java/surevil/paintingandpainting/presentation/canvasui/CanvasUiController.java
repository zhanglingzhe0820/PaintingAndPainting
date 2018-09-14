package surevil.paintingandpainting.presentation.canvasui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import surevil.paintingandpainting.dto.Point;
import surevil.paintingandpainting.kit.PaintingKit;

public class CanvasUiController {
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private StackPane container;
    @FXML
    private Canvas canvas;

    private PaintingKit paintingKit;

    @FXML
    private void initialize() {
        container.setMinWidth(canvas.getWidth());
        container.setMinHeight(canvas.getHeight());
        paintingKit = new PaintingKit(canvas);
    }

    @FXML
    private void draw(MouseEvent event) {
        paintingKit.drawPoint(new Point(event.getX(), event.getY()));
    }

    /**
     * 鼠标松开=放下画笔
     */
    @FXML
    private void exitDraw(MouseEvent event) {
        paintingKit.dropBrush();
    }

//    @FXML
//    private void onBtnResetClicked(MouseEvent event) {
//        paintingKit.clearAll();
//    }
//
//    @FXML
//    private void onBtnSubmitClicked(MouseEvent event) {
//    }
//
//    @FXML
//    private void showSubmitPrompt(MouseEvent event) {
//        btnSubmit.setText("提交");
//    }
//
//    @FXML
//    private void closeSubmitPrompt(MouseEvent event) {
//        btnSubmit.setText("");
//    }

}
