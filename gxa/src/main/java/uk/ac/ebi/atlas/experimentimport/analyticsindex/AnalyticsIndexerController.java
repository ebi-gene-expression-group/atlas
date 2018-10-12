package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

import static uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager.DEFAULT_SOLR_BATCH_SIZE;
import static uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager.DEFAULT_THREADS;
import static uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager.DEFAULT_TIMEOUT_IN_HOURS;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class AnalyticsIndexerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexerController.class);

    private AnalyticsIndexerManager analyticsIndexerManager;
    private AnalyticsIndexerMonitor analyticsIndexerMonitor;

    @Inject
    public AnalyticsIndexerController(AnalyticsIndexerManager analyticsIndexerManager,
                                      AnalyticsIndexerMonitor analyticsIndexerMonitor) {
        this.analyticsIndexerManager = analyticsIndexerManager;
        this.analyticsIndexerMonitor = analyticsIndexerMonitor;
    }

    @RequestMapping(value = "/analyticsIndex/buildIndex", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String analyticsIndexBuild(
            @RequestParam(value = "type", required = false, defaultValue = "") String experimentType,
            @RequestParam(value = "threads", required = false, defaultValue = DEFAULT_THREADS) int numThreads,
            @RequestParam(value = "batchSize", required = false, defaultValue = DEFAULT_SOLR_BATCH_SIZE) int batchSize,
            @RequestParam(value = "timeout", required = false, defaultValue = DEFAULT_TIMEOUT_IN_HOURS) int timeout) {

        try {
            if (!Strings.isNullOrEmpty(experimentType)) {
                if (ExperimentType.get(experimentType) == null) {
                    throw new IllegalArgumentException("Unknown experiment type " + experimentType);
                }
                analyticsIndexerManager.indexPublicExperiments(
                        ExperimentType.get(experimentType), numThreads, batchSize, timeout);
            } else {
                analyticsIndexerManager.indexAllPublicExperiments(numThreads, batchSize, timeout);
            }
        } catch (Exception e) {
            return Arrays.deepToString(e.getStackTrace());
        }

        return analyticsIndexerMonitor.toString();
    }

    @RequestMapping(value = "/analyticsIndex/buildIndex/status", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String analyticsIndexBuildStatus() {
        return analyticsIndexerMonitor.toString();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) {
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
    public String handleResourceNotFoundException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }
}
