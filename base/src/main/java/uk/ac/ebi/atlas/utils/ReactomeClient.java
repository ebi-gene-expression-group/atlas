package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Named
public class ReactomeClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactomeClient.class);

    static final String URL = "http://reactome.org/ContentService/data/query/ids";
    static final int QUERY_MAX_SIZE = 20;  // https://reactome.org/ContentService/#!/query/findByIdsUsingPOST

    private static final String PLANT_URL = "http://plantreactome.gramene.org/ContentService/data/query/ids";
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

    public Optional<String> getPlantPathwayName(String reactomeId) {
        return Optional.ofNullable(getPlantPathwayNames(ImmutableList.of(reactomeId)).get(reactomeId));
    }

    public ImmutableMap<String, String> getPathwayNames(Collection<String> stableIds) {
        ImmutableMap.Builder<String, String> mappedStableIdsBuilder = ImmutableMap.builder();
        for (List<String> partition : getPartitions(stableIds)) {
            mappedStableIdsBuilder.putAll(fetchPathwayNames(partition));
        }
        return getMappedIds(stableIds, mappedStableIdsBuilder);
    }

    public ImmutableMap<String, String> getPlantPathwayNames(Collection<String> stableIds) {
        ImmutableMap.Builder<String, String> mappedStableIdsBuilder = ImmutableMap.builder();
        for (List<String> partition : getPartitions(stableIds)) {
            mappedStableIdsBuilder.putAll(fetchPlantPathwayNames(partition));
        }
        return getMappedIds(stableIds, mappedStableIdsBuilder);
    }

    private List<List<String>> getPartitions(Collection<String> stableIds) {
        return Lists.partition(ImmutableList.copyOf(stableIds), QUERY_MAX_SIZE);
    }

    private ImmutableMap<String, String> getMappedIds(Collection<String> stableIds,
                                                      ImmutableMap.Builder<String, String> mappedStableIdsBuilder) {

        ImmutableMap<String, String> mappedStableIds = mappedStableIdsBuilder.build();
        logMissingIds(stableIds, mappedStableIds);
        return mappedStableIds;
    }

    private ImmutableMap<String, String> fetchPathwayNames(Collection<String> stableIds) {

        return parseResponse(stableIds, URL);
    }

    private ImmutableMap<String, String> fetchPlantPathwayNames(Collection<String> stableIds) {
        return parseResponse(stableIds, PLANT_URL);
    }

    private ImmutableMap<String, String> parseResponse(Collection<String> stableIds, String url) {
        String postData = stableIds.stream().collect(Collectors.joining(","));

        try {
            return parseJsonResponse(restTemplate.postForObject(url, postData, String.class), stableIds);
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
