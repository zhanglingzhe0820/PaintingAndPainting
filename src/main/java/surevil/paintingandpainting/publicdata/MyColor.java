package surevil.paintingandpainting.publicdata;

import java.io.Serializable;

/**
 * 用于序列化存储的颜色类
 */
public class MyColor implements Serializable {
    private double red;
    private double green;
    private double blue;
    private double opacity;

    public MyColor() {
    }

    public MyColor(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }
}
