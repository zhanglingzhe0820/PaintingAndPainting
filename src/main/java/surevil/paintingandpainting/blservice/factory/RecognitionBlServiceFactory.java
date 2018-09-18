package surevil.paintingandpainting.blservice.factory;

import surevil.paintingandpainting.bl.record.RecognitionBlServiceImpl;
import surevil.paintingandpainting.blservice.record.RecognitionBlService;

public class RecognitionBlServiceFactory {
    private static RecognitionBlService recognitionBlService = new RecognitionBlServiceImpl();

    public static RecognitionBlService getRecognitionBlService() {
        return recognitionBlService;
    }
}
