package uk.ac.ebi.atlas.geneannotation;

public enum BioEntityType {
    GENE("gene"),
    MIRNA("miRNA");

    private String name;

    BioEntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
