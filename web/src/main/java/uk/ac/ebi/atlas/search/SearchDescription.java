package uk.ac.ebi.atlas.search;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SearchDescription {
    public static String get(SemanticQuery geneQuery) {
        return get(geneQuery, SemanticQuery.create(), "");
    }

    public static String get(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        StringBuilder stringBuilder = new StringBuilder();

        if (geneQuery.isNotEmpty()) {
            String geneQueryString = geneQuery.asAnalyticsIndexQueryClause();

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
            String conditionQueryString = conditionQuery.asAnalyticsIndexQueryClause();

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
            stringBuilder.append(species);
        }

        return stringBuilder.toString();
    }

    public static String getSimple(SemanticQuery geneQuery) {
        return getSimple(geneQuery, SemanticQuery.create(), "");
    }

    public static String getSimple(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
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
            stringBuilder.append(species);
        }

        return stringBuilder.toString();
    }

    public static String getRaw(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("geneQuery = ").append(geneQuery.toJson()).append(", ");
        stringBuilder.append("conditionQuery = ").append(conditionQuery.toJson()).append(", ");
        stringBuilder.append("species = ").append(species);

        return stringBuilder.toString();
    }
}
