package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchService {

    private final DifferentialFacetsDAO differentialFacetsDAO;
    private final DifferentialFacetsReader differentialFacetsReader;
    private final DifferentialResultsDAO differentialResultsDAO;
    private final DifferentialResultsReader differentialResultsReader;

    @Inject
    public DifferentialAnalyticsSearchService(DifferentialFacetsDAO differentialFacetsDAO, DifferentialFacetsReader differentialFacetsReader,
                                              DifferentialResultsDAO differentialResultsDAO, DifferentialResultsReader differentialResultsReader) {
        this.differentialFacetsDAO = differentialFacetsDAO;
        this.differentialFacetsReader = differentialFacetsReader;
        this.differentialResultsDAO = differentialResultsDAO;
        this.differentialResultsReader = differentialResultsReader;
    }

    public JsonObject fetchDifferentialFacetsForSearch(SemanticQuery query) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForSearch(query);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchDifferentialResultsForSearch(SemanticQuery query) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(query);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public JsonObject fetchDifferentialFacetsForQuery(SemanticQuery geneQuery) {
        return fetchDifferentialFacetsForQuery(geneQuery, SemanticQuery.create(), "");
    }

    public JsonObject fetchDifferentialFacetsForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForQuery(geneQuery, conditionQuery, species);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchDifferentialResultsForQuery(SemanticQuery geneQuery) {
        return fetchDifferentialResultsForQuery(geneQuery, SemanticQuery.create(), "");
    }

    public JsonObject fetchDifferentialResultsForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForQuery(geneQuery, conditionQuery, species);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

}
