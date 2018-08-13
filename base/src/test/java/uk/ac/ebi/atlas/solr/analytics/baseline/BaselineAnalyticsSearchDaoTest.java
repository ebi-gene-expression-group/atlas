package uk.ac.ebi.atlas.solr.analytics.baseline;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BaselineAnalyticsSearchDaoTest {
    public static String JSON_PAYLOAD_SAMPLE_FROM_LUNG_SEARCH_IN_CCLE = "{" +
            "  \"response\": {" +
            "    \"numFound\": 8172687," +
            "    \"start\": 0," +
            "    \"maxScore\": 5.8732476," +
            "    \"docs\": []" +
            "  }," +
            "  \"facets\": {" +
            "    \"count\": 8172687," +
            "    \"experimentType\": {" +
            "      \"buckets\": [" +
            "        {" +
            "          \"val\": \"RNASEQ_MRNA_BASELINE\"," +
            "          \"count\": 8163714," +
            "          \"species\": {" +
            "            \"buckets\": [" +
            "              {" +
            "                \"val\": \"homo sapiens\"," +
            "                \"count\": 8163714," +
            "                \"defaultQueryFactorType\": {" +
            "                  \"buckets\": [" +
            "                    {" +
            "                      \"val\": \"CELL_LINE\"," +
            "                      \"count\": 8163714," +
            "                      \"experimentAccession\": {" +
            "                        \"buckets\": [" +
            "                          {" +
            "                            \"val\": \"E-MTAB-2770\"," +
            "                            \"count\": 3700442," +
            "                            \"uniqueIdentifiers\": 44862," +
            "                            \"assayGroupId\": {" +
            "                              \"buckets\": [" +
            "                                {" +
            "                                  \"val\": \"g571\"," +
            "                                  \"count\": 26691," +
            "                                  \"sumExpressionLevel\": 998046.7000000002" +
            "                                }," +
            "                                {" +
            "                                  \"val\": \"g520\"," +
            "                                  \"count\": 25722," +
            "                                  \"sumExpressionLevel\": 998387.899999999" +
            "                                }," +
            "                                {" +
            "                                  \"val\": \"g519\"," +
            "                                  \"count\": 25645," +
            "                                  \"sumExpressionLevel\": 998144.1999999979" +
            "                                }" +
            "                              ]" +
            "                            }" +
            "                          }" +
            "                        ]" +
            "                      }" +
            "                    }" +
            "                  ]" +
            "                }" +
            "              }" +
            "            ]" +
            "          }" +
            "        }" +
            "      ]" +
            "    }" +
            "  }" +
            "}";

    @Mock
    private AnalyticsQueryClient analyticsQueryClientMock;

    @Mock
    private AnalyticsQueryClient.Builder analyticsQueryClientBuilderMock;

    private BaselineAnalyticsSearchDao subject;

    @BeforeEach
    void setUp() {
        subject = new BaselineAnalyticsSearchDao(analyticsQueryClientMock);
    }

    // TODO Randomize tests
    @Test
    void sumOfExpressionIsAveragedByGenesExpressedInAssayGroup() {
        when(analyticsQueryClientBuilderMock.baselineFacets()).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.queryIdentifierSearch(any())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.queryConditionsSearch(any())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.ofSpecies(anyString())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.withFactorType(anyString())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.fetch()).thenReturn(JSON_PAYLOAD_SAMPLE_FROM_LUNG_SEARCH_IN_CCLE);

        when(analyticsQueryClientMock.queryBuilder()).thenReturn(analyticsQueryClientBuilderMock);

        assertThat(
                subject.fetchExpressionLevels(
                        SemanticQuery.create(), SemanticQuery.create("lung"), "homo sapiens", "CELL_LINE"))
                .containsAllEntriesOf(
                        ImmutableMap.of(
                                "E-MTAB-2770",
                                ImmutableMap.of(
                                        "g571", new Long(Math.round(998046.7000000002 / 26691)).doubleValue(),
                                        "g520", new Long(Math.round(998387.899999999 / 25722)).doubleValue(),
                                        "g519", new Long(Math.round(998144.1999999979 / 25645)).doubleValue())
                        )
                );
    }
}