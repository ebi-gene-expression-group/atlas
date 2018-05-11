package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@RestController
public class JsonCellMetadataController {
    private final CellMetadataService cellMetadataService;

    @Inject
    public JsonCellMetadataController(CellMetadataService cellMetadataService) {
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

        Optional<String> inferredCellType = cellMetadataService.getInferredCellType(experimentAccession, cellId);

        JsonArray result = new JsonArray();

        inferredCellType.ifPresent(value -> result.add(getMetadataObject("Inferred cell type", value)));

        for(Map.Entry<String, String> entry : cellMetadataService.getFactors(experimentAccession, cellId).entrySet()) {
            result.add(
                    getMetadataObject(
                            cellMetadataService.factorFieldNameToDisplayName(entry.getKey()),
                            entry.getValue()));
        }

        return result.toString();
    }

    private JsonObject getMetadataObject(String name, String value) {
        JsonObject result = new JsonObject();

        result.addProperty("displayName", name);
        result.addProperty("value", value);

        return  result;
    }

}
