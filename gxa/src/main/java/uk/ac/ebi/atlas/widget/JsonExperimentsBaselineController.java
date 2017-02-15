package uk.ac.ebi.atlas.widget;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.profiles.json.ProfilesToJsonConverter;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.experimentpage.baseline.AnatomogramFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.model.experiment.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("request")
public final class JsonExperimentsBaselineController extends WidgetExceptionHandler {

    private final AnatomogramFactory anatomogramFactory;
    private final SpeciesInferrer speciesInferrer;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final FactorGroupingService factorGroupingService;
    private final HeatmapDataToJsonService heatmapDataToJsonService;

    private Gson gson = new Gson();

    @Inject
    private JsonExperimentsBaselineController(SpeciesInferrer speciesInferrer,
                                              BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                              FactorGroupingService factorGroupingService,
                                              HeatmapDataToJsonService heatmapDataToJsonService) {
        this.anatomogramFactory = new AnatomogramFactory();
        this.speciesInferrer = speciesInferrer;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.factorGroupingService = factorGroupingService;
        this.heatmapDataToJsonService = heatmapDataToJsonService;
    }

    @RequestMapping(value = "/widgets/heatmap/baselineAnalytics", method = RequestMethod.GET)
    @Deprecated
    public String analyticsJson() {
        return "forward:/json/baseline_experiments";
    }

    @RequestMapping(value = "/json/baseline_experiments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String jsonBaselineExperiments(@RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
                                          @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                          @RequestParam(value = "source", required = false) String defaultQueryFactorType,
                                          @RequestParam(value = "species", required = false, defaultValue = "") String speciesString,
                                          HttpServletRequest request, Model model) {

        Species species = speciesInferrer.inferSpecies(geneQuery, conditionQuery, speciesString);

        BaselineExperimentSearchResult searchResult =
                baselineAnalyticsSearchService.findExpressions(
                        geneQuery, conditionQuery, species, defaultQueryFactorType);

        return gson.toJson(
                populateModelWithMultiExperimentResults(
                        request, geneQuery, conditionQuery, species, searchResult, model));
    }

    private JsonObject populateModelWithMultiExperimentResults(HttpServletRequest request,SemanticQuery geneQuery,
                                                               SemanticQuery conditionQuery, Species species,
                                                               BaselineExperimentSearchResult searchResult, Model model) {
        model.addAttribute("geneQuery", geneQuery.toUrlEncodedJson());
        model.addAttribute("conditionQuery", conditionQuery.toUrlEncodedJson());
        //TODO does not take conditionQuery into account - but it's very hard to implement
        try {

            return resultsFromDataAndModel(ProfilesToJsonConverter.createForCrossExperimentResults(
                    new URI(ApplicationProperties.buildServerURL(request) + "/experiments/"),
                    ImmutableMap.of("geneQuery", geneQuery.toUrlEncodedJson())
                    ),
                    request, species, searchResult, model);
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }

    }

    private JsonObject resultsFromDataAndModel(ProfilesToJsonConverter<Factor, BaselineExperimentProfile> profilesToJsonConverter,
                                               HttpServletRequest request, Species species,
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

            result.add("profiles", profilesToJsonConverter.convert(experimentProfiles, orderedFactors));
            result.add("geneSetProfiles", JsonNull.INSTANCE);
            result.add("jsonCoexpressions", new JsonArray());
        }

        model.addAttribute("species", species.getReferenceName());
        model.addAttribute("isWidget", true);
        result.add("experiment", JsonNull.INSTANCE);

        result.add("config", heatmapDataToJsonService.configAsJsonObject(request, model.asMap()));
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
