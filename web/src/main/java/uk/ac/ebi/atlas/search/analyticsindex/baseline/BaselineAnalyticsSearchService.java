package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultProducer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsSearchService {

    private final BaselineAnalyticsSearchDao baselineAnalyticsSearchDao;
    private final BaselineAnalyticsExpressionAvailableDao baselineAnalyticsExpressionAvailableDao;
    private final BaselineAnalyticsFacetsReader baselineAnalyticsFacetsReader;
    private final BaselineAnalyticsExpressionAvailableReader baselineAnalyticsExpressionAvailableReader;
    private final BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer;

    @Inject
    public BaselineAnalyticsSearchService(BaselineAnalyticsSearchDao baselineAnalyticsSearchDao, BaselineAnalyticsExpressionAvailableDao baselineAnalyticsExpressionAvailableDao,
                                          BaselineAnalyticsFacetsReader baselineAnalyticsFacetsReader, BaselineAnalyticsExpressionAvailableReader baselineAnalyticsExpressionAvailableReader,
                                          BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer) {
        this.baselineAnalyticsSearchDao = baselineAnalyticsSearchDao;
        this.baselineAnalyticsExpressionAvailableDao = baselineAnalyticsExpressionAvailableDao;
        this.baselineAnalyticsFacetsReader = baselineAnalyticsFacetsReader;
        this.baselineAnalyticsExpressionAvailableReader = baselineAnalyticsExpressionAvailableReader;
        this.baselineExperimentSearchResultProducer = baselineExperimentSearchResultProducer;
    }

    public BaselineExperimentSearchResult findExpressions(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String queryFactorType) {
        List<Map<String, Object>> response = baselineAnalyticsSearchDao.fetchExpressionLevelFaceted(geneQuery, conditionQuery, SpeciesUtils.convertToEnsemblSpecies(species), queryFactorType);
        ImmutableList<BaselineExperimentExpression> expressions = baselineAnalyticsFacetsReader.extractAverageExpressionLevel(response);
        return baselineExperimentSearchResultProducer.buildProfilesForExperiments(expressions, queryFactorType);
    }

    public String findFacetsForTreeSearch(SemanticQuery geneQuery, String species) {
        return findFacetsForTreeSearch(geneQuery, SemanticQuery.create(), species);
    }

    public String findFacetsForTreeSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        List<Map<String, Object>> results = baselineAnalyticsSearchDao.fetchFacetsThatHaveExpression(geneQuery, conditionQuery, species);
        return BaselineAnalyticsFacetsReader.generateFacetsTreeJson(results);
    }

    public boolean tissueExpressionAvailableFor(SemanticQuery geneQuery) {
        int numFound = baselineAnalyticsExpressionAvailableReader.extractResultCount(baselineAnalyticsExpressionAvailableDao.fetchGenesInTissuesAboveCutoff(geneQuery));
        return numFound > 0;
    }

}
