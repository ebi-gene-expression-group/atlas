package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class ReactomeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactomeClient.class);

    static final String URL = "https://reactome.org/ContentService/data/query/ids";
    static final int QUERY_MAX_SIZE = 20;  // https://reactome.org/ContentService/#!/query/findByIdsUsingPOST
    private static final Gson GSON = new Gson();
    private static final String STATIC_ID_FIELD = "stId";
    private static final String DISPLAY_NAME_FIELD = "displayName";

    private final RestTemplate restTemplate;

    @Inject
    public ReactomeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<String> getPathwayName(String reactomeId) {
        return Optional.ofNullable(getPathwayNames(ImmutableList.of(reactomeId)).get(reactomeId));
    }

    public ImmutableMap<String, String> getPathwayNames(Collection<String> stableIds) {
        List<List<String>> partitions = Lists.partition(ImmutableList.copyOf(stableIds), QUERY_MAX_SIZE);

        ImmutableMap.Builder<String, String> mappedStableIdsBuilder = ImmutableMap.builder();
        for (List<String> partition : partitions) {
            mappedStableIdsBuilder.putAll(fetchPathwayNames(partition));
        }

        ImmutableMap<String, String> mappedStableIds = mappedStableIdsBuilder.build();
        logMissingIds(stableIds, mappedStableIds);

        return mappedStableIds;
    }

    private ImmutableMap<String, String> fetchPathwayNames(Collection<String> stableIds) {
        String postData = stableIds.stream().collect(Collectors.joining(","));

        try {
            return parseJsonResponse(restTemplate.postForObject(URL, postData, String.class), stableIds);
        } catch (JsonSyntaxException e) {
            LOGGER.error("Invalid JSON returned from Reactome API");
            return ImmutableMap.of();
        } catch (RestClientException e) {
            LOGGER.error("There was an error retrieving pathway names from Reactome");
            return ImmutableMap.of();
        }
    }

    private ImmutableMap<String, String> parseJsonResponse(String response, Collection<String> stableIds) {
        ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();

        JsonArray jsonArray = GSON.fromJson(response, JsonArray.class);
        if (jsonArray != null) {
            for (JsonElement jsonElement : jsonArray) {
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject.has(STATIC_ID_FIELD) &&
                            jsonObject.get(STATIC_ID_FIELD).isJsonPrimitive() &&
                            stableIds.contains(jsonObject.get(STATIC_ID_FIELD).getAsString()) &&
                            jsonObject.has(DISPLAY_NAME_FIELD) &&
                            jsonObject.get(DISPLAY_NAME_FIELD).isJsonPrimitive()) {
                        mapBuilder.put(
                                jsonObject.get(STATIC_ID_FIELD).getAsString(),
                                jsonObject.get(DISPLAY_NAME_FIELD).getAsString());
                    }
                }
            }
        }

        return mapBuilder.build();
    }

    private void logMissingIds(Collection<String> stableIds, Map<String, String> mappedIds) {
        for (String stableId : stableIds) {
            if (!mappedIds.keySet().contains(stableId)) {
                LOGGER.warn("Name of Reactome stable ID {} could not be found", stableId);
            }
        }
    }

}
