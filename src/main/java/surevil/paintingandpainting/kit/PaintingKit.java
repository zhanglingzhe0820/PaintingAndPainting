package surevil.paintingandpainting.kit;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.dto.Point;

public class PaintingKit {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Point lastPoint;

    public PaintingKit(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.graphicsContext.setFill(Color.GREEN);
    }

    public void drawPoint(Point point) {
        if (lastPoint == null) {
            lastPoint = point;
        }
        graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), point.getX(), point.getY());
        lastPoint = point;
    }

    public void dropBrush() {
        lastPoint = null;
    }

    public void clearAll() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
