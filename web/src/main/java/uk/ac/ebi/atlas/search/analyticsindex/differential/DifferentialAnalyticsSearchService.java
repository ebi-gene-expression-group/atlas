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

    public JsonObject fetchDifferentialFacetsForSearch(SemanticQuery geneQuery) {
        return fetchDifferentialFacetsForSearch(geneQuery, SemanticQuery.create(), "");
    }

    public JsonObject fetchDifferentialFacetsForSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, String
            species) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForSearch(geneQuery, conditionQuery, species);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchDifferentialResultsForSearch(SemanticQuery geneQuery) {
        return fetchDifferentialResultsForSearch(geneQuery, SemanticQuery.create(), "");
    }

    public JsonObject fetchDifferentialResultsForSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(geneQuery, conditionQuery, species);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

}
