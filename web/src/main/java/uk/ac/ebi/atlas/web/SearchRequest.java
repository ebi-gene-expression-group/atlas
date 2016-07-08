package uk.ac.ebi.atlas.web;

import com.google.common.base.Throwables;

import java.io.UnsupportedEncodingException;

public class SearchRequest {

    protected GeneQuery geneQuery = getDefaultGeneQuery();

    protected GeneQuery getDefaultGeneQuery() {
        return GeneQuery.create();
    }

    public GeneQuery getGeneQuery() {
        return geneQuery;
    }

    public void setGeneQuery(String geneQueryString) {
        try {
            this.geneQuery = GeneQuery.fromUrlEncodedJson(geneQueryString);
        } catch (UnsupportedEncodingException e) {
            throw Throwables.propagate(e);
        }

    }

    public boolean hasGeneQuery() {
        return !geneQuery.isEmpty();
    }

}
