package surevil.paintingandpainting.dataservice.record;

import surevil.paintingandpainting.entity.record.History;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;

import java.util.List;

public interface HistoryDataService {
    /**
     * save the history of record
     *
     * @param history
     */
    void save(History history) throws CanvasSaveException;

    /**
     * get all the history
     */
    List<History> getAllHistory() throws CanvasLoadException;
}
