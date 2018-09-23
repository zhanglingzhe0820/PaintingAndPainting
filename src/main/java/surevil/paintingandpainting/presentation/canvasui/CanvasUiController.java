package surevil.paintingandpainting.presentation.canvasui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.blservice.factory.RecognitionBlServiceFactory;
import surevil.paintingandpainting.blservice.record.RecognitionBlService;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.kit.PaintingKit;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.PaintingOperationKind;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;
import surevil.paintingandpainting.util.PathUtil;
import surevil.paintingandpainting.util.PromptDialogUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.function.Consumer;

public class CanvasUiController {
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private Label brushSizeLabel;

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
    //用于确认是否是擦除模式
    private boolean isErasing;
    //选择模式的起始点（画框模式）
    private Point selectStartPoint;
    private int brushSize;

    private RecognitionBlService recognitionBlService = RecognitionBlServiceFactory.getRecognitionBlService();
    private Stack<PaintingOperationKind> paintingOperationKindStack = new Stack<>();

    private static final String BRUSH_SIZE_TEXT = "笔画粗细：";

    @FXML
    private void initialize() {
        initContainer();
        initCanvas();
        initComponents();
        container.requestFocus();
    }

    /**
     * 初始化容器面板
     */
    private void initContainer() {
        container.setMinWidth(canvas.getWidth());
        container.setMinHeight(canvas.getHeight());
    }

    /**
     * 初始化三个画图面板
     */
    private void initCanvas() {
        paintingKit = new PaintingKit(canvas);
        fakePaintingKit = new PaintingKit(fakeCanvas);
        perfectPaintingKit = new PaintingKit(perfectCanvas);
        isSelecting = false;
        isErasing = false;
        loadCanvas();
    }

    /**
     * 根据类型加载面板
     */
    private void loadCanvas() {
        try {
            paintingKit.load(DataKind.RAW);
            perfectPaintingKit.load(DataKind.PERFECT);
        } catch (CanvasLoadException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化组件属性
     */
    private void initComponents() {
        colorPicker.setValue(Color.BLACK);

        brushSize = 1;
        refreshPaintingKit();
    }

    @FXML
    private void onHotKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Q:
                onBtnSelectClicked();
                break;
            case W:
                onBtnEditClicked();
                break;
            case E:
                onBtnEraseClicked();
                break;
            case R:
                onBtnEraseClicked();
                break;
            case Z:
                onBtnRevertClicked();
                break;
            case X:
                onBtnResetClicked();
                break;
            case S:
                onBtnSubmitClicked();
                break;
            case OPEN_BRACKET:
                onBtnDecreaseSizeClicked();
                break;
            case CLOSE_BRACKET:
                onBtnIncreaseSizeClicked();
                break;
        }
    }

    /**
     * 刷新面板属性
     */
    private void refreshPaintingKit() {
        brushSizeLabel.setText(BRUSH_SIZE_TEXT + brushSize);
        paintingKit.setBrushSize(brushSize);
        perfectPaintingKit.setBrushSize(brushSize);
    }


