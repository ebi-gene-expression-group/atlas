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

package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.MessageFormat;

@Controller
@Scope("request")
public class RnaSeqExperimentDownloadController {

    private static final Logger LOGGER = LogManager.getLogger(RnaSeqExperimentDownloadController.class);
    private static final String ALL_ANALYTICS_TSV = "-analytics.tsv";
    private static final String RAW_COUNTS_TSV = "-raw-counts.tsv";
    private static final String PARAMS_TYPE_DIFFERENTIAL = "type=RNASEQ_MRNA_DIFFERENTIAL";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";

    private final RnaSeqRequestContextBuilder requestContextBuilder;

    private RnaSeqProfilesWriter profilesWriter;

    private DataWriterFactory dataWriterFactory;

    @Inject
    public RnaSeqExperimentDownloadController(
            RnaSeqRequestContextBuilder requestContextBuilder, RnaSeqProfilesWriter profilesWriter
            , DataWriterFactory dataWriterFactory) {

        this.requestContextBuilder = requestContextBuilder;
        this.profilesWriter = profilesWriter;
        this.dataWriterFactory = dataWriterFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid DifferentialRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);


        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");

        response.setContentType("text/plain; charset=utf-8");


        RnaSeqRequestContext requestContext = requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

        try {

            long genesCount = profilesWriter.write(response.getWriter(), requestContext);
            LOGGER.info("<downloadGeneProfiles> streamed " + genesCount + " gene expression profiles");

        } catch (GenesNotFoundException e) {
            LOGGER.info("<downloadGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/raw-counts.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadRawCounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), RAW_COUNTS_TSV);

        ExpressionsWriter rnaSeqRawDataWriter = dataWriterFactory.getRnaSeqRawDataWriter(experiment, response.getWriter());

        long genesCount = rnaSeqRawDataWriter.write();
        LOGGER.info("<download" + RAW_COUNTS_TSV + "> streamed " + genesCount + " gene expression profiles");
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadAllAnalytics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), ALL_ANALYTICS_TSV);

        ExpressionsWriter rnaSeqAnalyticsDataWriter = dataWriterFactory.getRnaSeqAnalyticsDataWriter(experiment, response.getWriter());

        long genesCount = rnaSeqAnalyticsDataWriter.write();
        LOGGER.info("<download" + ALL_ANALYTICS_TSV + "> streamed " + genesCount + " gene expression profiles");

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = PARAMS_TYPE_DIFFERENTIAL)
    public String downloadRdataURL(HttpServletRequest request) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String path = MessageFormat.format("/expdata/{0}/{0}-atlasExperimentSummary.Rdata", experiment.getAccession());

        return "forward:" + path;
    }

    private void prepareResponse(HttpServletResponse response, String experimentAccession, String fileExtension) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + fileExtension + "\"");

        response.setContentType("text/plain; charset=utf-8");
    }
}