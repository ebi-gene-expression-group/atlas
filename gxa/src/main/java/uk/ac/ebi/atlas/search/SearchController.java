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
import uk.ac.ebi.atlas.species.SpeciesInferrer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;
import static uk.ac.ebi.atlas.search.SemanticQuery.isNotEmpty;

@Controller
@Scope("prototype")
public class SearchController {

    @Autowired
    private Environment env;

    private final AnalyticsSearchService analyticsSearchService;
    private final DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final SpeciesInferrer speciesInferrer;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    public SearchController(AnalyticsSearchService analyticsSearchService,
                            DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                            BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                            SpeciesInferrer speciesInferrer) {
        this.analyticsSearchService = analyticsSearchService;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.speciesInferrer = speciesInferrer;
    }

    @RequestMapping(value = "/search")
    public String showGeneQueryResultPage(@RequestParam(value = "query", defaultValue = "") SemanticQuery geneQuery,
                                          @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                          @RequestParam(value = "organism", required = false, defaultValue = "") String speciesString,
                                          @RequestParam(value = "foundation", required = false) String foundationKey,
                                          Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {

        checkArgument(isNotEmpty(geneQuery), "Please specify a search term, a gene query or a condition query.");
        Species species = speciesInferrer.inferSpecies(geneQuery, conditionQuery, speciesString);

        model.addAttribute("searchDescription", SearchDescription.get(geneQuery));
        model.addAttribute("query", geneQuery.toUrlEncodedJson());
        model.addAttribute("conditionQuery", conditionQuery.toUrlEncodedJson());
        model.addAttribute("species", species.getReferenceName());

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

        ImmutableSet<String> geneIds = analyticsSearchService.searchMoreThanOneBioentityIdentifier(geneQuery,
                conditionQuery, species.getReferenceName());

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
            ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(geneQuery,
                    conditionQuery, species.getReferenceName()); //fetchExperimentTypesInAnyField(geneQuery);

            boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
            boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

            if (!hasDifferentialResults && !hasBaselineResults) {
                return "empty-search-page";
            }

            if (hasBaselineResults) {
                model.addAttribute("jsonFacets", fetchBaselineJsonFacets(geneQuery, conditionQuery, species));
            }

            model.addAttribute("hasDifferentialResults", hasDifferentialResults);
            model.addAttribute("hasBaselineResults", hasBaselineResults);

            model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

            return foundationKey != null ? "foundation-bioentities-search-results" : "bioentities-search-results";
        }
    }

    private void copyModelAttributesToFlashAttributes(Model model, RedirectAttributes redirectAttributes) {
        for (String attributeName : model.asMap().keySet()) {
            redirectAttributes.addFlashAttribute(attributeName, model.asMap().get(attributeName));
        }
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
    public String fetchBaselineJsonFacets(@RequestParam(value = "query", defaultValue = "") SemanticQuery geneQuery,
                                          @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                          @RequestParam(value = "organism", required = false, defaultValue = "") Species species) {
        return gson.toJson(baselineAnalyticsSearchService.findFacetsForTreeSearch(geneQuery, conditionQuery, species));
    }

    // TODO Endpoint with generic “query” parameter (cf. with geneQuery and conditionQuery) for single input search in Foundation homepage
    @RequestMapping(value = "/json/search/baselineResults", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String analyticsJson(@RequestParam(value = "query") SemanticQuery query,
                                @RequestParam(value = "species") String speciesString,
                                @RequestParam(value = "source") String defaultQueryFactorType,
                                HttpServletRequest request,
                                Model model) {
        // See JsonExperimentsBaselineController for a reference implementation that takes geneQuery and conditionQuery
        // Species species = speciesInferrer.inferSpeciesForGeneQuery(query, speciesString);
        // Pair<BaselineExperimentProfilesList,List<FactorAcrossExperiments>>  searchResult = baselineAnalyticsSearchService.findExpressions(query, species, defaultQueryFactorType);
        //
        // return gson.toJson(populateModelWithMultiExperimentResults(request,query, species, searchResult, model));
        return "";
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
