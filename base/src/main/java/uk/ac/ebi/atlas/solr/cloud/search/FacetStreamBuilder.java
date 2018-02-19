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
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;

import java.io.IOException;
import java.io.UncheckedIOException;

public class FacetStreamBuilder<T extends CollectionProxy> {
    private final T collectionProxy;
    private final Bucket[] buckets;

    private SolrParamsBuilder solrParamsBuilder = new SolrParamsBuilder();
    private ImmutableSet.Builder<Metric> metricsBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<FieldComparator> sortsBuilder = ImmutableSet.builder();

    @SafeVarargs
    private FacetStreamBuilder(T collectionProxy, CollectionProxy.SchemaField<T>... bucketFields) {
        this.collectionProxy = collectionProxy;

        buckets =
                ImmutableSet.<CollectionProxy.SchemaField<T>>builder().add(bucketFields).build().stream()
                        .map(CollectionProxy.SchemaField::name)
                        .map(Bucket::new)
                        .toArray(Bucket[]::new);
    }

    public static <T extends CollectionProxy> FacetStreamBuilder<T> create(T collectionProxy,
                                                                           CollectionProxy.SchemaField<T> field) {
        return new FacetStreamBuilder<>(collectionProxy, field);
    }

    public FacetStreamBuilder<T> addQueryTermsClause(CollectionProxy.SchemaField<T> field, String... values) {
        solrParamsBuilder.addQueryTermsClause(field.name(), values);
        return this;
    }

    public FacetStreamBuilder<T> addFilterTermsClause(CollectionProxy.SchemaField<T> field, String... values) {
        solrParamsBuilder.addFilterTermsClause(field.name(), values);
        return this;
    }

    public FacetStreamBuilder<T> addQueryRangeClause(CollectionProxy.SchemaField<T> field, Double rangeLowerBound) {
        solrParamsBuilder.addQueryRangeClause(field.name(), rangeLowerBound);
        return this;
    }

    public FacetStreamBuilder<T> addFilterRangeClause(CollectionProxy.SchemaField<T> field, Double rangeLowerBound) {
        solrParamsBuilder.addFilterRangeClause(field.name(), rangeLowerBound);
        return this;
    }

    public FacetStreamBuilder<T> sortByCountsAscending() {
        metricsBuilder.add(new CountMetric("*"));
        sortsBuilder.add(new FieldComparator("count(*)", ComparatorOrder.ASCENDING));
        return this;
    }

    public FacetStreamBuilder<T> sortByAverageDescending(CollectionProxy.SchemaField<T> field) {
        metricsBuilder.add(new MeanMetric(field.name()));
        sortsBuilder.add(new FieldComparator("avg(" + field.name() + ")", ComparatorOrder.DESCENDING));
        return this;
    }

    public FacetStreamBuilder<T> withAverageOver(CollectionProxy.SchemaField<T> field) {
        metricsBuilder.add(new MeanMetric(field.name()));
        return this;
    }

    public TupleStreamer build() {
        SolrParams solrParams = solrParamsBuilder.build();
        Metric[] metrics = metricsBuilder.build().toArray(new Metric[0]);
        FieldComparator[] sorts = sortsBuilder.build().toArray(new FieldComparator[0]);
        int limit = Integer.MAX_VALUE;  // retrieve all, see https://issues.apache.org/jira/browse/SOLR-11836

        try {
            // Will throw ClassCastException if SolrClient isn’t CloudSolrClient, beware in testing
            String zkHost = ((CloudSolrClient) collectionProxy.solrClient).getZkHost();
            String collectionNameOrAlias = collectionProxy.nameOrAlias;
            return TupleStreamer.of(
                    new FacetStream(zkHost, collectionNameOrAlias, solrParams, buckets, metrics, sorts, limit));
        } catch (IOException e) {
            // Can only happen if multiple sorters with different names are set, the class API prevents it :)
            throw new UncheckedIOException(e);
        }
    }
}
