package uk.ac.ebi.atlas.search;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;
import static uk.ac.ebi.atlas.search.SemanticQuery.isNotEmpty;

public class SearchDescription {

    public static String get(SemanticQuery geneQuery) {
        return get(geneQuery, SemanticQuery.create(), "");
    }

    public static String get(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        if (isEmpty(geneQuery) && isEmpty(conditionQuery)) {
            return "default query";
        }

        StringBuilder stringBuilder = new StringBuilder();

        if (isNotEmpty(geneQuery)) {
            String geneQueryString = geneQuery.description();

            if (geneQuery.size() > 1 && isNotEmpty(conditionQuery)) {
                stringBuilder.append("(").append(geneQueryString).append(")");
            } else {
                stringBuilder.append(geneQueryString);
            }
        }

        if (isNotEmpty(geneQuery) && isNotEmpty(conditionQuery)) {
            stringBuilder.append(" AND ");
        }

        if (isNotEmpty(conditionQuery)) {
            String conditionQueryString = conditionQuery.description();

            if (conditionQuery.size() > 1 && isNotEmpty(geneQuery)) {
                stringBuilder.append("(").append(conditionQueryString).append(")");
            } else {
                stringBuilder.append(conditionQueryString);
            }
        }

        if ((isNotEmpty(geneQuery) || isNotEmpty(conditionQuery)) && isNotBlank(species)) {
            stringBuilder.append(" AND ");
        }

        if (isNotBlank(species)) {
            stringBuilder.append(StringUtils.capitalize(species));
        }

        return stringBuilder.toString();
    }
}
