package uk.ac.ebi.atlas.widget;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultFormatter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
@Scope("request")
public final class HeatmapWidgetDownloadController {

    private final BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;
    private final String tsvFileMastheadTemplate;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final SolrQueryService solrQueryService;

    @Inject
    private HeatmapWidgetDownloadController(BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                            @Value("classpath:/file-templates/download-headers-baseline-widget.txt") Resource tsvFileMastheadResource,
                                            BaselineAnalyticsSearchService baselineAnalyticsSearchService,SolrQueryService solrQueryService) throws
            IOException {
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.tsvFileMastheadTemplate = IOUtils.toString(tsvFileMastheadResource.getInputStream());
        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = {"/widgets/heatmap/bioentity.tsv", "/widgets/heatmap/multiExperiment.tsv"}, method = RequestMethod.GET)
         public void heatmapWidgetData (@RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                        @RequestParam(value = "species", required = true) String species,
                                        HttpServletResponse response) throws IOException {

        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds(bioEntityAccession, species);

        BaselineExperimentSearchResult searchResult = geneIds.isPresent()
                ?   baselineExperimentProfileSearchService.query(geneIds.get())
                : new BaselineExperimentSearchResult();

        if (!searchResult.isEmpty()) {
            setHttpHeaders(response, bioEntityAccession + "_baseline.tsv");
            PrintWriter writer = response.getWriter();
            writer.write(formatFileHeader(bioEntityAccession));
            writeTsv(searchResult, writer);
        }
    }

    @RequestMapping(value = {"/widgets/heatmap/baselineAnalytics.tsv"}, method = RequestMethod.GET)
    public void baselineAnalytics (@RequestParam(value = "geneQuery", required = true) String geneQuery,
                                   @RequestParam(value = "species", required = true) String species,
                                   @RequestParam(value = "source", required = false) String source,
                                   HttpServletResponse response) throws IOException {

        String defaultFactorQueryType = StringUtils.isBlank(source) ? "ORGANISM_PART" : source;
        BaselineExperimentSearchResult searchResult = baselineAnalyticsSearchService.findExpressions(GeneQuery.create(geneQuery), species, defaultFactorQueryType);

        if (!searchResult.isEmpty()) {
            setHttpHeaders(response, geneQuery + "_baseline.tsv");
            PrintWriter writer = response.getWriter();
            writer.write(formatFileHeader(geneQuery));
            writeTsv(searchResult, writer);
        }
    }

    private void setHttpHeaders(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/tab-separated-values");
    }

    private String formatFileHeader(String bioEntityAccession) {
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, bioEntityAccession, timeStamp) + "\n";
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
