package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsSearchDao {

    //TODO no need for a filter there
    public static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='RNASEQ_MRNA_BASELINE' ||" +
            " @.val=='PROTEOMICS_BASELINE')]..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='RNASEQ_MRNA_BASELINE' || @.val=='PROTEOMICS_BASELINE')].species.buckets[*]";

    private final AnalyticsQueryClient analyticsQueryClient;

    @Inject
    public BaselineAnalyticsSearchDao(AnalyticsQueryClient analyticsQueryClient) {
        this.analyticsQueryClient = analyticsQueryClient;
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String response =
                analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .fetch();
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        String response = analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType)
                .fetch();

        return JsonPath.read(response,EXPERIMENTS_PATH);
    }

}
