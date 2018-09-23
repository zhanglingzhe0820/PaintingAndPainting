package surevil.paintingandpainting.blservice.record;

import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.publicdata.DataKind;

import java.util.List;
import java.util.Stack;

public interface HistoryBlService {
    /**
     * 更新历史记录
     *
     * @param recordStack
     * @param dataKind
     */
    void updateHistory(Stack<Record> recordStack, DataKind dataKind) throws CanvasSaveException;

    /**
     * 加载历史记录
     *
     * @return
     */
    List<Record> loadHistory(DataKind dataKind) throws CanvasLoadException;
}
