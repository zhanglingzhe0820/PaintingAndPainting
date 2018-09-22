package surevil.paintingandpainting.entity.record.rawrecord;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.raw.RawKind;
import surevil.paintingandpainting.util.PaintingUtil;

public class LineRawRecord extends RawRecord {
    private Point startPoint;
    private Point endPoint;

    public LineRawRecord(Color color, int brushSize, Point startPoint, Point endPoint) {
        super(color, brushSize, RawKind.LINE);
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
        graphicsContext.save();

        graphicsContext.setLineWidth(this.getBrushSize());
        graphicsContext.setStroke(this.getColor());
        PaintingUtil.drawLine(graphicsContext, startPoint, endPoint);

        graphicsContext.restore();
    }
}
