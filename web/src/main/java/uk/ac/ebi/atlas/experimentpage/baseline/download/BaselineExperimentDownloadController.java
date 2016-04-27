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

package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentController;
import uk.ac.ebi.atlas.experimentpage.baseline.PreferencesForBaselineExperiments;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@Scope("request")
public class BaselineExperimentDownloadController extends BaselineExperimentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentDownloadController.class);

    private static final String PARAMS_TYPE_RNASEQ_BASELINE = "type=RNASEQ_MRNA_BASELINE";
    private final PreferencesForBaselineExperiments preferencesForBaselineExperiments;

    private final BaselineProfilesWriterService baselineProfilesWriterService;

    private final JsonParser jsonParser = new JsonParser();

    @Inject
    public BaselineExperimentDownloadController(@Qualifier("baselineProfileInputStreamFactory")
                                                BaselineProfileInputStreamFactory inputStreamFactory,
                                                BaselineProfilesWriterServiceFactory baselineProfilesWriterServiceFactory) {
        this.preferencesForBaselineExperiments = new PreferencesForBaselineExperiments();
        this.baselineProfilesWriterService = baselineProfilesWriterServiceFactory.create(inputStreamFactory);
    }

    protected void geneProfilesHandler(HttpServletRequest request, BaselineRequestPreferences preferences, HttpServletResponse response) throws IOException {
        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        preferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

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

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_RNASEQ_BASELINE)
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        geneProfilesHandler(request, preferences, response);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = PARAMS_TYPE_RNASEQ_BASELINE)
    public String downloadRdataURL(HttpServletRequest request) throws IOException {
        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String path = MessageFormat.format("/expdata/{0}/{0}-atlasExperimentSummary.Rdata", experiment.getAccession());

        return "forward:" + path;
    }
}