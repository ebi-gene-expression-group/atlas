package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.search.SemanticQuery;

public class SearchRequest {

    private SemanticQuery geneQuery = getDefaultGeneQuery();

    protected SemanticQuery getDefaultGeneQuery() {
        return SemanticQuery.create();
    }

    public SemanticQuery getGeneQuery() {
        return this.geneQuery;
    }

    public void setGeneQuery(SemanticQuery geneQuery) {
        this.geneQuery = geneQuery;
    }
    
}
