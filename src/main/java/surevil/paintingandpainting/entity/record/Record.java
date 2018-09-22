package surevil.paintingandpainting.entity.record;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.publicdata.DataKind;

import java.io.Serializable;

public abstract class Record implements Serializable {
    private DataKind dataKind;
    private Color color;
    private int brushSize;

    public Record(DataKind dataKind, Color color, int brushSize) {
        this.dataKind = dataKind;
        this.color = color;
        this.brushSize = brushSize;
    }

    public DataKind getDataKind() {
        return dataKind;
    }

    public void setDataKind(DataKind dataKind) {
        this.dataKind = dataKind;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }

    public abstract void draw(GraphicsContext graphicsContext);
}
