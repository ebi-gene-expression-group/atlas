package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 * Date: 14/03/2014
 */

@Named
public class OLSClient {

    private static final Logger LOGGER = Logger.getLogger(OLSClient.class);

    @Value("#{configuration['ols.query.url']}")
    private String olsServiceURL;

    private RestTemplate restTemplate;

    @Inject
    public OLSClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValid(String ontologyTerm) {
        try{

            if(ontologyTerm == null || ontologyTerm.isEmpty()) return false;

            String xmlResponse = restTemplate.getForObject(olsServiceURL, String.class, ontologyTerm);

            return StringUtils.containsIgnoreCase(xmlResponse, ontologyTerm);


        } catch (RestClientException e){

            UriTemplate uriTemplate = new UriTemplate(olsServiceURL);
            URI expanded = uriTemplate.expand(ontologyTerm);
            String errorMessage = "Cannot access " + expanded + " " + e.getMessage();

            LOGGER.error(errorMessage, e);
            throw new OLSClientException(errorMessage, e);
        }

    }

    private class OLSClientException extends RuntimeException {

        public OLSClientException(String message, Throwable cause) {
            super(message, cause);
        }

    }

}
