package uk.ac.ebi.atlas.web;

import com.google.common.base.Objects;
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

    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        if (hasGeneQuery()) {
            stringBuilder.append(getGeneQuery());

            if (hasCondition()) {
                stringBuilder.append(" AND ");
            }
        }

        if (hasCondition()) {
            stringBuilder.append(getCondition());
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneQuery", getGeneQuery())
                .add("exactMatch", isExactMatch())
                .add("condition", condition)
                .toString();
    }
}
