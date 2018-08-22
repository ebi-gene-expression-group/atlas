package uk.ac.ebi.atlas.search;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SearchDescription {
    protected SearchDescription() {
        throw new UnsupportedOperationException();
    }

    public static String get(SemanticQuery geneQuery) {
        return get(geneQuery, SemanticQuery.create(), "");
    }

    public static String get(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        if (geneQuery.isEmpty() && conditionQuery.isEmpty()) {
            return "default query";
        }

        StringBuilder stringBuilder = new StringBuilder();

        if (geneQuery.isNotEmpty()) {
            String geneQueryString = geneQuery.description();

            if (geneQuery.size() > 1 && conditionQuery.isNotEmpty()) {
                stringBuilder.append("(").append(geneQueryString).append(")");
            } else {
                stringBuilder.append(geneQueryString);
            }
        }

        if (geneQuery.isNotEmpty() && conditionQuery.isNotEmpty()) {
            stringBuilder.append(" AND ");
        }

        if (conditionQuery.isNotEmpty()) {
            String conditionQueryString = conditionQuery.description();

            if (conditionQuery.size() > 1 && geneQuery.isNotEmpty()) {
                stringBuilder.append("(").append(conditionQueryString).append(")");
            } else {
                stringBuilder.append(conditionQueryString);
            }
        }

        if ((geneQuery.isNotEmpty() || conditionQuery.isNotEmpty()) && isNotBlank(species)) {
            stringBuilder.append(" AND ");
        }

        if (isNotBlank(species)) {
            stringBuilder.append(StringUtils.capitalize(species));
        }

        return stringBuilder.toString();
    }
}
