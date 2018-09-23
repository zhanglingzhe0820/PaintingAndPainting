package surevil.paintingandpainting.kit;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.blservice.factory.HistoryBlServiceFactory;
import surevil.paintingandpainting.blservice.record.HistoryBlService;
import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.entity.record.perfectrecord.PerfectRecord;
import surevil.paintingandpainting.entity.record.rawrecord.LineRawRecord;
import surevil.paintingandpainting.entity.record.rawrecord.TextRawRecord;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;
import surevil.paintingandpainting.util.PaintingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 自己封装的画板工具类
 */
public class PaintingKit {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color color;
    private int brushSize;
    private Point lastPoint;
    private Stack<Record> records;
    private HistoryBlService historyBlService = HistoryBlServiceFactory.getHistoryBlService();

    public PaintingKit(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();

        records = new Stack<>();

        initAttributes();
    }

    /**
     * 初始化画板属性
     */
    private void initAttributes() {
        color = Color.BLACK;
        brushSize = 1;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }

    /**
     * 画点并连线（外部看来只是画点）
     *
     * @param point
     */
    public void drawPoint(Point point) {
        graphicsContext.save();

        if (lastPoint == null) {
            lastPoint = point;
        }
        graphicsContext.setLineWidth(brushSize);
        graphicsContext.setStroke(color);
        PaintingUtil.drawLine(graphicsContext, lastPoint, point);
        records.push(new LineRawRecord(color, brushSize, lastPoint, point));
        lastPoint = point;

        graphicsContext.restore();
    }

    /**
     * 同画点，颜色调为白色，即擦除
     *
     * @param point
     */
    public void erasePoint(Point point) {
        graphicsContext.save();

        if (lastPoint == null) {
            lastPoint = point;
        }
        graphicsContext.setLineWidth(brushSize);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(5);
        PaintingUtil.drawLine(graphicsContext, lastPoint, point);
        records.push(new LineRawRecord(Color.WHITE, brushSize, lastPoint, point));
        lastPoint = point;

        graphicsContext.restore();
    }

    /**
     * 画框
     *
     * @param startPoint
     * @param endPoint
     */
    public void drawFrame(Point startPoint, Point endPoint) {
        graphicsContext.save();

        graphicsContext.setLineDashes(3);
        graphicsContext.strokeRect(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());

        graphicsContext.restore();
    }

    /**
     * 回撤，仅回撤单独一步
     *
     * @throws CanvasLoadException
     */
    public void revert() throws CanvasLoadException {
        restoreLast();
    }

    /**
     * 丢下画笔
     */
    public void dropBrush() {
        lastPoint = null;
    }

    /**
     * 清屏
     */
    public void clearAll() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        records.clear();
    }

    /**
     * 存储
     *
     * @param dataKind
     * @throws CanvasSaveException
     */
    public void save(DataKind dataKind) throws CanvasSaveException {
        historyBlService.updateHistory(records, dataKind);
    }

    /**
     * 加载
     *
     * @param dataKind
     * @throws CanvasLoadException
     */
    public void load(DataKind dataKind) throws CanvasLoadException {
        List<Record> loadedRecords = historyBlService.loadHistory(dataKind);
        drawAllOperations(loadedRecords);
        records = loadedRecords.stream().collect(Stack::new, Stack::push, Stack::addAll);
    }

    /**
     * 打标签
     *
     * @param shapeKind
     * @param core
     */
    public void tag(ShapeKind shapeKind, Point core) {
        graphicsContext.save();

        PaintingUtil.drawText(graphicsContext, shapeKind.getName(), core);
        records.push(new TextRawRecord(color, brushSize, shapeKind.getName(), core));

        graphicsContext.restore();
    }

    /**
     * 画形状
     *
     * @param shapeKind
     * @param startPoint
     * @param endPoint
     */
    public void drawShape(ShapeKind shapeKind, Point startPoint, Point endPoint) {
        graphicsContext.save();

        graphicsContext.setLineWidth(brushSize);
        graphicsContext.setStroke(color);
        PaintingUtil.drawShape(graphicsContext, shapeKind, startPoint, endPoint);
        records.push(new PerfectRecord(color, brushSize, shapeKind, startPoint, endPoint));

        graphicsContext.restore();
    }

    /**
     * 内部方法：用于实现回撤，即pop一步，并重绘
     *
     * @throws CanvasLoadException
     */
    private void restoreLast() throws CanvasLoadException {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        records.pop();
        drawAllOperations(new ArrayList<>(records));
    }

    /**
     * 内部方法：用于实现回撤，即pop后重画所有笔画
     *
     * @param records
     * @throws CanvasLoadException
     */
    private void drawAllOperations(List<Record> records) throws CanvasLoadException {
        try {
            for (Record record : records) {
                record.draw(graphicsContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CanvasLoadException();
        }
    }
}

