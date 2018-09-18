package surevil.paintingandpainting.bl.record;

import surevil.paintingandpainting.blservice.record.RecognitionBlService;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

public class RecognitionBlServiceImpl implements RecognitionBlService {
    /**
     * 通过图像识别出图形
     *
     * @return
     */
    @Override
    public ShapeKind recognizeShapeByImage() {
        return ShapeKind.RECTANGLE;
    }
}
