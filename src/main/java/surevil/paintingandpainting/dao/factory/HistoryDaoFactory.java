package surevil.paintingandpainting.dao.factory;

import surevil.paintingandpainting.dao.record.HistoryDao;
import surevil.paintingandpainting.daoimpl.record.HistoryDaoImpl;

public class HistoryDaoFactory {
    private static HistoryDao historyDao = new HistoryDaoImpl();

    public static HistoryDao getHistoryDao() {
        return historyDao;
    }
}
