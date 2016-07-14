package uk.ac.ebi.atlas.web;

public class SearchRequest {

    private GeneQuery geneQuery = getDefaultGeneQuery();

    protected GeneQuery getDefaultGeneQuery() {
        return GeneQuery.create();
    }

    public GeneQuery getGeneQuery() {
        return this.geneQuery;
    }

    public void setGeneQuery(GeneQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

    public boolean hasGeneQuery() {
        return !geneQuery.isEmpty();
    }
}
