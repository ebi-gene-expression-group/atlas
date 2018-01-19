package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.io.comp.ComparatorOrder;
import org.apache.solr.client.solrj.io.comp.FieldComparator;
import org.apache.solr.client.solrj.io.stream.FacetStream;
import org.apache.solr.client.solrj.io.stream.metrics.Bucket;
import org.apache.solr.client.solrj.io.stream.metrics.CountMetric;
import org.apache.solr.client.solrj.io.stream.metrics.MeanMetric;
import org.apache.solr.client.solrj.io.stream.metrics.Metric;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.Collection;

public class FacetStreamDaoBuilder {

    private SolrParamsBuilder solrParamsBuilder = new SolrParamsBuilder();
    private ImmutableSet.Builder<String> bucketsBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<Metric> metricsBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<FieldComparator> sortsBuilder = ImmutableSet.builder();

    public FacetStreamDaoBuilder addQueryTermsClause(String field, Collection<String> values) {
        solrParamsBuilder.addQueryTermsClause(field, values);
        return this;
    }

    public FacetStreamDaoBuilder addQueryTermsClause(String field, String value) {
        return addQueryTermsClause(field, ImmutableSet.of(value));
    }

    public FacetStreamDaoBuilder addFilterTermsClause(String field, Collection<String> values) {
        solrParamsBuilder.addFilterTermsClause(field, values);
        return this;
    }

    public FacetStreamDaoBuilder addFilterTermsClause(String field, String value) {
        return addFilterTermsClause(field, ImmutableSet.of(value));
    }

    public FacetStreamDaoBuilder addQueryRangeClause(String field, Double rangeLowerBound) {
        solrParamsBuilder.addQueryRangeClause(field, rangeLowerBound);
        return this;
    }

    public FacetStreamDaoBuilder addFilterRangeClause(String field, Double rangeLowerBound) {
        solrParamsBuilder.addFilterRangeClause(field, rangeLowerBound);
        return this;
    }

    public FacetStreamDaoBuilder addBucket(String field) {
        bucketsBuilder.add(field);
        return this;
    }

    public FacetStreamDaoBuilder sortByCountsAscending() {
        metricsBuilder.add(new CountMetric("*"));
        sortsBuilder.add(new FieldComparator("count(*)", ComparatorOrder.ASCENDING));
        return this;
    }

    public FacetStreamDaoBuilder withAverage(String field) {
        metricsBuilder.add(new MeanMetric(field));
        return this;
    }

    //  facet(analytics,
    //        q="*:*",
    //        fq="experiment_accession: ${experimentAccession} AND expression_level:[${expressionLevelCutoff}> TO *]",
    //        buckets="bioentity_identifier",
    //        bucketSorts="count(*) asc",
    //        bucketSizeLimit=${limit},
    //        count(*),
    //        avg(expression_level))
    //
    // The streaming expression above can be transformed to a TupleStream in two ways:

    // Method #1:
    //    SolrParams solrParams =
    //            mapParams(
    //                    "q", "*:*",
    //                    "fq", "experiment_accession:" + experimentAccession + " AND " +
    //                            "expression_level:[" + Double.toString(expressionLevelCutoff) + " TO *]");
    //    Bucket[] buckets = {new Bucket("bioentity_identifier")};
    //    Metric[] metrics = {new CountMetric("*"), new MeanMetric("expression_level")};
    //    FieldComparator[] sorts = {new FieldComparator("count(*)", ComparatorOrder.ASCENDING)};
    //
    //    TupleStream tupleStream =
    //            new FacetStream(
    //                    zkHost, collectionNameOrAlias, solrParams, buckets, metrics, bucketSorts, Integer.MAX_VALUE);
    //
    //    StreamContext streamContext = new StreamContext();
    //    SolrClientCache solrClientCache = new SolrClientCache();
    //    streamContext.setSolrClientCache(solrClientCache);
    //    facetStream.setStreamContext(streamContext);

    // Method #2:
    //  StreamExpression streamExpression =
    //          StreamExpressionParser.parse(
    //                  "facet(analytics," +
    //                          "q=\"*:*\"," +
    //                          "fq=\"experiment_accession:" + experimentAccession +
    //                          " AND expression_level:[" + Double.toString(expressionLevelCutoff) + " TO *]\"," +
    //                          "buckets=\"bioentity_identifier\"," +
    //                          "bucketSorts=\"count(*) asc\"," +
    //                          "bucketSizeLimit=" + MAX_GENES + "," +
    //                          "count(*)," +
    //                          "avg(expression_level))");
    //
    //  streamFactory =
    //          new StreamFactory()
    //                      .withCollectionZkHost("analytics", zkHost)
    //                      .withFunctionName("facet", FacetStream.class)
    //                      .withFunctionName("count", CountMetric.class)
    //                      .withFunctionName("avg", MeanMetric.class);
    //
    //  TupleStream tupleStream = new FacetStream(streamExpression, streamFactory)) { ... }

    public TupleStreamDao build() {

        return (zkHost, collectionNameOrAlias) -> {
            SolrParams solrParams = solrParamsBuilder.build();
            Bucket[] buckets = bucketsBuilder.build().stream().map(Bucket::new).toArray(Bucket[]::new);
            Metric[] metrics = metricsBuilder.build().toArray(new Metric[0]);
            FieldComparator[] sorts = sortsBuilder.build().toArray(new FieldComparator[0]);
            int limit = Integer.MAX_VALUE;  // retrieve all, see https://issues.apache.org/jira/browse/SOLR-11836

            try {
                // After the pattern in StreamingTest::testFacetStream
                // https://github.com/apache/lucene-solr/blob/master/solr/solrj/src/test/org/apache/solr/client/solrj/io/stream/StreamingTest.java#L719
                // For an (arguably higher level) alternative, see:
                // https://lucidworks.com/2017/12/06/streaming-expressions-in-solrj/

                // FacetStream facetStream =
                //         new FacetStream(zkHost, collectionNameOrAlias, solrParams, buckets, metrics, sorts, limit);
                // StreamContext streamContext = new StreamContext();
                // SolrClientCache solrClientCache = new SolrClientCache();
                // streamContext.setSolrClientCache(solrClientCache);
                // facetStream.setStreamContext(streamContext);

                return new FacetStream(zkHost, collectionNameOrAlias, solrParams, buckets, metrics, sorts, limit);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        };

    }
}
