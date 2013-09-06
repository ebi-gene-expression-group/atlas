package uk.ac.ebi.atlas.solr.query.conditions;

public class IndexedContrast {

    private String experimentAccession;

    private String contrastId;

    public IndexedContrast(String experimentAccession, String contrastId) {
        this.experimentAccession = experimentAccession;
        this.contrastId = contrastId;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getContrastId() {
        return contrastId;
    }
}

