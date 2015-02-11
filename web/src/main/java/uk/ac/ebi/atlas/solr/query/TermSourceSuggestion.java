package uk.ac.ebi.atlas.solr.query;


public class TermSourceSuggestion {
    String term;
    String source;

    public TermSourceSuggestion(String term, String source) {
        this.term = term;
        this.source = source;
    }

}
