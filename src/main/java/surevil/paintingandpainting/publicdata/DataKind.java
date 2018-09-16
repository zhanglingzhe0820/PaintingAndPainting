package surevil.paintingandpainting.publicdata;

public enum DataKind {
    RAW(1),
    PERFECT(2);

    private int index;

    DataKind(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
