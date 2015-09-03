package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.apache.solr.client.solrj.SolrServerException;
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
import java.io.IOException;

@Controller
@Scope("request")
public class SearchBaselineController extends SearchController {

    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Inject
    public SearchBaselineController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, BaselineAnalyticsSearchService baselineAnalyticsSearchService, AnalyticsSearchDao analyticsSearchDao) {
        super(ebiGlobalSearchQueryBuilder, analyticsSearchDao);
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    @RequestMapping(value = "/search")
    public String searchBaseline(@Valid GeneQuerySearchRequestParameters requestParameters, Model model) throws IOException, SolrServerException {

        GeneQuery geneQuery = requestParameters.getGeneQuery();

        if (!geneQuery.isEmpty()) {
            addSearchHeader(requestParameters, model);
            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(geneQuery));
        }

        return "search-results-baseline";
    }

}
