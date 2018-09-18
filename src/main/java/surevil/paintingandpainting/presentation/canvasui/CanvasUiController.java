package surevil.paintingandpainting.presentation.canvasui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import surevil.paintingandpainting.blservice.factory.RecognitionBlServiceFactory;
import surevil.paintingandpainting.blservice.record.RecognitionBlService;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.kit.PaintingKit;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.PaintingOperationKind;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;
import surevil.paintingandpainting.util.PromptDialogUtil;

import java.util.Stack;
import java.util.function.Consumer;

public class CanvasUiController {
    @FXML
    private StackPane container;
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas fakeCanvas;
    @FXML
    private Canvas perfectCanvas;

    //用于画图的canvas
    private PaintingKit paintingKit;
    //用于虚线画框的canvas
    private PaintingKit fakePaintingKit;
    //用于画完美图形的canvas
    private PaintingKit perfectPaintingKit;

    //用于确认是否是选择模式（画框模式）
    private boolean isSelecting;
    //选择模式的起始点（画框模式）
    private Point selectStartPoint;

    private RecognitionBlService recognitionBlService = RecognitionBlServiceFactory.getRecognitionBlService();
    private Stack<PaintingOperationKind> paintingOperationKindStack = new Stack<>();

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
        perfectPaintingKit = new PaintingKit(perfectCanvas);
        isSelecting = false;
        loadCanvas();
    }

    private void loadCanvas() {
        try {
            paintingKit.load(DataKind.RAW);
            perfectPaintingKit.load(DataKind.PERFECT);
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
            paintingOperationKindStack.push(PaintingOperationKind.LINE);
        } else {
            //选择模式下画框
            if (selectStartPoint == null) {
                selectStartPoint = new Point(event.getX(), event.getY());
            } else {
                fakePaintingKit.clearAll();
            }
            fakePaintingKit.drawFrame(selectStartPoint, new Point(event.getX(), event.getY()));
        }
    }

    /**
     * 鼠标松开=放下画笔
     */
    @FXML
    private void exitDraw(MouseEvent event) {
        if (!isSelecting) {
            paintingKit.dropBrush();
            paintingOperationKindStack.push(PaintingOperationKind.DROP);
        } else {
            Point endPoint = new Point(event.getX(), event.getY());
            fakePaintingKit.drawFrame(selectStartPoint, endPoint);
            Point core = calculateCore(selectStartPoint, endPoint);
            promptShapeDialog(shape -> {
                paintingKit.tag(shape, core);
                perfectPaintingKit.drawShape(shape, selectStartPoint, endPoint);
                paintingOperationKindStack.push(PaintingOperationKind.SELECT);
                exitSelectingMode();
            });
        }
    }

    /**
     * 退出选择模式
     */
    private void exitSelectingMode() {
        isSelecting = false;
        selectStartPoint = null;
        fakePaintingKit.clearAll();
    }

    private Point calculateCore(Point start, Point end) {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    @FXML
    private void onBtnRevertClicked(MouseEvent event) {
        try {
            while (true) {
                PaintingOperationKind paintingOperationKind = paintingOperationKindStack.pop();
                if (paintingOperationKind == PaintingOperationKind.DROP) {
                    break;
                } else if (paintingOperationKind == PaintingOperationKind.LINE) {
                    paintingKit.revert();
                } else {
                    paintingKit.revert();
                    perfectPaintingKit.revert();
                }
            }
        } catch (CanvasLoadException e) {
            e.printStackTrace();
            PromptDialogUtil.show(container, "回撤失败！请重试！或者寻找支持人员！", "回撤失败！");
        }
    }

    @FXML
    private void onBtnResetClicked(MouseEvent event) {
        paintingKit.clearAll();
        perfectPaintingKit.clearAll();
    }

    @FXML
    private void onBtnSubmitClicked(MouseEvent event) {
        try {
            paintingKit.save(DataKind.RAW);
            perfectPaintingKit.save(DataKind.PERFECT);
        } catch (CanvasSaveException exception) {
            exception.printStackTrace();
            PromptDialogUtil.show(container, "保存失败！请重试！或者寻找支持人员！", "保存失败！");
        }
    }

    @FXML
    private void onBtnSelectClicked(MouseEvent event) {
        isSelecting = true;
    }

    private void promptShapeDialog(Consumer<ShapeKind> shapeConsumer) {
        JFXDialogLayout layout = new JFXDialogLayout();
        VBox vBox = new VBox();
        ShapeKind recognizedShapeKind = recognitionBlService.recognizeShapeByImage();
        Label label = new Label(" 请选择图形，系统推荐结果为" + recognizedShapeKind.getName());
        vBox.getChildren().add(label);
        layout.setHeading(vBox);
        JFXDialog dialog = new JFXDialog(container, layout, JFXDialog.DialogTransition.CENTER);
        HBox hBox = new HBox();
        for (ShapeKind shapeKind : ShapeKind.values()) {
            JFXButton button = new JFXButton(shapeKind.getName(), new MaterialIconView(shapeKind.getMaterialIcon()));
            button.setOnMouseClicked(event -> {
                shapeConsumer.accept(shapeKind);
                dialog.close();
            });
            hBox.getChildren().add(button);
        }
        vBox.getChildren().add(hBox);
        dialog.show();
    }

}
