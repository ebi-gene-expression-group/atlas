package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.alreadyForwardedButNoOtherControllerHandledTheRequest;
import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.buildForwardURL;

@Controller
public final class ExperimentDownloadDispatcher {

    public static final String url = "/experiments-content/{experimentAccession}/download";

    private ExperimentTrader experimentTrader;

    @Inject
    private ExperimentDownloadDispatcher(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = url)
    public String dispatch(HttpServletRequest request,
                           @PathVariable String experimentAccession,
                           @RequestParam(required = false) String accessKey) {
        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            throw new NoExperimentSubResourceException();
        }

        return "forward:" + buildForwardURL(request, experimentTrader.getExperiment(experimentAccession, accessKey));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NoExperimentSubResourceException extends RuntimeException {}

}
