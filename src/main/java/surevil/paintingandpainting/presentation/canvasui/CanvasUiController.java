package surevil.paintingandpainting.presentation.canvasui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import surevil.paintingandpainting.dto.Point;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.kit.PaintingKit;
import surevil.paintingandpainting.publicdata.Shape;
import surevil.paintingandpainting.util.PathUtil;

import java.util.function.Consumer;

public class CanvasUiController {
    @FXML
    private StackPane container;
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas fakeCanvas;

    //用于画图的canvas
    private PaintingKit paintingKit;
    //用于虚线画框的canvas
    private PaintingKit fakePaintingKit;

    //用于确认是否是选择模式（画框模式）
    private boolean isSelecting;
    //选择模式的起始点（画框模式）
    private Point selectStartPoint;

    @FXML
    private void initialize() {
        initContainer();
        initCanvas();
    }

    private void initContainer() {
        container.setMinWidth(canvas.getWidth());
        container.setMinHeight(canvas.getHeight());
    }

    private void initCanvas() {
        paintingKit = new PaintingKit(canvas);
        fakePaintingKit = new PaintingKit(fakeCanvas);
        isSelecting = false;
        loadCanvas();
    }

    private void loadCanvas() {
        try {
            paintingKit.load(getStoreFilePath());
        } catch (CanvasLoadException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void draw(MouseEvent event) {
        if (!isSelecting) {
            //非选择模式下自由绘图
            fakePaintingKit.clearAll();
            paintingKit.drawPoint(new Point(event.getX(), event.getY()));
        } else {
            //选择模式下画框
            if (selectStartPoint == null) {
                selectStartPoint = new Point(event.getX(), event.getY());
            } else {
                fakePaintingKit.clearAll();
                fakePaintingKit.drawFrame(selectStartPoint, new Point(event.getX(), event.getY()));
            }
        }
    }

    /**
     * 鼠标松开=放下画笔
     */
    @FXML
    private void exitDraw(MouseEvent event) {
        if (!isSelecting) {
            paintingKit.dropBrush();
        } else {
            Point endPoint = new Point(event.getX(), event.getY());
            fakePaintingKit.drawFrame(selectStartPoint, endPoint);
            Point core = calculateCore(selectStartPoint, endPoint);
            promptDialog(shape -> paintingKit.drawShape(shape, core));

            exitSelectingMode();
        }
    }

    /**
     * 退出选择模式
     */
    private void exitSelectingMode() {
        isSelecting = false;
        selectStartPoint = null;
    }

    private Point calculateCore(Point start, Point end) {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    @FXML
    private void onBtnResetClicked(MouseEvent event) {
        paintingKit.clearAll();
    }

    @FXML
    private void onBtnSubmitClicked(MouseEvent event) {
        try {
            paintingKit.save(getStoreFilePath());
        } catch (CanvasSaveException exception) {
            exception.printStackTrace();
            JFXDialogLayout layout = new JFXDialogLayout();
            JFXButton button = new JFXButton("好", new MaterialIconView(MaterialIcon.CHECK));
            layout.setBody(new Label("保存失败！请重试！或者寻找支持人员！"));
            layout.setHeading(new Label("保存失败！"));
            layout.setActions(button);
            JFXDialog dialog = new JFXDialog(container, layout, JFXDialog.DialogTransition.CENTER);
            button.setOnAction(e -> dialog.close());
            dialog.show();
        }
    }

    @FXML
    private void onBtnSelectClicked(MouseEvent event) {
        isSelecting = true;
    }

    private void promptDialog(Consumer<Shape> shapeConsumer) {
        JFXDialogLayout layout = new JFXDialogLayout();
        HBox hBox = new HBox();
        layout.setHeading(new Label("请选择图形"));
        layout.setHeading(hBox);
        JFXDialog dialog = new JFXDialog(container, layout, JFXDialog.DialogTransition.CENTER);
        for (Shape shape : Shape.values()) {
            JFXButton button = new JFXButton(shape.getName(), new MaterialIconView(shape.getMaterialIcon()));
            button.setOnMouseClicked(event -> {
                shapeConsumer.accept(shape);
                dialog.close();
            });
            hBox.getChildren().add(button);
        }
        dialog.show();
    }

    private String getStoreFilePath() {
        return PathUtil.getDatabasePath("canvas.png");
    }

}
