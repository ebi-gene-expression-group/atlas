
package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@Scope("prototype")
public class BioentitiesNewSearchController {

    private AnalyticsSearchService analyticsSearchService;
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Inject
    public BioentitiesNewSearchController(AnalyticsSearchService analyticsSearchService,
                                          DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                          BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, RedirectAttributes redirectAttributes)
    throws UnsupportedEncodingException {

        checkArgument(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify a gene query or a condition.");

        model.addAttribute("isSearch", true);
        model.addAttribute("searchDescription", requestParameters.getDescription());
        redirectAttributes.addFlashAttribute("searchDescription", requestParameters.getDescription());

        if (requestParameters.hasGeneQuery() && !requestParameters.hasCondition()) {

            String species = requestParameters.hasOrganism() ? requestParameters.getOrganism().trim().toLowerCase() : "";
            model.addAttribute("species", species);
            redirectAttributes.addFlashAttribute("species", species);

            GeneQuery geneQuery = requestParameters.getGeneQuery();

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
                model.addAttribute("identifier", geneQuery.toUrlEncodedJson());
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

        } else if (!requestParameters.hasGeneQuery() && requestParameters.hasCondition()) {

            // Only condition was specified
            // if (requestParameters.getConditionQuery().size() == 1) {
            //     return singleTermConditionQuery(requestParameters.getConditionQuery(), selectedSpecies, requestParameters.isExactMatch(), model, redirectAttributes);
            // } else {
            //     return multiTermConditionQuery(requestParameters.getConditionQuery(), selectedSpecies, requestParameters.isExactMatch(), model, redirectAttributes);
            // }
            return "";

        } else {

            return "";
            // Both gene query and condition were specified

        }
    }

    @RequestMapping(value = {"/json/query/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@Valid GeneQuerySearchRequestParameters requestParameters) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(requestParameters.getGeneQuery(), requestParameters.getOrganism());
    }

    @RequestMapping(value = {"/json/query/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@Valid GeneQuerySearchRequestParameters requestParameters) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(requestParameters.getGeneQuery(), requestParameters.getOrganism());
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class, })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleSolrException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

}
