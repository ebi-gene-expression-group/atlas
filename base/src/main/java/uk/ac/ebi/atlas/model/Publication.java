package uk.ac.ebi.atlas.model;

public class Publication {

    private String pubmedId;
    private String doi;
    private String title;

    public Publication(String pubmedId, String doi, String title) {
        this.pubmedId = pubmedId;
        this.doi = doi;
        this.title = title;
    }

    public String getPubmedId() {
        return pubmedId;
    }

    public String getDoi() {
        return doi;
    }

    public String getTitle() {
        return title;
    }

}
