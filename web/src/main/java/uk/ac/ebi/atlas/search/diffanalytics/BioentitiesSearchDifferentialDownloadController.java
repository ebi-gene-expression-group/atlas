package uk.ac.ebi.atlas.search.diffanalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.utils.VisitorException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Scope("request")
public class BioentitiesSearchDifferentialDownloadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentitiesSearchDifferentialDownloadController.class);

    private DiffAnalyticsSearchService diffAnalyticsSearchService;
    private DiffAnalyticsTSVWriter tsvWriter;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");


    @Inject
    public BioentitiesSearchDifferentialDownloadController(DiffAnalyticsSearchService diffAnalyticsSearchService, DiffAnalyticsTSVWriter tsvWriter) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
        this.tsvWriter = tsvWriter;
    }


    @RequestMapping(value = "/query.tsv")
    public void downloadGeneQueryDifferentialExpressions(@RequestParam(value = "geneQuery", required = false, defaultValue = "[]") SemanticQuery geneQuery,
                                                         @RequestParam(value = "conditionQuery", required = false, defaultValue = "[]") SemanticQuery conditionQuery,
                                                         @RequestParam(value = "organism", required = false, defaultValue = "") String species,
                                                         HttpServletResponse response) throws IOException {
        LOGGER.info("downloadGeneQueryDifferentialExpressions for {}", SearchDescription.getRaw(geneQuery, conditionQuery, species));

        downloadExpressions(response, geneQuery, conditionQuery, species);
    }


    @RequestMapping(value = {"/genes/{identifier:.*}.tsv", "/genesets/{identifier:.*}.tsv"})
    public void downloadGeneDifferentialExpressions(@PathVariable String identifier, HttpServletResponse response) throws IOException {

        SemanticQuery geneQuery = SemanticQuery.create(identifier);
        SemanticQuery emptyConditionQuery = SemanticQuery.create("");
        String noSpecies = "";

        LOGGER.info("downloadGeneDifferentialExpressions for {}", SearchDescription.getRaw(geneQuery, emptyConditionQuery, noSpecies));

        downloadExpressions(response, geneQuery, emptyConditionQuery, noSpecies);
    }


    private void downloadExpressions(HttpServletResponse response, SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) throws IOException {

        if (geneQuery.size() > 1) {
            setDownloadHeaders(response, "expression_atlas-differential_results-" + dateFormat.format(new Date()) + ".tsv");
        } else {
            setDownloadHeaders(response, "expression_atlas-" + SearchDescription.get(geneQuery, conditionQuery, species).replaceAll(" ", "_") + ".tsv");
        }

        try (DiffAnalyticsTSVWriter writer = tsvWriter) {
            writer.setResponseWriter(response.getWriter());
            writer.writeHeader(geneQuery, conditionQuery, species);

            int count = diffAnalyticsSearchService.visitEachExpression(geneQuery, conditionQuery, species, writer);
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
