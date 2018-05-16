package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@RestController
public class JsonCellMetadataController extends JsonExperimentController {
    private final CellMetadataService cellMetadataService;

    @Inject
    public JsonCellMetadataController(ScxaExperimentTrader experimentTrader, CellMetadataService cellMetadataService) {
        super(experimentTrader);
        this.cellMetadataService = cellMetadataService;
    }

    @RequestMapping(
            value = "json/experiment/{experimentAccession}/cell/{cellId}/metadata",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String getCellMetadata(
            @PathVariable String experimentAccession,
            @PathVariable String cellId,
            @RequestParam(defaultValue = "") String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        JsonArray result = new JsonArray();

        cellMetadataService
                .getInferredCellType(experiment.getAccession(), cellId)
                .ifPresent(inferredCellType -> result.add(getMetadataObject("Inferred cell type", inferredCellType)));

        cellMetadataService.getFactors(experiment.getAccession(), cellId).forEach((factorName, factorValue) ->
                result.add(
                        getMetadataObject(cellMetadataService.factorFieldNameToDisplayName(factorName), factorValue)));

        return result.toString();
    }

    private JsonObject getMetadataObject(String name, String value) {
        JsonObject result = new JsonObject();

        result.addProperty("displayName", name);
        result.addProperty("value", value);

        return  result;
    }

}
