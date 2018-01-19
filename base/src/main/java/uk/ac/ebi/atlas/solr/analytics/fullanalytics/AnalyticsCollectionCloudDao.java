package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.io.Tuple;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamAutoCloseableIterator;
import uk.ac.ebi.atlas.solr.cloud.search.FacetStreamDaoBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class AnalyticsCollectionCloudDao {

    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public AnalyticsCollectionCloudDao(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.analyticsCollectionProxy = solrCloudCollectionProxyFactory.createAnalyticsCollectionProxy();
    }

    public List<Tuple> facetedSearch(
            Map<AnalyticsCollectionProxy.Field, String> termFilters,
            Map<AnalyticsCollectionProxy.Field, Double> rangeFilters,
            Collection<AnalyticsCollectionProxy.Field> bucketFields) {

        // Will throw ClassCastException if the collection isn’t backed by a SolrCloud cluster (e.g. tests)
        String zkHost = ((CloudSolrClient) analyticsCollectionProxy.solrClient).getZkHost();

        FacetStreamDaoBuilder facetStreamingExpressionBuilder = new FacetStreamDaoBuilder();

        termFilters.forEach((field, value) -> facetStreamingExpressionBuilder.addFilterTermsClause(field.name, value));
        rangeFilters.forEach((field, value) -> facetStreamingExpressionBuilder.addFilterRangeClause(field.name, value));
        bucketFields.forEach(field -> facetStreamingExpressionBuilder.addBucket(field.name));

        try (TupleStreamAutoCloseableIterator tupleStreamAutoCloseableIterator =
                    new TupleStreamAutoCloseableIterator(
                            facetStreamingExpressionBuilder
                                    .withAverage(AnalyticsCollectionProxy.Field.EXPRESSION_LEVEL.name)
                                    .sortByCountsAscending()
                                    .build()
                                    .fetchTupleStream(zkHost, analyticsCollectionProxy.nameOrAlias))) {

            Iterable<Tuple> iterable = () -> tupleStreamAutoCloseableIterator;
            return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
}
