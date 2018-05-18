package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source;

// The correct way of doing these tests is by creating an EmbeddedSolrServer with a set of documents to have an
// analytics collection fixture but, alas!, streaming expressions only work in SolrCloud so we use the instance in lime

import org.apache.solr.client.solrj.io.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.FacetStreamBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FacetStreamBuilderIT {
    public static final String E_MTAB_513 = "E-MTAB-513";

    @Inject
    private SolrCloudCollectionProxyFactory collectionProxyFactory;

    private AnalyticsCollectionProxy analyticsCollectionProxy;

    @BeforeEach
    void setUp() {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    // TODO Maybe check correctness against AnalyticsCollectionProxy by querying the collection

    @ParameterizedTest
    @MethodSource("solrQueryBuildersProvider")
    @DisplayName("Narrower query results is a subset of the broader query results")
    void filtersAndQueriesRestrictResults(SolrQueryBuilder<AnalyticsCollectionProxy> broadSolrQueryBuilder,
                                                 SolrQueryBuilder<AnalyticsCollectionProxy> narrowSolrQueryBuilder) {
        try (TupleStreamer broadQueryStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(broadSolrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build());
             TupleStreamer narrowQueryStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(narrowSolrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build())) {

            List<Map> broadQueryResults = broadQueryStreamer.get().map(Tuple::getMap).collect(toList());
            List<Map> narrowQueryResults = narrowQueryStreamer.get().map(Tuple::getMap).collect(toList());

            assertThat(broadQueryResults.size()).isGreaterThan(narrowQueryResults.size());

            assertThat(broadQueryResults).extracting(BIOENTITY_IDENTIFIER.name())
                    .contains(
                            narrowQueryResults.stream()
                                    .map(tupleMap -> tupleMap.get(BIOENTITY_IDENTIFIER.name())).toArray());
        }
    }

    @ParameterizedTest
    @MethodSource("solrQueryBuildersProvider")
    void queryAndQueryBuilderAreEquivalent(SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder) {
        try (TupleStreamer filteredStreamer1 =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(solrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build());
             TupleStreamer filteredStreamer2 =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQuery(solrQueryBuilder.build())
                                     .sortByCountsAscending()
                                     .build())) {
            assertThat(filteredStreamer1.get().map(Tuple::getMap))
                    .extracting(BIOENTITY_IDENTIFIER.name())
                    .containsExactlyInAnyOrder(filteredStreamer2.get().map(tuple -> tuple.getString(BIOENTITY_IDENTIFIER.name())).toArray());
        }
    }

    @Test
    void includeAverage() {
        // We need to specify a baseline experiment, otherwise avg(abs(expression_level)) will be 0
        SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder.addFilterFieldByTerm(EXPERIMENT_ACCESSION, E_MTAB_513);
        try (TupleStreamer filteredByExperimentStreamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .withQueryBuilder(solrQueryBuilder)
                            .withAbsoluteAverageOf(EXPRESSION_LEVEL)
                            .sortByCountsAscending()
                            .build())) {
            filteredByExperimentStreamer.get().forEach(
                    tuple -> assertThat(tuple.getDouble("avg(abs(expression_level))")).isNotNull().isGreaterThan(0));
        }
    }

    @Test
    void sortByCounts() {
        try (TupleStreamer streamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build())) {

            List<Tuple> results = streamer.get().collect(toList());

            for (int i = 0 ; i < results.size() - 1; i++) {
                assertThat(results.get(i).getLong("count(*)"))
                        .isLessThanOrEqualTo(results.get(i + 1).getLong("count(*)"));
            }

        }
    }

    @Test
    void sortByAverage() {
        try (TupleStreamer streamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .sortByAbsoluteAverageDescending(EXPRESSION_LEVEL)
                            .build())) {

            List<Tuple> results = streamer.get().collect(toList());

            for (int i = 0 ; i < results.size() - 1; i++) {
                assertThat(results.get(i).getDouble("avg(abs(" + EXPRESSION_LEVEL.name() + "))"))
                        .isGreaterThanOrEqualTo(
                                results.get(i + 1).getDouble(
                                        "avg(abs(" + EXPRESSION_LEVEL.name() + "))"));
            }

        }
    }

    private static Stream<Arguments> solrQueryBuildersProvider() {
        String[] assayGroups = IntStream.range(1, 16).boxed().map(i -> "g" + i.toString()).toArray(String[]::new);

        SolrQueryBuilder<AnalyticsCollectionProxy> hugeSolrQueryBuilder = new SolrQueryBuilder<>();
        hugeSolrQueryBuilder
                .addFilterFieldByTerm(ASSAY_GROUP_ID, assayGroups)
                .addFilterFieldByRangeMin(EXPRESSION_LEVEL, 10.0);

        SolrQueryBuilder<AnalyticsCollectionProxy> bigSolrQueryBuilder = new SolrQueryBuilder<>();
        bigSolrQueryBuilder
                .addFilterFieldByTerm(ASSAY_GROUP_ID, assayGroups)
                .addFilterFieldByRangeMin(EXPRESSION_LEVEL, 10.0)
                .addFilterFieldByRangeMax(EXPRESSION_LEVEL, 10000.0);

        SolrQueryBuilder<AnalyticsCollectionProxy> smallSolrQueryBuilder = new SolrQueryBuilder<>();
        smallSolrQueryBuilder
                .addFilterFieldByTerm(ASSAY_GROUP_ID, assayGroups)
                .addFilterFieldByRangeMin(EXPRESSION_LEVEL, 10.0)
                .addFilterFieldByRangeMinMax(EXPRESSION_LEVEL, 300.0, 600.0);

        SolrQueryBuilder<AnalyticsCollectionProxy> tinySolrQueryBuilder = new SolrQueryBuilder<>();
        tinySolrQueryBuilder
                .addFilterFieldByTerm(ASSAY_GROUP_ID, assayGroups)
                .addFilterFieldByRangeMin(EXPRESSION_LEVEL, 10.0)
                .addFilterFieldByRangeMinMax(EXPRESSION_LEVEL, 300.0, 600.0)
                .addQueryFieldByTerm(EXPERIMENT_ACCESSION, E_MTAB_513);


        return Stream.of(
                Arguments.of(hugeSolrQueryBuilder, bigSolrQueryBuilder),
                Arguments.of(bigSolrQueryBuilder, smallSolrQueryBuilder),
                Arguments.of(smallSolrQueryBuilder, tinySolrQueryBuilder));
    }

}