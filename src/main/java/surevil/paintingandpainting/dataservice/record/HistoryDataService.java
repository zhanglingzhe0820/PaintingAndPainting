package surevil.paintingandpainting.dataservice.record;

import surevil.paintingandpainting.entity.record.History;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;

import java.util.List;

public interface HistoryDataService {
    /**
     * 保存历史记录
     *
     * @param history
     */
    void save(History history) throws CanvasSaveException;

    /**
     * 获得所有历史记录
     */
    List<History> getAllHistory() throws CanvasLoadException;
}
