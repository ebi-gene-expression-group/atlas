package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineAnalyticsSearchService {

    private final BaselineAnalyticsSearchDao baselineAnalyticsSearchDao;
    private final BaselineAnalyticsFacetsReader baselineAnalyticsFacetsReader;
    private final BaselineTissueExperimentSearchResultProducer baselineTissueExperimentSearchResultProducer;

    @Inject
    public BaselineAnalyticsSearchService(BaselineAnalyticsSearchDao baselineAnalyticsSearchDao, BaselineAnalyticsFacetsReader baselineAnalyticsFacetsReader, BaselineTissueExperimentSearchResultProducer baselineTissueExperimentSearchResultProducer) {
        this.baselineAnalyticsSearchDao = baselineAnalyticsSearchDao;
        this.baselineAnalyticsFacetsReader = baselineAnalyticsFacetsReader;
        this.baselineTissueExperimentSearchResultProducer = baselineTissueExperimentSearchResultProducer;
    }

    public BaselineTissueExperimentSearchResult findExpressionsForTissueExperiments(String geneQuery, String species) {
        String jsonResponse = baselineAnalyticsSearchDao.queryByIdentifierSearch(geneQuery);
        ImmutableList<RnaSeqBslnExpression> expressions = baselineAnalyticsFacetsReader.extractAverageExpressionLevel(jsonResponse, species, "ORGANISM_PART");
        return baselineTissueExperimentSearchResultProducer.buildProfilesForTissueExperiments(expressions);
    }

}
