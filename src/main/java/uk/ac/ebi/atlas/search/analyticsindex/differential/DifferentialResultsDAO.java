package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialResultsDAO extends DifferentialAnalyticsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialResultsDAO.class);

    private static final int ROWS = 1000;
    private static final String SORT_FIELD = "&sort=abs(foldChange)desc";
    private final AnalyticsQueryFactory analyticsQueryFactory;

    @Inject
    public DifferentialResultsDAO(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl,
                                  AnalyticsQueryFactory analyticsQueryFactory,@Value("classpath:differential.facets.query.json") Resource differentialFacetsQueryJSON) {
        super(restTemplate, solrBaseUrl, differentialFacetsQueryJSON);  // settings of restTemplate in applicationContext.xml
        this.analyticsQueryFactory = analyticsQueryFactory;
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForSearch(SemanticQuery query) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .differentialOnly()
                        .queryIdentifierOrConditionsSearch(query)
                .build();

        return fetchDifferentialResultsAboveDefaultFoldChange(solrQuery.getQuery());
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .differentialOnly()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                .build();

        return fetchDifferentialResultsAboveDefaultFoldChange(solrQuery.getQuery());
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
