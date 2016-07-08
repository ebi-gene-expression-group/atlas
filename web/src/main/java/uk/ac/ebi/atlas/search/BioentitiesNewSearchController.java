
package uk.ac.ebi.atlas.search;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@Scope("prototype")
public class BioentitiesNewSearchController {

    private AnalyticsSearchService analyticsSearchService;

    @Inject
    public BioentitiesNewSearchController(AnalyticsSearchService analyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
    }

    @RequestMapping(value = "/newquery")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, RedirectAttributes redirectAttributes) {

        checkArgument(requestParameters.hasSemanticQuery() || requestParameters.hasCondition(), "Please specify a gene query or condition!");

        String selectedSpecies = requestParameters.hasOrganism() ? requestParameters.getOrganism().trim().toLowerCase() : "";

        if (requestParameters.hasSemanticQuery() && !requestParameters.hasCondition()) {

            if (requestParameters.getSemanticQuery().size() == 1) {
                return singleTermGeneQuery(requestParameters.getSemanticQuery(), selectedSpecies, model, redirectAttributes);
            } else {
                return multiTermGeneQuery(requestParameters.getSemanticQuery(), selectedSpecies, model, redirectAttributes);
            }

        } else if (!requestParameters.hasSemanticQuery() && requestParameters.hasCondition()) {

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


    private String singleTermGeneQuery(GeneQuery geneQuery, String species, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("searchDescription", geneQuery.toString());

        // Gene set ID
        if (GeneSetUtil.isGeneSetSourceOrMatchesGeneSetAccession(geneQuery)) {
            return "redirect:/genesets/" + geneQuery.terms().iterator().next().value();
        }

        // Resolves to one gene ID
        Optional<ImmutableSet<String>> geneIds = analyticsSearchService.searchBioentityIdentifiers(geneQuery, species);
        if (geneIds.isPresent() && geneIds.get().size() == 1) {
            return "redirect:/genes/" + geneIds.get().iterator().next();
        }

        // Resolves to more than one gene ID
        return "";


//        model.addAttribute("geneQuery", requestParameters.getGeneQueryDeprecated());
//        model.addAttribute("searchDescription", requestParameters.getDescription());
//        model.addAttribute("mainTitle", "Expression summary for " + requestParameters.getDescription());
//
//        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = baselineExperimentAssayGroupSearchService.query(geneQuery, species, isExactMatch);
//
//        boolean showWidget = hasAllSameSpecies(baselineExperimentAssayGroups) && hasAnyTissueExperiment(baselineExperimentAssayGroups);
//
//        if (showWidget) {
//            model.addAttribute("widgetHasBaselineProfiles", true);
//            model.addAttribute("species", baselineExperimentAssayGroups.iterator().next().getSpecies());
//
//            SortedSet<BaselineExperimentAssayGroup> nonTissueExperimentAssayGroups = selectNonTissueExperiments(baselineExperimentAssayGroups);
//            model.addAttribute("firstBaselineCounts", removeFirstAssayGroups(nonTissueExperimentAssayGroups, DEFAULT_BASELINE_SPLIT));
//            model.addAttribute("remainingBaselineCounts", nonTissueExperimentAssayGroups);
//        } else {
//            model.addAttribute("firstBaselineCounts", removeFirstAssayGroups(baselineExperimentAssayGroups, DEFAULT_BASELINE_SPLIT));
//            model.addAttribute("remainingBaselineCounts", baselineExperimentAssayGroups);
//        }
//
//        // used to populate diff-heatmap-table
//        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(geneQuery, species, isExactMatch);
//
//        model.addAttribute("bioentities", bioentityExpressions);
//        model.addAttribute("preferences", new DifferentialRequestPreferences());
//        model.addAttribute("requestParameters", requestParameters);
//        model.addAttribute("exactMatch", requestParameters.isExactMatch());
//
//        String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQueryString, requestParameters.getConditionQuery());
//
//        model.addAttribute("globalSearchTerm", globalSearchTerm);
//
//        return "bioEntities";
    }

    private String multiTermGeneQuery(GeneQuery geneQuery, String species, Model model, RedirectAttributes redirectAttributes) {
        return "";
    }

    @RequestMapping(value ={"/newquery/results.json"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String newQueryAsJson(@Valid GeneQuerySearchRequestParameters requestParameters) {
        checkArgument(requestParameters.hasSemanticQuery() || requestParameters.hasCondition(), "Please specify a gene query or condition!");

        String selectedSpecies = requestParameters.hasOrganism() ? requestParameters.getOrganism().trim().toLowerCase() : "";

        if (requestParameters.hasSemanticQuery() && !requestParameters.hasCondition()) {

            if (requestParameters.getSemanticQuery().size() == 1) {
                return singleTermGeneQueryAsJson(requestParameters.getSemanticQuery(), selectedSpecies);
            } else {
                return multiTermGeneQueryAsJson(requestParameters.getSemanticQuery(), selectedSpecies);
            }

        } else if (!requestParameters.hasSemanticQuery() && requestParameters.hasCondition()) {

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

    private String singleTermGeneQueryAsJson(GeneQuery geneQuery, String species) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();

        // Gene set ID
        if (GeneSetUtil.isGeneSetSourceOrMatchesGeneSetAccession(geneQuery)) {
            return gson.toJson(Sets.newHashSet(geneQuery.terms().iterator().next().value()));
        }

        // Resolves to one gene ID
        Optional<ImmutableSet<String>> geneIds = analyticsSearchService.searchBioentityIdentifiers(geneQuery, species);
        if (geneIds.isPresent()) {
            return gson.toJson(geneIds.get());
        }

        // Resolves to more than one gene ID
        return gson.toJson(Sets.newHashSet());
    }

    private String multiTermGeneQueryAsJson(GeneQuery geneQuery, String species) {
        return "";
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("bioentities-search-results");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleSolrException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

}
