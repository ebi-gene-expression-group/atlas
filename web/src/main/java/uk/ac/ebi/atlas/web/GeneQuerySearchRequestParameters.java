package uk.ac.ebi.atlas.web;

import org.apache.commons.lang.StringUtils;

public class GeneQuerySearchRequestParameters extends SearchRequest {
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean hasCondition() {
        return StringUtils.isNotBlank(condition);
    }
}
