package uk.ac.ebi.atlas.web;

public class QuerySearchRequestParameters extends SearchRequest {
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
