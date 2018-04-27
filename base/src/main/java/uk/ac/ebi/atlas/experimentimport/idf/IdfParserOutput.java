package uk.ac.ebi.atlas.experimentimport.idf;

import uk.ac.ebi.atlas.model.Publication;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IdfParserOutput {

    private String title;
    // Map of Pubmed IDs and publication titles
    private List<Publication> publications;
    // Only expected for single cell
    private int expectedClusters;

    public IdfParserOutput(String title, List<Publication> publications, int expectedClusters) {
        this.title = title;
        this.publications = publications;
        this.expectedClusters = expectedClusters;
    }

    public String getTitle() {
        return title;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public int getExpectedClusters() {
        return expectedClusters;
    }

    public Set<String> getPubmedIds() {
        return publications.stream().map(Publication::getPubmedId).collect(Collectors.toSet());
    }

    public Set<String> getDois() {
        return publications.stream().map(Publication::getDoi).collect(Collectors.toSet());
    }
}
