package surevil.paintingandpainting.bl.record;

import surevil.paintingandpainting.blservice.record.RecognitionBlService;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

import java.awt.image.BufferedImage;

public class RecognitionBlServiceImpl implements RecognitionBlService {
    /**
     * 通过图像识别出图形
     *
     * @return
     * @param bufferedImage
     */
    @Override
    public ShapeKind recognizeShapeByImage(BufferedImage bufferedImage) {
        return ShapeKind.RECTANGLE;
    }
}
