package uk.ac.ebi.atlas.metadata;

import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.metadata.CellMetadataService;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

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

        Map<String, String> metadataByCellIds = cellMetadataService.getValuesForMetadataCategory("E-EHCA-2", SingleCellAnalyticsCollectionProxy.factorAsSchemaField("sampling_site"), Arrays.asList("21784_6_100", "21784_6_103", "21784_6_125"));

        return GSON.toJson(metadataByCellIds);
//        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
//
//        JsonArray result = new JsonArray();
//
//        cellMetadataService
//                .getInferredCellType(experiment.getAccession(), cellId)
//                .ifPresent(inferredCellType -> result.add(createMetadataJson("Inferred cell type", inferredCellType)));
//
//        cellMetadataService.getFactors(experiment.getAccession(), cellId).forEach((factorName, factorValue) ->
//                result.add(createMetadataJson(factorName, factorValue)));
//
//        return result.toString();
    }

    private JsonObject createMetadataJson(String name, String value) {
        JsonObject result = new JsonObject();

        result.addProperty("displayName", name);
        result.addProperty("value", value);

        return  result;
    }

}
