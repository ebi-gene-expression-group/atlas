/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.experimentpage;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a global router for the experiment page.
 * It looks up the kind of experiment and sends the request to the right controller.
 *
 */

@Controller
public final class ExperimentDispatcher {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private ExperimentTrader experimentTrader;

    @Inject
    private ExperimentDispatcher(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}", "/experiments/{experimentAccession}/*"})
    public String dispatch(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String experimentAccession,
                           @RequestParam(value = "accessKey",required = false) String accessKey, Model model) {

        if (alreadyForwardedButNoOtherControllerHandledTheRequest(request)) {
            // prevent an infinite loop
            throw new NoExperimentSubResourceException();
        }

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);

        return "forward:" + buildForwardURL(request, experiment, accessKey);
    }

    private boolean alreadyForwardedButNoOtherControllerHandledTheRequest(HttpServletRequest request) {
        return StringUtils.startsWith(request.getQueryString(), "type=");
    }

    private String buildForwardURL(HttpServletRequest request, Experiment experiment, String accessKey) {
        String requestURL = getRequestURL(request);
        requestURL += "?type=" + experiment.getType().getParent();
        if (StringUtils.isNotBlank(accessKey)){
            requestURL += "&accessKey=" + accessKey;
        }
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
        ModelAndView mav = new ModelAndView("experiment-notFound-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NoExperimentSubResourceException extends RuntimeException {}

}
