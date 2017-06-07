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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.geneset.GeneSetUtil;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;
import static uk.ac.ebi.atlas.search.SemanticQuery.isNotEmpty;

@Controller
@Scope("prototype")
public class SearchController extends HtmlExceptionHandlingController {

    @Autowired
    private Environment env;

    private final AnalyticsSearchService analyticsSearchService;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final SpeciesFactory speciesFactory;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    public SearchController(AnalyticsSearchService analyticsSearchService,
                            BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                            SpeciesFactory speciesFactory) {

        this.analyticsSearchService = analyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.speciesFactory = speciesFactory;

    }

    @RequestMapping(value = "/search")
    public String showGeneQueryResultPage(@RequestParam(value = "geneQuery", required = false, defaultValue = "")
                                          SemanticQuery geneQuery,
                                          @RequestParam(value = "conditionQuery", required = false, defaultValue = "")
                                          SemanticQuery conditionQuery,
                                          @RequestParam(value = "organism", required = false, defaultValue = "")
                                          String speciesString,
                                          Model model, RedirectAttributes redirectAttributes)
            throws UnsupportedEncodingException {

        checkArgument(
                isNotEmpty(geneQuery) || isNotEmpty(conditionQuery),
                "Please specify a gene query or a condition query.");

        Species species = speciesFactory.create(speciesString);

        model.addAttribute("searchDescription", SearchDescription.get(geneQuery, conditionQuery, speciesString));
        model.addAttribute("geneQuery", geneQuery.toUrlEncodedJson());
        model.addAttribute("conditionQuery", conditionQuery.toUrlEncodedJson());
        model.addAttribute("species", species.getReferenceName());

        // Matches gene set ID -> Gene set page
        // TODO We decide it’s a gene set because of how the query *looks*, and things like GO:FOOBAR will be incorrectly redirected to /genesets/GO:FOOBAR
        if (isEmpty(conditionQuery) && GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(geneQuery)) {
            String geneSetId = geneQuery.terms().iterator().next().value();

            StringBuilder stringBuilder = new StringBuilder("redirect:/genesets/" + geneSetId);
            // Reactome IDs are species-specific
            if (!GeneSetUtil.matchesReactomeID(geneSetId) && !species.isUnknown()) {
                stringBuilder.append("?").append("organism=").append(species.getReferenceName());
            }

            copyModelAttributesToFlashAttributes(model, redirectAttributes);
            return stringBuilder.toString();
        }

        ImmutableSet<String> geneIds =
                analyticsSearchService.searchMoreThanOneBioentityIdentifier(
                        geneQuery, conditionQuery, species.getReferenceName());

        // No gene IDs -> empty results page
        if (geneIds.size() == 0) {
            return "no-results";
        }

        // Resolves to a single Gene ID -> Gene page
        if (isEmpty(conditionQuery) && geneIds.size() == 1) {
            copyModelAttributesToFlashAttributes(model, redirectAttributes);
            return "redirect:/genes/" + geneIds.iterator().next();
        }
        // Resolves to multiple IDs or the query includes a condition -> General results page
        else {
            ImmutableSet<String> experimentTypes =
                    analyticsSearchService.fetchExperimentTypes(
                            geneQuery, conditionQuery, species.getReferenceName());

            boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
            boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

            if (!hasDifferentialResults && !hasBaselineResults) {
                return "no-results";
            }

            // TODO Should BaselineFacetsTree.jsx do a request to the endpoint in JsonBaselineExperimentsController?
            if (hasBaselineResults) {
                model.addAttribute(
                        "jsonFacets",
                        gson.toJson(baselineAnalyticsSearchService.findFacetsForTreeSearch(
                                geneQuery, conditionQuery, species)));
            }

            model.addAttribute("hasDifferentialResults", hasDifferentialResults);
            model.addAttribute("hasBaselineResults", hasBaselineResults);

            model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

            return "foundation-bioentities-search-results";
        }
    }

    private void copyModelAttributesToFlashAttributes(Model model, RedirectAttributes redirectAttributes) {
        for (String attributeName : model.asMap().keySet()) {
            redirectAttributes.addFlashAttribute(attributeName, model.asMap().get(attributeName));
        }
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class, UnsupportedEncodingException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleSolrException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

}
