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

package uk.ac.ebi.atlas.web.controllers.rest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.WriteMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.download.DataWriterFactory;
import uk.ac.ebi.atlas.commands.download.ExpressionsWriter;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class MicroarrayPageDownloadController {
    private static final Logger LOGGER = Logger.getLogger(MicroarrayPageDownloadController.class);
    private static final String NORMALIZED_EXPRESSIONS_TSV = "-normalized-expressions.tsv";
    private static final String ANALYTICS_TSV = "-analytics.tsv";

    private final MicroarrayRequestContextBuilder requestContextBuilder;

    private WriteMicroarrayProfilesCommand writeGeneProfilesCommand;

    private DataWriterFactory dataWriterFactory;

    @Inject
    public MicroarrayPageDownloadController(
            MicroarrayRequestContextBuilder requestContextBuilder, WriteMicroarrayProfilesCommand writeGeneProfilesCommand, DataWriterFactory dataWriterFactory) {

        this.requestContextBuilder = requestContextBuilder;
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
        this.dataWriterFactory = dataWriterFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = "type=MICROARRAY")
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        preferences.setArrayDesignAccession(getSelectedArrayDesign(preferences, experiment));


        String arrayDesign = preferences.getArrayDesignAccession();

        LOGGER.info("<downloadMicroarrayGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "_" + arrayDesign + "-query-results.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

        writeGeneProfilesCommand.setResponseWriter(response.getWriter());
        writeGeneProfilesCommand.setExperiment(experiment);

        try {

            long genesCount = writeGeneProfilesCommand.execute(experiment.getAccession());
            LOGGER.info("<downloadMicroarrayGeneProfiles> streamed " + genesCount + "gene expression profiles");

        } catch (GenesNotFoundException e) {
            LOGGER.info("<downloadMicroarrayGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/normalized.tsv", params = "type=MICROARRAY")
    public void downloadRawCounts(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String selectedArrayDesign = getSelectedArrayDesign(preferences, experiment);

        prepareResponse(response, experiment.getAccession(), selectedArrayDesign, NORMALIZED_EXPRESSIONS_TSV);

        ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                selectedArrayDesign,
                response.getWriter());

        long genesCount = writer.write();

        LOGGER.info("<download" + NORMALIZED_EXPRESSIONS_TSV + "> streamed " + genesCount + " gene expression profiles");
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = "type=MICROARRAY")
    public void downloadAllAnalytics(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String selectedArrayDesign = getSelectedArrayDesign(preferences, experiment);

        prepareResponse(response, experiment.getAccession(), selectedArrayDesign, ANALYTICS_TSV);

        ExpressionsWriter writer = dataWriterFactory.getMicroarrayAnalyticsDataWriter(experiment,
                selectedArrayDesign,
                response.getWriter());


        long genesCount = writer.write();

        LOGGER.info("<download" + ANALYTICS_TSV + "> streamed " + genesCount + " gene expression profiles");
    }


    private String getSelectedArrayDesign(MicroarrayRequestPreferences preferences, MicroarrayExperiment experiment) {
        String arrayDesignAccession = preferences.getArrayDesignAccession();
        return arrayDesignAccession == null ? experiment.getArrayDesignAccessions().first() : arrayDesignAccession;
    }

    private void prepareResponse(HttpServletResponse response, String experimentAccession, String arrayDesignAccession, String fileExtension) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "_" + arrayDesignAccession + fileExtension + "\"");

        response.setContentType("text/plain; charset=utf-8");
    }

}