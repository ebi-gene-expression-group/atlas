package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchService {

    private final DifferentialFacetsReader differentialFacetsReader;
    private final DifferentialResultsReader differentialResultsReader;
    private final DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao;

    @Inject
    public DifferentialAnalyticsSearchService(DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao,
                                              DifferentialFacetsReader differentialFacetsReader,
                                              DifferentialResultsReader differentialResultsReader) {
        this.differentialAnalyticsSearchDao = differentialAnalyticsSearchDao;
        this.differentialFacetsReader = differentialFacetsReader;
        this.differentialResultsReader = differentialResultsReader;
    }

    public JsonObject fetchDifferentialFacetsForSearch(SemanticQuery query) {
        String jsonResponse = differentialAnalyticsSearchDao.fetchFacetsAboveDefaultFoldChangeForSearch(query);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchDifferentialResultsForSearch(SemanticQuery query) {
        String differentialResults = differentialAnalyticsSearchDao.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(query);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public JsonObject fetchDifferentialFacetsForQuery(SemanticQuery geneQuery) {
        return fetchDifferentialFacetsForQuery(geneQuery, SemanticQuery.create(), "");
    }

    public JsonObject fetchDifferentialFacetsForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String jsonResponse = differentialAnalyticsSearchDao.fetchFacetsAboveDefaultFoldChangeForQuery(geneQuery, conditionQuery, species);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchDifferentialResultsForQuery(SemanticQuery geneQuery) {
        return fetchDifferentialResultsForQuery(geneQuery, SemanticQuery.create(), "");
    }

    public JsonObject fetchDifferentialResultsForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String differentialResults = differentialAnalyticsSearchDao.fetchDifferentialResultsAboveDefaultFoldChangeForQuery(geneQuery, conditionQuery, species);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

}
