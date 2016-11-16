package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class ReactomeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactomeClient.class);

    private RestTemplate restTemplate;
    private String reactomeURL;

    @Inject
    public ReactomeClient(RestTemplate restTemplate, @Value("#{configuration['reactome.restful.query.url']}") String reactomeURL) {
        this.restTemplate = restTemplate;
        this.reactomeURL = reactomeURL;
    }

    public String fetchPathwayNameFailSafe(String reactomeId) {
        String url = MessageFormat.format(reactomeURL, reactomeId);

        try {
            String result = restTemplate.getForObject(url, String.class);
            return StringUtils.trimToEmpty(result);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return reactomeId;
        }

    }

}
