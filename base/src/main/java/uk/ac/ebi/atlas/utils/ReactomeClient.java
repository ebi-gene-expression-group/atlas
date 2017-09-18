package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class ReactomeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactomeClient.class);

    private final RestTemplate restTemplate;

    @Inject
    public ReactomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Nullable
    public String fetchPathwayNameFailSafe(String reactomeId) {
        String reactomeURL = "https://www.reactome.org/ContentService/data/query/{0}/displayName";
        String url = MessageFormat.format(reactomeURL, reactomeId);

        try {
            return StringUtils.trim(restTemplate.getForObject(url, String.class));
        } catch (RestClientException e) {
            LOGGER.warn("Reactome ID \"{}\" could not be found", reactomeId);
            return null;
        }

    }

}
