package surevil.paintingandpainting.bl.record;

import surevil.paintingandpainting.blservice.record.HistoryBlService;
import surevil.paintingandpainting.dataservice.factory.HistoryDataServiceFactory;
import surevil.paintingandpainting.dataservice.record.HistoryDataService;
import surevil.paintingandpainting.entity.record.History;
import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.exception.CanvasLoadException;
import surevil.paintingandpainting.exception.CanvasSaveException;
import surevil.paintingandpainting.publicdata.DataKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class HistoryBlServiceImpl implements HistoryBlService {
    private HistoryDataService historyDataService = HistoryDataServiceFactory.getHistoryDataService();

    /**
     * save records(cover the raw data)
     *
     * @param recordStack
     * @param dataKind
     */
    @Override
    public void updateHistory(Stack<Record> recordStack, DataKind dataKind) throws CanvasSaveException {
        List<Record> recordList = new ArrayList<>(recordStack);
        historyDataService.save(new History(dataKind.getName(), recordList));
    }

    /**
     * load the raw history
     *
     * @param dataKind
     * @return
     */
    @Override
    public List<Record> loadHistory(DataKind dataKind) throws CanvasLoadException {
        try {
            List<History> histories = historyDataService.getAllHistory();
            Optional<History> optionalHistory = histories.stream().filter(tempHistory -> dataKind.getName().equals(tempHistory.getDataKind())).findFirst();
            if (optionalHistory.isPresent()) {
                History history = optionalHistory.get();
                return history.getRecords();
            } else {
                throw new CanvasLoadException();
            }
        } catch (Exception e) {
            throw new CanvasLoadException();
        }
    }

}
