package uk.ac.ebi.atlas.search.analyticsindex.differential;

import uk.ac.ebi.atlas.search.GeneQuery;

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

    public String fetchDifferentialFacetsForSearch(GeneQuery geneQuery) {
        return fetchDifferentialFacetsForSearch(geneQuery, "");
    }

    public String fetchDifferentialFacetsForSearch(GeneQuery geneQuery, String species) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForSearch(geneQuery, species);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }


    public String fetchDifferentialResultsForSearch(GeneQuery geneQuery) {
        return fetchDifferentialResultsForSearch(geneQuery, "");
    }

    public String fetchDifferentialResultsForSearch(GeneQuery geneQuery, String species) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(geneQuery, species);
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
