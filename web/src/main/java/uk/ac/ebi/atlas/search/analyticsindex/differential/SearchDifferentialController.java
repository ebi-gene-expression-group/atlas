package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDao;
import uk.ac.ebi.atlas.search.analyticsindex.SearchController;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@Scope("request")
public class SearchDifferentialController extends SearchController {

    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader;

    @Inject
    public SearchDifferentialController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder,
                                        AnalyticsSearchDao analyticsSearchDao,
                                        DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                        DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader) {
        super(ebiGlobalSearchQueryBuilder, analyticsSearchDao);
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.differentialAnalyticsFacetsReader = differentialAnalyticsFacetsReader;
    }

    @RequestMapping(value = "/search/differential")
    public String searchDifferential(@Valid GeneQuerySearchRequestParameters requestParameters, Model model) {

        GeneQuery geneQuery = requestParameters.getGeneQuery();

        if (!geneQuery.isEmpty()) {

            addSearchHeader(requestParameters, model);

            model.addAttribute("jsonDifferentialGeneQueryFacets", differentialAnalyticsSearchService.fetchDifferentialGeneQueryFacetsAsJson(geneQuery, new Gson()));

            model.addAttribute("jsonDifferentialGeneQueryResults", differentialAnalyticsSearchService.fecthDifferentialGeneQueryResultsAsJson(geneQuery, new Gson()));
        }

        return "search-results-differential";
    }
}
