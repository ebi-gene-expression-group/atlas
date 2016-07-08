package uk.ac.ebi.atlas.web;

import com.google.common.base.Objects;

public class SearchRequest {

    private GeneQuery geneQuery = getDefaultGeneQuery();

    protected GeneQuery getDefaultGeneQuery() {
        return GeneQuery.create();
    }

    public void setGeneQuery(GeneQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .toString();
    }

    public boolean hasGeneQuery() {
        return !geneQuery.isEmpty();
    }

}
