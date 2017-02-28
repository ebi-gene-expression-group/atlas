package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaselineExperimentPageService extends ExperimentPageService {

    private final TracksUtil tracksUtil;
    private final BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory;
    private final AnatomogramFactory anatomogramFactory;
    private final FactorGroupingService factorGroupingService;

    public BaselineExperimentPageService(BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory,
                                         ApplicationProperties applicationProperties,
                                         AtlasResourceHub atlasResourceHub,
                                         TracksUtil tracksUtil,FactorGroupingService factorGroupingService,
                                         HeatmapDataToJsonService heatmapDataToJsonService) {
        super(atlasResourceHub, heatmapDataToJsonService, applicationProperties);
        this.anatomogramFactory = new AnatomogramFactory();
        this.baselineProfilesHeatMapWranglerFactory = baselineProfilesHeatMapWranglerFactory;
        this.tracksUtil = tracksUtil;
        this.factorGroupingService = factorGroupingService;
    }

    public void prepareRequestPreferencesAndHeaderData(BaselineExperiment experiment, BaselineRequestPreferences preferences, Model model,
                                                       HttpServletRequest request) {
        PreferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);
        
        // not sure if I still need atlasHost, old comment:
        // this is currently required for the request requestPreferences filter  drop-down multi-selection box
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(headerAttributes(experiment));
    }

    public JsonObject populateModelWithHeatmapData(BaselineExperiment experiment, BaselineRequestPreferences preferences,
                                             Model model, HttpServletRequest request, boolean isWidget) {
        JsonObject result = new JsonObject();
        //we'd rather set these defaults elsewhere, and ideally not use the preferences object at all.
        PreferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

        BaselineRequestContext requestContext = new BaselineRequestContext(preferences, experiment);
        List<AssayGroup> dataColumnsToReturn =requestContext.getDataColumnsToReturn();

        /*From here on preferences are immutable, variables not required for request-preferences.jsp*/
        model.addAttribute("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        model.addAllAttributes(experiment.getAttributes());

        model.addAttribute("queryFactorName", experiment.getExperimentalFactors().getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAttribute("serializedFilterFactors", preferences.getSerializedFilterFactors());

        model.addAttribute("enableEnsemblLauncher", !isWidget&& !requestContext.getDataColumnsToReturn().isEmpty()
                && tracksUtil.hasBaselineTracksPath(experiment.getAccession(),
                requestContext.getDataColumnsToReturn().iterator().next().getId()));

        BaselineProfilesHeatMapWrangler heatMapResults = baselineProfilesHeatMapWranglerFactory.create(preferences,experiment);

        result.add("columnHeaders",
                constructColumnHeaders(dataColumnsToReturn,requestContext, experiment));

        result.add("columnGroupings",factorGroupingService.group(dataColumnsToReturn));

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
                dataColumnsToReturn));

        model.addAttribute("isWidget", isWidget);

        for(Map.Entry<String, JsonElement> e: payloadAttributes(experiment, preferences).entrySet()){
            result.add(e.getKey(), e.getValue());
        }
        model.addAttribute("downloadProfilesURL", downloadURL(preferences.getGeneQuery(), request));
        result.add("config", heatmapDataToJsonService.configAsJsonObject(request,
                model.asMap()));

        return result;
    }

    private JsonArray constructColumnHeaders(List<AssayGroup> dataColumnsToReturn, BaselineRequestContext
            baselineRequestContext,
            BaselineExperiment
            experiment){
        JsonArray result = new JsonArray();

        for(AssayGroup dataColumnDescriptor: dataColumnsToReturn){
            JsonObject o = new JsonObject();
            o.addProperty("assayGroupId", dataColumnDescriptor.getId());
            o.addProperty("factorValue", baselineRequestContext.displayNameForColumn(dataColumnDescriptor));
            o.addProperty("factorValueOntologyTermId", dataColumnDescriptor.getValueOntologyTermId()); //TODO
            o.add("assayGroupSummary",
                    new AssayGroupSummaryBuilder()
                    .forAssayGroup(experiment.getDataColumnDescriptor(dataColumnDescriptor.getId()))
                    .withExperimentDesign(experiment.getExperimentDesign())
                    .build().toJson());
            result.add(o);
        }


        return result;
    }

}