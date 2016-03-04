/*
 * Copyright 2008-2016 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
 * Expression Atlas:
 * https://www.ebi.ac.uk/gxa
 *
 * For further details of the Expression Atlas project, including source code,
 * downloads and documentation, please see:
 * https://github.com/gxa/atlas
 */

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 04/03/2016.
 */

package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class BaselineCoexpressionsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionsController.class);

    private BaselineCoexpressionsLoader baselineCoexpressionsLoader;

    @Inject
    public BaselineCoexpressionsController(BaselineCoexpressionsLoader baselineCoexpressionsLoader) {
        this.baselineCoexpressionsLoader = baselineCoexpressionsLoader;
    }

    @RequestMapping(value = "/importAllCoexpressions", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String analyticsIndexBuild() {

        return("");
    }


    @RequestMapping(value = "/importCoexpressions", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String importCoexpressions(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        int count = 0;
//        int count = analyticsIndexerManager.addToAnalyticsIndex(experimentAccession);
        baselineCoexpressionsLoader.loadBaselineCoexpressions(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s coexpressions imported: %,d genes in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping(value = "/deleteCoexpressions", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteCoexpressions(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        baselineCoexpressionsLoader.deleteCoexpressions(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s coexpressions deleted in %s seconds", experimentAccession, stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping(value = "/updateCoexpressions", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateCoexpressions(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        baselineCoexpressionsLoader.deleteCoexpressions(experimentAccession);
        baselineCoexpressionsLoader.loadBaselineCoexpressions(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s coexpressions updated in %s seconds", experimentAccession, stopWatch.getTotalTimeSeconds());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) throws IOException {
        String lineSeparator = "<br>";
        LOGGER.error(e.getMessage(), e);

        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(lineSeparator);

        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append(lineSeparator);
        }

        return sb.toString();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException(Exception e) throws IOException {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }

}