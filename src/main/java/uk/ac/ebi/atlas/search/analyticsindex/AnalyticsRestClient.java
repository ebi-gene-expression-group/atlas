package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Named
public class AnalyticsRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsRestClient.class);


    private final RestTemplate restTemplate;
    private final String solrBaseUrl;

    @Inject
    AnalyticsRestClient(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl) {
        this.restTemplate = restTemplate;
        this.solrBaseUrl = solrBaseUrl;
    }

    public String fetchResults(SolrQuery q) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String result = fetchResponseAsString(MessageFormat.format("{0}query?{1}", solrBaseUrl, q.toString()));
        stopwatch.stop();

        LOGGER.debug("fetchResults q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return result;
    }


    String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw Throwables.propagate(e);
        }
    }
}
