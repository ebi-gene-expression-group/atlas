package uk.ac.ebi.atlas.tracks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;

@Controller
public class TracksController extends HtmlExceptionHandlingController {

    private final ExperimentTrader experimentTrader;

    @Inject
    public TracksController(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments-content/{experimentAccession}/tracks/{trackFileName:.*}",
                    method = {RequestMethod.GET})
    public String forwardToTrackFile(@PathVariable String experimentAccession,
                                     @PathVariable String trackFileName,
                                     @RequestParam(required = false, defaultValue = "") String accessKey)
    throws IOException {
        // We only want to check that if the experiment is private the correct accessKey has been provided, otherwise
        // the following will throw an exception
        experimentTrader.getExperiment(experimentAccession, accessKey);

        String path = String.format("/expdata/%s/%s", experimentAccession, trackFileName);
        return "forward:" + path;
    }

}
