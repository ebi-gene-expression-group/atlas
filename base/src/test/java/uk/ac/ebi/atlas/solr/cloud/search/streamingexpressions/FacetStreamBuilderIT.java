package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import org.apache.solr.client.solrj.io.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL;

// The correct way of doing these tests is by creating an EmbeddedSolrServer with a set of documents to have an
// analytics collection fixture but, alas!, streaming expressions only work in SolrCloud so we use the instance in lime

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FacetStreamBuilderIT {
    public static final String E_MTAB_513 = "E-MTAB-513";

    @Inject
    private SolrCloudCollectionProxyFactory collectionProxyFactory;

    private AnalyticsCollectionProxy analyticsCollectionProxy;

    @Before
    public void setUp() {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    // TODO Maybe check correctness against AnalyticsCollectionProxy by querying the collection

    @Test
    public void filtersAndQueriesRestrictResults() {
        String[] assayGroups = IntStream.range(1, 16).boxed().map(i -> "g" + i.toString()).toArray(String[]::new);
        SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();

        int previousStepSize;

        try (TupleStreamer unfilteredStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .sortByCountsAscending()
                                     .build())) {
            previousStepSize = unfilteredStreamer.get().collect(toList()).size();
        }

        solrQueryBuilder.filterFieldOr(ASSAY_GROUP_ID, assayGroups);
        try (TupleStreamer filteredStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(solrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build())) {
            List<Tuple> results = filteredStreamer.get().collect(toList());
            assertThat(results.size()).isLessThan(previousStepSize);
            previousStepSize = results.size();
        }

        solrQueryBuilder.filterFieldLowerRange(EXPRESSION_LEVEL, 10.0);
        try (TupleStreamer filteredStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(solrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build())) {
            List<Tuple> results = filteredStreamer.get().collect(toList());
            assertThat(results.size()).isLessThan(previousStepSize);
            previousStepSize = results.size();
        }

        solrQueryBuilder.filterFieldUpperRange(EXPRESSION_LEVEL, 10000.0);
        try (TupleStreamer filteredStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(solrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build())) {
            List<Tuple> results = filteredStreamer.get().collect(toList());
            assertThat(results.size()).isLessThan(previousStepSize);
            previousStepSize = results.size();
        }

        solrQueryBuilder.filterFieldDoubleRange(EXPRESSION_LEVEL, 300.0, 600.0);
        try (TupleStreamer filteredStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(solrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build())) {
            List<Tuple> results = filteredStreamer.get().collect(toList());
            assertThat(results.size()).isLessThan(previousStepSize);
            previousStepSize = results.size();
        }

        solrQueryBuilder.queryField(EXPERIMENT_ACCESSION, E_MTAB_513);
        try (TupleStreamer filteredStreamer =
                     TupleStreamer.of(
                             new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                                     .withQueryBuilder(solrQueryBuilder)
                                     .sortByCountsAscending()
                                     .build())) {
            List<Tuple> results = filteredStreamer.get().collect(toList());
            assertThat(results.size()).isLessThan(previousStepSize);
            // previousStepSize = results.size();
        }
    }

//    @Test
//    public void termsFilter() {
//        List<Tuple> unfilteredResults;
//        List<Tuple> filteredResults;
//
//        SolrQueryBuilder solrQueryBuilder = new SolrQueryBuilder();
//        solrQueryBuilder.addFilterTermsClause(EXPERIMENT_ACCESSION.name(), E_MTAB_513);
//
//        try (TupleStreamer unfilteredStreamer =
//                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .sortByCountsAscending()
//                            .build());
//            TupleStreamer streamer =
//                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .withQueryBuilder(solrQueryBuilder.build())
//                            .sortByCountsAscending()
//                            .build())) {
//            unfilteredResults = unfilteredStreamer.get().collect(toList());
//            filteredResults = streamer.get().collect(toList());
//
//            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
//        }
//    }
//
//    @Test
//    public void rangeFilter() {
//        List<Tuple> unfilteredResults;
//        List<Tuple> filteredResults;
//
//        SolrQueryBuilder solrQueryBuilder = new SolrQueryBuilder();
//        solrQueryBuilder.add
//
//        try (TupleStreamer unfilteredStreamer =
//                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .sortByCountsAscending()
//                            .build());
//            TupleStreamer filteredStreamer =
//                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterLowerRangeClause(EXPRESSION_LEVEL, 10.0)
//                            .sortByCountsAscending()
//                            .build())) {
//            unfilteredResults = unfilteredStreamer.get().collect(toList());
//            filteredResults = filteredStreamer.get().collect(toList());
//            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
//        }
//    }
//
//    @Test
//    public void filterCombination() {
//        List<Tuple> filteredResultsByExperiment;
//        List<Tuple> filteredResultsByExperimentAndExpressionLevel;
//
//        try (TupleStreamer filteredByExperimentStreamer =
//                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_513)
//                            .sortByCountsAscending()
//                            .build());
//            TupleStreamer filteredByExperimentAndExpressionLevelStreamer =
//                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_513)
//                            .addFilterLowerRangeClause(EXPRESSION_LEVEL, 10.0)
//                            .sortByCountsAscending()
//                            .build())) {
//            filteredResultsByExperiment = filteredByExperimentStreamer.get().collect(toList());
//            filteredResultsByExperimentAndExpressionLevel =
//                    filteredByExperimentAndExpressionLevelStreamer.get().collect(toList());
//
//            assertThat(filteredResultsByExperimentAndExpressionLevel.size())
//                    .isGreaterThan(0)
//                    .isLessThan(filteredResultsByExperiment.size());
//        }
//    }
//
//    @Test
//    public void includeAverage() {
//        try (TupleStreamer filteredByExperimentStreamer =
//                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_513)
//                            .withAbsoluteAverageOf(EXPRESSION_LEVEL)
//                            .sortByCountsAscending()
//                            .build())) {
//            filteredByExperimentStreamer.get().forEach(
//                    tuple -> assertThat(tuple.getDouble("avg(abs(expression_level))")).isNotNull().isGreaterThan(0));
//        }
//    }
//
//    @Test
//    public void termsQuery() {
//        List<Tuple> twoAssayGroupsResult;
//        List<Tuple> oneAssayGroupResult;
//
//        try (TupleStreamer twoAssayGroupsStreamer =
//                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_513)
//                            .addFilterLowerRangeClause(EXPRESSION_LEVEL, 10.0)
//                            .addQueryTermsClause(ASSAY_GROUP_ID, "g10", "g5")
//                            .sortByCountsAscending()
//                            .build()) ;
//            TupleStreamer oneAssayGroupsStreamer =
//                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_513)
//                            .addFilterLowerRangeClause(EXPRESSION_LEVEL, 10.0)
//                            .addQueryTermsClause(ASSAY_GROUP_ID, "g10")
//                            .sortByCountsAscending()
//                            .build())) {
//            twoAssayGroupsResult = twoAssayGroupsStreamer.get().collect(toList());
//            oneAssayGroupResult = oneAssayGroupsStreamer.get().collect(toList());
//
//            assertThat(oneAssayGroupResult.size()).isGreaterThan(0).isLessThan(twoAssayGroupsResult.size());
//        }
//    }

//    @Test
//    public void rangeQuery() {
//        List<Tuple> filteredExpressionLevel10;
//        List<Tuple> queryexpressionLevel20;
//
//        try (TupleStreamer filterExpressionLevel10Stremer =
//                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_2706)
//                            .addFilterLowerRangeClause(EXPRESSION_LEVEL, 10.0)
//                            .addQueryTermsClause(ASSAY_GROUP_ID, "g39")
//                            .sortByCountsAscending()
//                            .build()) ;
//            TupleStreamer queryExpressionLevel20Stremer =
//                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                            .addFilterTermsClause(EXPERIMENT_ACCESSION, E_MTAB_2706)
//                            .addFilterLowerRangeClause(EXPRESSION_LEVEL, 10.0)
//                            .addQueryTermsClause(ASSAY_GROUP_ID, "g39")
//                            .addQueryLowerRangeClause(EXPRESSION_LEVEL, 20.0)
//                            .sortByCountsAscending()
//                            .build())) {
//            filteredExpressionLevel10 = filterExpressionLevel10Stremer.get().collect(toList());
//            queryexpressionLevel20 = queryExpressionLevel20Stremer.get().collect(toList());
//
//            assertThat(queryexpressionLevel20.size()).isGreaterThan(0).isLessThan(filteredExpressionLevel10.size());
//        }
//    }

    @Test
    public void sortByCounts() {
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
    public void sortByAverage() {
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

}