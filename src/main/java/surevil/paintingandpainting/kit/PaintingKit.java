package surevil.paintingandpainting.kit;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

public class PaintingKit {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Point lastPoint;
    private Stack<Record> records;
    private HistoryBlService historyBlService = HistoryBlServiceFactory.getHistoryBlService();

    public PaintingKit(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();

        records = new Stack<>();
    }

    public void drawPoint(Point point) {
        if (lastPoint == null) {
            lastPoint = point;
        }
        PaintingUtil.drawLine(graphicsContext, lastPoint, point);
        records.push(new LineRawRecord(lastPoint, point));
        lastPoint = point;
    }

    public void drawFrame(Point startPoint, Point endPoint) {
        graphicsContext.save();
        graphicsContext.setLineDashes(3);
        graphicsContext.strokeRect(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
        graphicsContext.restore();
    }

    public void revert() throws CanvasLoadException {
        restoreLast();
    }

    public void dropBrush() {
        lastPoint = null;
    }

    public void clearAll() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        records.clear();
    }

    public void save(DataKind dataKind) throws CanvasSaveException {
        historyBlService.updateHistory(records, dataKind);
    }

    public void load(DataKind dataKind) throws CanvasLoadException {
        List<Record> loadedRecords = historyBlService.loadHistory(dataKind);
        drawAllOperations(loadedRecords);
        records = loadedRecords.stream().collect(Stack::new, Stack::push, Stack::addAll);
    }

    public void tag(ShapeKind shapeKind, Point core) {
        PaintingUtil.drawText(graphicsContext, shapeKind.getName(), core);
        records.push(new TextRawRecord(shapeKind.getName(), core));
    }

    public void drawShape(ShapeKind shapeKind, Point startPoint, Point endPoint) {
        PaintingUtil.drawShape(graphicsContext, shapeKind, startPoint, endPoint);
        records.push(new PerfectRecord(shapeKind, startPoint, endPoint));
    }

    private void restoreLast() throws CanvasLoadException {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        records.pop();
        drawAllOperations(new ArrayList<>(records));
    }

    private void drawAllOperations(List<Record> records) throws CanvasLoadException {
        try {
            for (Record record : records) {
                record.draw(graphicsContext);
            }
        } catch (Exception e) {
            throw new CanvasLoadException();
        }
    }
}

