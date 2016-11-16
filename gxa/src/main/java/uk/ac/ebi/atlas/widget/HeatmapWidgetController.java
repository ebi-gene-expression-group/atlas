package uk.ac.ebi.atlas.widget;

import com.google.gson.*;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.experimentpage.baseline.AnatomogramFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageServiceFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineExperimentProfilesViewModelBuilder;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public final class HeatmapWidgetController extends HeatmapWidgetErrorHandler {

    private AnatomogramFactory anatomogramFactory;
    private SpeciesLookupService speciesLookupService;

    private final BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final BaselineExperimentPageService baselineExperimentPageService;
    private final SpeciesFactory speciesFactory;
    private final FactorGroupingService factorGroupingService;
    private final HeatmapDataToJsonService heatmapDataToJsonService;

    private Gson gson = new Gson();

    @Inject
    private HeatmapWidgetController(SpeciesLookupService speciesLookupService,
                                    BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder,
                                    BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                    BaselineExperimentPageServiceFactory baselineExperimentPageServiceFactory,
                                    @Qualifier ("baselineProfileInputStreamFactory")BaselineProfileInputStreamFactory
                                                baselineProfileInputStreamFactory,
                                    SpeciesFactory speciesFactory,FactorGroupingService factorGroupingService,
                                    HeatmapDataToJsonService heatmapDataToJsonService) {
        this.anatomogramFactory = new AnatomogramFactory();
        this.speciesLookupService = speciesLookupService;
        this.baselineExperimentProfilesViewModelBuilder = baselineExperimentProfilesViewModelBuilder;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(baselineProfileInputStreamFactory);
        this.speciesFactory = speciesFactory;
        this.factorGroupingService = factorGroupingService;
        this.heatmapDataToJsonService = heatmapDataToJsonService;
    }

    @RequestMapping(value = "/widgets/heatmap/referenceExperiment", params = "type=RNASEQ_MRNA_BASELINE", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchReferenceExperimentProfilesJson(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                                       Model model, HttpServletRequest request) {
        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute("experiment");

        baselineExperimentPageService.prepareRequestPreferencesAndHeaderData(experiment, preferences, model, request,true);
        try {
            return gson.toJson(baselineExperimentPageService.populateModelWithHeatmapData(experiment, preferences, model,
                    request, true));
        } catch (uk.ac.ebi.atlas.web.GenesNotFoundException e) {
            throw new ResourceNotFoundException("No genes found matching query: '" + preferences.getGeneQuery() + "'");
        }
    }

    @RequestMapping(value = "/widgets/heatmap/baselineAnalytics", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String analyticsJson(@RequestParam(value = "geneQuery") SemanticQuery geneQuery,
                                @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                @RequestParam(value = "species", required = false, defaultValue = "") String speciesString,
                                @RequestParam(value = "propertyType", required = false) String propertyType,
                                @RequestParam(value = "source", required = false) String defaultQueryFactorType,
                                HttpServletRequest request,
                                Model model) {

        if(isBlank(speciesString)){
            Optional<String> maybeSpecies = speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery);
            if(maybeSpecies.isPresent()){
                speciesString = maybeSpecies.get();
            } else {
                throw new ResourceNotFoundException(
                        "Species can't be determined for " + propertyType + ":" + geneQuery.toJson());
            }
        }

        Species species = speciesFactory.create(speciesString);

        BaselineExperimentSearchResult searchResult =
                baselineAnalyticsSearchService.findExpressions(
                        geneQuery, conditionQuery, species, defaultQueryFactorType);

        return gson.toJson(populateModelWithMultiExperimentResults(request,geneQuery,conditionQuery, species, searchResult, model));
    }

    @RequestMapping(value = "/json/search/baselineResults", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String analyticsJson(@RequestParam(value = "query") SemanticQuery query,
                                @RequestParam(value = "species") String speciesString,
                                @RequestParam(value = "source") String defaultQueryFactorType,
                                HttpServletRequest request,
                                Model model) {

        Species species = speciesFactory.create(speciesString);
        BaselineExperimentSearchResult searchResult = baselineAnalyticsSearchService.findExpressions(query, species, defaultQueryFactorType);

        return gson.toJson(populateModelWithMultiExperimentResults(request,query, species, searchResult, model));
    }

    private JsonObject populateModelWithMultiExperimentResults(HttpServletRequest request,SemanticQuery geneQuery,
                                                               SemanticQuery conditionQuery, Species species,
                                                               BaselineExperimentSearchResult searchResult, Model model) {
        model.addAttribute("geneQuery", geneQuery);
        model.addAttribute("conditionQuery", conditionQuery);
        return resultsFromDataAndModel(request, species, searchResult, model);
    }

    private JsonObject populateModelWithMultiExperimentResults(HttpServletRequest request,SemanticQuery query, Species species,
                                                         BaselineExperimentSearchResult searchResult, Model model) {
        model.addAttribute("query", query);
        return resultsFromDataAndModel(request, species, searchResult, model);
    }

    private JsonObject resultsFromDataAndModel(HttpServletRequest request, Species species,
                                BaselineExperimentSearchResult searchResult, Model model){

        JsonObject result = new JsonObject();
        List<Factor> orderedFactors = Lists.newArrayList(searchResult.getFactorsAcrossAllExperiments());

        if (searchResult.containsFactorOfType("ORGANISM_PART")) {
            result.add(
                    "anatomogram",
                    anatomogramFactory.get("ORGANISM_PART", species, convert(orderedFactors)));
        } else {
            result.add("anatomogram", JsonNull.INSTANCE);
        }

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        if(!experimentProfiles.isEmpty()){
            result.add("columnHeaders", gson.toJsonTree(AssayGroupFactorViewModel.createList(convert(orderedFactors))));
            result.add("columnGroupings", factorGroupingService.group(convert(orderedFactors)));
            result.add("profiles", baselineExperimentProfilesViewModelBuilder.buildJson
                    (experimentProfiles, orderedFactors));
            result.add("geneSetProfiles", JsonNull.INSTANCE);
            result.add("jsonCoexpressions", new JsonArray());
        }

        model.addAttribute("species", species.mappedName);
        model.addAttribute("isWidget", true);
        result.add("experiment", JsonNull.INSTANCE);

        result.add("config", heatmapDataToJsonService.configAsJsonObject(request, SemanticQuery.create(), SemanticQuery.create(),
                model.asMap()));
        return result;
    }

    private List<AssayGroupFactor> convert(List<Factor> orderedFactors) {
        ImmutableSortedSet.Builder<AssayGroupFactor> builder = ImmutableSortedSet.naturalOrder();

        for (Factor factor : orderedFactors) {
            builder.add( new AssayGroupFactor("none",factor));
        }

        return new ArrayList<>(builder.build());
    }
}
