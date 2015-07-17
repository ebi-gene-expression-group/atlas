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

package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.apache.log4j.Logger;
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
public class AnalyticsIndexerController {

    private static final Logger LOGGER = Logger.getLogger(AnalyticsIndexerController.class);

    private AnalyticsIndexerService analyticsIndexerService;

    @Inject
    public AnalyticsIndexerController(AnalyticsIndexerService analyticsIndexerService) {
        this.analyticsIndexerService = analyticsIndexerService;
    }

    @RequestMapping("/analyticsIndex/indexAllExperiments")
    @ResponseBody
    public String indexAllPublicExperiments() {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        try {
            analyticsIndexerService.indexAllPublicExperiments();
        } catch (InterruptedException e) {
            return e.getStackTrace().toString();
        }

        stopWatch.stop();

        return String.format("All experiments (re)indexed in %s seconds", stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping("/analyticsIndex/indexExperiment")
    @ResponseBody
    public String indexExperiment(@RequestParam("accession") String experimentAccession) {
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        int count = analyticsIndexerService.index(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s (re)indexed %,d documents in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping("/analyticsIndex/deleteExperiment")
    @ResponseBody
    public String unindexExperiment(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s removed from index in %s seconds", experimentAccession, stopWatch.getTotalTimeSeconds());
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