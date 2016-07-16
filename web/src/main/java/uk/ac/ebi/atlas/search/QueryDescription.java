package uk.ac.ebi.atlas.search;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class QueryDescription {
    public static String get(GeneQuery geneQuery, ConditionQuery conditionQuery, String species) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!geneQuery.isEmpty()) {
            stringBuilder.append(geneQuery.toJson());
        }

        if (!geneQuery.isEmpty() && !conditionQuery.isEmpty()) {
            stringBuilder.append(" OR ");
        }

        if (!conditionQuery.isEmpty()) {
            stringBuilder.append(conditionQuery.asString());
        }

        if (!geneQuery.isEmpty() && !conditionQuery.isEmpty() && !isBlank(species)) {
            stringBuilder.insert(0, "(");
            stringBuilder.append(") AND ");
        }

        if (!isBlank(species)) {
            stringBuilder.append(species);
        }

        return stringBuilder.toString();
    }

    public static String getRaw(GeneQuery geneQuery, ConditionQuery conditionQuery, String species) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("geneQuery = ").append(geneQuery.toJson()).append(", ");
        stringBuilder.append("conditionQuery = ").append(conditionQuery.asString()).append(", ");
        stringBuilder.append("species = ").append(species);

        return stringBuilder.toString();
    }
}
