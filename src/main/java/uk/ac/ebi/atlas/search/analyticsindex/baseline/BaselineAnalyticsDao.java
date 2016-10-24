package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static uk.ac.ebi.atlas.utils.ResourceUtils.readPlainTextResource;

public class BaselineAnalyticsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsDao.class);

    private static final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private static final String FQ_TEMPLATE = "&fq=experimentType:(rnaseq_mrna_baseline OR proteomics_baseline)";

    private final RestTemplate restTemplate;
    private final String solrBaseUrl;
    private final String baselineFacetsQuery;

    public BaselineAnalyticsDao(RestTemplate restTemplate, String solrBaseUrl, Resource baselineFacetsQueryJSON) {
        this(restTemplate,solrBaseUrl, "&json.facet=" + encodeQueryParam(readPlainTextResource
                (baselineFacetsQueryJSON).replaceAll("\\s+","")));
    }

    BaselineAnalyticsDao(RestTemplate restTemplate, String solrBaseUrl, String baselineFacetsQuery) {
        this.restTemplate = restTemplate;   // settings of restTemplate in applicationContext.xml
        this.solrBaseUrl = solrBaseUrl;
        this.baselineFacetsQuery = baselineFacetsQuery;
    }

    public String fetchResults(String q) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String result = fetchResponseAsString(buildQueryUrl(q));
        stopwatch.stop();

        LOGGER.debug("fetchResults q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }


    String buildQueryUrl(String query) {
        return solrBaseUrl +
                buildQueryParameters(query.isEmpty() ? "*:*" : query) +
                baselineFacetsQuery;
    }

    String buildQueryParameters(String q) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q)) + encodeQuery(FQ_TEMPLATE);
    }

    String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw new BaselineAnalyticsDaoException(e);
        }
    }

    protected static String encodeQueryParam(String param) {
        try {
            return UriUtils.encodeQueryParam(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaselineAnalyticsDaoException(e);
        }
    }

    private static String encodeQuery(String s) {
        // doesn't encode =
        try {
            return UriUtils.encodeQuery(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BaselineAnalyticsDaoException(e);
        }
    }



    private static class BaselineAnalyticsDaoException extends RuntimeException {
        public BaselineAnalyticsDaoException(Exception e) {
            super(e);
        }
    }

}
