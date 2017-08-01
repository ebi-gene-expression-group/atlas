package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaselineExperimentDownloadService<T extends BaselineRequestPreferences> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqBaselineExperimentDownloadController.class);

    private final BaselineProfilesWriterService baselineProfilesWriterService;

    private final JsonParser jsonParser = new JsonParser();

    private final ExperimentTrader experimentTrader;

    public BaselineExperimentDownloadService(BaselineProfilesWriterService baselineProfilesWriterService,
                                             ExperimentTrader experimentTrader) {
        this.baselineProfilesWriterService = baselineProfilesWriterService;
        this.experimentTrader = experimentTrader;
    }

    public void download(String experimentAccession, HttpServletRequest request, T preferences,
                         HttpServletResponse response, String accessKey)
    throws IOException {
        BaselineExperiment experiment =
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: {}", preferences);

        response.setHeader(
                "Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");
        response.setContentType("text/plain; charset=utf-8");

        long genesCount = baselineProfilesWriterService.write(response.getWriter(), preferences, experiment);
        LOGGER.info("<downloadGeneProfiles> streamed {} gene expression profiles", genesCount);
    }

}
