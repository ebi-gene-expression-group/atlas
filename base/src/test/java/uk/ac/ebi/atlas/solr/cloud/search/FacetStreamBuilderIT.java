package uk.ac.ebi.atlas.solr.cloud.search;

import org.apache.solr.client.solrj.io.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.analytics.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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

        try(TupleStreamer unfilteredStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build();
            TupleStreamer streamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .sortByCountsAscending()
                            .build()) {
            unfilteredResults = unfilteredStreamer.get().collect(Collectors.toList());
            filteredResults = streamer.get().collect(Collectors.toList());

            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
        }
    }

    @Test
    public void rangeFilter() {
        List<Tuple> unfilteredResults;
        List<Tuple> filteredResults;

        try(TupleStreamer unfilteredStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build();
            TupleStreamer filteredStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .sortByCountsAscending()
                            .build()) {
            unfilteredResults = unfilteredStreamer.get().collect(Collectors.toList());
            filteredResults = filteredStreamer.get().collect(Collectors.toList());
            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
        }
    }

    @Test
    public void filterCombination() {
        List<Tuple> filteredResultsByExperiment;
        List<Tuple> filteredResultsByExperimentAndExpressionLevel;

        try(TupleStreamer filteredByExperimentStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .sortByCountsAscending()
                            .build();
            TupleStreamer filteredByExperimentAndExpressionLevelStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .sortByCountsAscending()
                            .build()) {
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
        try(TupleStreamer filteredByExperimentStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .withAverageOver(analyticsCollectionProxy.EXPRESSION_LEVEL)
                            .sortByCountsAscending()
                            .build()) {
            filteredByExperimentStreamer.get().forEach(
                    tuple -> assertThat(tuple.getDouble("avg(expression_level)")).isNotNull().isGreaterThan(0));
        }
    }

    @Test
    public void termsQuery() {
        List<Tuple> twoAssayGroupsResult;
        List<Tuple> oneAssayGroupResult;

        try(TupleStreamer twoAssayGroupsStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39", "g341")
                            .sortByCountsAscending()
                            .build() ;
            TupleStreamer oneAssayGroupsStreamer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39")
                            .sortByCountsAscending()
                            .build()) {
            twoAssayGroupsResult = twoAssayGroupsStreamer.get().collect(Collectors.toList());
            oneAssayGroupResult = oneAssayGroupsStreamer.get().collect(Collectors.toList());

            assertThat(oneAssayGroupResult.size()).isGreaterThan(0).isLessThan(twoAssayGroupsResult.size());
        }
    }

    @Test
    public void rangeQuery() {
        List<Tuple> filteredExpressionLevel10;
        List<Tuple> queryexpressionLevel20;

        try(TupleStreamer filterExpressionLevel10Stremer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39")
                            .sortByCountsAscending()
                            .build() ;
            TupleStreamer queryExpressionLevel20Stremer =
                    new FacetStreamBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39")
                            .addQueryRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 20.0)
                            .sortByCountsAscending()
                            .build()) {
            filteredExpressionLevel10 = filterExpressionLevel10Stremer.get().collect(Collectors.toList());
            queryexpressionLevel20 = queryExpressionLevel20Stremer.get().collect(Collectors.toList());

            assertThat(queryexpressionLevel20.size()).isGreaterThan(0).isLessThan(filteredExpressionLevel10.size());
        }
    }
}