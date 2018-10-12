package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.io.comp.ComparatorOrder;
import org.apache.solr.client.solrj.io.comp.FieldComparator;
import org.apache.solr.client.solrj.io.stream.FacetStream;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import org.apache.solr.client.solrj.io.stream.metrics.Bucket;
import org.apache.solr.client.solrj.io.stream.metrics.CountMetric;
import org.apache.solr.client.solrj.io.stream.metrics.MeanMetric;
import org.apache.solr.client.solrj.io.stream.metrics.Metric;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;

public class FacetStreamBuilder<T extends CollectionProxy> extends TupleStreamBuilder<T> {
    private final T collectionProxy;
    private final Bucket[] buckets;

    private SolrQuery solrQuery;
    private ImmutableSet.Builder<Metric> metricsBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<FieldComparator> sortsBuilder = ImmutableSet.builder();

    public FacetStreamBuilder(T collectionProxy, Collection<SchemaField<T>> bucketFields) {
        this.collectionProxy = collectionProxy;

        buckets =
                ImmutableSet.copyOf(bucketFields).stream()
                        .map(SchemaField::name)
                        .map(Bucket::new)
                        .toArray(Bucket[]::new);

        solrQuery = new SolrQueryBuilder<T>().build();
    }

    // Convenience constructor when using a single bucket
    public FacetStreamBuilder(T collectionProxy, SchemaField<T> bucketField) {
        this(collectionProxy, ImmutableSet.of(bucketField));
    }


    public FacetStreamBuilder<T> sortByCountsAscending() {
        metricsBuilder.add(new CountMetric("*"));
        sortsBuilder.add(new FieldComparator("count(*)", ComparatorOrder.ASCENDING));
        return this;
    }

    public FacetStreamBuilder<T> sortByAbsoluteAverageDescending(SchemaField<T> field) {
        metricsBuilder.add(new MeanMetric("abs(" + field.name() + ")"));
        sortsBuilder.add(new FieldComparator("avg(abs(" + field.name() + "))", ComparatorOrder.DESCENDING));
        return this;
    }

    public FacetStreamBuilder<T> sortByAscending(SchemaField<T> field) {
        sortsBuilder.add(new FieldComparator(field.name(), ComparatorOrder.ASCENDING));
        return this;
    }

    public FacetStreamBuilder<T> withCounts() {
        metricsBuilder.add(new CountMetric("*"));
        return this;
    }

    public FacetStreamBuilder<T> withAbsoluteAverageOf(SchemaField<T> field) {
        metricsBuilder.add(new MeanMetric("abs(" + field.name() + ")"));
        return this;
    }

    public FacetStreamBuilder<T> withQuery(SolrQuery solrQuery) {
        this.solrQuery = solrQuery;
        return this;
    }

    @Override
    protected TupleStream getRawTupleStream() {
        Metric[] metrics = metricsBuilder.build().toArray(new Metric[0]);
        FieldComparator[] sorts = sortsBuilder.build().toArray(new FieldComparator[0]);
        int limit = Integer.MAX_VALUE;  // retrieve all, see https://issues.apache.org/jira/browse/SOLR-11836

        try {
            String zkHost = ((CloudSolrClient) collectionProxy.solrClient).getZkHost();
            String collectionNameOrAlias = collectionProxy.nameOrAlias;
            return new FacetStream(zkHost, collectionNameOrAlias, solrQuery, buckets, metrics, sorts, limit);
        } catch (IOException e) {
            // Can only happen if multiple sorters with different names are set, the class API prevents it :)
            throw new UncheckedIOException(e);
        }
    }
}
