package uk.ac.ebi.atlas.solr.cloud.search;

import org.apache.solr.client.solrj.io.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

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

    @Inject
    private SolrCloudCollectionProxyFactory collectionProxyFactory;

    private AnalyticsCollectionProxy analyticsCollectionProxy;

    @Before
    public void setUp() {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    @Test
    public void termsFilter() {
        List<Tuple> unfilteredResults;
        List<Tuple> filteredResults;

        try (TupleStreamer unfilteredStreamer =
                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build());
            TupleStreamer streamer =
                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .sortByCountsAscending()
                            .build())) {
            unfilteredResults = unfilteredStreamer.get().collect(Collectors.toList());
            filteredResults = streamer.get().collect(Collectors.toList());

            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
        }
    }

    @Test
    public void rangeFilter() {
        List<Tuple> unfilteredResults;
        List<Tuple> filteredResults;

        try (TupleStreamer unfilteredStreamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build());
            TupleStreamer filteredStreamer =
                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterRangeClause(EXPRESSION_LEVEL, 10.0)
                            .sortByCountsAscending()
                            .build())) {
            unfilteredResults = unfilteredStreamer.get().collect(Collectors.toList());
            filteredResults = filteredStreamer.get().collect(Collectors.toList());
            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
        }
    }

    @Test
    public void filterCombination() {
        List<Tuple> filteredResultsByExperiment;
        List<Tuple> filteredResultsByExperimentAndExpressionLevel;

        try (TupleStreamer filteredByExperimentStreamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .sortByCountsAscending()
                            .build());
            TupleStreamer filteredByExperimentAndExpressionLevelStreamer =
                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(EXPRESSION_LEVEL, 10.0)
                            .sortByCountsAscending()
                            .build())) {
            filteredResultsByExperiment = filteredByExperimentStreamer.get().collect(Collectors.toList());
            filteredResultsByExperimentAndExpressionLevel =
                    filteredByExperimentAndExpressionLevelStreamer.get().collect(Collectors.toList());

            assertThat(filteredResultsByExperimentAndExpressionLevel.size())
                    .isGreaterThan(0)
                    .isLessThan(filteredResultsByExperiment.size());
        }
    }

    @Test
    public void includeAverage() {
        try (TupleStreamer filteredByExperimentStreamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .withAverageOver(EXPRESSION_LEVEL)
                            .sortByCountsAscending()
                            .build())) {
            filteredByExperimentStreamer.get().forEach(
                    tuple -> assertThat(tuple.getDouble("avg(expression_level)")).isNotNull().isGreaterThan(0));
        }
    }

    @Test
    public void termsQuery() {
        List<Tuple> twoAssayGroupsResult;
        List<Tuple> oneAssayGroupResult;

        try (TupleStreamer twoAssayGroupsStreamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(ASSAY_GROUP_ID, "g39", "g341")
                            .sortByCountsAscending()
                            .build()) ;
            TupleStreamer oneAssayGroupsStreamer =
                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(ASSAY_GROUP_ID, "g39")
                            .sortByCountsAscending()
                            .build())) {
            twoAssayGroupsResult = twoAssayGroupsStreamer.get().collect(Collectors.toList());
            oneAssayGroupResult = oneAssayGroupsStreamer.get().collect(Collectors.toList());

            assertThat(oneAssayGroupResult.size()).isGreaterThan(0).isLessThan(twoAssayGroupsResult.size());
        }
    }

    @Test
    public void rangeQuery() {
        List<Tuple> filteredExpressionLevel10;
        List<Tuple> queryexpressionLevel20;

        try (TupleStreamer filterExpressionLevel10Stremer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(ASSAY_GROUP_ID, "g39")
                            .sortByCountsAscending()
                            .build()) ;
            TupleStreamer queryExpressionLevel20Stremer =
                    TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(ASSAY_GROUP_ID, "g39")
                            .addQueryRangeClause(EXPRESSION_LEVEL, 20.0)
                            .sortByCountsAscending()
                            .build())) {
            filteredExpressionLevel10 = filterExpressionLevel10Stremer.get().collect(Collectors.toList());
            queryexpressionLevel20 = queryExpressionLevel20Stremer.get().collect(Collectors.toList());

            assertThat(queryexpressionLevel20.size()).isGreaterThan(0).isLessThan(filteredExpressionLevel10.size());
        }
    }

    @Test
    public void sortByCounts() {
        try (TupleStreamer streamer =
                     TupleStreamer.of(new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build())) {

            List<Tuple> results = streamer.get().collect(Collectors.toList());

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
                            .sortByAverageDescending(EXPRESSION_LEVEL)
                            .build())) {

            List<Tuple> results = streamer.get().collect(Collectors.toList());

            for (int i = 0 ; i < results.size() - 1; i++) {
                assertThat(results.get(i).getDouble("avg(" + EXPRESSION_LEVEL.name() + ")"))
                        .isGreaterThanOrEqualTo(
                                results.get(i + 1).getDouble(
                                        "avg(" + EXPRESSION_LEVEL.name() + ")"));
            }

        }
    }

}