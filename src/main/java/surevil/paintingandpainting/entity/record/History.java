package surevil.paintingandpainting.entity.record;

import surevil.paintingandpainting.entity.Entity;
import surevil.paintingandpainting.entity.annotation.*;
import surevil.paintingandpainting.publicdata.DataKind;

import java.util.List;

@Table(name = "History")
public class History extends Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "dataKind")
    @EnumTranslate(targetClass = DataKind.class)
    private DataKind dataKind;
    @Column(name = "records")
    @JsonSerialize
    private List<Record> records;

    public History() {
    }

    public History(DataKind dataKind, List<Record> records) {
        this.dataKind = dataKind;
        this.records = records;
    }

    public History(int id, DataKind dataKind, List<Record> records) {
        this.id = id;
        this.dataKind = dataKind;
        this.records = records;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataKind getDataKind() {
        return dataKind;
    }

    public void setDataKind(DataKind dataKind) {
        this.dataKind = dataKind;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
