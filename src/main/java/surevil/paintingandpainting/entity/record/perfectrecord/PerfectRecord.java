package surevil.paintingandpainting.entity.record.perfectrecord;

import javafx.scene.canvas.GraphicsContext;
import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.Shape;
import surevil.paintingandpainting.util.PaintingUtil;

public class PerfectRecord extends Record {
    private Shape shape;
    private Point startPoint;
    private Point endPoint;

    public PerfectRecord(Shape shape, Point startPoint, Point endPoint) {
        super(DataKind.PERFECT);
        this.shape = shape;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
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
        PaintingUtil.drawShape(graphicsContext, shape, startPoint, endPoint);
    }
}

