package uk.ac.ebi.atlas.web;

import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;

public class GeneQuerySearchRequestParameters extends SearchRequest {
    private String condition;

    private String organism;

    public String getCondition() {
        return condition;
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
