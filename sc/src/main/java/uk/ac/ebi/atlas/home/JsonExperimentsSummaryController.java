package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonExperimentsSummaryController extends JsonExceptionHandlingController {
    private final LatestExperimentsService latestExperimentsService;

    public JsonExperimentsSummaryController(LatestExperimentsService latestExperimentsService) {
        this.latestExperimentsService = latestExperimentsService;
    }

    @GetMapping(value = "/json/experiments-summary",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getLatestExperiments() {
        return GSON.toJson(
                ImmutableMap.of(
                        "latestExperiments",
                        latestExperimentsService.fetchLatestExperimentsAttributes().get("latestExperiments"),
                        "featuredExperiments",
                        ImmutableList.of()));
    }
}
