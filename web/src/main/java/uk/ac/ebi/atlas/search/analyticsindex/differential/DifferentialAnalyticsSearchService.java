package uk.ac.ebi.atlas.search.analyticsindex.differential;

import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class DifferentialAnalyticsSearchService {

    private final DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao;
    private final DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader;

    @Inject
    public DifferentialAnalyticsSearchService(DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao, DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader) {
        this.differentialAnalyticsSearchDao = differentialAnalyticsSearchDao;
        this.differentialAnalyticsFacetsReader = differentialAnalyticsFacetsReader;
    }

    public String fetchDifferentialGeneQueryFacetsAsJson(GeneQuery geneQuery) {
        String jsonResponse = differentialAnalyticsSearchDao.fetchFacets(geneQuery);
        return differentialAnalyticsFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public String fetchDifferentialGeneQueryResultsAsJson(GeneQuery geneQuery) {
        String differentialResults = differentialAnalyticsSearchDao.fetchDifferentialGeneQueryResultsAboveDefaultFoldChange(geneQuery);
        return differentialAnalyticsFacetsReader.extractResultsAsJson(differentialResults);
    }

    public String fetchDifferentialGeneQuerySelectionResultsAsJson(GeneQuery geneQuery, List<String> species, List<String> experimentType, List<String> kingdoms, List<String> factors, List<Integer> numReplicates, String regulation) {
        String differentialResults = differentialAnalyticsSearchDao.fetchDifferentialGeneQueryResultsAboveDefaultFoldChange(geneQuery, species, experimentType, kingdoms, factors, numReplicates, regulation);
        return differentialAnalyticsFacetsReader.extractResultsAsJson(differentialResults);
    }

}
