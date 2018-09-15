package surevil.paintingandpainting.kit;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.dto.Point;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.publicdata.Shape;
import surevil.paintingandpainting.util.PathUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

public class PaintingKit {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Point lastPoint;
    private Stack<File> historys;

    public PaintingKit(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.graphicsContext.setFill(Color.GREEN);

        historys = new Stack<>();
    }

    public void drawPoint(Point point) {
        if (lastPoint == null) {
            lastPoint = point;
        }
        graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), point.getX(), point.getY());
        lastPoint = point;
    }

    public void drawFrame(Point startPoint, Point endPoint) {
        graphicsContext.save();
        graphicsContext.setLineDashes(3);
        graphicsContext.strokeRect(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
        graphicsContext.restore();
    }

    public void revert() {
        restoreLast();
    }

    public void dropBrush() {
        lastPoint = null;
    }

    public void clearAll() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void save(String path) throws CanvasSaveException {
        try {
            File file = new File(path);
            WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CanvasSaveException();
        }
    }

    public void load(String path) throws CanvasLoadException {
        try {
            double wholeWidth = canvas.getWidth();
            double wholeHeight = canvas.getHeight();
            Image image = new Image(new FileInputStream(new File(path)), wholeWidth, wholeHeight, false, false);
            WritableImage croppedImage = new WritableImage(image.getPixelReader(), 0, 0, (int) wholeWidth, (int) wholeHeight);
            graphicsContext.drawImage(croppedImage, canvas.getLayoutX(), canvas.getLayoutY());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CanvasLoadException();
        }
    }

    public void tag(Shape shape, Point core) {
        graphicsContext.fillText(shape.getName(), core.getX(), core.getY());
    }

    public void drawShape(Shape shape, Point startPoint, Point endPoint) {
        Point core = new Point((startPoint.getX() + endPoint.getX()) / 2, (startPoint.getY() + endPoint.getY()) / 2);
        double minLength = Math.min(Math.abs(startPoint.getX() - endPoint.getX()), Math.abs(startPoint.getY() - endPoint.getY()));
        switch (shape) {
            case CIRCLE:
                graphicsContext.strokeOval(core.getX() - minLength / 2, core.getY() - minLength / 2, minLength, minLength);
                break;
            case SQUARE:
                graphicsContext.strokeRect(core.getX() - minLength / 2, core.getY() - minLength / 2, minLength, minLength);
                break;
            case TRIANGLE:
                double[] xs = new double[3];
                double[] ys = new double[3];
                xs[0] = core.getX() - minLength / 2;
                ys[0] = core.getY() + minLength / 2 / Math.sqrt(3);
                xs[1] = core.getX() + minLength / 2;
                ys[1] = core.getY() + minLength / 2 / Math.sqrt(3);
                xs[2] = core.getX();
                ys[2] = core.getY() - minLength / Math.sqrt(3);
                graphicsContext.strokePolygon(xs, ys, 3);
                break;
            case RECTANGLE:
                graphicsContext.strokeRect(startPoint.getX(), startPoint.getY(), endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
                break;
        }

    }

    private void restoreLast() {
        double wholeWidth = canvas.getWidth();
        double wholeHeight = canvas.getHeight();
        String imageURL = historys.pop().getAbsolutePath();
        Image image = new Image(imageURL, wholeWidth, wholeHeight, false, true);
        WritableImage croppedImage = new WritableImage(image.getPixelReader(), 0, 0, (int) wholeWidth, (int) wholeHeight);
        graphicsContext.drawImage(croppedImage, canvas.getLayoutX(), canvas.getLayoutY());
    }

    private void saveNow() {
        File file = nextFile();
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            historys.push(file);
        } catch (IOException ignored) {
        }
    }

    private File nextFile() {
        return new File(nextHistoryIndex());
    }

    private String nextHistoryIndex() {
        if (historys.empty()) {
            return PathUtil.getTmpPath("0");
        } else {
            File file = historys.lastElement();
            String[] paths = file.getAbsolutePath().split("/");
            int lastIndex = Integer.parseInt(paths[paths.length - 1]);
            return PathUtil.getTmpPath(String.valueOf(lastIndex + 1));
        }
    }
}

