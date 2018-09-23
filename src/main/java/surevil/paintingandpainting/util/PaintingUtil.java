package surevil.paintingandpainting.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.publicdata.MyColor;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

public class PaintingUtil {

    public static void drawLine(GraphicsContext graphicsContext, Point startPoint, Point endPoint) {
        graphicsContext.save();
        graphicsContext.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
        graphicsContext.restore();
    }

    public static void drawText(GraphicsContext graphicsContext, String text, Point core) {
        graphicsContext.save();
        graphicsContext.fillText(text, core.getX(), core.getY());
        graphicsContext.restore();
    }

    public static void drawShape(GraphicsContext graphicsContext, ShapeKind shapeKind, Point startPoint, Point endPoint) {
        Point core = new Point((startPoint.getX() + endPoint.getX()) / 2, (startPoint.getY() + endPoint.getY()) / 2);
        double minLength = Math.min(Math.abs(startPoint.getX() - endPoint.getX()), Math.abs(startPoint.getY() - endPoint.getY()));
        switch (shapeKind) {
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

    /**
     * 将awt.Color转为javafx.Color
     *
     * @param myColor
     * @return
     */
    public static Color convertToJavafxColor(MyColor myColor) {
        return new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), myColor.getOpacity());
    }

    /**
     * 将javafx.Color转为awt.Color
     *
     * @param color
     * @return
     */
    public static MyColor convertToMyColor(Color color) {
        return new MyColor(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
    }
}
