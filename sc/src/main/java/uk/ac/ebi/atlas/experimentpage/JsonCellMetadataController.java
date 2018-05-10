package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

        JsonObject result = new JsonObject();

        inferredCellType.ifPresent(s -> result.addProperty("inferredCellType", s));

        for(Map.Entry<String, String> entry : cellMetadataService.getAttributeFromIdfFile(experimentAccession, cellId).entrySet()) {
            result.addProperty(entry.getKey(), entry.getValue());
        }

        return result.toString();
    }
}
