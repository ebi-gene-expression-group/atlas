package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class DifferentialAnalyticsFacetsReader {

    private final ExperimentTrader experimentTrader;
    private final ContrastTrader contrastTrader;
    private final FoldChangeRounder foldChangeRounder;

    private static final String[] FACET_FIELDS = {"kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"};

    private static final String DOCS_PATH                  = "$.response.docs[*]";
    private static final String LOG2_FOLD_CHANGE_PATH      = "$.response.docs[*].foldChange";
    private static final String EXPERIMENT_TYPE_FIELD      = "experimentType";
    private static final String EXPERIMENT_ACCESSION_FIELD = "experimentAccession";
    private static final String CONTRAST_ID_FIELD          = "contrastId";
    private static final String LOG2_FOLD_CHANGE_FIELD     = "foldChange";

    private final ColourGradient colourGradient;

    @Inject
    public DifferentialAnalyticsFacetsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader, FoldChangeRounder foldChangeRounder, ColourGradient colourGradient) {
        this.experimentTrader = experimentTrader;
        this.contrastTrader = contrastTrader;
        this.foldChangeRounder = foldChangeRounder;
        this.colourGradient = colourGradient;
    }


    public String extractResultsAsJson(String solrResponseAsJson) {
        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        double minUpLevel = Double.POSITIVE_INFINITY, maxUpLevel = 0.0, minDownLevel = Double.NEGATIVE_INFINITY, maxDownLevel = 0.0;
        // We can’t use List<Double> https://github.com/jayway/JsonPath/issues/130
        List<Object> foldChanges = jsonReadContext.read(LOG2_FOLD_CHANGE_PATH);
        for (Object foldChangeSymbol : foldChanges) {
            double foldChange = foldChangeSymbol instanceof Double ? (double) foldChangeSymbol : Double.parseDouble((String) foldChangeSymbol);

            if (foldChange > 0.0) {
                minUpLevel = Math.min(minUpLevel, foldChange);
                maxUpLevel = Math.max(maxUpLevel, foldChange);
            } else {
                minDownLevel = Math.max(minDownLevel, foldChange);
                maxDownLevel = Math.min(maxDownLevel, foldChange);
            }
        }

        Map<String, Object> resultsWithLevels = new HashMap<>(2);

        List<Map<String, Object>> documents = jsonReadContext.read(DOCS_PATH);
        for (Map<String, Object> document : documents) {
            ExperimentType experimentType = ExperimentType.get((String) document.get(EXPERIMENT_TYPE_FIELD));
            String experimentAccession = (String) document.get(EXPERIMENT_ACCESSION_FIELD);
            String contrastId = (String) document.get(CONTRAST_ID_FIELD);

            Object foldChangeSymbol = document.get(LOG2_FOLD_CHANGE_FIELD);
            double foldChange = foldChangeSymbol instanceof Double ? (double) foldChangeSymbol : Double.parseDouble((String) foldChangeSymbol);
            String colour = foldChange > 0.0 ? colourGradient.getGradientColour(foldChange, minUpLevel, maxUpLevel, "pink", "red") : colourGradient.getGradientColour(foldChange, minDownLevel, maxDownLevel, "lightGray", "blue");

            document.put("foldChange", foldChangeRounder.format(foldChange));
            document.put("colour", colour);
            document.put("comparison", contrastTrader.getContrastFromCache(experimentAccession, experimentType, contrastId).getDisplayName());
            document.put("experimentName", experimentTrader.getExperimentFromCache(experimentAccession, experimentType).getDescription());

        }
        resultsWithLevels.put("results", documents);

        if (documents.size() > 0) {
            resultsWithLevels.put("maxDownLevel", foldChangeRounder.format(maxDownLevel));
            resultsWithLevels.put("minDownLevel", foldChangeRounder.format(minDownLevel));
            resultsWithLevels.put("minUpLevel", foldChangeRounder.format(minUpLevel));
            resultsWithLevels.put("maxUpLevel", foldChangeRounder.format(maxUpLevel));
        }

        return gson.toJson(resultsWithLevels);
    }

    // TODO Prettify fields with a Hashmap: <Field as it is stored in Solr> -> <Pretty field>
    public String generateFacetsTreeJson(String solrResponseAsJson) {
        JsonObject facets = new JsonObject();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        for (String facetField : FACET_FIELDS) {
            JsonArray facet = new JsonArray();
            String fField="";
            for (Object facetFieldValue : (List<Object>) jsonReadContext.read("$.." + facetField + "..val")) {
                JsonObject facetItem = new JsonObject();
                facetItem.addProperty("name", facetFieldValue.toString());
                if(facetField.equals("experimentType")) {
                    facetItem.addProperty("value", ExperimentsTypeMapConverter.getType(facetFieldValue.toString()));
                    fField = "Experiment type";
                } else if(facetField.equals("numReplicates")) {
                    fField = "Number of replicates";
                    facetItem.addProperty("value", facetFieldValue.toString());
                } else if(facetField.equals("regulation")) {
                    facetItem.addProperty("value", facetFieldValue.toString().toLowerCase());
                }
                else {
                    facetItem.addProperty("value", facetFieldValue.toString());
                }
                facet.add(facetItem);
            }

            if(!fField.isEmpty()) {
                facets.add(fField, facet);
            } else {
                facets.add(facetField, facet);
            }
        }

        return facets.toString();
    }

    protected static class ExperimentsTypeMapConverter {
        private static final Logger LOGGER = Logger.getLogger(ExperimentsTypeMapConverter.class);

        private static final Map<String,String> EXPERIMENTS_TYPE_MAP = ImmutableMap.<String, String>builder()
                .put("rnaseq_mrna_baseline", "RNA-seq mRNA baseline")
                .put("rnaseq_mrna_differential", "RNA-seq mRNA differential")
                .put("proteomics_baseline", "proteomics baseline")
                .put("microarray_1colour_microrna_differential", "microarray 1-colour microRNA differential")
                .put("microarray_1colour_mrna_differential", "microarray 1-colour mRNA differential")
                .put("microarray_2colour_mrna_differential", "microarray 2-colour mRNA differential")
                .build();

        public static String getType(String type) {
            if(EXPERIMENTS_TYPE_MAP.get(type) != null) {
                return EXPERIMENTS_TYPE_MAP.get(type);
            } else {
                LOGGER.warn(String.format("Experiment type not found %s", type));
                return type;
            }
        }
    }
}
