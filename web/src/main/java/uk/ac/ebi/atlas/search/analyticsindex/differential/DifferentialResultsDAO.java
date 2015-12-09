package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
public class DifferentialResultsDAO extends DifferentialAnalyticsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialResultsDAO.class);

    private static final int ROWS = 1000000;
    private static final String SORT_FIELD = "&sort=abs(foldChange)desc";

    @Inject
    public DifferentialResultsDAO(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, @Value("classpath:differential.facets.query.json") Resource differentialFacetsQueryJSON) {
        super(restTemplate, solrBaseUrl, differentialFacetsQueryJSON);  // settings of restTemplate in applicationContext.xml
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForSearch(GeneQuery geneQuery) {
        String identifierSearch = buildSolrQuery(geneQuery, IDENTIFIER_SEARCH_FIELD);
        return fetchDifferentialResultsAboveDefaultFoldChange(identifierSearch);
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForSearch(GeneQuery geneQuery, List<String> species, List<String> experimentType, List<String> kingdom,
                                                                          List<String> factors, List<Integer> numReplicates, String regulation) {
        String identifierSearch = buildSolrQuery(geneQuery, IDENTIFIER_SEARCH_FIELD);
        return fetchDifferentialResultsAboveDefaultFoldChange(identifierSearch, species, experimentType, kingdom, factors, numReplicates, regulation);
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForIdentifier(GeneQuery geneQuery) {
        String identifierSearch = buildSolrQuery(geneQuery, BIOENTITY_IDENTIFIER_FIELD);
        return fetchDifferentialResultsAboveDefaultFoldChange(identifierSearch);
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForIdentifier(GeneQuery geneQuery, List<String> species, List<String> experimentType, List<String> kingdom,
                                                                          List<String> factors, List<Integer> numReplicates, String regulation) {
        String identifierSearch = buildSolrQuery(geneQuery, BIOENTITY_IDENTIFIER_FIELD);
        return fetchDifferentialResultsAboveDefaultFoldChange(identifierSearch, species, experimentType, kingdom, factors, numReplicates, regulation);
    }


    private String fetchDifferentialResultsAboveDefaultFoldChange(String q) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialResultsSortedAboveDefaultFoldChangeUrl(q));

        stopwatch.stop();
        LOGGER.debug("q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }

    private String fetchDifferentialResultsAboveDefaultFoldChange(String q, List<String> species, List<String> experimentType, List<String> kingdom,
                                                                            List<String> factors, List<Integer> numReplicates, String regulation) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildDifferentialResultsSortedAboveDefaultFoldChangeUrl(q, species, experimentType, kingdom, factors, numReplicates, regulation));

        stopwatch.stop();
        LOGGER.debug("fetchDifferentialGeneResults q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }

    private String buildParamsQuery(List<String> params, String name) {
        return (params != null && !params.isEmpty()) ? String.format(" AND %s :(\"%s\")", name, StringUtils.join(params, "\" OR \"")) : "";
    }

    private String buildDifferentialResultsSortedAboveDefaultFoldChangeUrl(String q) {
        String query = q.isEmpty() ? DIFFERENTIAL_ONLY : q + " AND " + DIFFERENTIAL_ONLY;
        return solrBaseUrl + buildQueryParameters(query, ROWS, DEFAULT_NEGATIVE_FOLD_CHANGE, DEFAULT_POSITIVE_FOLD_CHANGE) + SORT_FIELD;
    }

    private String buildDifferentialResultsSortedAboveDefaultFoldChangeUrl(String q, List<String> species, List<String> experimentTypes, List<String> kingdoms,
                                                                           List<String> factors, List<Integer> numReplicates, String regulation) {
        String query;

        if(q.isEmpty() && experimentTypes == null) {
            query = DIFFERENTIAL_ONLY;
        } else if (!q.isEmpty() && experimentTypes == null) {
            query = q + " AND " + DIFFERENTIAL_ONLY;
        } else {
            query = q + buildParamsQuery(experimentTypes, "experimentType");
        }

        query = query + buildParamsQuery(species, "species");
        query = query + buildParamsQuery(factors, "factors");
        query = query + buildParamsQuery(kingdoms, "kingdom");

        if(numReplicates != null && numReplicates.size()!=0) {
            query = query + String.format(" AND numReplicates:(\"%s\")", StringUtils.join(numReplicates, "\" OR \""));
        }
        if(StringUtils.isNotBlank(regulation)) {
            query = query + " AND regulation:" + regulation;
        }

        return solrBaseUrl + buildQueryParameters(query, ROWS, DEFAULT_NEGATIVE_FOLD_CHANGE, DEFAULT_POSITIVE_FOLD_CHANGE) + SORT_FIELD;
    }
}
