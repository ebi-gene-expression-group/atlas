package uk.ac.ebi.atlas.search.analyticsindex.differential;

import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchService {

    private final DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao;
    private final DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader;

    @Inject
    public DifferentialAnalyticsSearchService(DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao, DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader) {
        this.differentialAnalyticsSearchDao = differentialAnalyticsSearchDao;
        this.differentialAnalyticsFacetsReader = differentialAnalyticsFacetsReader;
    }

    String fetchDifferentialGeneQueryFacetsAsJson(GeneQuery geneQuery) {
        String jsonResponse = differentialAnalyticsSearchDao.fetchFacets(geneQuery);
        return differentialAnalyticsFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    String fetchDifferentialGeneQueryResultsAsJson(GeneQuery geneQuery) {
        String differentialResults = differentialAnalyticsSearchDao.fetchDifferentialGeneQueryResultsAboveDefaultFoldChange(geneQuery);
        return differentialAnalyticsFacetsReader.extractResultsAsJson(differentialResults);
    }

}
