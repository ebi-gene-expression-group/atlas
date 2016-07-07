
package uk.ac.ebi.atlas.web;

import com.google.common.base.Objects;

public class SearchRequest {

    private OldGeneQuery geneQuery = getDefaultGeneQuery();
    private SemanticQuery semanticQuery = getDefaultSemanticQuery();

    protected OldGeneQuery getDefaultGeneQuery() {
        return OldGeneQuery.EMPTY;
    }

    protected SemanticQuery getDefaultSemanticQuery() {
        return SemanticQuery.create();
    }

    public OldGeneQuery getGeneQuery() {
        return this.geneQuery;
    }

    public SemanticQuery getSemanticQuery() {
        return this.semanticQuery;
    }

    public void setGeneQuery(OldGeneQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

    public void setSemanticQuery(SemanticQuery semanticQuery) {
        this.semanticQuery = semanticQuery;
    }

    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .toString();
    }

    public boolean hasGeneQuery() {
        return !geneQuery.isEmpty();
    }

    public boolean hasSemanticQuery() {
        return !semanticQuery.isEmpty();
    }
}
