package uk.ac.ebi.atlas.home;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

@RestController
public class JsonLatestExperimentController extends JsonExceptionHandlingController {

    private final LatestExperimentsService latestExperimentsService;

    public JsonLatestExperimentController(LatestExperimentsService latestExperimentsService) {
        this.latestExperimentsService = latestExperimentsService;
    }

    @GetMapping(
            value = "/json/experiments/latestExperiments",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getLatestExperiments() {
        return latestExperimentsService.fetchLatestExperimentsJSON();
    }
}
