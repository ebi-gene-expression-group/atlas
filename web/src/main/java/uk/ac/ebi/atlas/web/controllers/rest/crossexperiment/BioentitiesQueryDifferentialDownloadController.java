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

package uk.ac.ebi.atlas.web.controllers.rest.crossexperiment;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.commands.DifferentialBioentityExpressionsBuilder;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.WriteDifferentialProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.commands.download.DataWriterFactory;
import uk.ac.ebi.atlas.commands.download.DifferentialBioentityExpressionsTSVWriter;
import uk.ac.ebi.atlas.commands.download.ExpressionsWriter;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class BioentitiesQueryDifferentialDownloadController {

    private static final Logger LOGGER = Logger.getLogger(BioentitiesQueryDifferentialDownloadController.class);

    private DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder;
    private DifferentialBioentityExpressionsTSVWriter tsvWriter;

    @Inject
    public BioentitiesQueryDifferentialDownloadController(DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder, DifferentialBioentityExpressionsTSVWriter tsvWriter) {
        this.differentialBioentityExpressionsBuilder = differentialBioentityExpressionsBuilder;
        this.tsvWriter = tsvWriter;
    }

    @RequestMapping(value = "/query.tsv")
    public void downloadGeneQueryDifferentialExpressions(@Valid GeneQuerySearchRequestParameters requestParameters, HttpServletResponse response) throws IOException {
        LOGGER.info("downloadGeneQueryDifferentialExpressions for " + requestParameters);

        try {
            DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.build(requestParameters);

            if (bioentityExpressions.isEmpty()) {
                noGenesFoundException(requestParameters);
            }

            setDownloadHeaders(response, requestParameters.getDescription() + ".tsv");

            tsvWriter.setResponseWriter(response.getWriter());
            tsvWriter.writeHeader(requestParameters);
            tsvWriter.write(bioentityExpressions);

            LOGGER.info("downloadGeneQueryResults streamed " + bioentityExpressions.size() + " differential gene expressions");

        } catch (GenesNotFoundException e) {
            noGenesFoundException(requestParameters);
        }
    }

    private void setDownloadHeaders(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/plain; charset=utf-8");
    }

    private void noGenesFoundException(GeneQuerySearchRequestParameters requestParameters) {
        String message = "No genes found for " + requestParameters;
        LOGGER.info(message);
        throw new ResourceNotFoundException(message);
    }

}