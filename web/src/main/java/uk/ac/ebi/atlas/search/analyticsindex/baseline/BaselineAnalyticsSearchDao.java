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
public class BaselineAnalyticsSearchDao extends BaselineAnalyticsDao {

    static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[?(@.val=='%s')].defaultQueryFactorType.buckets[?(@.val=='%s')].experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";

    @Inject
    public BaselineAnalyticsSearchDao(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl, @Value("classpath:baseline.heatmap.pivot.query.json") Resource baselineFacetsQueryJSON) {
        super(restTemplate, solrBaseUrl, baselineFacetsQueryJSON);
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        AnalyticsQueryBuilder analyticsQueryBuilder =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species);

        String response = fetchResults(analyticsQueryBuilder.build().getQuery());
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        AnalyticsQueryBuilder analyticsQueryBuilder =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .withFactorType(defaultQueryFactorType);

        String response = fetchResults(analyticsQueryBuilder.build().getQuery());
        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType));
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery query, String species, String defaultQueryFactorType) {
        AnalyticsSolrQuery solrQuery = new AnalyticsSolrQuery(
                AnalyticsSolrQuery.Operator.AND,
                new AnalyticsSolrQuery(
                        AnalyticsSolrQuery.Operator.OR,
                        new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), query),
                        new AnalyticsSolrQuery(CONDITIONS_SEARCH.toString(), query)
                ),
                new AnalyticsSolrQuery(SPECIES.toString(), SemanticQuery.create(species)),
                new AnalyticsSolrQuery(FACTOR_TYPE.toString(), SemanticQuery.create(defaultQueryFactorType))
        );

        String response = fetchResults(solrQuery.toString());
        return JsonPath.read(response, String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType));
    }

}
