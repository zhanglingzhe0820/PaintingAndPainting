package surevil.paintingandpainting.blservice.factory;

import surevil.paintingandpainting.bl.record.HistoryBlServiceImpl;
import surevil.paintingandpainting.blservice.record.HistoryBlService;

public class HistoryBlServiceFactory {
    private static HistoryBlService historyBlService = new HistoryBlServiceImpl();

    public static HistoryBlService getHistoryBlService() {
        return historyBlService;
    }
}
