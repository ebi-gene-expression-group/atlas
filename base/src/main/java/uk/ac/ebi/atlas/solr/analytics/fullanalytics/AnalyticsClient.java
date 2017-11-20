package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsClient {

    private final RestTemplate restTemplate;
    private final String solrServerUrl;

    @Inject
    public AnalyticsClient(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrServerUrl) {
        this.restTemplate = restTemplate;
        this.solrServerUrl = solrServerUrl;
    }

    protected String fetchResponseAsString(SolrQuery query) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> request = new HttpEntity<>(query.toString(), headers);
            return restTemplate.postForObject(solrServerUrl + "query", request, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
