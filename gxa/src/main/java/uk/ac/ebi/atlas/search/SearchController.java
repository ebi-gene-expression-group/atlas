package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.search.SemanticQuery.isNotEmpty;

@Controller
@Scope("prototype")
public class SearchController {

    private AnalyticsSearchService analyticsSearchService;
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    public SearchController(AnalyticsSearchService analyticsSearchService,
                            DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                            BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    @RequestMapping(value = "/search")
    public String showGeneQueryResultPage(@RequestParam(value = "query", defaultValue = "") SemanticQuery query,
                                          Model model)
    throws UnsupportedEncodingException {
        checkArgument(isNotEmpty(query), "Please specify a search term, a gene query or a condition query.");

        model.addAttribute("searchDescription", SearchDescription.get(query));
        model.addAttribute("query", query.toUrlEncodedJson());
        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypesInAnyField(query);

        boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
        boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

        if (!hasDifferentialResults && !hasBaselineResults) {
            return "empty-search-page";
        }

        if (hasBaselineResults) {
            model.addAttribute("jsonFacets", fetchBaselineJsonFacets(query));
        }

        model.addAttribute("hasDifferentialResults", hasDifferentialResults);
        model.addAttribute("hasBaselineResults", hasBaselineResults);

        return "bioentities-search-results";
    }

    @RequestMapping(value = {"/json/search/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@RequestParam(value = "query", defaultValue = "") SemanticQuery query) {
        return gson.toJson(differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(query));
    }

    @RequestMapping(value = {"/json/search/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@RequestParam(value = "query", defaultValue = "") SemanticQuery query) {
        return gson.toJson(differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(query));
    }

    @RequestMapping(value = {"/json/search/baselineFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchBaselineJsonFacets(@RequestParam(value = "query", defaultValue = "") SemanticQuery query) {
        return gson.toJson(baselineAnalyticsSearchService.findFacets(query));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class, UnsupportedEncodingException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleSolrException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

}
