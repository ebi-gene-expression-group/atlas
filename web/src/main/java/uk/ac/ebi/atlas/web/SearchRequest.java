package uk.ac.ebi.atlas.web;

import java.util.Date;

public class SearchRequest {

    private GeneQuery geneQuery = getDefaultGeneQuery();
    private Date blah;

    protected GeneQuery getDefaultGeneQuery() {
        return GeneQuery.create();
    }

    public GeneQuery getGeneQuery() {
        return this.geneQuery;
    }

    public void setGeneQuery(GeneQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

//    public String toString() {
//        return Objects.toStringHelper(this.getClass())
//                .add("geneQuery", geneQuery)
//                .toString();
//    }

    public boolean hasGeneQuery() {
        return !geneQuery.isEmpty();
    }

    public Date getBlah() {
        return blah;
    }

    public void setBlah(String blahString) {
        this.blah = new Date(blahString);
    }

}
