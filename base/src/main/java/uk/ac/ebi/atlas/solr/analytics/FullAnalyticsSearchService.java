package uk.ac.ebi.atlas.solr.analytics;

import com.google.auto.value.AutoValue;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeMultimap;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.analyticsindex.AutoValue_FullAnalyticsSearchService_GeneProfileScore;
import uk.ac.ebi.atlas.utils.ResourceUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Named
public class FullAnalyticsSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsSearchService.class);

    // Number of rows in the heatmap
    private static final int DEFAULT_RESULT_SIZE = 50;

    // Observed a slowdown in the second query if bioentity_identifier terms query has size >50,000 terms
    private static final int TERMS_QUERY_SIZE_THRESHOLD = 25000;

    private static final String FQ_TEMPLATE = "experiment_accession:{0}";

    // When searching all assay groups we can use these:
    // private static final String SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE = "expression_level:[{0} TO *]";
    // private static final String NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE = "('{'!terms f=bioentity_identifier'}'{0}) AND expression_level:[{1} TO *]";
    // private static final String NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE_ALT = "expression_level:[{1} TO *]";

    private static final String SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE = "('{'!terms f=assay_group_id'}'{0}) AND expression_level:[{1} TO *]";
    private static final String NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE = "-('{'!terms f=assay_group_id'}'{0}) AND ('{'!terms f=bioentity_identifier'}'{1}) AND expression_level:[{2} TO *]";
    private static final String NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE_ALT = "-('{'!terms f=assay_group_id'}'{0}) AND expression_level:[{1} TO *]";


    private final RestTemplate restTemplate;
    private final Resource avgExpressionOverBioentityIdentifiersFacet;
    private final Resource maxExpressionOverBioentityIdentifiersFacet;

    @Inject
    public FullAnalyticsSearchService(RestTemplate restTemplate,
                                      @Value("classpath:/uk/ac/ebi/atlas/search/baseline/solr/avgExpressionOverBioentityIdentifiers.json")
                             Resource avgExpressionOverBioentityIdentifiersFacet,
                                      @Value("classpath:/uk/ac/ebi/atlas/search/baseline/solr/maxExpressionOverBioentityIdentifiers.json")
                             Resource maxExpressionOverBioentityIdentifiersFacet) throws IOException {
        this.restTemplate = restTemplate;
        this.avgExpressionOverBioentityIdentifiersFacet = avgExpressionOverBioentityIdentifiersFacet;
        this.maxExpressionOverBioentityIdentifiersFacet = maxExpressionOverBioentityIdentifiersFacet;
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

        String filterQueries = MessageFormat.format(FQ_TEMPLATE, experimentAccession);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRows(0)
                .setFilterQueries(filterQueries)
                .setQuery(
                        MessageFormat.format(SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE, Joiner.on(",").join(assayGroupIds), expressionThreshold))
                .set("json.facet", ResourceUtils.readPlainTextResource(avgExpressionOverBioentityIdentifiersFacet));

        Stopwatch stopwatch1 = Stopwatch.createStarted();
        LOGGER.debug(
                "Searching for genes in {} {} across {} assay groups...",
                experimentAccession, expressionThreshold, assayGroupIds.size());

        TreeMultimap<Integer, Pair<String, Double>> genesBySpecificity =
                parseAverageExpressionOverSelectedAssayGroups(
                        fetchResponseAsString("http://localhost:8983/solr/analytics/query", solrQuery), resultSize);

        LOGGER.debug("Finished in {} ms", stopwatch1.elapsed(TimeUnit.MILLISECONDS));

        Map<String, Double> avgExpressionByGeneId = genesBySpecificity.keySet().stream()
                .flatMap(specificity -> genesBySpecificity.get(specificity).stream())
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));


        String nonSelectedQuery = avgExpressionByGeneId.keySet().size() > TERMS_QUERY_SIZE_THRESHOLD ?
                MessageFormat.format(
                        NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE_ALT,
                        Joiner.on(",").join(assayGroupIds),
                        expressionThreshold) :
                MessageFormat.format(
                        NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE,
                        Joiner.on(",").join(assayGroupIds),
                        Joiner.on(",").join(avgExpressionByGeneId.keySet()),
                        expressionThreshold);

        solrQuery.setQuery(nonSelectedQuery)
                .set("json.facet", ResourceUtils.readPlainTextResource(maxExpressionOverBioentityIdentifiersFacet));

        Stopwatch stopwatch2 = Stopwatch.createStarted();
        LOGGER.debug(
                "Searching for genes in {} {} across inv({}) assay groups and {} gene IDs...",
                experimentAccession, expressionThreshold, assayGroupIds.size(), avgExpressionByGeneId.keySet().size());

        Map<String, Double> maxExpressionByGene =
                parseMaximumExpressionOverNonSelectedAssayGroups(
                        fetchResponseAsString("http://localhost:8983/solr/analytics/query", solrQuery));

        LOGGER.debug("Finished in {} ms", stopwatch2.elapsed(TimeUnit.MILLISECONDS));


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
                .collect(Collectors.toList())
                .subList(0, resultSize);
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

    protected String fetchResponseAsString(String url, SolrQuery query) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> request = new HttpEntity<>(query.toString(), headers);
            return restTemplate.postForObject(url, request, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
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
