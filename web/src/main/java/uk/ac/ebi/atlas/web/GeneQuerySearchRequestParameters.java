package uk.ac.ebi.atlas.web;

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

    private String trimCondition(String condition) {
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
            stringBuilder.append(String.format("(%s)", getGeneQuery().asSolr1DNF()));

            if (hasCondition()) {
                stringBuilder.append(" AND ");
                stringBuilder.append(getCondition());
            }

            if (hasOrganism()) {
                stringBuilder.append(" AND ");
                stringBuilder.append(organism);
            }
        }

        else if (hasCondition()) {
            stringBuilder.append(getCondition());
            if (hasOrganism()) {
                stringBuilder.append(" AND ");
                stringBuilder.append(organism);
            }
        }

        return stringBuilder.toString();
    }
}
