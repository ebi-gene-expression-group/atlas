package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

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
public class BioentityIdentifiersDao {

    private static final Logger LOGGER = Logger.getLogger(BioentityIdentifiersDao.class);

    private final RestTemplate restTemplate;

    private final String solrBaseUrl;
    private final String QUERY_TEMPLATE = "query?q={0}&rows=0&omitHeader=true";
    private final String bioentityIdentifiersFacetedQuery;

    @Inject
    public BioentityIdentifiersDao(RestTemplate restTemplate, @Value("#{configuration['solr.analytics.base.url']}") String solrBaseUrl, String bioentityIdentifiersFacetedQuery) {
        this.restTemplate = restTemplate;
        this.solrBaseUrl = solrBaseUrl;
        this.bioentityIdentifiersFacetedQuery = "&json.facet=" + encodeQueryParam(bioentityIdentifiersFacetedQuery);
    }


    public String fetchBioentityIdentifiers(String experimentAccession) {
        return fetchFacets(buildExperimentAccessionQuery(experimentAccession));
    }


    public String fetchAllBioentityIdentifiers() {
        return fetchFacets("*:*");
    }


    private String buildExperimentAccessionQuery(String experimentAccession) {
        return experimentAccession.isEmpty() ? "" : String.format("experimentAccession:\"%s\"", experimentAccession);
    }


    private String fetchFacets(String q) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String result = fetchResponseAsString(buildQueryUrl(q));

        stopwatch.stop();

        LOGGER.debug(String.format("fetchFacets q=%s took %.2f seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));
        return result;

    }


    private String buildQueryUrl(String q) {
        return solrBaseUrl + buildQueryParameters(q) + bioentityIdentifiersFacetedQuery;
    }


    private String buildQueryParameters(String q) {
        return MessageFormat.format(QUERY_TEMPLATE, encodeQueryParam(q));
    }


    private String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw new BioentityIdentifiersDaoException(e);
        }
    }


    private static String encodeQueryParam(String param) {
        try {
            return UriUtils.encodeQueryParam(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BioentityIdentifiersDaoException(e);
        }
    }


    private static class BioentityIdentifiersDaoException extends RuntimeException {
        public BioentityIdentifiersDaoException(Exception e) {
            super(e);
        }
    }

}
