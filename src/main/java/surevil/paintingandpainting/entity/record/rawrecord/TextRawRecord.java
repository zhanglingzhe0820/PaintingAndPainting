package surevil.paintingandpainting.entity.record.rawrecord;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import surevil.paintingandpainting.publicdata.Point;
import surevil.paintingandpainting.publicdata.raw.RawKind;
import surevil.paintingandpainting.util.PaintingUtil;

public class TextRawRecord extends RawRecord {
    private String text;
    private Point core;

    public TextRawRecord(Color color, int brushSize, String text, Point core) {
        super(color, brushSize, RawKind.TEXT);
        this.text = text;
        this.core = core;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Point getCore() {
        return core;
    }

    public void setCore(Point core) {
        this.core = core;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.save();

        graphicsContext.setLineWidth(this.getBrushSize());
        graphicsContext.setStroke(this.getColor());
        PaintingUtil.drawText(graphicsContext, text, core);

        graphicsContext.restore();
    }
}
