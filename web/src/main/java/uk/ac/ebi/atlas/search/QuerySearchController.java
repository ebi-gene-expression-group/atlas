package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@Scope("prototype")
public class QuerySearchController {

    private AnalyticsSearchService analyticsSearchService;
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Inject
    public QuerySearchController(AnalyticsSearchService analyticsSearchService,
                                 DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                 BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@RequestParam(value = "geneQuery", required = false, defaultValue = "[]") SemanticQuery geneQuery,
                                          @RequestParam(value = "conditionQuery", required = false, defaultValue = "[]") SemanticQuery conditionQuery,
                                          @RequestParam(value = "organism", required = false, defaultValue = "") String species,
                                          Model model, RedirectAttributes redirectAttributes)
    throws UnsupportedEncodingException {

        checkArgument(geneQuery.isNotEmpty() || conditionQuery.isNotEmpty(), "Please specify a gene query or a condition.");

//        model.addAttribute("searchDescription", String.format("%s OR %s AND %s", geneQuery.toJson(), conditionQuery, species));
//        redirectAttributes.addFlashAttribute("searchDescription", requestParameters.getDescription());

        //model.addAttribute("species", species);
        redirectAttributes.addFlashAttribute("species", species);
        redirectAttributes.addFlashAttribute("identifier", geneQuery.toUrlEncodedJson());

        // Matches gene set ID -> Gene set page
        // TODO We decide it’s a gene set because of how the query *looks*, and things like GO:FOOBAR will be incorrectly redirected to /genesets/GO:FOOBAR
        if (GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(geneQuery)) {
            return "redirect:/genesets/" + geneQuery.terms().iterator().next().value();
        }

        // No gene IDs -> empty results page
        ImmutableSet<String> geneIds = analyticsSearchService.searchBioentityIdentifiers(geneQuery, species);
        if (geneIds.size() == 0) {
            return "empty-search-page";
        }

        // Resolves to a single Gene ID -> Gene page
        if (geneIds.size() == 1) {
            return "redirect:/genes/" + geneIds.iterator().next();
        }
        // Resolves to multiple IDs -> General results page
        else {
            ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(geneQuery, species);

            boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
            boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

            if (!hasDifferentialResults && !hasBaselineResults) {
                return "empty-search-page";
            }

            if (hasBaselineResults) {
                model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(geneQuery, species));
            }

            model.addAttribute("hasDifferentialResults", hasDifferentialResults);
            model.addAttribute("hasBaselineResults", hasBaselineResults);

            return "new-bioentities-search-results";
        }

    }

    @RequestMapping(value = {"/json/query/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@RequestParam(value = "geneQuery", required = false, defaultValue = "[]") SemanticQuery geneQuery,
                                              @RequestParam(value = "conditionQuery", required = false, defaultValue = "") String conditionQuery,
                                              @RequestParam(value = "organism", required = false, defaultValue = "") String species) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(geneQuery, species);
    }

    @RequestMapping(value = {"/json/query/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@RequestParam(value = "geneQuery", required = false, defaultValue = "[]") SemanticQuery geneQuery,
                                               @RequestParam(value = "conditionQuery", required = false, defaultValue = "") String conditionQuery,
                                               @RequestParam(value = "organism", required = false, defaultValue = "") String species) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(geneQuery, species);
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
