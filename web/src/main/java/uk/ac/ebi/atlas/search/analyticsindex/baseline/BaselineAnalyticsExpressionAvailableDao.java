package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Named
public class BaselineAnalyticsExpressionAvailableDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsExpressionAvailableDao.class);

    private final RestTemplate restTemplate;

    private final String solrBaseUrl;
    private static final String QUERY_TEMPLATE = "query?q={0}&rows=1&omitHeader=true";
    public static final String BASELINE_ONLY = "experimentType:(rnaseq_mrna_baseline OR proteomics_baseline)";
    public static final String TISSUES_ONLY = "defaultQueryFactorType:ORGANISM_PART";

    //TODO This class is very similar to BaselineAnalyticsSearchDao, maybe they could be refactored
    @Inject
    public BaselineAnalyticsExpressionAvailableDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl) {
        this.restTemplate = restTemplate;
        this.solrBaseUrl = solrBaseUrl;
    }

    public String fetchGenesInTissuesAboveCutoff(GeneQuery geneQuery) {
        String identifierSearchQuery = buildGeneIdentifierQuery(geneQuery);
        return fetchResults(identifierSearchQuery);
    }


    String buildGeneIdentifierQuery(GeneQuery geneQuery) {
        return geneQuery.isEmpty() ? "" : String.format("identifierSearch:(\"%s\")", StringUtils.join(geneQuery.terms(), "\" OR \""));
    }


    String fetchResults(String q) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildQueryUrl(q));

        stopwatch.stop();

        LOGGER.debug("fetchResults q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }


    String buildQueryUrl(String q) {
        String query = q.isEmpty() ? BASELINE_ONLY : q + " AND " + BASELINE_ONLY + " AND " + TISSUES_ONLY;
        return solrBaseUrl + buildQueryParameters(query);
    }


    String buildQueryParameters(String q) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q));
    }


    String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw new BaselineAnalyticsSearchDaoException(e);
        }
    }


    private static String encodeQueryParam(String param) {
        try {
            return UriUtils.encodeQueryParam(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaselineAnalyticsSearchDaoException(e);
        }
    }


    private static class BaselineAnalyticsSearchDaoException extends RuntimeException {
        public BaselineAnalyticsSearchDaoException(Exception e) {
            super(e);
        }
    }

}
