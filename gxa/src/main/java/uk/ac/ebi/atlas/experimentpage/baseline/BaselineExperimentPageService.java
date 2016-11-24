package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.*;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.model.experiment.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class BaselineExperimentPageService extends ExperimentPageService {

    private final TracksUtil tracksUtil;
    private final BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory;
    private final ApplicationProperties applicationProperties;
    private final AnatomogramFactory anatomogramFactory;
    private final FactorGroupingService factorGroupingService;

    public BaselineExperimentPageService(BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory,
                                         ApplicationProperties applicationProperties,
                                         AtlasResourceHub atlasResourceHub,
                                         TracksUtil tracksUtil,FactorGroupingService factorGroupingService,
                                         HeatmapDataToJsonService heatmapDataToJsonService) {
        super(atlasResourceHub, heatmapDataToJsonService);
        this.applicationProperties = applicationProperties;
        this.anatomogramFactory = new AnatomogramFactory();
        this.baselineProfilesHeatMapWranglerFactory = baselineProfilesHeatMapWranglerFactory;
        this.tracksUtil = tracksUtil;
        this.factorGroupingService = factorGroupingService;
    }

    public void prepareRequestPreferencesAndHeaderData(BaselineExperiment experiment, BaselineRequestPreferences preferences, Model model,
                                                       HttpServletRequest request, boolean isWidget) {
        PreferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);
        BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment, preferences);

        if(!isWidget) {
            addFactorMenu(model, experiment, requestContext);
        }

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));
        model.addAttribute("allQueryFactors", requestContext.getOrderedAssayGroupFactors());
        model.addAttribute("queryFactorName", experiment.getExperimentalFactors().getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(headerAttributes(experiment, preferences));
    }

    public JsonObject populateModelWithHeatmapData(BaselineExperiment experiment, BaselineRequestPreferences preferences,
                                             Model model, HttpServletRequest request, boolean isWidget) {
        JsonObject result = new JsonObject();
        //we'd rather set these defaults elsewhere, and ideally not use the preferences object at all.
        PreferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

        BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment, preferences);
        List<AssayGroupFactor> filteredAssayGroupFactors =requestContext.getOrderedAssayGroupFactors();

        /*From here on preferences are immutable, variables not required for request-preferences.jsp*/
        model.addAttribute("geneQuery", preferences.getGeneQuery());
        model.addAllAttributes(experiment.getAttributes());

        model.addAttribute("queryFactorName", experiment.getExperimentalFactors().getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAttribute("serializedFilterFactors", preferences.getSerializedFilterFactors());

        model.addAttribute("enableEnsemblLauncher", !isWidget&& !filteredAssayGroupFactors.isEmpty()
                && tracksUtil.hasBaselineTracksPath(experiment.getAccession(),
                filteredAssayGroupFactors.get(0).getAssayGroupId()));

        BaselineProfilesHeatMapWrangler heatMapResults = baselineProfilesHeatMapWranglerFactory.create(preferences,experiment);

        result.add("columnHeaders",
                constructColumnHeaders(filteredAssayGroupFactors,experiment));

        result.add("columnGroupings",factorGroupingService.group(filteredAssayGroupFactors));

        try {
            result.add("profiles", heatMapResults.getJsonProfiles());
            //TODO remove me after old heatmap goes away, the new heatmap handles no data gracefully
            if(heatMapResults.getJsonProfiles().get("rows").getAsJsonArray().size() == 0 ){
                return heatmapDataToJsonService.jsonError("No genes found matching query: '" + preferences.getGeneQuery() + "'");
            }

            result.add("jsonCoexpressions", heatMapResults.getJsonCoexpressions());

            // Optional<JsonObject> geneSets = heatMapResults.getJsonProfilesAsGeneSets();
            result.add("jsonGeneSetProfiles", new JsonArray());
                /* We decided to disable the feature because it has a performance cost and isn't
                very useful..
                TODO remove (or make more useful)
                geneSets.isPresent()
                ? viewModelAsJson(geneSets.get())
                : "null");*/
        } catch (GenesNotFoundException e){
            return heatmapDataToJsonService.jsonError("No genes found matching query: '" + preferences.getGeneQuery() + "'");
        }

        result.add("anatomogram", anatomogramFactory.get(requestContext.getQueryFactorType(),
                experiment.getSpecies(),
                filteredAssayGroupFactors));

        model.addAttribute("isWidget", isWidget);
        if (!isWidget) {
            addFactorMenu(model, experiment, requestContext);
        } else {
            model.addAttribute("downloadURL", applicationProperties.buildDownloadURLForWidget(request, experiment.getAccession()));
        }

        for(Map.Entry<String, JsonElement> e: payloadAttributes(experiment, preferences).entrySet()){
            result.add(e.getKey(), e.getValue());
        }
        result.add("config", heatmapDataToJsonService.configAsJsonObject(request,preferences.getGeneQuery(), SemanticQuery.create(),
                model.asMap()));

        return result;
    }

    private JsonArray constructColumnHeaders(List<AssayGroupFactor> filteredAssayGroupFactors, BaselineExperiment experiment){
        JsonArray result = new JsonArray();

        for(AssayGroupFactor assayGroupFactor: filteredAssayGroupFactors){
            JsonObject o = new JsonObject();
            o.addProperty("assayGroupId", assayGroupFactor.getAssayGroupId());
            o.addProperty("factorValue", assayGroupFactor.getValue());
            o.addProperty("factorValueOntologyTermId", assayGroupFactor.getValueOntologyTermId());
            o.add("assayGroupSummary",
                    new AssayGroupSummaryBuilder()
                    .forAssayGroup(experiment.getAssayGroups().getAssayGroup(assayGroupFactor.getAssayGroupId()))
                    .withExperimentDesign(experiment.getExperimentDesign())
                    .build().toJson());
            result.add(o);
        }


        return result;
    }

    private void addFactorMenu(Model model, BaselineExperiment experiment, BaselineRequestContext requestContext) {

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        SortedSet<String> menuFactorNames = experimentalFactors.getMenuFilterFactorNames();
        if (!menuFactorNames.isEmpty()) {
            Set<Factor> menuFactors = experimentalFactors.getAllFactors();

            SortedSet<FilterFactorMenuVoice> filterFactorMenu = new FilterFactorMenuBuilder()
                    .withExperimentalFactors(experimentalFactors)
                    .forFilterFactors(menuFactors)
                    .build();

            model.addAttribute("filterFactorMenu", filterFactorMenu);
            model.addAttribute("menuFactorNames", menuFactorNames);
        }

        Map<String, String> selectedFilterFactorNamesAndValues = new HashMap<>();
        for (Factor selectedFilterFactor : requestContext.getSelectedFilterFactors()) {
            selectedFilterFactorNamesAndValues.put(experimentalFactors.getFactorDisplayName(selectedFilterFactor.getType()), selectedFilterFactor.getValue());
        }
        model.addAttribute("selectedFilterFactorNamesAndValues", selectedFilterFactorNamesAndValues);

    }

}