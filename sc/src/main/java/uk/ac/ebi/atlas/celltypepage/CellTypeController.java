package uk.ac.ebi.atlas.celltypepage;

import com.google.gson.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SingleCellAtlasExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class CellTypeController {

    private final ExperimentTrader experimentTrader;

    private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


    @Inject
    public CellTypeController(SingleCellAtlasExperimentTrader experimentTrader){
        this.experimentTrader = experimentTrader;
    }


    @RequestMapping(value = "/cell_types")
    @ResponseBody
    public String cellTypes(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        JsonObject result = new JsonObject();
        for(Map.Entry<String, Map<String,Set<String>>> e: cellTypePerExperimentAndId().entrySet()){
            JsonArray a = new JsonArray();
            for(Map.Entry<String,Set<String>> m: e.getValue().entrySet()){
                JsonObject oo = new JsonObject();
                oo.addProperty("experiment accession", m.getKey());
                JsonArray aa = new JsonArray();
                for(String s: m.getValue()){
                    aa.add(new JsonPrimitive(s));
                }
                oo.add("cell ids", aa);
                a.add(oo);
            }
            result.add(e.getKey(), a);
        }
        return gson.toJson(result);
    }

    @RequestMapping(value = "/cell_types/{cellType}")
    @ResponseBody
    public String cellType(@PathVariable String cellType, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return gson.toJson(cellTypePerExperimentAndId().get(cellType));
    }

    Map<String, Map<String,Set<String>>> cellTypePerExperimentAndId(){
        Map<String, Map<String,Set<String>>> result = new HashMap<>();
        for(String experimentAccession: experimentTrader.getPublicExperimentAccessions(ExperimentType
                .SINGLE_CELL_RNASEQ_MRNA_BASELINE)){
            ExperimentDesign experimentDesign = experimentTrader.getPublicExperiment(experimentAccession)
                    .getExperimentDesign();

            for(String cellId: experimentDesign.getAllRunOrAssay()){
                String cellType = experimentDesign.getFactorValue(cellId, "cell type");
                if(StringUtils.isNotEmpty(cellType)){
                    if(!result.containsKey(cellType)){
                        result.put(cellType, new HashMap<String, Set<String>>());
                    }
                    if(!result.get(cellType).containsKey(experimentAccession)){
                        result.get(cellType).put(experimentAccession, new HashSet<String>());
                    }
                    result.get(cellType).get(experimentAccession).add(cellId);
                }
            }
        }

        return result;

    }
}
