package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.utils.GsonProvider;

import javax.inject.Inject;
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

        // TODO Query to Solr to get "characteristic_inferred_cell_type"
        // if that is empty then parse idf for Comment[EAAdditionalAttributes]

        Optional<String> inferredCellType = cellMetadataService.getInferredCellType(experimentAccession, cellId);

        if (inferredCellType.isPresent()) {
            return GsonProvider.GSON.toJson(inferredCellType.get());
        }
        else {
            Optional<String> attributeFromIdf = cellMetadataService.getAttributeFromIdfFile(experimentAccession, cellId);
        }

        return "";
    }
}
