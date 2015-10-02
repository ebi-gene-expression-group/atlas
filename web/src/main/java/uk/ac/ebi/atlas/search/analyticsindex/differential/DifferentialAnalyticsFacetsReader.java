package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
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

        double minUpLevel = Double.MAX_VALUE, maxUpLevel = 0.0, minDownLevel = Double.MAX_VALUE * -1.0, maxDownLevel = 0.0;
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
