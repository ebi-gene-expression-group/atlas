package uk.ac.ebi.atlas.search.diffanalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.EFO.ConditionSearchEFOExpander;
import uk.ac.ebi.atlas.utils.VisitorException;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.OldGeneQuery;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentitiesSearchDifferentialDownloadController.class);

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
        LOGGER.info("downloadGeneQueryDifferentialExpressions for {}", requestParameters);

        downloadExpressions(response, requestParameters);
    }


    @RequestMapping(value = {"/genes/{identifier:.*}.tsv", "/genesets/{identifier:.*}.tsv"})
    public void downloadGeneDifferentialExpressions(@PathVariable String identifier, HttpServletResponse response) throws IOException {

        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create(identifier));

        LOGGER.info("downloadGeneDifferentialExpressions for {}", requestParameters);

        downloadExpressions(response, requestParameters);
    }


    private void downloadExpressions(HttpServletResponse response, GeneQuerySearchRequestParameters requestParameters) throws IOException {

        if (requestParameters.getGeneQuery().size() > 1) {
            setDownloadHeaders(response, "expression_atlas-differential_results-" + dateFormat.format(new Date()) + ".tsv");
        } else {
            setDownloadHeaders(response, "expression_atlas-" + requestParameters.getDescription().replaceAll(" ", "_") + ".tsv");
        }

        try (DiffAnalyticsTSVWriter writer = tsvWriter) {
            writer.setResponseWriter(response.getWriter());
            writer.writeHeader(requestParameters);

            String condition = efoExpander.addEfoAccessions(requestParameters.getConditionQuery()).asString();
            //String condition = requestParameters.getConditionQuery().asString();

            int count = diffAnalyticsSearchService.visitEachExpression(requestParameters.getGeneQuery(), condition, requestParameters.getOrganism(), writer);
            LOGGER.info("downloadGeneQueryResults streamed {} differential gene expressions", count);
        } catch (VisitorException e) {
            LOGGER.warn("downloadGeneQueryResults aborted, connection may have been lost with the client: {}", e.getMessage());
        }
    }


    private void setDownloadHeaders(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/plain; charset=utf-8");
    }

}
