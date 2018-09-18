package surevil.paintingandpainting.entity.record.rawrecord;

import javafx.scene.canvas.GraphicsContext;
import surevil.paintingandpainting.publicdata.raw.RawKind;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.util.PaintingUtil;

public class LineRawRecord extends RawRecord {
    private Point startPoint;
    private Point endPoint;

    public LineRawRecord(Point startPoint, Point endPoint) {
        super(RawKind.LINE);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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

    @Override
    public void draw(GraphicsContext graphicsContext) {
        PaintingUtil.drawLine(graphicsContext, startPoint, endPoint);
    }
}
