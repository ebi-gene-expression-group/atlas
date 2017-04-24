package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;

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

    public JsonObject fetchFacets(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {
        String jsonResponse =
                differentialAnalyticsSearchDao.fetchFacetsAboveDefaultFoldChange(
                        geneQuery, conditionQuery, species.getReferenceName());
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchFacets(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        String jsonResponse =
                differentialAnalyticsSearchDao.fetchFacetsAboveDefaultFoldChange(geneQuery, conditionQuery);
        return differentialFacetsReader.generateFacetsTreeJson(jsonResponse);
    }

    public JsonObject fetchResults(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {
        String differentialResults =
                differentialAnalyticsSearchDao.fetchResultsAboveDefaultFoldChange(
                        geneQuery, conditionQuery, species.getReferenceName());
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

    public JsonObject fetchResults(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        String differentialResults =
                differentialAnalyticsSearchDao.fetchResultsAboveDefaultFoldChange(geneQuery, conditionQuery);
        return differentialResultsReader.extractResultsAsJson(differentialResults);
    }

}
