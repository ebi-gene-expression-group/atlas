package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.Species;

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

    public BaselineExperimentSearchResult findExpressions(String geneQuery, String species, String defaultQueryFactorType) {
        String jsonResponse = baselineAnalyticsSearchDao.queryByIdentifierSearch(geneQuery);
        ImmutableList<RnaSeqBslnExpression> expressions = baselineAnalyticsFacetsReader.extractAverageExpressionLevel(jsonResponse, Species.convertToEnsemblSpecies(species), defaultQueryFactorType);
        return baselineExperimentSearchResultProducer.buildProfilesForTissueExperiments(expressions);
    }

    public BaselineExperimentSearchResult findExpressionsForTissueExperiments(String geneQuery, String species) {
        String jsonResponse = baselineAnalyticsSearchDao.queryByIdentifierSearch(geneQuery);
        ImmutableList<RnaSeqBslnExpression> expressions = baselineAnalyticsFacetsReader.extractAverageExpressionLevel(jsonResponse, Species.convertToEnsemblSpecies(species), "ORGANISM_PART");
        return baselineExperimentSearchResultProducer.buildProfilesForTissueExperiments(expressions);
    }

    public String findFacetsForTreeSearch(String geneQuery) {
        String jsonResponse = baselineAnalyticsSearchDao.queryByIdentifierSearch(geneQuery);

        return baselineAnalyticsFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

}