    /**
     * 绘图
     *
     * @param event
     */
    @FXML
    private void draw(MouseEvent event) {
        Point nowPoint = new Point(event.getX(), event.getY());
        if (!isSelecting) {
            fakePaintingKit.clearAll();
            //非选择模式下自由绘图
            if (isErasing) {
                //擦除模式下橡皮书写
                paintingKit.erasePoint(nowPoint);
            } else {//非擦除模式下正常绘图
                paintingKit.drawPoint(nowPoint);
            }
            paintingOperationKindStack.push(PaintingOperationKind.LINE);
        } else {
            //选择模式下画框
            if (selectStartPoint == null) {
                selectStartPoint = nowPoint;
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
            saveSnapshot(selectStartPoint, endPoint);
            fakePaintingKit.drawFrame(selectStartPoint, endPoint);
            Point core = calculateCore(selectStartPoint, endPoint);
            promptShapeDialog(selectStartPoint, endPoint, shape -> {
                paintingKit.tag(shape, core);
                perfectPaintingKit.drawShape(shape, selectStartPoint, endPoint);
                paintingOperationKindStack.push(PaintingOperationKind.SELECT);
                exitSelectingMode();
            });
        }
    }

    /**
     * 保存区域截图
     *
     * @param startPoint
     * @param endPoint
     * @return
     */
    private BufferedImage saveSnapshot(Point startPoint, Point endPoint) {
        try {
            File file = new File(getSnapshotPath());
            assert !file.exists() : file.createNewFile();
            WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
            PixelReader pixelReader = image.getPixelReader();

            WritableImage wImage = new WritableImage(
                    (int) Math.floor(endPoint.getX() - startPoint.getX()),
                    (int) Math.floor(endPoint.getY() - startPoint.getY()));
            PixelWriter pixelWriter = wImage.getPixelWriter();

            //算出X轴的起始点与终结点
            int readStartX;
            int readEndX;
            if (startPoint.getX() > endPoint.getX()) {
                readStartX = (int) Math.floor(endPoint.getX());
                readEndX = (int) Math.floor(startPoint.getX());
            } else {
                readStartX = (int) Math.floor(startPoint.getX());
                readEndX = (int) Math.floor(endPoint.getX());
            }

            //算出Y轴的起始点与终结点
            int readStartY;
            int readEndY;
            if (startPoint.getY() > endPoint.getY()) {
                readStartY = (int) Math.floor(endPoint.getY());
                readEndY = (int) Math.floor(startPoint.getY());
            } else {
                readStartY = (int) Math.floor(startPoint.getY());
                readEndY = (int) Math.floor(endPoint.getY());
            }

            // 按起始点与终结点填充入新的图中
            for (int i = readStartY; i < readEndY; i++) {
                for (int j = readStartX; j < readEndX; j++) {
                    Color color = pixelReader.getColor(j, i);
                    color = color.brighter();
                    pixelWriter.setColor(j - readStartX, i - readStartY, color);
                }
            }
            //导出截图
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(wImage, null);
            ImageIO.write(bufferedImage, "png", file);
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
            PromptDialogUtil.show(container, "截图失败！", "截图失败！请重试……");
            return null;
        }
    }

    private String getSnapshotPath() {
        return PathUtil.getDatabasePath("snapshot.png");
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

    /**
     * 进入画图模式
     */
    @FXML
    private void onBtnEditClicked() {
        isErasing = false;
        isSelecting = false;
    }

    /**
     * 进入擦除模式
     */
    @FXML
    private void onBtnEraseClicked() {
        isErasing = true;
    }

    /**
     * 根据事件序列进行回撤
     */
    @FXML
    private void onBtnRevertClicked() {
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
                    break;
                }
            }
        } catch (CanvasLoadException e) {
            e.printStackTrace();
            PromptDialogUtil.show(container, "回撤失败！请重试！或者寻找支持人员！", "回撤失败！");
        }
    }

    /**
     * 重置
     */
    @FXML
    private void onBtnResetClicked() {
        paintingKit.clearAll();
        perfectPaintingKit.clearAll();
    }

    /**
     * 提交
     */
    @FXML
    private void onBtnSubmitClicked() {
        try {
            paintingKit.save(DataKind.RAW);
            perfectPaintingKit.save(DataKind.PERFECT);
        } catch (CanvasSaveException exception) {
            exception.printStackTrace();
            PromptDialogUtil.show(container, "保存失败！请重试！或者寻找支持人员！", "保存失败！");
        }
    }

    /**
     * 进入选择模式
     */
    @FXML
    private void onBtnSelectClicked() {
        isSelecting = true;
    }

    /**
     * 弹出选择图形框
     *
     * @param startPoint
     * @param endPoint
     * @param shapeConsumer
     */
    private void promptShapeDialog(Point startPoint, Point endPoint, Consumer<ShapeKind> shapeConsumer) {
        JFXDialogLayout layout = new JFXDialogLayout();
        VBox vBox = new VBox();
        BufferedImage bufferedImage = saveSnapshot(startPoint, endPoint);
        assert bufferedImage != null;
        ShapeKind recognizedShapeKind = recognitionBlService.recognizeShapeByImage(getSnapshotPath(), bufferedImage.getWidth(), bufferedImage.getHeight());
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

    /**
     * 根据颜色变化变化画板颜色属性
     */
    @FXML
    private void onColorChanged() {
        distributeColor(colorPicker.getValue());
    }

    private void distributeColor(Color color) {
        paintingKit.setColor(color);
        perfectPaintingKit.setColor(color);
    }

    /**
     * 增加笔画粗细
     */
    @FXML
    private void onBtnIncreaseSizeClicked() {
        brushSize++;
        refreshPaintingKit();
    }

    /**
     * 减少笔画粗细
     */
    @FXML
    private void onBtnDecreaseSizeClicked() {
        if (brushSize > 1) {
            brushSize--;
            refreshPaintingKit();
        }
    }
}
