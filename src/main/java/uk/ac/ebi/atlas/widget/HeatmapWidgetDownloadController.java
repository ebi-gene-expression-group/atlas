package uk.ac.ebi.atlas.widget;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultFormatter;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Scope("request")
public final class HeatmapWidgetDownloadController {

    private final BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;
    private final String tsvFileMastheadTemplate;
    private final AnalyticsSearchService analyticsSearchService;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final SpeciesFactory speciesFactory;

    @Inject
    private HeatmapWidgetDownloadController(BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                            @Value("classpath:/file-templates/download-headers-baseline-widget.txt") Resource tsvFileMastheadResource,
                                            BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                            AnalyticsSearchService analyticsSearchService,
                                            SpeciesFactory speciesFactory)
            throws IOException {
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.analyticsSearchService = analyticsSearchService;
        this.tsvFileMastheadTemplate = IOUtils.toString(tsvFileMastheadResource.getInputStream());
        this.speciesFactory = speciesFactory;
    }

    //TODO I wonder if this isn't dead code?
    @RequestMapping(value = {"/widgets/heatmap/bioentity.tsv", "/widgets/heatmap/multiExperiment.tsv"}, method = RequestMethod.GET)
         public void heatmapWidgetData (@RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
                                        @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                        @RequestParam(value = "species") String species,
                                        HttpServletResponse response) throws IOException {

        ImmutableSet<String> geneIds = analyticsSearchService.searchBioentityIdentifiers(geneQuery, conditionQuery,
                speciesFactory.create(species));

        BaselineExperimentSearchResult searchResult = !geneIds.isEmpty()
                ? baselineExperimentProfileSearchService.query(geneIds)
                : new BaselineExperimentSearchResult();

        if (!searchResult.isEmpty()) {
            setHttpHeaders(response, "Expression_Atlas_results_baseline.tsv");
            PrintWriter writer = response.getWriter();
            writer.write(formatFileHeader(geneQuery, conditionQuery, species));
            writeTsv(searchResult, writer);
        }
    }

    @RequestMapping(value = {"/widgets/heatmap/baselineAnalytics.tsv"}, method = RequestMethod.GET)
    public void baselineAnalytics (@RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
                                   @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                   @RequestParam(value = "species") String species,
                                   @RequestParam(value = "source", required = false) String defaultFactorQueryType,
                                   HttpServletResponse response) throws IOException {

        BaselineExperimentSearchResult searchResult = baselineAnalyticsSearchService.findExpressions(geneQuery,
                conditionQuery, speciesFactory.create(species), defaultFactorQueryType);

        if (!searchResult.isEmpty()) {
            setHttpHeaders(response, "Expression_Atlas_results_baseline.tsv");
            PrintWriter writer = response.getWriter();
            writer.write(formatFileHeader(geneQuery, conditionQuery, species));
            writeTsv(searchResult, writer);
        }
    }

    private void setHttpHeaders(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/tab-separated-values");
    }

    private String formatFileHeader(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, SearchDescription.get(geneQuery, conditionQuery, species), timeStamp) + "\n";
    }

    private void writeTsv(BaselineExperimentSearchResult searchResult, PrintWriter writer)  {
        BaselineExperimentSearchResultFormatter formatter = new BaselineExperimentSearchResultFormatter(searchResult);

        String headers = Joiner.on("\t").join(formatter.getHeaders()) + "\n";
        writer.write(headers);

        for (String[] row : formatter) {
            String rowTab = Joiner.on("\t").join(row);
            writer.write(rowTab + "\n");
        }

    }

}
