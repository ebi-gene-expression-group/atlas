package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

/**
 * Created by barrera on 28/07/2014.
 *
 */

@Named
@Scope("prototype")
public class ReactomeClient {

    private static final Logger LOGGER = LogManager.getLogger(ReactomeClient.class);

    private RestTemplate restTemplate;

    private String reactomeURL;

    @Inject
    public ReactomeClient(RestTemplate restTemplate, @Value("#{configuration['reactome.restful.query.url']}") String reactomeURL) {
        this.restTemplate = restTemplate;
        this.reactomeURL = reactomeURL;
    }

    /**
     * @return pathway name, or if there is an error then log it but continue by returning the reactomeId
     */
    public String fetchPathwayNameFailSafe(String reactomeId) {
        String url = MessageFormat.format(reactomeURL, reactomeId);

        try {
            String result = restTemplate.getForObject(url, String.class);

            return StringUtils.trimToEmpty(result);

        } catch (RestClientException e) {
            LOGGER.error(e);
            return reactomeId;
        }

    }

}
