package uk.ac.ebi.atlas.web;

import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.search.ConditionQuery;

public class GeneQuerySearchRequestParameters extends SearchRequest {
    private String condition;

    private String organism;

    @Deprecated // use getConditionQuery
    public String getCondition() {
        return trimCondition(condition);
    }

    public ConditionQuery getConditionQuery() {
        return ConditionQuery.create(condition);
    }

    public void setCondition(String condition) {
        this.condition = TagEditorConverter.tagsToQueryString(condition);
    }

    public boolean hasCondition() {
        return StringUtils.isNotBlank(condition);
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        if (!"Any".equals(organism)) {
            this.organism = organism;
        }
    }

    public String trimCondition(String condition) {
        if(StringUtils.isNotBlank(condition)) {
            String trimmedCondition = condition.trim();
            return trimmedCondition.replace("\"", "");
        }
        return condition;
    }

    public boolean hasOrganism() {
        return (StringUtils.isNotBlank(organism) && !organism.equals("Any"));
    }

    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        if (hasGeneQuery()) {
            stringBuilder.append(getGeneQuery());

            if (hasCondition()) {
                stringBuilder.append(" AND ");
                stringBuilder.append(getCondition());
            }

            if (hasOrganism()) {
                stringBuilder.append(" AND ");
                stringBuilder.append(getOrganism());
            }
        }

        else if (hasCondition()) {
            stringBuilder.append(getCondition());
            if (hasOrganism()) {
                stringBuilder.append(" AND ");
                stringBuilder.append(getOrganism());
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneQuery", getGeneQuery())
                .add("exactMatch", isExactMatch())
                .add("condition", condition)
                .add("organism", organism)
                .toString();
    }
}
