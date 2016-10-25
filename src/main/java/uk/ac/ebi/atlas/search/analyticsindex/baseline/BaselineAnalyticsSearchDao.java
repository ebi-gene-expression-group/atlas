package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsSearchDao {

    static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')]..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final AnalyticsQueryFactory analyticsQueryFactory;

    @Inject
    public BaselineAnalyticsSearchDao(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String
            solrBaseUrl, AnalyticsQueryFactory analyticsQueryFactory,@Value("classpath:baseline.heatmap.pivot.query.json") Resource baselineFacetsQueryJSON) {
        this.baselineAnalyticsDao = new BaselineAnalyticsDao(restTemplate, solrBaseUrl, baselineFacetsQueryJSON);
        this.analyticsQueryFactory = analyticsQueryFactory;
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .baselineOnly()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                .build();

        String response = baselineAnalyticsDao.fetchResults(solrQuery.getQuery());
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery) {
        SolrQuery solrQuery = analyticsQueryFactory.builder().queryIdentifierSearch(geneQuery).build();

        String response = baselineAnalyticsDao.fetchResults(solrQuery.getQuery());
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .baselineOnly()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .withFactorType(defaultQueryFactorType)
                .build();

        String response = baselineAnalyticsDao.fetchResults(solrQuery.getQuery());
        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType));
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery query, String species, String defaultQueryFactorType) {
        SolrQuery solrQuery =analyticsQueryFactory.builder()
                .baselineOnly()
                .queryIdentifierOrConditionsSearch(query)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType).build();

        String response = baselineAnalyticsDao.fetchResults(solrQuery.getQuery());
        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType));
    }

}
