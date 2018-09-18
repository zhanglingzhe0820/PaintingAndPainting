package surevil.paintingandpainting.publicdata;

public enum DataKind {
    RAW("RAW"),
    PERFECT("PERFECT");

    private String name;

    DataKind(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
