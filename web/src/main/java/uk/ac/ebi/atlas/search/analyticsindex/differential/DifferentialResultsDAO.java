package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.QueryBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialResultsDAO extends DifferentialAnalyticsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialResultsDAO.class);

    private static final int ROWS = 1000;
    private static final String SORT_FIELD = "&sort=abs(foldChange)desc";

    private final QueryBuilder queryBuilder = new QueryBuilder();

    @Inject
    public DifferentialResultsDAO(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl, @Value("classpath:differential.facets.query.json") Resource differentialFacetsQueryJSON) {
        super(restTemplate, solrBaseUrl, differentialFacetsQueryJSON);  // settings of restTemplate in applicationContext.xml
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        ImmutableList.Builder<Pair<String, SemanticQuery>> searchQueriesBuilder = ImmutableList.builder();
        searchQueriesBuilder.add(Pair.of(IDENTIFIER_SEARCH_FIELD, geneQuery));
        searchQueriesBuilder.add(Pair.of(CONDITION_SEARCH_FIELD, conditionQuery));
        searchQueriesBuilder.add(Pair.of(SPECIES_FIELD, SemanticQuery.create(species)));
        return fetchDifferentialResultsAboveDefaultFoldChange(queryBuilder.buildSolrQuery(searchQueriesBuilder.build
                ()));
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForIdentifier(String identifier) {
        return fetchDifferentialResultsAboveDefaultFoldChange(queryBuilder.buildSolrQuery(identifier,
                BIOENTITY_IDENTIFIER_FIELD));
    }

    private String fetchDifferentialResultsAboveDefaultFoldChange(String q) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialResultsSortedAboveDefaultFoldChangeUrl(q));

        stopwatch.stop();
        LOGGER.debug("q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }

    private String buildDifferentialResultsSortedAboveDefaultFoldChangeUrl(String q) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;
        return solrBaseUrl + buildQueryParameters(query, ROWS, DEFAULT_P_VALUE) + SORT_FIELD;
    }
}
