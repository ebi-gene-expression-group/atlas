package uk.ac.ebi.atlas.geneannotation.arraydesign;

public enum ArrayDesignType {
    ENSEMBL("ensembl"),
    MICRO_RNA("microRNA");

    private String name;

    private ArrayDesignType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
