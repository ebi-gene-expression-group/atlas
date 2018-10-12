package uk.ac.ebi.atlas.solr.analytics.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.solr.analytics.baseline.BaselineAnalyticsSearchDao.BASELINE_EXPRESSION_ROUNDER;
import static uk.ac.ebi.atlas.solr.analytics.baseline.BaselineAnalyticsSearchDao.MIN_PRECISION;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BaselineAnalyticsSearchDaoTest {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();
    private static final double MAX_AGGREGATE_EXPRESSION = 100000;
    private static final long MAX_EXPRESSED_GENES = 30000;

    private static final String JSON_PAYLOAD_SAMPLE_FROM_LUNG_SEARCH_IN_CCLE = "{" +
            "  \"response\": {" +
            "    \"numFound\": 8172687," +
            "    \"start\": 0," +
            "    \"maxScore\": 5.8732476," +
            "    \"docs\": []" +
            "  }," +
            "  \"facets\": {" +
            "    \"count\": %d," +
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
            "                                  \"count\": %d," + //26691," +
            "                                  \"sumExpressionLevel\": %f" + //998046.7000000002" +
            "                                }," +
            "                                {" +
            "                                  \"val\": \"g520\"," +
            "                                  \"count\": %d," + //25722," +
            "                                  \"sumExpressionLevel\": %f" + //998387.899999999" +
            "                                }," +
            "                                {" +
            "                                  \"val\": \"g519\"," +
            "                                  \"count\": %d," + //25645," +
            "                                  \"sumExpressionLevel\": %f" + //998144.1999999979" +
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

    @Test
    void roundVeryTinyExpressionLevel() {
        double expressionLevel = RNG.nextDouble(Double.MIN_VALUE, 5 * Math.pow(10, -MIN_PRECISION - 1));
        assertThat(BASELINE_EXPRESSION_ROUNDER.applyAsDouble(expressionLevel))
                .isEqualTo(BASELINE_EXPRESSION_ROUNDER.applyAsDouble(0.0))
               .isZero();
    }

    @Test
    void roundTinyExpressionLevel() {
        double expressionLevel = RNG.nextDouble(5 * Math.pow(10, -MIN_PRECISION - 1), 0.1);

        assertThat(BASELINE_EXPRESSION_ROUNDER.applyAsDouble(expressionLevel))
                .isCloseTo(expressionLevel, Offset.offset(Math.pow(10, -MIN_PRECISION) / 2));
    }

    @Test
    void roundSmallExpressionLevel() {
        double expressionLevel = RNG.nextDouble(0.1, 1.0);

        assertThat(BASELINE_EXPRESSION_ROUNDER.applyAsDouble(expressionLevel))
                .isCloseTo(expressionLevel, Offset.offset(0.1 / 2));
    }

    @Test
    void roundBigExpressionLevel() {
        double expressionLevel = RNG.nextDouble(1.0, Double.MAX_VALUE);
        assertThat(BASELINE_EXPRESSION_ROUNDER.applyAsDouble(expressionLevel))
                .isEqualTo(expressionLevel);
    }

    @Test
    void sumOfExpressionLevelsIsAveragedByGenesExpressedInAssayGroup() {
        List<Pair<Long, Double>> aggregatedExpression =
            ImmutableList.of(
                    Pair.of(RNG.nextLong(1, MAX_EXPRESSED_GENES), RNG.nextDouble(0.1, MAX_AGGREGATE_EXPRESSION)),
                    Pair.of(RNG.nextLong(1, MAX_EXPRESSED_GENES), RNG.nextDouble(0.1, MAX_AGGREGATE_EXPRESSION)),
                    Pair.of(RNG.nextLong(1, MAX_EXPRESSED_GENES), RNG.nextDouble(0.1, MAX_AGGREGATE_EXPRESSION)));

        String randomizedPayload =
                String.format(
                        JSON_PAYLOAD_SAMPLE_FROM_LUNG_SEARCH_IN_CCLE,
                        aggregatedExpression.stream().mapToLong(Pair::getLeft).sum(),
                        aggregatedExpression.get(0).getLeft(), aggregatedExpression.get(0).getRight(),
                        aggregatedExpression.get(1).getLeft(), aggregatedExpression.get(1).getRight(),
                        aggregatedExpression.get(2).getLeft(), aggregatedExpression.get(2).getRight());

        when(analyticsQueryClientBuilderMock.baselineFacets()).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.queryIdentifierSearch(any())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.queryConditionsSearch(any())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.ofSpecies(anyString())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.withFactorType(anyString())).thenReturn(analyticsQueryClientBuilderMock);
        when(analyticsQueryClientBuilderMock.fetch()).thenReturn(randomizedPayload);

        when(analyticsQueryClientMock.queryBuilder()).thenReturn(analyticsQueryClientBuilderMock);

        assertThat(
                subject.fetchExpressionLevels(
                        SemanticQuery.create(), SemanticQuery.create("lung"), "homo sapiens", "CELL_LINE")
                        .get("E-MTAB-2770"))
                .containsAllEntriesOf(
                                ImmutableMap.of(
                                        "g571",
                                        BASELINE_EXPRESSION_ROUNDER.applyAsDouble(
                                                aggregatedExpression.get(0).getRight() /
                                                aggregatedExpression.get(0).getLeft()),
                                        "g520",
                                        BASELINE_EXPRESSION_ROUNDER.applyAsDouble(
                                                aggregatedExpression.get(1).getRight() /
                                                aggregatedExpression.get(1).getLeft()),
                                        "g519",
                                        BASELINE_EXPRESSION_ROUNDER.applyAsDouble(
                                                aggregatedExpression.get(2).getRight() /
                                                aggregatedExpression.get(2).getLeft())));
    }
}
