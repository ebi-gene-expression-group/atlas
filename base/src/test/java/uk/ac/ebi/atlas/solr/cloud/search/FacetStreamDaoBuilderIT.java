package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.io.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.analytics.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamAutoCloseableIterator;

import javax.inject.Inject;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

// The correct way of doing these tests is by creating an EmbeddedSolrServer with a set of documents to have an
// analytics collection fixture but, alas!, streaming expressions only work in SolrCloud so we use the instance in lime

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FacetStreamDaoBuilderIT {

    @Inject
    private SolrCloudCollectionProxyFactory collectionProxyFactory;

    private AnalyticsCollectionProxy analyticsCollectionProxy;

    @Before
    public void setUp() {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    @Test
    public void termsFilter() throws IOException {
        ImmutableList<Tuple> unfilteredResults;
        ImmutableList<Tuple> filteredResults;

        try(TupleStreamAutoCloseableIterator unfilteredIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build().fetchTupleStream();
            TupleStreamAutoCloseableIterator iterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .sortByCountsAscending()
                            .build().fetchTupleStream()) {
            unfilteredResults = ImmutableList.copyOf(unfilteredIterator);
            filteredResults = ImmutableList.copyOf(iterator);

            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
        }
    }

    @Test
    public void rangeFilter() throws IOException {
        ImmutableList<Tuple> unfilteredResults;
        ImmutableList<Tuple> filteredResults;

        try(TupleStreamAutoCloseableIterator unfilteredIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .sortByCountsAscending()
                            .build().fetchTupleStream();
            TupleStreamAutoCloseableIterator filteredIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .sortByCountsAscending()
                            .build().fetchTupleStream()) {
            unfilteredResults = ImmutableList.copyOf(unfilteredIterator);
            filteredResults = ImmutableList.copyOf(filteredIterator);
            assertThat(filteredResults.size()).isGreaterThan(0).isLessThan(unfilteredResults.size());
        }
    }

    @Test
    public void filterCombination() throws IOException {
        ImmutableList<Tuple> filteredResultsByExperiment;
        ImmutableList<Tuple> filteredResultsByExperimentAndExpressionLevel;

        try(TupleStreamAutoCloseableIterator filteredByExperimentIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .sortByCountsAscending()
                            .build().fetchTupleStream() ;
            TupleStreamAutoCloseableIterator filteredByExperimentAndExpressionLevelIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .sortByCountsAscending()
                            .build().fetchTupleStream()) {
            filteredResultsByExperiment = ImmutableList.copyOf(filteredByExperimentIterator);
            filteredResultsByExperimentAndExpressionLevel = ImmutableList.copyOf(filteredByExperimentAndExpressionLevelIterator);

            assertThat(filteredResultsByExperimentAndExpressionLevel.size())
                    .isGreaterThan(0)
                    .isLessThan(filteredResultsByExperiment.size());
        }
    }

    @Test
    public void includeAverage() throws IOException {
        try(TupleStreamAutoCloseableIterator filteredByExperimentIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .withAverageOver(analyticsCollectionProxy.EXPRESSION_LEVEL)
                            .sortByCountsAscending()
                            .build().fetchTupleStream()) {
            filteredByExperimentIterator.forEachRemaining(
                    tuple -> assertThat(tuple.getDouble("avg(expression_level)")).isNotNull().isGreaterThan(0));
        }
    }

    @Test
    public void termsQuery() throws IOException {
        ImmutableList<Tuple> twoAssayGroupsResult;
        ImmutableList<Tuple> oneAssayGroupResult;

        try(TupleStreamAutoCloseableIterator twoAssayGroupsIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39", "g341")
                            .sortByCountsAscending()
                            .build().fetchTupleStream() ;
            TupleStreamAutoCloseableIterator oneAssayGroupsIterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39")
                            .sortByCountsAscending()
                            .build().fetchTupleStream()) {
            twoAssayGroupsResult = ImmutableList.copyOf(twoAssayGroupsIterator);
            oneAssayGroupResult = ImmutableList.copyOf(oneAssayGroupsIterator);

            assertThat(oneAssayGroupResult.size()).isGreaterThan(0).isLessThan(twoAssayGroupsResult.size());
        }
    }

    @Test
    public void rangeQuery() throws IOException {
        ImmutableList<Tuple> filteredExpressionLevel10;
        ImmutableList<Tuple> queryexpressionLevel20;

        try(TupleStreamAutoCloseableIterator filterExpressionLevel10Iterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39")
                            .sortByCountsAscending()
                            .build().fetchTupleStream() ;
            TupleStreamAutoCloseableIterator queryExpressionLevel20Iterator =
                    new FacetStreamDaoBuilder<>(
                            analyticsCollectionProxy,
                            analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                            .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, "E-MTAB-2706")
                            .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 10.0)
                            .addQueryTermsClause(analyticsCollectionProxy.ASSAY_GROUP_ID, "g39")
                            .addQueryRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, 20.0)
                            .sortByCountsAscending()
                            .build().fetchTupleStream()) {
            filteredExpressionLevel10 = ImmutableList.copyOf(filterExpressionLevel10Iterator);
            queryexpressionLevel20 = ImmutableList.copyOf(queryExpressionLevel20Iterator);

            assertThat(queryexpressionLevel20.size()).isGreaterThan(0).isLessThan(filteredExpressionLevel10.size());
        }
    }
}