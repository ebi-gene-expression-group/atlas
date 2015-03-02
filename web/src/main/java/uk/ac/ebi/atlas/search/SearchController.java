package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDao;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@Scope("request")
public class SearchController {

    private EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private AnalyticsSearchDao analyticsSearchDao;

    @Inject
    public SearchController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, BaselineAnalyticsSearchService baselineAnalyticsSearchService, AnalyticsSearchDao analyticsSearchDao) {
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.analyticsSearchDao = analyticsSearchDao;
    }

    @RequestMapping(value = "/search")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model) {

        GeneQuery geneQuery = requestParameters.getGeneQuery();

        if (!geneQuery.isEmpty()) {

            ImmutableSet<String> experimentTypes = analyticsSearchDao.fetchExperimentTypes(geneQuery.asString());

            model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));
            model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));

            model.addAttribute("searchDescription", requestParameters.getDescription());
            model.addAttribute("geneQuery", geneQuery);

            String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery.asString(), requestParameters.getConditionQuery());
            model.addAttribute("globalSearchTerm", globalSearchTerm);

            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(geneQuery.asString()));
        }

        return "search-results";
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
