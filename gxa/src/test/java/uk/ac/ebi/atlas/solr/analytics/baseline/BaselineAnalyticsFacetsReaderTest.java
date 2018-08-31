package uk.ac.ebi.atlas.solr.analytics.baseline;

import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class BaselineAnalyticsFacetsReaderTest {
    private static final JsonObject RESPONSE_2_JSON_FACETS = GSON.fromJson(
            "{\n" +
            "  \"homo sapiens\": [\n" +
            "    {\n" +
            "      \"name\": \"ORGANISM_PART\",\n" +
            "      \"value\": \"Organism part\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"CELL_LINE\",\n" +
            "      \"value\": \"Cell line\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"mus musculus\": [\n" +
            "    {\n" +
            "      \"name\": \"ORGANISM_PART\",\n" +
            "      \"value\": \"Organism part\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"CELL_LINE\",\n" +
            "      \"value\": \"Cell line\"\n" +
            "    }\n" +
            "  ]\n" +
            "}", JsonObject.class);


    private static List<Map<String, Object>> loadJsonWithFacets() throws IOException {
        InputStream in =
                BaselineAnalyticsFacetsReaderTest.class.getResourceAsStream(
                        "/uk/ac/ebi/atlas/solr/analytics/baseline/baseline.heatmap.pivot.response2.json");
        return JsonPath.read(in, BaselineAnalyticsSearchDao.FACET_TREE_PATH);
    }

    @Test
    public void extractTreeFacets() throws IOException {
        JsonObject facetsTreeJson = BaselineAnalyticsFacetsReader.generateFacetsTreeJson(loadJsonWithFacets());

        assertThat(facetsTreeJson, is(RESPONSE_2_JSON_FACETS));
    }
}
