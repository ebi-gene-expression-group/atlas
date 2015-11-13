package uk.ac.ebi.atlas.search.analyticsindex.differential;

import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForSearch(geneQuery);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public String fetchDifferentialResultsForSearch(GeneQuery geneQuery) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(geneQuery);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public String fetchDifferentialResultsForSearch(GeneQuery geneQuery, List<String> species, List<String> experimentType, List<String> kingdoms, List<String> factors, List<Integer> numReplicates, String regulation) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForSearch(geneQuery, species, experimentType, kingdoms, factors, numReplicates, regulation);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public String fetchDifferentialFacetsForIdentifier(GeneQuery geneQuery) {
        String jsonResponse = differentialFacetsDAO.fetchFacetsAboveDefaultFoldChangeForIdentifier(geneQuery);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public String fetchDifferentialResultsForIdentifier(GeneQuery geneQuery) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForIdentifier(geneQuery);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public String fetchDifferentialResultsForIdentifier(GeneQuery geneQuery, List<String> species, List<String> experimentType, List<String> kingdoms, List<String> factors, List<Integer> numReplicates, String regulation) {
        String differentialResults = differentialResultsDAO.fetchDifferentialResultsAboveDefaultFoldChangeForIdentifier(geneQuery, species, experimentType, kingdoms, factors, numReplicates, regulation);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }


}
