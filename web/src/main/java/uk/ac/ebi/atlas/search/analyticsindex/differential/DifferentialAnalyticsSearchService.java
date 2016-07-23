package uk.ac.ebi.atlas.search.analyticsindex.differential;

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

    public String fetchDifferentialFacetsForSearch(SemanticQuery geneQuery) {
        return fetchDifferentialFacetsForSearch(geneQuery, SemanticQuery.create(), "");
    }

    public String fetchDifferentialFacetsForSearch(SemanticQuery geneQuery, String species) {
        return fetchDifferentialFacetsForSearch(geneQuery, SemanticQuery.create(), species);
    }

    public String fetchDifferentialFacetsForSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForSearch(geneQuery, conditionQuery, species);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }


    public String fetchDifferentialResultsForSearch(SemanticQuery geneQuery) {
        return fetchDifferentialResultsForSearch(geneQuery, SemanticQuery.create(), "");
    }

    public String fetchDifferentialResultsForSearch(SemanticQuery geneQuery, String species) {
        return fetchDifferentialResultsForSearch(geneQuery, SemanticQuery.create(), species);
    }

    public String fetchDifferentialResultsForSearch(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(geneQuery, conditionQuery, species);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public String fetchDifferentialFacetsForIdentifier(String identifier) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForIdentifier(identifier);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public String fetchDifferentialResultsForIdentifier(String identifier) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForIdentifier(identifier);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }
}
