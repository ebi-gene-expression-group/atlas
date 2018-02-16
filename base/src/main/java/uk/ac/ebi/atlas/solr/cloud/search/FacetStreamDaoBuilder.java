package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.io.comp.ComparatorOrder;
import org.apache.solr.client.solrj.io.comp.FieldComparator;
import org.apache.solr.client.solrj.io.stream.FacetStream;
import org.apache.solr.client.solrj.io.stream.metrics.Bucket;
import org.apache.solr.client.solrj.io.stream.metrics.CountMetric;
import org.apache.solr.client.solrj.io.stream.metrics.MeanMetric;
import org.apache.solr.client.solrj.io.stream.metrics.Metric;
import org.apache.solr.common.params.SolrParams;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamAutoCloseableIterator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;

public class FacetStreamDaoBuilder<T extends CollectionProxy> {

    private final T collectionProxy;
    private final Bucket[] buckets;

    private SolrParamsBuilder solrParamsBuilder = new SolrParamsBuilder();
    private ImmutableSet.Builder<Metric> metricsBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<FieldComparator> sortsBuilder = ImmutableSet.builder();

    @SafeVarargs
    public FacetStreamDaoBuilder(T collectionProxy, CollectionProxy.SchemaField<? extends T>... bucketFields) {
        this.collectionProxy = collectionProxy;

        buckets =
                ImmutableSet.<CollectionProxy.SchemaField<? extends T>>builder().add(bucketFields).build().stream()
                        .map(CollectionProxy.SchemaField::name)
                        .map(Bucket::new)
                        .toArray(Bucket[]::new);
    }

    public FacetStreamDaoBuilder<T> addQueryTermsClause(CollectionProxy.SchemaField<? extends T> field, String... values) {
        solrParamsBuilder.addQueryTermsClause(field.name(), values);
        return this;
    }

    public FacetStreamDaoBuilder<T> addFilterTermsClause(CollectionProxy.SchemaField<? extends T> field, String... values) {
        solrParamsBuilder.addFilterTermsClause(field.name(), values);
        return this;
    }

    public FacetStreamDaoBuilder<T> addQueryRangeClause(CollectionProxy.SchemaField<? extends T> field, Double rangeLowerBound) {
        solrParamsBuilder.addQueryRangeClause(field.name(), rangeLowerBound);
        return this;
    }

    public FacetStreamDaoBuilder<T> addFilterRangeClause(CollectionProxy.SchemaField<? extends T> field, Double rangeLowerBound) {
        solrParamsBuilder.addFilterRangeClause(field.name(), rangeLowerBound);
        return this;
    }

    public FacetStreamDaoBuilder<T> sortByCountsAscending() {
        metricsBuilder.add(new CountMetric("*"));
        sortsBuilder.add(new FieldComparator("count(*)", ComparatorOrder.ASCENDING));
        return this;
    }

    public FacetStreamDaoBuilder<T> withAverageOver(CollectionProxy.SchemaField<? extends T> field) {
        metricsBuilder.add(new MeanMetric(field.name()));
//        sortsBuilder.add(new FieldComparator("avg(" + field.name() + ")", ComparatorOrder.ASCENDING));
        return this;
    }

    public TupleStreamDao build() {

        return () -> {
            SolrParams solrParams = solrParamsBuilder.build();
            Metric[] metrics = metricsBuilder.build().toArray(new Metric[0]);
            FieldComparator[] sorts = sortsBuilder.build().toArray(new FieldComparator[0]);
            int limit = Integer.MAX_VALUE;  // retrieve all, see https://issues.apache.org/jira/browse/SOLR-11836

            try {
                // Will throw ClassCastException if SolrClient isn’t CloudSolrClient, beware in testing
                String zkHost = ((CloudSolrClient) collectionProxy.solrClient).getZkHost();
                String collectionNameOrAlias = collectionProxy.nameOrAlias;
                return TupleStreamAutoCloseableIterator.of(
                        new FacetStream(zkHost, collectionNameOrAlias, solrParams, buckets, metrics, sorts, limit));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };

    }
}
