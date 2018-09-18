package surevil.paintingandpainting.entity.record.rawrecord;

import surevil.paintingandpainting.entity.record.Record;
import surevil.paintingandpainting.publicdata.DataKind;
import surevil.paintingandpainting.publicdata.raw.RawKind;

public abstract class RawRecord extends Record {
    private RawKind rawKind;

    public RawRecord(RawKind rawKind) {
        super(DataKind.RAW);
        this.rawKind = rawKind;
    }

    public RawKind getRawKind() {
        return rawKind;
    }

    public void setRawKind(RawKind rawKind) {
        this.rawKind = rawKind;
    }
}
