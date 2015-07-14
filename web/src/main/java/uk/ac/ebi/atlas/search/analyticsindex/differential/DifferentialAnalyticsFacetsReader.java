package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class DifferentialAnalyticsFacetsReader {

    private final ExperimentTrader experimentTrader;
    private final ContrastTrader contrastTrader;

    private static final String[] FACET_FIELDS = {"kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"};

    private static final String SPECIES_PATH              = "$.facets.species.buckets[*].val";
    private static final String EXPERIMENT_TYPE_PATH      = "$.facets.species.buckets[?(@.val=='%s')].experimentType.buckets[*].val";
    private static final String EXPERIMENT_ACCESSION_PATH = "$.facets.species.buckets[?(@.val=='%s')].experimentType.buckets[?(@.val=='%s')].experimentAccession.buckets[*].val";
    private static final String CONTRASTID_PATH           = "$.facets.species.buckets[?(@.val=='%s')].experimentType.buckets[?(@.val=='%s')].experimentAccession.buckets[?(@.val=='%s')].contrastId.buckets[*].val";
    private static final String GENE_COUNT_PATH           = "$.facets.species.buckets[?(@.val=='%s')].experimentType.buckets[?(@.val=='%s')].experimentAccession.buckets[?(@.val=='%s')].contrastId.buckets[?(@.val=='%s')].geneCount";

    @Inject
    public DifferentialAnalyticsFacetsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader) {
        this.experimentTrader = experimentTrader;
        this.contrastTrader = contrastTrader;
    }


    public String extractResultsAsJson(String solrResponseAsJson) {
        JsonArray differentialResults = new JsonArray();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        for (String species : (List<String>) jsonReadContext.read(SPECIES_PATH)) {
            for (String experimentTypeDesc : (List<String>) jsonReadContext.read(String.format(EXPERIMENT_TYPE_PATH, species))) {
                for (String experimentAccession: (List<String>) jsonReadContext.read(String.format(EXPERIMENT_ACCESSION_PATH, species, experimentTypeDesc))) {
                    for (String contrastId : (List<String>) jsonReadContext.read(String.format(CONTRASTID_PATH, species, experimentTypeDesc, experimentAccession))) {
                        int geneCount = ((List<Integer>) jsonReadContext.read(String.format(GENE_COUNT_PATH, species, experimentTypeDesc, experimentAccession, contrastId))).get(0);
                        ExperimentType experimentType = ExperimentType.get(experimentTypeDesc);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("geneCount", geneCount);
                        jsonObject.addProperty("organism", species);
                        jsonObject.addProperty("contrastId", contrastId);
                        jsonObject.addProperty("comparison", contrastTrader.getContrastFromCache(experimentAccession, experimentType, contrastId).getDisplayName());
                        jsonObject.addProperty("experimentAccession", experimentAccession);
                        jsonObject.addProperty("experimentName", experimentTrader.getExperimentFromCache(experimentAccession, experimentType).getDescription());
                        differentialResults.add(jsonObject);
                    }
                }
            }
        }

        return differentialResults.toString();
    }

    // TODO Prettify fields with a Hashmap: <Field as it is stored in Solr> -> <Pretty field>
    public String generateFacetsTreeJson(String solrResponseAsJson) {
        JsonObject facets = new JsonObject();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        for (String facetField : FACET_FIELDS) {
            JsonArray facet = new JsonArray();
            for (Object facetFieldValue : (List<Object>) jsonReadContext.read("$.." + facetField + "..val")) {
                JsonObject facetItem = new JsonObject();
                facetItem.addProperty("name", facetFieldValue.toString());
                facetItem.addProperty("value", facetFieldValue.toString());  // TODO Prettify (see above)
                facet.add(facetItem);
            }
            facets.add(facetField, facet);
        }

        return facets.toString();
    }
}
