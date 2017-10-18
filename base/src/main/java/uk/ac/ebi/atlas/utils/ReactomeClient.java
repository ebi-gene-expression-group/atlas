package uk.ac.ebi.atlas.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class ReactomeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactomeClient.class);

    private static final String JSON_PATH_IDS = "$[*].stId";
    private static final String JSON_PATH_NAMES = "$[*].displayName";

    private final RestTemplate restTemplate;

    @Inject
    public ReactomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<Pair<String, String>> fetchPathwayNames(Collection<String> reactomeIds) {
        String url = "https://reactome.org/ContentService/data/query/ids";
        String body = reactomeIds.stream().collect(Collectors.joining(","));

        try {

            String response = restTemplate.postForObject(url, body, String.class);

            ReadContext jsonReadContext = JsonPath.parse(response);
            Collection<String> ids = jsonReadContext.read(JSON_PATH_IDS);
            Collection<String> displayNames = jsonReadContext.read(JSON_PATH_NAMES);

            Preconditions.checkArgument(ids.size() == displayNames.size(), "Retrieved pathway IDs did not match retrieved pathway names");

            return Streams.zip(ids.stream(), displayNames.stream(), Pair::of).collect(Collectors.toSet());

        } catch (InvalidJsonException e) {
            LOGGER.warn("Invalid JSON returned from Reactome API");
            return Collections.emptySet();
        } catch (RestClientException e) {
            LOGGER.error("There was an error retrieving pathway names from Reactome API");
            return Collections.emptySet();
        }
    }

    // Use fetchPathwayNames above instead
    @Deprecated
    public Optional<String> fetchPathwayNameFailSafe(String reactomeId) {
        String reactomeURL = "https://reactome.org/ContentService/data/query/{0}/displayName";
        String url = MessageFormat.format(reactomeURL, reactomeId);

        try {
            return Optional.of(StringUtils.trim(restTemplate.getForObject(url, String.class)));
        } catch (RestClientException e) {
            LOGGER.warn("Reactome ID \"{}\" could not be found", reactomeId);
            return Optional.empty();
        }

    }

}
