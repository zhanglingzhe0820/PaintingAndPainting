package surevil.paintingandpainting.dataservice.factory;

import surevil.paintingandpainting.data.record.HistoryDataServiceImpl;
import surevil.paintingandpainting.dataservice.record.HistoryDataService;

public class HistoryDataServiceFactory {
    private static HistoryDataService historyDataService = new HistoryDataServiceImpl();

    public static HistoryDataService getHistoryDataService() {
        return historyDataService;
    }
}
