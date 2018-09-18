package surevil.paintingandpainting.blservice.record;

import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

public interface RecognitionBlService {
    /**
     * 通过图像识别出图形
     *
     * @return
     */
    ShapeKind recognizeShapeByImage();
}
