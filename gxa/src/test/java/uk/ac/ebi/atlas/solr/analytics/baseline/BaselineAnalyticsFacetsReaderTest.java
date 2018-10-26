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
    private static final String JSON_RESPONSE =
            "{\n" +
            "  \"response\": {\n" +
            "    \"numFound\": 456088,\n" +
            "    \"start\": 0,\n" +
            "    \"docs\": []\n" +
            "  },\n" +
            "  \"facets\": {\n" +
            "    \"count\": 456088,\n" +
            "    \"experimentType\": {\n" +
            "      \"buckets\": [\n" +
            "        {\n" +
            "          \"val\": \"RNASEQ_MRNA_BASELINE\",\n" +
            "          \"count\": 456088,\n" +
            "          \"species\": {\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"val\": \"homo sapiens\",\n" +
            "                \"count\": 456088,\n" +
            "                \"defaultQueryFactorType\": {\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"val\": \"ORGANISM_PART\",\n" +
            "                      \"count\": 453289,\n" +
            "                      \"experimentAccession\": {\n" +
            "                        \"buckets\": [\n" +
            "                          {\n" +
            "                            \"val\": \"E-MTAB-1733\",\n" +
            "                            \"count\": 449957,\n" +
            "                            \"uniqueIdentifiers\": 25247,\n" +
            "                            \"assayGroupId\": {\n" +
            "                              \"buckets\": [\n" +
            "                                {\n" +
            "                                  \"val\": \"g22\",\n" +
            "                                  \"count\": 21971,\n" +
            "                                  \"sumExpressionLevel\": 467512.3999999801\n" +
            "                                },\n" +
            "                                {\n" +
            "                                  \"val\": \"g15\",\n" +
            "                                  \"count\": 16868,\n" +
            "                                  \"sumExpressionLevel\": 486396.0999999961\n" +
            "                                }\n" +
            "                              ]\n" +
            "                            }\n" +
            "                          },\n" +
            "                          {\n" +
            "                            \"val\": \"E-MTAB-513\",\n" +
            "                            \"count\": 3332,\n" +
            "                            \"uniqueIdentifiers\": 352,\n" +
            "                            \"assayGroupId\": {\n" +
            "                              \"buckets\": [\n" +
            "                                {\n" +
            "                                  \"val\": \"g13\",\n" +
            "                                  \"count\": 262,\n" +
            "                                  \"sumExpressionLevel\": 4510.1\n" +
            "                                }\n" +
            "                              ]\n" +
            "                            }\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"val\": \"CELL_LINE\",\n" +
            "                      \"count\": 2799,\n" +
            "                      \"experimentAccession\": {\n" +
            "                        \"buckets\": [\n" +
            "                          {\n" +
            "                            \"val\": \"E-GEOD-26284\",\n" +
            "                            \"count\": 2799,\n" +
            "                            \"uniqueIdentifiers\": 75,\n" +
            "                            \"assayGroupId\": {\n" +
            "                              \"buckets\": []\n" +
            "                            }\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"val\": \"mus musculus\",\n" +
            "                \"count\": 456088,\n" +
            "                \"defaultQueryFactorType\": {\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"val\": \"ORGANISM_PART\",\n" +
            "                      \"count\": 453289,\n" +
            "                      \"experimentAccession\": {\n" +
            "                        \"buckets\": [\n" +
            "                          {\n" +
            "                            \"val\": \"E-MTAB-1733\",\n" +
            "                            \"count\": 449957,\n" +
            "                            \"uniqueIdentifiers\": 25247,\n" +
            "                            \"assayGroupId\": {\n" +
            "                              \"buckets\": [\n" +
            "                                {\n" +
            "                                  \"val\": \"g22\",\n" +
            "                                  \"count\": 21971,\n" +
            "                                  \"sumExpressionLevel\": 467512.3999999801\n" +
            "                                },\n" +
            "                                {\n" +
            "                                  \"val\": \"g15\",\n" +
            "                                  \"count\": 16868,\n" +
            "                                  \"sumExpressionLevel\": 486396.0999999961\n" +
            "                                }\n" +
            "                              ]\n" +
            "                            }\n" +
            "                          },\n" +
            "                          {\n" +
            "                            \"val\": \"E-MTAB-513\",\n" +
            "                            \"count\": 3332,\n" +
            "                            \"uniqueIdentifiers\": 352,\n" +
            "                            \"assayGroupId\": {\n" +
            "                              \"buckets\": [\n" +
            "                                {\n" +
            "                                  \"val\": \"g13\",\n" +
            "                                  \"count\": 262,\n" +
            "                                  \"sumExpressionLevel\": 4510.1\n" +
            "                                }\n" +
            "                              ]\n" +
            "                            }\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"val\": \"CELL_LINE\",\n" +
            "                      \"count\": 2799,\n" +
            "                      \"experimentAccession\": {\n" +
            "                        \"buckets\": [\n" +
            "                          {\n" +
            "                            \"val\": \"E-GEOD-26284\",\n" +
            "                            \"count\": 2799,\n" +
            "                            \"uniqueIdentifiers\": 75,\n" +
            "                            \"assayGroupId\": {\n" +
            "                              \"buckets\": []\n" +
            "                            }\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    private static final JsonObject JSON_RESPONSE_FACETS = GSON.fromJson(
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

    @Test
    public void extractTreeFacets() {
        List<Map<String, Object>> parsedJsonResponse =
                JsonPath.read(JSON_RESPONSE, BaselineAnalyticsSearchDao.FACET_TREE_PATH);

        JsonObject facetsTreeJson = BaselineAnalyticsFacetsReader.generateFacetsTreeJson(parsedJsonResponse);

        assertThat(facetsTreeJson, is(JSON_RESPONSE_FACETS));
    }
}
