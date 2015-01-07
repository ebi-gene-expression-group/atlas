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

package uk.ac.ebi.atlas.experimentimport.analytics.index;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class AnalyticsIndexerController {

    private static final Logger LOGGER = Logger.getLogger(AnalyticsIndexerController.class);

    private AnalyticsIndexerService experimentIndexer;

    @Inject
    public AnalyticsIndexerController(AnalyticsIndexerService experimentIndexer) {
        this.experimentIndexer = experimentIndexer;
    }

    @RequestMapping("/analyticsIndex/indexExperiment")
    @ResponseBody
    public String indexExperiment(@RequestParam("accession") String experimentAccession) {
        experimentIndexer.deleteExperimentFromIndex(experimentAccession);

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        int count = experimentIndexer.indexBaselineExperimentAnalytics(experimentAccession);

        stopWatch.stop();

        return String.format("Experiment %s (re)indexed %,d documents in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds());
    }

    @RequestMapping("/analyticsIndex/deleteExperiment")
    @ResponseBody
    public String unindexExperiment(@RequestParam("accession") String experimentAccession) {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        experimentIndexer.deleteExperimentFromIndex(experimentAccession);

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

}