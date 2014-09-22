/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWrapperAsGeneSets;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineExperimentProfilesViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineTissueExperimentSearchResult;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesEnsemblTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class BaselineExperimentPageController extends BaselineExperimentController {

    private final TracksUtil tracksUtil;

    private final BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final ApplicationProperties applicationProperties;

    private final FilterFactorMenuBuilder filterFactorMenuBuilder;

    private BaselineRequestContext requestContext;

    private BaselineExperiment experiment;

    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;

    private final BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder;

    private final SpeciesEnsemblTrader speciesEnsemblTrader;

    @Inject
    public BaselineExperimentPageController(BaselineProfilesHeatMap baselineProfilesHeatMap,
                                            ApplicationProperties applicationProperties,
                                            BaselineRequestContextBuilder requestContextBuilder,
                                            FilterFactorsConverter filterFactorsConverter,
                                            FilterFactorMenuBuilder filterFactorMenuBuilder,
                                            BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                            SpeciesEnsemblTrader speciesEnsemblTrader,
                                            TracksUtil tracksUtil,
                                            BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                            BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder) {

        super(requestContextBuilder, filterFactorsConverter);
        this.applicationProperties = applicationProperties;
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
        this.filterFactorMenuBuilder = filterFactorMenuBuilder;
        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.speciesEnsemblTrader = speciesEnsemblTrader;
        this.tracksUtil = tracksUtil;
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.baselineExperimentProfilesViewModelBuilder = baselineExperimentProfilesViewModelBuilder;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        prepareModel(preferences, result, model, request);

        addFactorMenu(model);

        model.addAttribute("isWidget", false);

        return "experiment";
    }

    @RequestMapping(value = "/widgets/heatmap/protein", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showGeneProfilesWidget(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks, BindingResult result, Model model, HttpServletRequest request) {

        prepareModel(preferences, result, model, request);

        model.addAttribute("isWidget", true);
        model.addAttribute("disableGeneLinks", disableGeneLinks);
        return "heatmap-widget";
    }

    @RequestMapping(value = "/widgets/heatmap/bioentity", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showGeneProfilesWidgetBioentity(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks, BindingResult result, Model model, HttpServletRequest request) {

        prepareModel(preferences, result, model, request);

        model.addAttribute("isWidget", true);
        model.addAttribute("disableGeneLinks", disableGeneLinks);
        model.addAttribute("isMultiExperiment", true);

        String geneQuery = preferences.getGeneQuery();
        String species = requestContext.getFilteredBySpecies();
        BaselineTissueExperimentSearchResult searchResult;
        model.addAttribute("geneQuery", geneQuery);

        try {
            searchResult = baselineExperimentProfileSearchService.query(geneQuery, species, true);
        } catch (GenesNotFoundException e) {
            model.addAttribute("errorMessage", "No genes found matching query: '" + geneQuery + "'");
            model.addAttribute("identifier", geneQuery);
            return "widget-error";
        }


        SortedSet<Factor> orderedFactors = searchResult.getTissueFactorsAcrossAllExperiments();
        SortedSet<AssayGroupFactor> filteredAssayGroupFactors = convert(orderedFactors);

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        addJsonForHeatMap(experimentProfiles, filteredAssayGroupFactors, orderedFactors, model);

        //TODO: refactor this whole method into HeatmapWidgetController, and then remove this line as it won't be needed
        model.addAttribute("geneProfiles", experimentProfiles);

        return "heatmap-widget-react";
    }

    private SortedSet<AssayGroupFactor> convert(SortedSet<Factor> orderedFactors) {
        ImmutableSortedSet.Builder<AssayGroupFactor> builder = ImmutableSortedSet.naturalOrder();

        for (Factor factor : orderedFactors) {
            AssayGroupFactor assayGropuFactor = new AssayGroupFactor("none",factor);
            builder.add(assayGropuFactor);
        }

        return builder.build();
    }

    private BaselineProfilesList fetchGeneProfilesAsGeneSets() {
        BaselineProfileStreamOptionsWrapperAsGeneSets options = new BaselineProfileStreamOptionsWrapperAsGeneSets(requestContext);
        return baselineProfilesHeatMap.fetch(options);
    }

    private void prepareModel(BaselineRequestPreferences preferences, BindingResult result, Model model, HttpServletRequest request) {

        initializeContext(preferences, request);

        model.addAttribute("experimentAccession", experiment.getAccession());

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        model.addAttribute("queryFactorName", experimentalFactors.getFactorDisplayName(preferences.getQueryFactorType()));

        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();

        SortedSet<AssayGroupFactor> filteredAssayGroupFactors = experimentalFactors.getFilteredAssayGroupFactors(selectedFilterFactors);

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        // It is in order.
        model.addAttribute("allQueryFactors", filteredAssayGroupFactors);

        String species = requestContext.getFilteredBySpecies();

        //required by autocomplete
        model.addAttribute("species", species);

        //required for genome track browser in ensembl
        String ensemblDB = speciesEnsemblTrader.getEnsemblDb(species);
        model.addAttribute("ensemblDB", ensemblDB);

        if(!filteredAssayGroupFactors.isEmpty()) {
            model.addAttribute("enableEnsemblLauncher", tracksUtil.hasBaselineTracksPath(experiment.getAccession(), filteredAssayGroupFactors.iterator().next().getAssayGroupId()));
        } else {
            model.addAttribute("enableEnsemblLauncher", false);
        }

        if (!result.hasErrors()) {

            try {

                //TODO: remove model attributes for profiles when widget is converted over to React
                BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(requestContext);
                model.addAttribute("geneProfiles", baselineProfiles);

                BaselineProfilesList profilesAsGeneSets = requestContext.geneQueryResponseContainsGeneSets() ? fetchGeneProfilesAsGeneSets() : null;
                if (profilesAsGeneSets != null) {
                    model.addAttribute("profilesAsGeneSets", profilesAsGeneSets);
                    model.addAttribute("isGeneSetQuery", true);
                } else {
                    model.addAttribute("isGeneSetQuery", false);
                }

                addJsonForHeatMap(baselineProfiles, profilesAsGeneSets, filteredAssayGroupFactors, experimentalFactors.getFilteredFactors(selectedFilterFactors), model);

                if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
                    ImmutableSet<String> allSvgPathIds = extractOntologyTerm(filteredAssayGroupFactors);
                    addAnatomogram(allSvgPathIds, model, species);

                } else {
                    model.addAttribute("hasAnatomogram", false);
                }

            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }
    }

    private ImmutableSet<String> extractOntologyTerm(SortedSet<AssayGroupFactor> filteredAssayGroupFactors) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (AssayGroupFactor assayGroupFactor : filteredAssayGroupFactors) {
            builder.add(assayGroupFactor.getValueOntologyTerm());
        }
        return builder.build();
    }


    private void addAnatomogram(ImmutableSet<String> allSvgPathIds, Model model, String species) {
        //ToDo: check if this can be externalized in the view with a cutom EL or tag function
        String maleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, true);
        model.addAttribute("maleAnatomogramFile", maleAnatomogramFileName);

        String femaleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, false);
        model.addAttribute("femaleAnatomogramFile", femaleAnatomogramFileName);

        model.addAttribute("hasAnatomogram", maleAnatomogramFileName != null || femaleAnatomogramFileName != null);

        String jsonAllSvgPathIds = new Gson().toJson(allSvgPathIds);
        model.addAttribute("allSvgPathIds", jsonAllSvgPathIds);
    }


    private void addJsonForHeatMap(BaselineExperimentProfilesList baselineProfiles, SortedSet<AssayGroupFactor> filteredAssayGroupFactors, SortedSet<Factor> orderedFactors, Model model) {
        Gson gson = new Gson();

        String jsonAssayGroupFactors = gson.toJson(filteredAssayGroupFactors);
        model.addAttribute("jsonColumnHeaders", jsonAssayGroupFactors);

        BaselineProfilesViewModel profilesViewModel = baselineExperimentProfilesViewModelBuilder.build(baselineProfiles, orderedFactors);

        String jsonProfiles = gson.toJson(profilesViewModel);
        model.addAttribute("jsonProfiles", jsonProfiles);
    }

    private void addJsonForHeatMap(BaselineProfilesList baselineProfiles, BaselineProfilesList geneSetProfiles, SortedSet<AssayGroupFactor> filteredAssayGroupFactors, SortedSet<Factor> orderedFactors, Model model) {
        Gson gson = new Gson();

        String jsonAssayGroupFactors = gson.toJson(filteredAssayGroupFactors);
        model.addAttribute("jsonColumnHeaders", jsonAssayGroupFactors);

        BaselineProfilesViewModel profilesViewModel = baselineProfilesViewModelBuilder.build(baselineProfiles, orderedFactors);

        String jsonProfiles = gson.toJson(profilesViewModel);
        model.addAttribute("jsonProfiles", jsonProfiles);

        String jsonGeneSetProfiles = (geneSetProfiles != null) ? gson.toJson(baselineProfilesViewModelBuilder.build(geneSetProfiles, orderedFactors)) : "undefined";
        model.addAttribute("jsonGeneSetProfiles", jsonGeneSetProfiles);
    }

    private void initializeContext(BaselineRequestPreferences preferences, HttpServletRequest request) {
        experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        setPreferenceDefaults(preferences, experiment);

        requestContext = buildRequestContext(experiment, preferences);
    }

    private void addFactorMenu(Model model) {

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        //ToDo: this stuff should be refactored, menu should be a separate REST service
        SortedSet<String> menuFactorNames = experimentalFactors.getMenuFilterFactorNames();
        if (!menuFactorNames.isEmpty()) {

            Set<Factor> menuFactors = experimentalFactors.getAllFactors();

            SortedSet<FilterFactorMenuVoice> filterFactorMenu = filterFactorMenuBuilder
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

        model.addAttribute("selectedFilterFactorsJson", new Gson().toJson(requestContext.getSelectedFilterFactors()));
    }

}