package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultProducer;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

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

    public BaselineExperimentSearchResult findExpressions(GeneQuery geneQuery, String species, String defaultQueryFactorType) {
        String jsonResponse = baselineAnalyticsSearchDao.fetchExpressionLevelFaceted(geneQuery, defaultQueryFactorType);
        ImmutableList<BaselineExperimentExpression> expressions = baselineAnalyticsFacetsReader.extractAverageExpressionLevel(jsonResponse, Species.convertToEnsemblSpecies(species), defaultQueryFactorType);
        return baselineExperimentSearchResultProducer.buildProfilesForExperiments(expressions, defaultQueryFactorType);
    }

    public String findFacetsForTreeSearch(GeneQuery geneQuery) {
        String jsonResponse = baselineAnalyticsSearchDao.fetchFacetsThatHaveExpression(geneQuery);

        return BaselineAnalyticsFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public boolean tissueExpressionAvailableFor(GeneQuery geneQuery) {
        int numFound = baselineAnalyticsExpressionAvailableReader.extractResultCount(baselineAnalyticsExpressionAvailableDao.fetchGenesInTissuesAboveCutoff(geneQuery));
        return numFound > 0;
    }

}
