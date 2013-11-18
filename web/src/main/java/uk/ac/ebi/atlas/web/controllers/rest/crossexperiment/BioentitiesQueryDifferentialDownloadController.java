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
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GeneQueryDifferentialService;
import uk.ac.ebi.atlas.commands.download.DifferentialBioentityExpressionsTSVWriter;
import uk.ac.ebi.atlas.utils.VisitorException;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class BioentitiesQueryDifferentialDownloadController {

    private static final Logger LOGGER = Logger.getLogger(BioentitiesQueryDifferentialDownloadController.class);

    private GeneQueryDifferentialService geneQueryDifferentialService;
    private DifferentialBioentityExpressionsTSVWriter tsvWriter;

    @Inject
    public BioentitiesQueryDifferentialDownloadController(GeneQueryDifferentialService geneQueryDifferentialService, DifferentialBioentityExpressionsTSVWriter tsvWriter) {
        this.geneQueryDifferentialService = geneQueryDifferentialService;
        this.tsvWriter = tsvWriter;
    }

    @RequestMapping(value = "/query.tsv")
    public void downloadGeneQueryDifferentialExpressions(@Valid GeneQuerySearchRequestParameters requestParameters, HttpServletResponse response) throws IOException {
        LOGGER.info("downloadGeneQueryDifferentialExpressions for " + requestParameters);

        setDownloadHeaders(response, requestParameters.getDescription() + ".tsv");

        try (DifferentialBioentityExpressionsTSVWriter writer = tsvWriter) {
            writer.setResponseWriter(response.getWriter());
            writer.writeHeader(requestParameters);

            int count = geneQueryDifferentialService.forEachExpression(requestParameters, tsvWriter);
            LOGGER.info("downloadGeneQueryResults streamed " + count + " differential gene expressions");
        } catch (VisitorException e) {
            LOGGER.warn("downloadGeneQueryResults aborted, connection may have been lost with the client:" + e.getMessage());
        }
    }


    private void setDownloadHeaders(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/plain; charset=utf-8");
    }

}