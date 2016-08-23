package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.PreferencesForBaselineExperiments;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
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

    public BaselineExperimentDownloadService(BaselineProfileInputStreamFactory inputStreamFactory,
                                             BaselineProfilesWriterServiceFactory
                                                     baselineProfilesWriterServiceFactory,ExperimentTrader experimentTrader) {
        this.baselineProfilesWriterService = baselineProfilesWriterServiceFactory.create(inputStreamFactory);
        this.experimentTrader =experimentTrader;
    };

    public void download(String experimentAccession, HttpServletRequest request,
                         T preferences, HttpServletResponse response, String accessKey) throws
            IOException {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        PreferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: {}", preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        try {

            long genesCount = baselineProfilesWriterService.write(response.getWriter(), preferences, experiment,
                    readCoexpressionsRequested(request));

            LOGGER.info("<downloadGeneProfiles> streamed {} gene expression profiles", genesCount);

        } catch (GenesNotFoundException e) {
            LOGGER.info("<downloadGeneProfiles> no genes found");
        }

    }

    Map<String, Integer> readCoexpressionsRequested(HttpServletRequest request) {
        return request.getParameterMap().containsKey("coexpressions")
                ? coexpressionsRequested(request.getParameter("coexpressions"))
                : new HashMap<String, Integer>();
    }

    private Map<String, Integer> coexpressionsRequested(String argument) {
        Map<String, Integer> result = new HashMap<>();
        try {
            JsonElement el = jsonParser.parse(argument.replace("\\\"","\""));
            if (el != null && el.isJsonObject()) {
                for (Map.Entry<String, JsonElement> e : el.getAsJsonObject().entrySet()) {
                    result.put(e.getKey().toUpperCase(), e.getValue().getAsInt());
                }
            }
        } catch (JsonSyntaxException | NumberFormatException e) {
            LOGGER.debug(e.toString());
        }
        return result;
    }
}
