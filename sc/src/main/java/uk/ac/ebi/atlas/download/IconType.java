package uk.ac.ebi.atlas.download;

public enum IconType {

    TSV("icon-tsv"),
    ARRAY_EXPRESS("icon-ae"),
    REACTOME("icon-gsea-reactome"),
    INTERPRO("icon-gsea-interpro"),
    R_DATA("icon-Rdata"),
    EXPERIMENT_DESIGN("icon-experiment-design"),
    GENE_ONTOLOGY("icon-gsea-go"),
    MA_PLOT("icon-ma");

    private final String name;

    IconType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
