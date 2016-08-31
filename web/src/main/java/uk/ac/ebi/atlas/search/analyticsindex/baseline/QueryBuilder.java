package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.search.SemanticQuery;

public class QueryBuilder {

    public String buildSolrQuery(Iterable<Pair<String, SemanticQuery>> searchQueries) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Pair<String, SemanticQuery> searchQuery : searchQueries) {
            if (searchQuery.getRight().isNotEmpty()) {
                stringBuilder.append(String.format("%s:(%s)", searchQuery.getLeft(), searchQuery.getRight().asAnalyticsIndexQueryClause()));
                stringBuilder.append(" AND ");
            }
        }

        if (stringBuilder.lastIndexOf(" AND ") > 0) {
            stringBuilder.delete(stringBuilder.lastIndexOf(" AND "), stringBuilder.length());
        }

        return stringBuilder.toString();
    }

}
