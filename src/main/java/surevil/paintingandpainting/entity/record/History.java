package surevil.paintingandpainting.entity.record;

import surevil.paintingandpainting.entity.Entity;
import surevil.paintingandpainting.entity.annotation.Column;
import surevil.paintingandpainting.entity.annotation.Id;
import surevil.paintingandpainting.entity.annotation.JsonSerialize;
import surevil.paintingandpainting.entity.annotation.Table;

import java.util.List;

@Table(name = "History")
public class History extends Entity {
    @Id
    @Column(name = "dataKind")
    private String dataKind;
    @Column(name = "records")
    @JsonSerialize
    private List<Record> records;

    public History() {
    }

    public History(String dataKind, List<Record> records) {
        this.dataKind = dataKind;
        this.records = records;
    }

    public String getDataKind() {
        return dataKind;
    }

    public void setDataKind(String dataKind) {
        this.dataKind = dataKind;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
