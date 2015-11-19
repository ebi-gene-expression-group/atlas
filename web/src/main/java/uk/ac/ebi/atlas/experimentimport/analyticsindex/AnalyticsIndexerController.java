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

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class AnalyticsIndexerController {

    private static final Logger LOGGER = LogManager.getLogger(AnalyticsIndexerController.class);

    private AnalyticsIndexerManager analyticsIndexerManager;
    private AnalyticsIndexerMonitor analyticsIndexerMonitor;

    @Inject
    public AnalyticsIndexerController(AnalyticsIndexerManager analyticsIndexerManager, AnalyticsIndexerMonitor analyticsIndexerMonitor) {
        this.analyticsIndexerManager = analyticsIndexerManager;
        this.analyticsIndexerMonitor = analyticsIndexerMonitor;
    }

    @RequestMapping(value = "/analyticsIndex/buildIndex", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String analyticsIndexBuild(@RequestParam(value = "type", required = false, defaultValue = "") String experimentType,
                                      @RequestParam(value = "threads", required = false, defaultValue =  AnalyticsIndexerManager.DEFAULT_THREADS_8) int numThreads,
                                      @RequestParam(value = "batchSize", required = false, defaultValue = AnalyticsIndexerManager.DEFAULT_SOLR_BATCH_SIZE_1024) int batchSize,
                                      @RequestParam(value = "timeout", required = false, defaultValue = AnalyticsIndexerManager.DEFAULT_TIMEOUT_IN_HOURS_24) int timeout) {

        try {
            if (!Strings.isNullOrEmpty(experimentType)) {
                if (ExperimentType.get(experimentType) == null) {
                    throw new IllegalArgumentException("Unknown experiment type " + experimentType);
                }
                analyticsIndexerManager.indexPublicExperiments(ExperimentType.get(experimentType), numThreads, batchSize, timeout);
            } else {
                analyticsIndexerManager.indexAllPublicExperiments(numThreads, batchSize, timeout);
            }
        } catch (InterruptedException e) {
            return Arrays.deepToString(e.getStackTrace());
        }

        return analyticsIndexerMonitor.toString();
    }

    @RequestMapping(value = "/analyticsIndex/buildIndex/status", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String analyticsIndexBuildStatus() {
        return analyticsIndexerMonitor.toString();
    }

    @RequestMapping(value = "/analyticsIndex/indexExperiment", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String indexExperiment(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        int count = analyticsIndexerManager.addToAnalyticsIndex(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s (re)indexed %,d documents in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping(value = "/analyticsIndex/deleteExperiment", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String unindexExperiment(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        analyticsIndexerManager.deleteFromAnalyticsIndex(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s removed from index in %s seconds", experimentAccession, stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping(value = "/analyticsIndex/updateIdentifierSearchTerms", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateIdentifierSearchTerms(@RequestParam(value = "accession", required = false) String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();
//
//        int updatedIdentifiers = 0;
//        if (Strings.isNullOrEmpty(experimentAccession)) {
//            identifierSearchTermsTrader.init();
//        } else {
//            updatedIdentifiers = identifierSearchTermsManager.updateSearchTerms(experimentAccession);
//        }
//
        stopWatch.stop();
//
//        String message = Strings.isNullOrEmpty(experimentAccession) ?
//                String.format("All experiments search terms added for %,d identifiers in %s seconds", updatedIdentifiers, stopWatch.getTotalTimeSeconds()) :
//                String.format("Experiment %s search terms added for %,d identifiers in %s seconds", experimentAccession, updatedIdentifiers, stopWatch.getTotalTimeSeconds());
//        return message;

        return "Operation not supported";
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