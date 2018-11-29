package uk.ac.ebi.atlas.home;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

@RestController
public class JsonLatestExperimentController extends JsonExceptionHandlingController {
    private final LatestExperimentsService latestExperimentsService;

    public JsonLatestExperimentController (LatestExperimentsDao latestExperimentsDao, ScxaExperimentTrader experimentTrader) {
        this.latestExperimentsService = new LatestExperimentsService(latestExperimentsDao, experimentTrader);
    }
    @GetMapping(
            value = "/json/experiments/latestExperiments",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getLatestExperiments(){
        return latestExperimentsService.fetchLatestExperimentsJSON();
    }
}