package uk.ac.ebi.atlas.search;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SearchDescription {
    public static String get(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        StringBuilder stringBuilder = new StringBuilder();

        if (geneQuery.isNotEmpty()) {
            stringBuilder.append(geneQuery.asSolr1DNF());
        }

        if (geneQuery.isNotEmpty() && conditionQuery.isNotEmpty()) {
            stringBuilder.append(" AND ");
        }

        if (conditionQuery.isNotEmpty()) {
            stringBuilder.append(conditionQuery.asSolr1DNF());
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
