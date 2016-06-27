package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Optional;
import com.google.gson.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineExperimentUtil;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
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
    private final AnatomogramFactory anatomogramFactory;
    private Gson gson = new GsonBuilder()
            .create();

    public BaselineExperimentPageService(BaselineProfilesHeatMapWranglerFactory baselineProfilesHeatMapWranglerFactory,
                                         ApplicationProperties applicationProperties,
                                         SpeciesKingdomTrader speciesKingdomTrader,
                                         TracksUtil tracksUtil,
                                         BaselineExperimentUtil bslnUtil, PreferencesForBaselineExperiments preferencesForBaselineExperiments) {

        this.applicationProperties = applicationProperties;
        this.anatomogramFactory = new AnatomogramFactory(applicationProperties);
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

    public void prepareRequestPreferencesAndHeaderData(BaselineExperiment experiment, BaselineRequestPreferences preferences, Model model,
                                                       HttpServletRequest request, boolean isWidget) {


        if (isWidget) {
            // possibly we could always do this - investigate if it matters for not-a-widget
            //TODO: hacky work around to support clients using the geneQuery=A1A4S6+Q13177 syntax
            // ideally we should move queryStringToTags to javascript, and keep the former space separated syntax
            // instead of the current tab separated syntax for geneQuery
            preferences.setGeneQuery(GeneQuery.create(TagEditorConverter.queryStringToTags((String) request.getAttribute(HeatmapWidgetController.ORIGINAL_GENEQUERY))));
            preferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);
        } else {
            preferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);
            BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment, preferences);
            addFactorMenu(model, experiment, requestContext);
        }

        Set<AssayGroupFactor> filteredAssayGroupFactors = getFilteredAssayGroupFactors(experiment, preferences);

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));
        model.addAttribute("allQueryFactors", filteredAssayGroupFactors);
        model.addAttribute("queryFactorName", experiment.getExperimentalFactors().getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAllAttributes(experiment.getBaselineAttributes());
        DownloadURLBuilder.addRDownloadUrlToModel(model, request.getRequestURI());
    }

    public void populateModelWithHeatmapData(BaselineExperiment experiment, BaselineRequestPreferences preferences,
                                             Model model, HttpServletRequest request, boolean isWidget,
                                             boolean disableGeneLinks) throws GenesNotFoundException {
        //we'd rather set these defaults elsewhere, and ideally not use the preferences object at all.
        preferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

        BaselineRequestContext requestContext = BaselineRequestContext.createFor(experiment, preferences);
        Set<AssayGroupFactor> filteredAssayGroupFactors = getFilteredAssayGroupFactors(experiment, preferences);
        String contextRoot = request.getContextPath();
        /*From here on preferences are immutable, variables not required for request-preferences.jsp*/
        model.addAttribute("isFortLauderdale", bslnUtil.hasFortLauderdale(experiment.getAccession()));
        model.addAttribute("exactMatch", preferences.isExactMatch());
        model.addAttribute("geneQuery", preferences.getGeneQuery());
        model.addAllAttributes(experiment.getBaselineAttributes());

        model.addAttribute("queryFactorName", experiment.getExperimentalFactors().getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAttribute("serializedFilterFactors", preferences.getSerializedFilterFactors());

        String species = requestContext.getFilteredBySpecies();

        model.addAllAttributes(speciesKingdomTrader.getAttributesFor(species));

        model.addAttribute("enableEnsemblLauncher", !filteredAssayGroupFactors.isEmpty()
                && tracksUtil.hasBaselineTracksPath(experiment.getAccession(),
                filteredAssayGroupFactors.iterator().next().getAssayGroupId()));

        BaselineProfilesHeatMapWrangler heatMapResults = baselineProfilesHeatMapWranglerFactory.create(preferences,
                experiment);

        model.addAttribute("jsonColumnHeaders", constructColumnHeaders(filteredAssayGroupFactors));

        model.addAttribute("jsonProfiles", viewModelAsJson(heatMapResults.getJsonProfiles()));

        model.addAttribute("jsonCoexpressions", gson.toJson(heatMapResults.getJsonCoexpressions()));


        Optional<JsonObject> geneSets = heatMapResults.getJsonProfilesAsGeneSets();
        model.addAttribute("jsonGeneSetProfiles",
                geneSets.isPresent()
                ? viewModelAsJson(geneSets.get())
                : "null");

        model.addAttribute("anatomogram", gson.toJson(anatomogramFactory.get(requestContext.getQueryFactorType(), species,
                filteredAssayGroupFactors, contextRoot)));

        model.addAttribute("isWidget", isWidget);
        if (!isWidget) {
            addFactorMenu(model, experiment, requestContext);
        } else {
            model.addAttribute("disableGeneLinks", disableGeneLinks);
            model.addAttribute("downloadURL", applicationProperties.buildDownloadURLForWidget(request, experiment.getAccession()));
            model.addAttribute("enableEnsemblLauncher", false);
        }

        //note this should only happen for single experiment - see HeatmapWidgetController.populateModelWithMultiExperimentResults
        model.addAttribute(
            "jsonExperiment",
            gson.toJson(prepareExperimentDescription(experiment, preferences.getGeneQuery(), preferences.getSerializedFilterFactors()))
        );
    }

    //used when external parties include our widget
    private JsonElement prepareExperimentDescription(Experiment experiment, GeneQuery geneQuery, String serializedFilterFactors){
        String additionalQueryOptionsString =
                "?geneQuery="+geneQuery.asUrlQueryParameter()+
                        "&serializedFilterFactors="+serializedFilterFactors;

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("URL", "/experiments/"+experiment.getAccession()+additionalQueryOptionsString);
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies());
        return experimentDescription;
    }

    // heatmap-data.jsp will understand "" as empty
    private String viewModelAsJson(JsonObject viewModel){
        return viewModel.has("rows")
                && viewModel.get("rows").isJsonArray()
                && viewModel.get("rows").getAsJsonArray().size() >0
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