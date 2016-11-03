package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.tooltip.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
                                         TracksUtil tracksUtil,FactorGroupingService factorGroupingService) {
        super(atlasResourceHub);
        this.applicationProperties = applicationProperties;
        this.anatomogramFactory = new AnatomogramFactory();
        this.baselineProfilesHeatMapWranglerFactory = baselineProfilesHeatMapWranglerFactory;
        this.tracksUtil = tracksUtil;
        this.factorGroupingService = factorGroupingService;
    }

    //TODO I got misplaced when refactoring, I belong in a controller, not here
    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
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

    public void populateModelWithHeatmapData(BaselineExperiment experiment, BaselineRequestPreferences preferences,
                                             Model model, HttpServletRequest request, boolean isWidget) throws GenesNotFoundException, UnsupportedEncodingException {
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

        model.addAttribute("jsonColumnHeaders",
                gson.toJson(constructColumnHeaders(filteredAssayGroupFactors,experiment)));

        model.addAttribute("jsonColumnGroupings", gson.toJson(factorGroupingService.group(filteredAssayGroupFactors)));

        model.addAttribute("jsonProfiles", viewModelAsJson(heatMapResults.getJsonProfiles()));

        model.addAttribute("jsonCoexpressions", gson.toJson(heatMapResults.getJsonCoexpressions()));


        // Optional<JsonObject> geneSets = heatMapResults.getJsonProfilesAsGeneSets();
        model.addAttribute("jsonGeneSetProfiles", "null");
                /* We decided to disable the feature because it has a performance cost and isn't
                very useful..
                TODO remove (or make more useful)
                geneSets.isPresent()
                ? viewModelAsJson(geneSets.get())
                : "null");*/

        model.addAttribute("anatomogram", gson.toJson(anatomogramFactory.get(requestContext.getQueryFactorType(),
                experiment.getSpecies(),
                filteredAssayGroupFactors)));

        model.addAttribute("isWidget", isWidget);
        if (!isWidget) {
            addFactorMenu(model, experiment, requestContext);
        } else {
            model.addAttribute("downloadURL", applicationProperties.buildDownloadURLForWidget(request, experiment.getAccession()));
        }

        model.addAllAttributes(payloadAttributes(experiment, preferences));
    }

    // heatmap-data.jsp will understand "" as empty
    private String viewModelAsJson(JsonObject viewModel){
        return viewModel.has("rows")
                && viewModel.get("rows").isJsonArray()
                && viewModel.get("rows").getAsJsonArray().size() >0
                ? gson.toJson(viewModel)
                : "";
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