package surevil.paintingandpainting.daoimpl.record;

import surevil.paintingandpainting.dao.record.HistoryDao;
import surevil.paintingandpainting.data.fileservice.FileService;
import surevil.paintingandpainting.data.fileservice.FileServiceImpl;
import surevil.paintingandpainting.entity.record.History;

import java.util.List;

public class HistoryDaoImpl implements HistoryDao {
    private FileService<History> fileService = new FileServiceImpl<>();

    @Override
    public List<History> findAllHistorys() {
        return fileService.findAll(History.class);
    }

    @Override
    public History saveHistory(History history) {
        return fileService.saveTuple(history);
    }
}

