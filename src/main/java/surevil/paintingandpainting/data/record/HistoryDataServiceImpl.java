package surevil.paintingandpainting.data.record;

import surevil.paintingandpainting.dao.factory.HistoryDaoFactory;
import surevil.paintingandpainting.dao.record.HistoryDao;
import surevil.paintingandpainting.dataservice.record.HistoryDataService;
import surevil.paintingandpainting.entity.record.History;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;

import java.util.List;

public class HistoryDataServiceImpl implements HistoryDataService {
    private HistoryDao historyDao = HistoryDaoFactory.getHistoryDao();

    /**
     * 保存历史记录
     *
     * @param history
     */
    @Override
    public void save(History history) throws CanvasSaveException {
        History savedHistory = historyDao.saveHistory(history);
        if (savedHistory == null) {
            throw new CanvasSaveException();
        }
    }

    /**
     * 获得所有历史记录
     */
    @Override
    public List<History> getAllHistory() throws CanvasLoadException {
        List<History> histories = historyDao.findAllHistorys();
        if (histories.isEmpty()) {
            throw new CanvasLoadException();
        }
        return histories;
    }
}
