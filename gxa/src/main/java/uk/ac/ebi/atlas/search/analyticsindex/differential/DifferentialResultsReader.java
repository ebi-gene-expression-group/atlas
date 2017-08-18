package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ParseContext;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Named
public class DifferentialResultsReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialResultsReader.class);

    private final ExperimentTrader experimentTrader;

    private static final String DOCS_PATH = "$.response.docs[*]";

    private static final ParseContext parser = JsonPath.using(Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST));

    private final Gson gson = new Gson();

    @Inject
    public DifferentialResultsReader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public JsonObject extractResultsAsJson(String solrResponseAsJson, LinkToContrast linkToContrast) {
        JsonObject resultsWithLevels = new JsonObject();

        double minUpLevel = Double.POSITIVE_INFINITY;
        double maxUpLevel = 0.0;
        double minDownLevel = Double.NEGATIVE_INFINITY;
        double maxDownLevel = 0.0;

        List<JsonObject> filteredDocuments = Lists.newArrayList();
        List<Map<String, Object>> documents = parser.parse(solrResponseAsJson).read(DOCS_PATH);
        JsonArray results = new JsonArray();
        goThroughDocuments:
        for (Map<String, Object> document : documents) {
            String experimentAccession = (String) document.get("experiment_accession");
            String contrastId = (String) document.get("contrast_id");
            ExperimentType experimentType = ExperimentType.get((String) document.get("experiment_type"));

            String bioentityName = "";
            Object bioentityNamesOrEmpty = document.get("keyword_symbol");
            if(bioentityNamesOrEmpty!=null && bioentityNamesOrEmpty instanceof Collection && ((Collection) bioentityNamesOrEmpty).size()>0){
                bioentityName = ((Collection) bioentityNamesOrEmpty).iterator().next().toString();
            }

            double foldChange = castObjectToDouble(document.get("fold_change"));

            if (foldChange > 0.0) {
                minUpLevel = Math.min(minUpLevel, foldChange);
                maxUpLevel = Math.max(maxUpLevel, foldChange);
            } else {
                minDownLevel = Math.max(minDownLevel, foldChange);
                maxDownLevel = Math.min(maxDownLevel, foldChange);
            }

            double pValue = castObjectToDouble(document.get("p_value"));

            DifferentialExperiment experiment;
            try {
                experiment= (DifferentialExperiment)  experimentTrader.getExperimentFromCache(experimentAccession, experimentType);
            } catch (ExecutionException e) {
                LOGGER.error("Error adding differential result: {}", e.getMessage());
                continue goThroughDocuments;
            }

            Contrast contrast = experiment.getDataColumnDescriptor(contrastId);

            JsonObject o = gson.toJsonTree(document).getAsJsonObject();
            o.addProperty("bioentityIdentifier", (String) document.get("bioentity_identifier"));
            o.addProperty("numReplicates", (int) document.get("num_replicates"));
            o.addProperty("bioentityName", bioentityName);
            o.addProperty("experimentAccession", experimentAccession);
            o.addProperty("experimentType", experimentType.toString());
            o.addProperty("contrastId", contrastId);
            o.addProperty("foldChange", foldChange);
            o.addProperty("pValue", pValue);
            o.addProperty("comparison", contrast.getDisplayName());
            o.addProperty("experimentName",experiment.getDescription());
            o.addProperty("uri", linkToContrast.apply(Pair.of(experimentAccession, contrast)).toString());

            if (document.containsKey("t_statistic")) {
                o.addProperty("tStatistic", castObjectToDouble(document.get("t_statistic")));
            }

            filteredDocuments.add(o);
        }
        for (JsonObject document : filteredDocuments) {
            double foldChange = document.get("fold_change").getAsDouble();
            String colour = foldChange > 0.0
                    ? ColourGradient.getGradientColour(foldChange, minUpLevel, maxUpLevel, "pink", "red")
                    : ColourGradient.getGradientColour(foldChange, minDownLevel, maxDownLevel, "lightGray", "blue");
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

    private double castObjectToDouble(Object object) {
        return object instanceof BigDecimal ? ((BigDecimal) object).doubleValue() :
               object instanceof Double ? (double) object :
               Double.parseDouble((String) object);
    }

    private void addDoublePropertyIfValid(JsonObject resultsWithLevels, String name, double value){
        if(!Double.isInfinite(value)&&!Double.isNaN(value)){
            resultsWithLevels.addProperty(name,FoldChangeRounder.round(value));
        }
    }

}
