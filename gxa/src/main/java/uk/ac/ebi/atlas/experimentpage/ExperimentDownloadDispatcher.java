package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.alreadyForwardedButNoOtherControllerHandledTheRequest;
import static uk.ac.ebi.atlas.experimentpage.ExperimentDispatcherUtils.buildForwardURL;

/**
 * This is what used to be a global router for the experiment page.
 * We only use it for redirecting downloads now.
 */
@Controller
public final class ExperimentDownloadDispatcher {

    public static final String url = "/experiments-content/download/{experimentAccession}";

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

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("experiment-not-found-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NoExperimentSubResourceException extends RuntimeException {}

}
