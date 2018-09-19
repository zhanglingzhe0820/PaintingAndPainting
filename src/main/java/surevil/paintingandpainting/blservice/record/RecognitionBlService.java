package surevil.paintingandpainting.blservice.record;

import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

import java.awt.image.BufferedImage;

public interface RecognitionBlService {
    /**
     * 通过图像识别出图形
     *
     * @return
     * @param bufferedImage
     */
    ShapeKind recognizeShapeByImage(BufferedImage bufferedImage);
}
