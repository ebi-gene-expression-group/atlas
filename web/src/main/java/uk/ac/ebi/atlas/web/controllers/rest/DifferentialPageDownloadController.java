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
import uk.ac.ebi.atlas.commands.WriteDifferentialProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.commands.download.AllDataWriterFactory;
import uk.ac.ebi.atlas.commands.download.ExpressionsWriter;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class DifferentialPageDownloadController {
    private static final Logger LOGGER = Logger.getLogger(DifferentialPageDownloadController.class);

    private final RnaSeqRequestContextBuilder requestContextBuilder;

    private WriteDifferentialProfilesCommand writeGeneProfilesCommand;

    private AllDataWriterFactory allDataWriterFactory;

    @Inject
    public DifferentialPageDownloadController(
            RnaSeqRequestContextBuilder requestContextBuilder, WriteDifferentialProfilesCommand writeGeneProfilesCommand
            , AllDataWriterFactory allDataWriterFactory) {

        this.requestContextBuilder = requestContextBuilder;
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
        this.allDataWriterFactory = allDataWriterFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = "type=DIFFERENTIAL")
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);


        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-gene-expression-profiles.tsv\"");

        response.setContentType("text/plain; charset=utf-8");


        requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

        writeGeneProfilesCommand.setResponseWriter(response.getWriter());
        writeGeneProfilesCommand.setExperiment(experiment);

        try {

            long genesCount = writeGeneProfilesCommand.execute(experiment.getAccession());
            LOGGER.info("<downloadGeneProfiles> streamed " + genesCount + " gene expression profiles");

        } catch (GenesNotFoundException e) {
            LOGGER.info("<downloadGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/raw-counts.tsv", params = "type=DIFFERENTIAL")
    public void downloadRawCounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        writeAllData(response, experiment.getAccession(), allDataWriterFactory.getRnaSeqRawDataWriter(), "-raw-counts.tsv");
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = "type=DIFFERENTIAL")
    public void downloadAllAnalytics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        allDataWriterFactory.getRnaSeqAnalyticsDataWriter(experiment);

        writeAllData(response, experiment.getAccession(), allDataWriterFactory.getRnaSeqAnalyticsDataWriter(experiment), "-all-analytics.tsv");

    }

    private void writeAllData(HttpServletResponse response,
                              String experimentAccession,
                              ExpressionsWriter writer,
                              String fileExtension) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + fileExtension + "\"");

        response.setContentType("text/plain; charset=utf-8");

        writer.setResponseWriter(response.getWriter());

        long genesCount = writer.write(experimentAccession);
        LOGGER.info("<download" + fileExtension + "> streamed " + genesCount + " gene expression profiles");
    }
}