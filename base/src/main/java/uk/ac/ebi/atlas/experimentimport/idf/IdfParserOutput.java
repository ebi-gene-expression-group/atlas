package uk.ac.ebi.atlas.experimentimport.idf;

import java.util.Map;

public class IdfParserOutput {

    private String title;
    // Map of Pubmed IDs and publication titles
    private Map<String, String> publications;
    // Only expected for single cell
    private int expectedClusters;

    public IdfParserOutput(String title, Map<String, String> publications, int expectedClusters) {
        this.title = title;
        this.publications = publications;
        this.expectedClusters = expectedClusters;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, String> getPublications() {
        return publications;
    }

    public int getExpectedClusters() {
        return expectedClusters;
    }
}
