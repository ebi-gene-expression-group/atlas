
package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.model.Experiment;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * This is a global router for the experiment page.
 * It looks up the kind of experiment and sends the request to the right controller.
 */

@Controller
public final class ExperimentDispatcher {

    private ExperimentTrader experimentTrader;

    @Inject
    private ExperimentDispatcher(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}", "/experiments/{experimentAccession}/*"})
    public String dispatch(HttpServletRequest request,
                           @PathVariable String experimentAccession,
                           @RequestParam(required = false) String accessKey) {
        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            throw new NoExperimentSubResourceException();
        }

        return "forward:" + buildForwardURL(request, experimentTrader.getExperiment(experimentAccession, accessKey));
    }

    @RequestMapping(value = {"/json/experiments/{experimentAccession}", "/json/experiments/{experimentAccession}/*"})
    public String dispatchData(HttpServletRequest request,
                           @PathVariable String experimentAccession,
                           @RequestParam(required = false) String accessKey) {
        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            throw new NoExperimentSubResourceException();
        }

        return "forward:" + buildForwardURL(request, experimentTrader.getExperiment(experimentAccession, accessKey));
    }

    private boolean alreadyForwardedButNoOtherControllerHandledTheRequest(HttpServletRequest request) {
        return StringUtils.startsWith(request.getQueryString(), "type=");
    }

    private String buildForwardURL(HttpServletRequest request, Experiment experiment) {
        String requestURL = getRequestURL(request);
        requestURL += "?type=" + experiment.getType().getParent();

        return requestURL;
    }

    private String getRequestURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        return StringUtils.substringAfter(requestURI, contextPath);
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
