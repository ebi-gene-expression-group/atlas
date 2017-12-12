package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.io.Tuple;
import org.apache.solr.client.solrj.io.stream.FacetStream;
import org.apache.solr.client.solrj.io.stream.expr.StreamExpression;
import org.apache.solr.client.solrj.io.stream.expr.StreamExpressionParser;
import org.apache.solr.client.solrj.io.stream.expr.StreamFactory;
import org.apache.solr.client.solrj.io.stream.metrics.CountMetric;
import org.apache.solr.client.solrj.io.stream.metrics.MeanMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Named
public class FullAnalyticsSolrCloudDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsSolrCloudDao.class);
    private static final int MAX_GENES = 100000;    // At least as many as the number of genes in the largest experiment

    private final StreamFactory streamFactory;

    @Inject
    public FullAnalyticsSolrCloudDao(AnalyticsCloudClient analyticsCloudClient) {
        streamFactory =
                new StreamFactory()
                        .withCollectionZkHost("analytics", analyticsCloudClient.getZkServerAddress())
                        .withFunctionName("facet", FacetStream.class)
                        .withFunctionName("count", CountMetric.class)
                        .withFunctionName("avg", MeanMetric.class);
    }

    public ImmutableMap<String, Map<String, Number>> geneIdsWithAvgExpressionSortedByCountAscending(String experimentAccession,
                                                                       Collection<String> assayGroupIds,
                                                                       double expressionLevelCutoff) {
        return geneIdsWithAvgExpressionSortedByCountAscending(experimentAccession, assayGroupIds, expressionLevelCutoff, MAX_GENES);
    }

    public ImmutableMap<String, Map<String, Number>> geneIdsWithAvgExpressionSortedByCountAscending(String experimentAccession,
                                                                                                    Collection<String> assayGroupIds,
                                                                                                    double expressionLevelCutoff,
                                                                                                    int limit) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        StreamExpression streamExpression =
                StreamExpressionParser.parse(
                        "facet(analytics," +
                                "q=\"*:*\"," +
                                "fq=\"experiment_accession:" + experimentAccession +
                                " AND expression_level:[" + Double.toString(expressionLevelCutoff) + " TO *]\"," +
                                "buckets=\"bioentity_identifier\"," +
                                "bucketSorts=\"count(*) asc\"," +
                                "bucketSizeLimit=" + MAX_GENES + "," +
                                "count(*)," +
                                "avg(expression_level))");

        try (FacetStream facetStream = new FacetStream(streamExpression, streamFactory)) {
            facetStream.open();
            ImmutableMap.Builder<String, Map<String, Number>> geneIdsPropertiesMapBuilder = ImmutableMap.builder();

            Tuple tuple = facetStream.read();
            long processedDocs = 0; // The size of the map produced by geneIdsPropertiesMapBuilder
            while (!tuple.EOF && processedDocs < limit) {
                long currentCount = tuple.getLong("count(*)");

                while(!tuple.EOF && tuple.getLong("count(*)") == currentCount) {
                    geneIdsPropertiesMapBuilder.put(
                            tuple.getString("bioentity_identifier"),
                            ImmutableMap.of(
                                    "count", tuple.getLong("count(*)"),
                                    "avgExpression", tuple.getDouble("avg(expression_level)")));
                    processedDocs = processedDocs + 1;

                    tuple = facetStream.read();
                }
            }
            LOGGER.info("Finished in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

            return geneIdsPropertiesMapBuilder.build();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
