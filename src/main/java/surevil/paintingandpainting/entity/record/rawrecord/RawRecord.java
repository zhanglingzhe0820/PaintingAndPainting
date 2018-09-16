package surevil.paintingandpainting.entity.record.rawrecord;

import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.OperationKind;

public abstract class RawRecord extends Record {
    private OperationKind operationKind;

    public RawRecord(OperationKind operationKind) {
        super(DataKind.RAW);
        this.operationKind = operationKind;
    }

    public OperationKind getOperationKind() {
        return operationKind;
    }

    public void setOperationKind(OperationKind operationKind) {
        this.operationKind = operationKind;
    }
}
