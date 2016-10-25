package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.CONDITIONS_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.FACTOR_TYPE;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.SPECIES;

@Named
public class BaselineAnalyticsSearchDao {

    static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')]..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";

    private final BaselineAnalyticsDao baselineAnalyticsDao;

    @Inject
    public BaselineAnalyticsSearchDao(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl, @Value("classpath:baseline.heatmap.pivot.query.json") Resource baselineFacetsQueryJSON) {
        this(new BaselineAnalyticsDao(restTemplate, solrBaseUrl, baselineFacetsQueryJSON));
    }


    BaselineAnalyticsSearchDao(BaselineAnalyticsDao baselineAnalyticsDao) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        AnalyticsQueryBuilder analyticsQueryBuilder =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species);

        String response = baselineAnalyticsDao.fetchResults(analyticsQueryBuilder.build().getQuery());
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery) {
        AnalyticsQueryBuilder analyticsQueryBuilder = new AnalyticsQueryBuilder().queryIdentifierSearch(geneQuery);

        String response = baselineAnalyticsDao.fetchResults(analyticsQueryBuilder.build().getQuery());
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        AnalyticsQueryBuilder analyticsQueryBuilder =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .withFactorType(defaultQueryFactorType);

        String response = baselineAnalyticsDao.fetchResults(analyticsQueryBuilder.build().getQuery());
        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species.toLowerCase(), defaultQueryFactorType.toLowerCase()));
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery query, String species, String defaultQueryFactorType) {
        AnalyticsQueryBuilder analyticsQueryBuilder =new AnalyticsQueryBuilder()
                .queryIdentifierOrConditionsSearch(query)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType);

        String response = baselineAnalyticsDao.fetchResults(analyticsQueryBuilder.build().getQuery());
        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species.toLowerCase(), defaultQueryFactorType.toLowerCase()));
    }

}
