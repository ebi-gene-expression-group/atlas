package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultProducer;
import uk.ac.ebi.atlas.search.baseline.BaselineExpression;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineAnalyticsSearchService {

    private final BaselineAnalyticsSearchDao baselineAnalyticsSearchDao;
    private final BaselineAnalyticsFacetsReader baselineAnalyticsFacetsReader;
    private final BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer;

    @Inject
    public BaselineAnalyticsSearchService(BaselineAnalyticsSearchDao baselineAnalyticsSearchDao, BaselineAnalyticsFacetsReader baselineAnalyticsFacetsReader, BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer) {
        this.baselineAnalyticsSearchDao = baselineAnalyticsSearchDao;
        this.baselineAnalyticsFacetsReader = baselineAnalyticsFacetsReader;
        this.baselineExperimentSearchResultProducer = baselineExperimentSearchResultProducer;
    }

    public BaselineExperimentSearchResult findExpressions(GeneQuery geneQuery, String species, String defaultQueryFactorType) {
        String jsonResponse = baselineAnalyticsSearchDao.fetchExpressionLevelFaceted(geneQuery, defaultQueryFactorType);
        ImmutableList<BaselineExpression> expressions = baselineAnalyticsFacetsReader.extractAverageExpressionLevel(jsonResponse, Species.convertToEnsemblSpecies(species), defaultQueryFactorType);
        return baselineExperimentSearchResultProducer.buildProfilesForExperiments(expressions, defaultQueryFactorType);
    }

    public String findFacetsForTreeSearch(GeneQuery geneQuery) {
        String jsonResponse = baselineAnalyticsSearchDao.fetchFacetsThatHaveExpression(geneQuery);

        return BaselineAnalyticsFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

}
