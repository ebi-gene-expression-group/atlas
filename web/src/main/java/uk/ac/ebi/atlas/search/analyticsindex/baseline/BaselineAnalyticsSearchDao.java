package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Named
public class BaselineAnalyticsSearchDao {

    private static final Logger LOGGER = Logger.getLogger(BaselineAnalyticsSearchDao.class);

    public static final double DEFAULT_CUT_OFF = 0.5;
    private final RestTemplate restTemplate;

    private final String solrBaseUrl;
    private static final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private static final String FQ_TEMPLATE = "&fq=expressionLevel:[{0} TO *]";
    private final String analyticsSearchJsonFacet;

    @Inject
    public BaselineAnalyticsSearchDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, String analyticsSearchJsonFacet) {
        this.restTemplate = restTemplate;
        this.solrBaseUrl = solrBaseUrl;
        this.analyticsSearchJsonFacet = "&json.facet=" + encodeQueryParam(analyticsSearchJsonFacet);
    }

    public String fetchExpressionLevelFaceted(String geneQuery) {
        return fetchFacets("identifierSearch:" + geneQuery, DEFAULT_CUT_OFF);
    }

    public String fetchFacetsThatHaveExpression(String geneQuery) {
        //if needed, could improve perf by getting counts only, and not sum(expressionLevel) or unique(bioentity_identifier)
        return fetchExpressionLevelFaceted(geneQuery);
    }

    public String fetchExpressionLevelFaceted(String geneQuery, String defaultQueryFactorType) {
        return fetchFacets(String.format("identifierSearch:%s AND defaultQueryFactorType:%s", geneQuery, defaultQueryFactorType), DEFAULT_CUT_OFF);
    }

    String fetchFacets(String q, double cutOff) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildQueryUrl(q, cutOff));

        stopwatch.stop();

        LOGGER.debug(String.format("fetchFacets q=%s cutOff=%s took %.2f seconds", q, cutOff, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));
        return result;

    }

    String buildQueryUrl(String q, double cutOff) {
        return solrBaseUrl + buildQueryParameters(q, cutOff) + analyticsSearchJsonFacet;
    }

    String buildQueryParameters(String q, double cutOff) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q)) + encodeQuery(MessageFormat.format(FQ_TEMPLATE, cutOff));
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

    private static String encodeQuery(String s) {
        // doesn't encode =
        try {
            return UriUtils.encodeQuery(s, "UTF-8");
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
