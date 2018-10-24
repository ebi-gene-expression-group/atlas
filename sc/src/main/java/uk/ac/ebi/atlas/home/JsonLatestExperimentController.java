package uk.ac.ebi.atlas.home;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;


@RestController
public class JsonLatestExperimentController extends JsonExceptionHandlingController {

    private final LatestExperimentsService latestExperimentsService;

    @Inject
    public JsonLatestExperimentController (LatestExperimentsDao latestExperimentsDao, ScxaExperimentTrader experimentTrader) {
        this.latestExperimentsService = new LatestExperimentsService(latestExperimentsDao, experimentTrader);
    }

    @GetMapping(value = "/json/experiments/latestExperiments")
    public String getLatestExperiments(){
        return latestExperimentsService.fetchLatestExperimentsJSON();
    }
}
