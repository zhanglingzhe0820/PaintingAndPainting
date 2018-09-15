package surevil.paintingandpainting.publicdata;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;

public enum Shape {
    CIRCLE("圆形", MaterialIcon.CHECK_CIRCLE),
    TRIANGLE("三角形", MaterialIcon.WARNING),
    SQUARE("正方形", MaterialIcon.CROP_SQUARE),
    RECTANGLE("长方形", MaterialIcon.POLYMER);

    private String name;
    private MaterialIcon materialIcon;

    Shape(String name, MaterialIcon materialIcon) {
        this.name = name;
        this.materialIcon = materialIcon;
    }

    public String getName() {
        return name;
    }

    public MaterialIcon getMaterialIcon() {
        return materialIcon;
    }
}
