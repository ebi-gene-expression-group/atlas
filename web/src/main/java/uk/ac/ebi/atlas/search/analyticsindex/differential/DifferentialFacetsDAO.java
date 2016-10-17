package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialFacetsDAO extends DifferentialAnalyticsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialFacetsDAO.class);

    private static final int ROWS = 0;

    @Inject
    public DifferentialFacetsDAO(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl, @Value("classpath:differential.facets.query.json") Resource differentialFacetsQueryJSON) {
        super(restTemplate, solrBaseUrl, differentialFacetsQueryJSON);  // settings of restTemplate in applicationContext.xml
    }

    public String fetchFacetsAboveDefaultFoldChangeForSearch(SemanticQuery query) {
        AnalyticsQueryBuilder analyticsQueryBuilder =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(query)
                        .queryConditionsSearch(query)
                        .useOr();

        return fetchFacetsAboveFoldChange(analyticsQueryBuilder.build().getQuery(), DEFAULT_P_VALUE);
    }

    public String fetchFacetsAboveDefaultFoldChangeForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        AnalyticsQueryBuilder analyticsQueryBuilder =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species);

        return fetchFacetsAboveFoldChange(analyticsQueryBuilder.build().getQuery(), DEFAULT_P_VALUE);
    }

    private String fetchFacetsAboveFoldChange(String q, double pValue) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String result = fetchResponseAsString(buildDifferentialFacetsAboveFoldChangeQueryUrl(q, pValue));
        stopwatch.stop();
        LOGGER.debug("q={} foldChange={}/{} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }

    private String buildDifferentialFacetsAboveFoldChangeQueryUrl(String q, double pValue) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;
        return solrBaseUrl + buildQueryParameters(query, ROWS, pValue) + differentialGeneFacetsQuery;
    }
}
