package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeMultimap;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class FullAnalyticsSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsSearchService.class);

    // Number of rows in the heatmap
    private static final int DEFAULT_RESULT_SIZE = 50;

    private final FullAnalyticsDao fullAnalyticsDao;

    @Inject
    public FullAnalyticsSearchService(FullAnalyticsDao fullAnalyticsDao) {
        this.fullAnalyticsDao = fullAnalyticsDao;
    }

    public List<String> searchSpecificExpression(String experimentAccession,
                                                 Double expressionThreshold,
                                                 Set<String> assayGroupIds) {
        return searchSpecificExpression(experimentAccession, expressionThreshold, assayGroupIds, DEFAULT_RESULT_SIZE);
    }

    public List<String> searchSpecificExpression(String experimentAccession,
                                                 Double expressionThreshold,
                                                 Set<String> assayGroupIds,
                                                 int resultSize) {

        TreeMultimap<Integer, Pair<String, Double>> genesBySpecificity =
                parseAverageExpressionOverSelectedAssayGroups(
                        fullAnalyticsDao.mostSpecificGenesInSelectedAssayGroupsWithAverageExpression(
                                experimentAccession, assayGroupIds, expressionThreshold),
                        resultSize);

        Map<String, Double> avgExpressionByGeneId = genesBySpecificity.keySet().stream()
                .flatMap(specificity -> genesBySpecificity.get(specificity).stream())
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

        Map<String, Double> maxExpressionByGene =
                parseMaximumExpressionOverNonSelectedAssayGroups(
                        fullAnalyticsDao.genesWithMaxExpressionInNonSelectedAssayGroups(
                                experimentAccession, avgExpressionByGeneId.keySet(), assayGroupIds, expressionThreshold));

        TreeMultimap<Integer, GeneProfileScore> rankedGenesBySpecificity =
                TreeMultimap.create(
                        Comparator.naturalOrder(),
                        GeneProfileScore.COMPARATOR_BY_MAX_EXPRESSION.reversed());

        genesBySpecificity.keySet()
                .forEach(specificity ->
                        rankedGenesBySpecificity.putAll(
                                specificity,
                                genesBySpecificity.get(specificity).stream()
                                        .map(pair ->
                                                GeneProfileScore.create(
                                                        pair.getLeft(),
                                                        pair.getRight() /
                                                                maxExpressionByGene.getOrDefault(pair.getLeft(), 1.0)))
                                        .collect(Collectors.toSet())
                        )
                );

        return rankedGenesBySpecificity.entries().stream()
                .map(entry -> entry.getValue().geneId())
                .limit(resultSize)
                .collect(Collectors.toList());
    }

    // TODO consider adding a private class that represents this bucket for safe type deserialization (no casts needed)
    private ImmutableMap<String, Double> parseMaximumExpressionOverNonSelectedAssayGroups(String json) {
        ImmutableMap.Builder<String, Double> maxExpressionByGene = ImmutableMap.builder();

        Collection<Map<String, Object>> buckets = JsonPath.read(json, "$.facets.genes.buckets.*");

        buckets.forEach(
                bucket -> maxExpressionByGene.put((String) bucket.get("val"), (double) bucket.get("max_expression")));

        return maxExpressionByGene.build();
    }


//    private TreeMultimap<Integer, Pair<String, Double>> parseAverageExpressionOverSelectedAssayGroups(String json) {
//        TreeMultimap<Integer, Pair<String, Double>> genesBySpecificity = TreeMultimap.create();
//
//        Collection<Map<String, String>> buckets = JsonPath.read(json, "$.facets.genes.buckets.*");
//
//        buckets.forEach(
//                bucket ->
//                        genesBySpecificity.put(
//                                Integer.parseInt(bucket.get("count")),
//                                Pair.of(bucket.get("val"), Double.parseDouble(bucket.get("avg_expression")))));
//
//        return genesBySpecificity;
//    }

    // TODO consider adding a private class that represents this bucket for safe type deserialization (no casts needed)
    private TreeMultimap<Integer, Pair<String, Double>> parseAverageExpressionOverSelectedAssayGroups(String json,
                                                                                                      int limit) {
        TreeMultimap<Integer, Pair<String, Double>> genesBySpecificity = TreeMultimap.create();

        Collection<Map<String, Object>> buckets = JsonPath.read(json, "$.facets.genes.buckets.*");
        Iterator<Map<String, Object>> bucketsIterator = buckets.iterator();

        int specificity = 0;
        while (bucketsIterator.hasNext()) {
            Map<String, Object> nextBucket = bucketsIterator.next();

            // We must read the full set of genes with same specificity (the most highly expressed may be anywhere)
            if ((int) nextBucket.get("count") > specificity) {
                specificity = (int) nextBucket.get("count");
                if (genesBySpecificity.entries().size() >= limit) {
                    // We have read enough buckets, get outta here
                    break;
                }
            }

            // Keep reading all the buckets with this specificity
            genesBySpecificity.put(
                    specificity,
                    Pair.of((String) nextBucket.get("val"), ((Number) nextBucket.get("avg_expression")).doubleValue()));
            // avg_expression comes some times as Double and some times as BigDecimal

        }

        return genesBySpecificity;
    }

    @AutoValue
    static abstract class GeneProfileScore {
        static final Comparator<GeneProfileScore> COMPARATOR_BY_MAX_EXPRESSION = (o1, o2) -> {
            if (o1.profileScore().equals(o2.profileScore())) {
                return o1.geneId().compareTo(o2.geneId());
            }
            return o1.profileScore().compareTo(o2.profileScore());
        };

        static GeneProfileScore create(String geneId, Double profileScore) {
            return new AutoValue_FullAnalyticsSearchService_GeneProfileScore(geneId, profileScore);
        }

        abstract String geneId();
        abstract Double profileScore();
        // Returned by count in first query
        // abstract Integer specificityOverSelectedAssayGroups();
        // Returned by count in second query
        // abstract Integer specificityOverNonSelectedAssayGroups();
    }
}
