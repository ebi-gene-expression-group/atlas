package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultProducer;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;

@Named
public class BaselineAnalyticsSearchService {

    private final BaselineAnalyticsSearchDao baselineAnalyticsSearchDao;
    private final BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer;

    @Inject
    public BaselineAnalyticsSearchService(BaselineAnalyticsSearchDao baselineAnalyticsSearchDao,
                                          ExperimentTrader experimentTrader) {
        this.baselineAnalyticsSearchDao = baselineAnalyticsSearchDao;
        this.baselineExperimentSearchResultProducer = new BaselineExperimentSearchResultProducer(experimentTrader);
    }

    public BaselineExperimentProfilesList findExpressions(SemanticQuery geneQuery, SemanticQuery conditionQuery,
                                                          Species species, String queryFactorTypeOrBlank) {
        String queryFactorType = isBlank(queryFactorTypeOrBlank) ?
                species.getDefaultQueryFactorType() :
                queryFactorTypeOrBlank.toUpperCase();

        return baselineExperimentSearchResultProducer.buildProfilesForExperiments(baselineAnalyticsSearchDao.
                fetchExpressionLevels(geneQuery, conditionQuery, species.getReferenceName(), queryFactorType), queryFactorType);
    }

    public JsonObject findFacetsForTreeSearch(SemanticQuery geneQuery, Species species) {
        return findFacetsForTreeSearch(geneQuery, SemanticQuery.create(), species);
    }

    public JsonObject findFacetsForTreeSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {
        List<Map<String, Object>> results =
                baselineAnalyticsSearchDao.fetchFacetsThatHaveExpression(geneQuery, conditionQuery, species.getReferenceName());

        return BaselineAnalyticsFacetsReader.generateFacetsTreeJson(results);
    }

}
