package surevil.paintingandpainting.dao.record;

import surevil.paintingandpainting.entity.record.History;

import java.util.List;

public interface HistoryDao {
    List<History> findAllHistorys();

    History saveHistory(History history);
}

