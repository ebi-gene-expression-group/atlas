package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class DifferentialAnalyticsFacetsReader {

    private final ExperimentTrader experimentTrader;
    private final ContrastTrader contrastTrader;

    private static final String SPECIES_PATH              = "$.facets.species.buckets[*].val";
    private static final String EXPERIMENT_ACCESSION_PATH = "$.facets.species.buckets[?(@.val=='%s')].experimentAccession.buckets[*].val";
    private static final String CONTRASTID_PATH           = "$.facets.species.buckets[?(@.val=='%s')].experimentAccession.buckets[?(@.val=='%s')].contrastId.buckets[*].val";
    private static final String GENE_COUNT_PATH           = "$.facets.species.buckets[?(@.val=='%s')].experimentAccession.buckets[?(@.val=='%s')].contrastId.buckets[?(@.val=='%s')].geneCount";

    @Inject
    public DifferentialAnalyticsFacetsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader) {
        this.experimentTrader = experimentTrader;
        this.contrastTrader = contrastTrader;
    }

    public JsonArray extractResultsAsJson(String solrResponseAsJson) {
        JsonArray differentialResults = new JsonArray();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        for (String species : (List<String>) jsonReadContext.read(SPECIES_PATH)) {
            for (String experimentAccession: (List<String>) jsonReadContext.read(String.format(EXPERIMENT_ACCESSION_PATH, species))) {
                for (String contrastId : (List<String>) jsonReadContext.read(String.format(CONTRASTID_PATH, species, experimentAccession))) {
                    int geneCount = ((List<Integer>) jsonReadContext.read(String.format(GENE_COUNT_PATH, species, experimentAccession, contrastId))).get(0);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("geneCount", geneCount);
                    jsonObject.addProperty("organism", species);
                    jsonObject.addProperty("contrastId", contrastId);
                    jsonObject.addProperty("comparison", contrastTrader.getContrast(experimentAccession, contrastId).getDisplayName());
                    jsonObject.addProperty("experimentAccession", experimentAccession);
                    jsonObject.addProperty("experimentName", experimentTrader.getPublicExperiment(experimentAccession).getDescription());
                    differentialResults.add(jsonObject);
                }
            }
        }

        return differentialResults;
    }
}
