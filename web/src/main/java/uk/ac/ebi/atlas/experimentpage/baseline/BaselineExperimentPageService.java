package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineExperimentUtil;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModel;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.TagEditorConverter;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.widget.HeatmapWidgetController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class BaselineExperimentPageService {

    private final TracksUtil tracksUtil;
    private final BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory;
    private final ApplicationProperties applicationProperties;
    private final SpeciesKingdomTrader speciesKingdomTrader;
    private BaselineExperimentUtil bslnUtil;
    private final PreferencesForBaselineExperiments preferencesForBaselineExperiments;
    private Gson gson = new GsonBuilder()
            .serializeSpecialFloatingPointValues() //Allow "NaN" for expression levels
            .create();

    public BaselineExperimentPageService(BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory,
                                         ApplicationProperties applicationProperties,
                                         SpeciesKingdomTrader speciesKingdomTrader,
                                         TracksUtil tracksUtil,
                                         BaselineExperimentUtil bslnUtil, PreferencesForBaselineExperiments preferencesForBaselineExperiments) {

        this.applicationProperties = applicationProperties;
        this.baselineProfilesHeatMapWranglerFactory = baselineProfilesHeatMapWranglerFactory;
        this.speciesKingdomTrader = speciesKingdomTrader;
        this.tracksUtil = tracksUtil;
        this.bslnUtil = bslnUtil;
        this.preferencesForBaselineExperiments = preferencesForBaselineExperiments;
    }

    //TODO I got misplaced when refactoring, I belong in a controller, not here
    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    public void prepareModel(BaselineRequestPreferences preferences, Model model, HttpServletRequest request, boolean
            shouldAddRDownloadUrl, boolean amIAWidget, boolean disableGeneLinks) throws
            GenesNotFoundException {

        if (amIAWidget) {
            // possibly we could always do this - investigate if it matters for not-a-widget

            //TODO: hacky work around to support clients using the geneQuery=A1A4S6+Q13177 syntax
            // ideally we should move queryStringToTags to javascript, and keep the former space separated syntax
            // instead of the current tab separated syntax for geneQuery
            preferences.setGeneQuery(GeneQuery.create(TagEditorConverter.queryStringToTags((String) request.getAttribute(HeatmapWidgetController.ORIGINAL_GENEQUERY))));
        }

        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);
        preferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

        BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment, preferences);

        model.addAttribute("isFortLauderdale", bslnUtil.hasFortLauderdale(experiment.getAccession()));
        model.addAllAttributes(experiment.getBaselineAttributes());

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        model.addAttribute("queryFactorName", experimentalFactors.getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAttribute("serializedFilterFactors", preferences.getSerializedFilterFactors());

        Set<AssayGroupFactor> filteredAssayGroupFactors = getFilteredAssayGroupFactors(experiment, preferences);

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        model.addAttribute("allQueryFactors", filteredAssayGroupFactors);

        String species = requestContext.getFilteredBySpecies();

        model.addAllAttributes(speciesKingdomTrader.getAttributesFor(species));

        model.addAttribute("enableEnsemblLauncher", !filteredAssayGroupFactors.isEmpty()
                && tracksUtil.hasBaselineTracksPath(experiment.getAccession(),
                filteredAssayGroupFactors.iterator().next().getAssayGroupId()));

        BaselineProfilesHeatMapWrangler heatMapResults = baselineProfilesHeatMapWranglerFactory.create(preferences,
                experiment);



        model.addAttribute("jsonColumnHeaders", constructColumnHeaders(filteredAssayGroupFactors));

        model.addAttribute("jsonProfiles", viewModelAsJson(heatMapResults.getJsonProfiles()));

        model.addAttribute("jsonCoexpressions",
                coexpressionsAsJsonArray(heatMapResults.getJsonCoexpressions()));


        Optional<BaselineProfilesViewModel> geneSets = heatMapResults.getJsonProfilesAsGeneSets();
        model.addAttribute("jsonGeneSetProfiles",
                geneSets.isPresent()
                ? viewModelAsJson(geneSets.get())
                : "null");

        if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
            model.addAllAttributes(applicationProperties.getAnatomogramProperties(species, filteredAssayGroupFactors));

        } else {
            model.addAttribute("hasAnatomogram", false);
        }

        if (shouldAddRDownloadUrl) {
            //Currently I am false for proteomics and widgets - is there a harm in adding me?
            DownloadURLBuilder.addRDownloadUrlToModel(model, request);
        }
        if (!amIAWidget) {
            model.addAttribute("isWidget", false);
            addFactorMenu(model, experiment, requestContext);
        } else {
            model.addAttribute("isWidget", true);
            model.addAttribute("disableGeneLinks", disableGeneLinks);
            model.addAttribute("downloadURL", applicationProperties.buildDownloadURLForWidget(request, experiment.getAccession()));
            model.addAttribute("enableEnsemblLauncher", false);
        }
    }

    // heatmap-data.jsp will understand "" as empty
    private String viewModelAsJson(BaselineProfilesViewModel viewModel){
        return viewModel.getRows().length > 0
                ? gson.toJson(viewModel)
                : "";
    }

    private Set<AssayGroupFactor> getFilteredAssayGroupFactors(BaselineExperiment experiment, BaselineRequestPreferences preferences){
        Set<Factor> selectedFilterFactors = BaselineRequestContext.createFor(experiment,preferences).getSelectedFilterFactors();

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
        if (experimentalFactors.getAllFactorsOrderedByXML() != null && !experimentalFactors.getAllFactorsOrderedByXML().isEmpty()) {
            return experimentalFactors.getComplementAssayGroupFactorsByXML(selectedFilterFactors);
        } else {
            return experimentalFactors.getComplementAssayGroupFactors(selectedFilterFactors);
        }
    }

    private String coexpressionsAsJsonArray(Map<String, BaselineProfilesViewModel> coexpressions){
        JsonArray result = new JsonArray();
        for(Map.Entry<String, BaselineProfilesViewModel> e: coexpressions.entrySet()){
            JsonObject resultForThisGene = new JsonObject();
            resultForThisGene.addProperty("geneName", e.getKey());
            resultForThisGene.add("jsonProfiles", gson.toJsonTree(e.getValue()).getAsJsonObject());
            result.add(resultForThisGene);
        }
        return gson.toJson(result);
    }

    private String constructColumnHeaders(Set<AssayGroupFactor> filteredAssayGroupFactors){
        return gson.toJson(AssayGroupFactorViewModel.createList(filteredAssayGroupFactors));
    }

    private void addFactorMenu(Model model, BaselineExperiment experiment, BaselineRequestContext requestContext) {

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        //ToDo: this stuff should be refactored, menu should be a separate REST service
        SortedSet<String> menuFactorNames = experimentalFactors.getMenuFilterFactorNames();
        if (!menuFactorNames.isEmpty()) {
            Set<Factor> menuFactors;
            if (!experimentalFactors.getAllFactorsOrderedByXML().isEmpty()) {
                menuFactors = experimentalFactors.getAllFactorsOrderedByXML();
            } else {
                menuFactors = experimentalFactors.getAllFactors();
            }

            SortedSet<FilterFactorMenuVoice> filterFactorMenu = new FilterFactorMenuBuilder()
                    .withExperimentalFactors(experimentalFactors)
                    .forFilterFactors(menuFactors)
                    .build();

            model.addAttribute("filterFactorMenu", filterFactorMenu);
            model.addAttribute("menuFactorNames", menuFactorNames);

        }

        //ToDo: looks bad, a custom EL function or jsp tag function to resolve names would be much better
        Map<String, String> selectedFilterFactorNamesAndValues = new HashMap<>();
        for (Factor selectedFilterFactor : requestContext.getSelectedFilterFactors()) {
            selectedFilterFactorNamesAndValues.put(experimentalFactors.getFactorDisplayName(selectedFilterFactor.getType()), selectedFilterFactor.getValue());
        }
        model.addAttribute("selectedFilterFactorNamesAndValues", selectedFilterFactorNamesAndValues);

    }

}