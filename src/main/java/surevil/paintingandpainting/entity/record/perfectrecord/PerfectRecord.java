package surevil.paintingandpainting.entity.record.perfectrecord;

import javafx.scene.canvas.GraphicsContext;
import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.MyColor;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;
import surevil.paintingandpainting.util.PaintingUtil;

public class PerfectRecord extends Record {
    private ShapeKind shapeKind;
    private Point startPoint;
    private Point endPoint;

    public PerfectRecord(MyColor color, int brushSize, ShapeKind shapeKind, Point startPoint, Point endPoint) {
        super(DataKind.PERFECT, color, brushSize);
        this.shapeKind = shapeKind;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public ShapeKind getShapeKind() {
        return shapeKind;
    }

    public void setShapeKind(ShapeKind shapeKind) {
        this.shapeKind = shapeKind;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.save();

        graphicsContext.setLineWidth(this.getBrushSize());
        graphicsContext.setStroke(PaintingUtil.convertToJavafxColor(this.getColor()));
        PaintingUtil.drawShape(graphicsContext, shapeKind, startPoint, endPoint);

        graphicsContext.restore();
    }
}

