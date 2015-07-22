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

package uk.ac.ebi.atlas.search.diffanalytics;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.EFO.ConditionSearchEFOExpander;
import uk.ac.ebi.atlas.utils.VisitorException;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Scope("request")
public class BioentitiesSearchDifferentialDownloadController {

    private static final Logger LOGGER = Logger.getLogger(BioentitiesSearchDifferentialDownloadController.class);

    private DiffAnalyticsSearchService diffAnalyticsSearchService;
    private DiffAnalyticsTSVWriter tsvWriter;
    private ConditionSearchEFOExpander efoExpander;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");


    @Inject
    public BioentitiesSearchDifferentialDownloadController(DiffAnalyticsSearchService diffAnalyticsSearchService, DiffAnalyticsTSVWriter tsvWriter, ConditionSearchEFOExpander efoExpander) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
        this.tsvWriter = tsvWriter;
        this.efoExpander = efoExpander;
    }


    @RequestMapping(value = "/query.tsv")
    public void downloadGeneQueryDifferentialExpressions(@Valid GeneQuerySearchRequestParameters requestParameters, HttpServletResponse response) throws IOException {
        LOGGER.info("downloadGeneQueryDifferentialExpressions for " + requestParameters);

        downloadExpressions(response, requestParameters);
    }


    @RequestMapping(value = {"/genes/{identifier:.*}.tsv", "/genesets/{identifier:.*}.tsv"})
    public void downloadGeneDifferentialExpressions(@PathVariable String identifier, HttpServletResponse response) throws IOException {

        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create(identifier));

        LOGGER.info("downloadGeneDifferentialExpressions for " + requestParameters);

        downloadExpressions(response, requestParameters);
    }


    private void downloadExpressions(HttpServletResponse response, GeneQuerySearchRequestParameters requestParameters) throws IOException {

        if (requestParameters.getGeneQuery().size() > 1) {
            setDownloadHeaders(response, "expression-atlas_differential_results_" + dateFormat.format(new Date()) + ".tsv");
        } else {
            setDownloadHeaders(response, "expression-atlas_" + requestParameters.getDescription().replaceAll(" ", "_") + ".tsv");
        }

        try (DiffAnalyticsTSVWriter writer = tsvWriter) {
            writer.setResponseWriter(response.getWriter());
            writer.writeHeader(requestParameters);

            String condition = efoExpander.addEfoAccessions(requestParameters.getConditionQuery()).asString();
            //String condition = requestParameters.getConditionQuery().asString();

            int count = diffAnalyticsSearchService.visitEachExpression(requestParameters.getGeneQuery().asString(), condition, requestParameters.getOrganism(), requestParameters.isExactMatch(), writer);
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