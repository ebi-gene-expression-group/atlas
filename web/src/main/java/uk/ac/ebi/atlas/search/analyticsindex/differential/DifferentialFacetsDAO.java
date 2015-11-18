package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialFacetsDAO extends DifferentialAnalyticsDAO {

    private static final Logger LOGGER = LogManager.getLogger(DifferentialFacetsDAO.class);

    private static final int ROWS = 0;

    @Inject
    public DifferentialFacetsDAO(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, @Value("classpath:differential.facets.query.json") Resource differentialFacetsQueryJSON) {
        super(restTemplate, solrBaseUrl, differentialFacetsQueryJSON);
    }

    public String fetchFacetsAboveDefaultFoldChangeForSearch(GeneQuery geneQuery) {
        String identifierSearch = buildSolrQuery(geneQuery, IDENTIFIER_SEARCH_FIELD);
        return fetchFacetsAboveFoldChange(identifierSearch, DEFAULT_NEGATIVE_FOLD_CHANGE, DEFAULT_POSITIVE_FOLD_CHANGE);
    }

    public String fetchFacetsAboveDefaultFoldChangeForIdentifier(GeneQuery geneQuery) {
        String identifierSearch = buildSolrQuery(geneQuery, BIOENTITY_IDENTIFIER_FIELD);
        return fetchFacetsAboveFoldChange(identifierSearch, DEFAULT_NEGATIVE_FOLD_CHANGE, DEFAULT_POSITIVE_FOLD_CHANGE);
    }

    private String fetchFacetsAboveFoldChange(String q, double negativeFoldChange, double positiveFoldChange) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String result = fetchResponseAsString(buildDifferentialFacetsAboveFoldChangeQueryUrl(q, negativeFoldChange, positiveFoldChange));
        stopwatch.stop();
        LOGGER.debug(String.format("q=%s foldChange=%s/%s took %.2f seconds", q, negativeFoldChange, positiveFoldChange, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return result;
    }

    private String buildDifferentialFacetsAboveFoldChangeQueryUrl(String q, double negativeFoldChange, double positiveFoldChange) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;
        return solrBaseUrl + buildQueryParameters(query, ROWS, negativeFoldChange, positiveFoldChange) + differentialGeneFacetsQuery;
    }
}
