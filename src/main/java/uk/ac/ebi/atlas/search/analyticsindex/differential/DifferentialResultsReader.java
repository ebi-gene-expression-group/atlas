package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.*;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class DifferentialResultsReader {

    private final ExperimentTrader experimentTrader;
    private final ContrastTrader contrastTrader;

    private static final String DOCS_PATH                  = "$.response.docs[*]";

    private static final ParseContext parser = JsonPath.using(Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST));

    private final ColourGradient colourGradient;
    private final Gson gson = new Gson();

    @Inject
    public DifferentialResultsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader, ColourGradient colourGradient) {
        this.experimentTrader = experimentTrader;
        this.contrastTrader = contrastTrader;
        this.colourGradient = colourGradient;
    }

    public JsonObject extractResultsAsJson(String solrResponseAsJson) {
        JsonObject resultsWithLevels = new JsonObject();

        double minUpLevel = Double.POSITIVE_INFINITY;
        double maxUpLevel = 0.0;
        double minDownLevel = Double.NEGATIVE_INFINITY;
        double maxDownLevel = 0.0;

        List<JsonObject> filteredDocuments = Lists.newArrayList();
        List<Map<String, Object>> documents = parser.parse(solrResponseAsJson).read(DOCS_PATH);
        JsonArray results = new JsonArray();
        for (Map<String, Object> document : documents) {
            String experimentAccession = (String) document.get("experimentAccession");
            String contrastId = (String) document.get("contrastId");
            ExperimentType experimentType = ExperimentType.get((String) document.get("experimentType"));

            Object foldChangeSymbol = document.get("foldChange");
            double foldChange = foldChangeSymbol instanceof Double ? (double) foldChangeSymbol : Double.parseDouble((String) foldChangeSymbol);

            if (foldChange > 0.0) {
                minUpLevel = Math.min(minUpLevel, foldChange);
                maxUpLevel = Math.max(maxUpLevel, foldChange);
            } else {
                minDownLevel = Math.max(minDownLevel, foldChange);
                maxDownLevel = Math.min(maxDownLevel, foldChange);
            }

            JsonObject o = gson.toJsonTree(document).getAsJsonObject();
            o.addProperty("bioentityIdentifier", (String) document.get("bioentityIdentifier"));
            o.addProperty("experimentAccession", experimentAccession);
            o.addProperty("experimentType", experimentType.toString());
            o.addProperty("contrastId", contrastId);
            o.addProperty("foldChange", foldChange);
            o.addProperty("comparison", contrastTrader.getContrastFromCache(experimentAccession, experimentType, contrastId).getDisplayName());
            o.addProperty("experimentName", experimentTrader.getExperimentFromCache(experimentAccession, experimentType).getDescription());
            filteredDocuments.add(o);
        }
        for (JsonObject document : filteredDocuments) {
            double foldChange = document.get("foldChange").getAsDouble();
            String colour = foldChange > 0.0
                    ? colourGradient.getGradientColour(foldChange, minUpLevel, maxUpLevel, "pink", "red")
                    : colourGradient.getGradientColour(foldChange, minDownLevel, maxDownLevel, "lightGray", "blue");
            document.addProperty("colour", colour);
            document.addProperty("foldChange", FoldChangeRounder.round(foldChange));
            results.add(document);
        }

        resultsWithLevels.add("results", results);
        addDoublePropertyIfValid(resultsWithLevels,"maxDownLevel",maxDownLevel);
        addDoublePropertyIfValid(resultsWithLevels,"minDownLevel",minDownLevel);
        addDoublePropertyIfValid(resultsWithLevels,"minUpLevel",minUpLevel);
        addDoublePropertyIfValid(resultsWithLevels,"maxUpLevel",maxUpLevel);

        return resultsWithLevels;
    }

    private void addDoublePropertyIfValid(JsonObject resultsWithLevels, String name, double value){
        if(!Double.isInfinite(value)&&!Double.isNaN(value)){
            resultsWithLevels.addProperty(name,FoldChangeRounder.round(value));
        }
    }

}
