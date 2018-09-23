package surevil.paintingandpainting.blservice.record;

import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

public interface RecognitionBlService {
    /**
     * 通过图像识别出图形(识别优先级：圆形、三角形、正方形、长方形）
     *
     * @param path
     * @return
     */
    ShapeKind recognizeShapeByImage(String path, double width, double height);
}
