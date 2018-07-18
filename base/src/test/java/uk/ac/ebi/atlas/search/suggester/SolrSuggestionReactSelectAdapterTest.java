package uk.ac.ebi.atlas.search.suggester;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.ID_PROPERTY_NAMES;

class SolrSuggestionReactSelectAdapterTest {
    private static final Gson GSON = new Gson();

    @Test
    void emptyStreamReturnsEmptyJsonArray() {
        assertThat(SolrSuggestionReactSelectAdapter.serialize(Stream.empty()))
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("idPropertyNameProvider")
    void suggestionsAreSerializedToJsonInAnOptionsArray(List<BioentityPropertyName> idPropertyNames) {
        Map<String, String> suggestionA = ImmutableMap.of("term", "term a", "category", idPropertyNames.get(0).name);

        // This is just ridiculous...
        String json = SolrSuggestionReactSelectAdapter
                                .serialize(Stream.of(suggestionA))
                                .get(0)
                                .getAsJsonObject()
                                .getAsJsonArray("options")
                                .get(0)
                                .getAsJsonObject()
                                .get("value")
                                .getAsString();

        assertThat(GSON.fromJson(json, JsonObject.class))
                .matches(jsonObject -> jsonObject.get("term").getAsString().equals(suggestionA.get("term")))
                .matches(jsonObject -> jsonObject.get("category").getAsString().equals(suggestionA.get("category")));
    }

    @ParameterizedTest
    @MethodSource("idPropertyNameProvider")
    void groupsAreSortedByIdPrecedence(List<BioentityPropertyName> idPropertyNames) {
        List<Map<String, String>> suggestions =
                idPropertyNames.stream()
                        .map(idPropertyName ->
                                ImmutableMap.of("term", randomAlphanumeric(30), "category", idPropertyName.name))
                .collect(toImmutableList());

        JsonArray results = SolrSuggestionReactSelectAdapter.serialize(suggestions.stream());
        ImmutableList.Builder<String> resultsPropertyNameLabels = ImmutableList.builder();
        results.forEach(result -> resultsPropertyNameLabels.add(result.getAsJsonObject().get("label").getAsString()));

        assertThat(resultsPropertyNameLabels.build())
                .containsExactlyElementsOf(
                        ID_PROPERTY_NAMES.stream()
                                .map(idPropertyName -> idPropertyName.label)
                                .collect(toList()));
    }

    @ParameterizedTest
    @MethodSource("idPropertyNameProvider")
    void suggestionsWithinACategoryAreSortedAlphabetically(List<BioentityPropertyName> idPropertyNames) {
        Map<String, String> suggestionA = ImmutableMap.of("term", "term a", "category", idPropertyNames.get(0).name);
        Map<String, String> suggestionB = ImmutableMap.of("term", "term b", "category", idPropertyNames.get(0).name);
        Map<String, String> suggestionC = ImmutableMap.of("term", "term c", "category", idPropertyNames.get(0).name);

        JsonElement results = GSON.toJsonTree(
                ImmutableMap.of(
                        "label", idPropertyNames.get(0).label,
                        "options",
                        ImmutableList.of(
                                ImmutableMap.of("label", suggestionA.get("term"), "value", GSON.toJson(suggestionA)),
                                ImmutableMap.of("label", suggestionB.get("term"), "value", GSON.toJson(suggestionB)),
                                ImmutableMap.of("label", suggestionC.get("term"), "value", GSON.toJson(suggestionC)))));

        List<Map<String, String>> suggestions = Lists.newArrayList(suggestionA, suggestionB, suggestionC);
        Collections.shuffle(suggestions);

        assertThat(SolrSuggestionReactSelectAdapter.serialize(suggestions.stream()))
                .containsExactly(results);
    }

    private static Stream<Arguments> idPropertyNameProvider() {
        ArrayList<BioentityPropertyName> idPropertyNames = new ArrayList<>(ID_PROPERTY_NAMES);
        Collections.shuffle(idPropertyNames);
        return Stream.of(Arguments.of(idPropertyNames));
    }
}