package surevil.paintingandpainting.entity.record;

import javafx.scene.canvas.GraphicsContext;
import surevil.paintingandpainting.publicdata.DataKind;

import java.io.Serializable;

public abstract class Record implements Serializable {
    private DataKind dataKind;

    public Record(DataKind dataKind) {
        this.dataKind = dataKind;
    }

    public DataKind getDataKind() {
        return dataKind;
    }

    public void setDataKind(DataKind dataKind) {
        this.dataKind = dataKind;
    }

    public abstract void draw(GraphicsContext graphicsContext);
}
