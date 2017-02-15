package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;
import static uk.ac.ebi.atlas.search.SemanticQuery.isNotEmpty;

@Controller
@Scope("prototype")
public class QuerySearchController {

    @Autowired
    private Environment env;

    private AnalyticsSearchService analyticsSearchService;
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private SpeciesFactory speciesFactory;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    public QuerySearchController(AnalyticsSearchService analyticsSearchService,
                                 DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                 BaselineAnalyticsSearchService baselineAnalyticsSearchService, SpeciesFactory speciesFactory) {
        this.analyticsSearchService = analyticsSearchService;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.speciesFactory = speciesFactory;
    }


    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
                                          @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                          @RequestParam(value = "organism", required = false, defaultValue = "") String speciesString,
                                          Model model, RedirectAttributes redirectAttributes){
        checkArgument(isNotEmpty(geneQuery) || isNotEmpty(conditionQuery), "Please specify a gene query or a condition.");
        Species species = speciesFactory.create(speciesString);

        model.addAttribute("searchDescription", SearchDescription.get(geneQuery, conditionQuery, species.getName()));
        model.addAttribute("species", species.getReferenceName());
        model.addAttribute("geneQuery", geneQuery.toUrlEncodedJson());
        model.addAttribute("conditionQuery", conditionQuery.toUrlEncodedJson());

        // Matches gene set ID -> Gene set page
        // TODO We decide it’s a gene set because of how the query *looks*, and things like GO:FOOBAR will be incorrectly redirected to /genesets/GO:FOOBAR
        if (isEmpty(conditionQuery) && GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(geneQuery)) {
            String geneSetId = geneQuery.terms().iterator().next().value();

            StringBuilder stringBuilder = new StringBuilder("redirect:/genesets/" + geneSetId);
            if (!GeneSetUtil.matchesReactomeID(geneSetId) && !species.isUnknown()) {
                stringBuilder.append("?").append("organism=").append(species.getName());
            }

            copyModelAttributesToFlashAttributes(model, redirectAttributes);
            return stringBuilder.toString();
        }


        ImmutableSet<String> geneIds =
                analyticsSearchService.searchMoreThanOneBioentityIdentifier(
                        geneQuery, conditionQuery, species.getReferenceName());

        // No gene IDs -> empty results page
        if (geneIds.size() == 0) {
            return "empty-search-page";
        }

        // Resolves to a single Gene ID -> Gene page
        if (isEmpty(conditionQuery) && geneIds.size() == 1) {
            copyModelAttributesToFlashAttributes(model, redirectAttributes);
            return "redirect:/genes/" + geneIds.iterator().next();
        }
        // Resolves to multiple IDs or the query includes a condition -> General results page
        else {
            ImmutableSet<String> experimentTypes =
                    analyticsSearchService.fetchExperimentTypes(geneQuery, conditionQuery, species.getReferenceName());

            boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
            boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

            if (!hasDifferentialResults && !hasBaselineResults) {
                return "empty-search-page";
            }

            if (hasBaselineResults) {
                model.addAttribute("jsonFacets", gson.toJson(baselineAnalyticsSearchService.findFacetsForTreeSearch
                        (geneQuery, conditionQuery, species)));
            }

            model.addAttribute("hasDifferentialResults", hasDifferentialResults);
            model.addAttribute("hasBaselineResults", hasBaselineResults);

            model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

            return "bioentities-search-results";
        }

    }

    private void copyModelAttributesToFlashAttributes(Model model, RedirectAttributes redirectAttributes) {
        for (String attributeName : model.asMap().keySet()) {
            redirectAttributes.addFlashAttribute(attributeName, model.asMap().get(attributeName));
        }
    }

    @RequestMapping(value = {"/json/query/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
                                              @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                              @RequestParam(value = "organism", required = false, defaultValue = "") String species) {
        return gson.toJson(differentialAnalyticsSearchService.fetchDifferentialFacetsForQuery(geneQuery, conditionQuery,
                species));
    }

    @RequestMapping(value = {"/json/query/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
                                               @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                               @RequestParam(value = "organism", required = false, defaultValue = "") String species) {
        return gson.toJson(differentialAnalyticsSearchService.fetchDifferentialResultsForQuery(geneQuery, conditionQuery,
                species));
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
