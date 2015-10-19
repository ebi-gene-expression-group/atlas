package uk.ac.ebi.atlas.search.analyticsindex.differential;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDAO;
import uk.ac.ebi.atlas.search.analyticsindex.SearchController;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("request")
public class SearchDifferentialController extends SearchController {

    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;

    @Inject
    public SearchDifferentialController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder,
                                        AnalyticsSearchDAO analyticsSearchDAO,
                                        DifferentialAnalyticsSearchService differentialAnalyticsSearchService) {
        super(ebiGlobalSearchQueryBuilder, analyticsSearchDAO);
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
    }

    @RequestMapping(value = "/search/differential")
    public String searchDifferential(@Valid GeneQuerySearchRequestParameters requestParameters, Model model) throws IOException, SolrServerException {
        GeneQuery geneQuery = requestParameters.getGeneQuery();

        if (!geneQuery.isEmpty()) {
            addSearchHeader(requestParameters, model);
            model.addAttribute("jsonDifferentialGeneQueryFacets", differentialAnalyticsSearchService.fetchDifferentialGeneQueryFacetsAsJson(geneQuery));
            model.addAttribute("jsonDifferentialGeneQueryResults", differentialAnalyticsSearchService.fetchDifferentialGeneQueryResultsAsJson(geneQuery));
        }

        return "search-results-differential";
    }

    @RequestMapping(value = "/search/differential/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView searchDifferentialUrlParams(@Valid GeneQuerySearchRequestParameters requestParameters,
                                              @RequestParam(value = "species", required = false) List<String> species,
                                              @RequestParam(value = "experimentType", required = false) List<String> experimentType,
                                              @RequestParam(value = "kingdom", required = false) List<String> kingdom,
                                              @RequestParam(value = "factors", required = false) List<String> factors,
                                              @RequestParam(value = "numReplicates", required = false) List<Integer> numReplicates,
                                              @RequestParam(value = "regulation", required = false) String regulation) {
        String jsonResults="";
        GeneQuery geneQuery = requestParameters.getGeneQuery();

        if(!geneQuery.isEmpty()) {
            jsonResults = differentialAnalyticsSearchService.fetchDifferentialGeneQuerySelectionResultsAsJson(geneQuery, species, experimentType, kingdom, factors, numReplicates, regulation);
        }

        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        mav.addObject("results", jsonResults);

        return mav;
    }

}
