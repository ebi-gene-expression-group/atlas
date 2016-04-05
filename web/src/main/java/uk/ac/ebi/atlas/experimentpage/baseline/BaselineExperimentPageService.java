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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineExperimentUtil;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWrapperAsGeneSets;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.*;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.widget.HeatmapWidgetController;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Named
public class BaselineExperimentPageService {

    private final TracksUtil tracksUtil;
    private final BaselineProfilesHeatMap baselineProfilesHeatMap;
    private final ApplicationProperties applicationProperties;
    private final FilterFactorMenuBuilder filterFactorMenuBuilder;
    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;
    private final SpeciesKingdomTrader speciesKingdomTrader;
    private final AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder;
    private BaselineExperimentUtil bslnUtil;
    private final PreferencesForBaselineExperiments preferencesForBaselineExperiments;

    @Inject
    public BaselineExperimentPageService(BaselineProfilesHeatMap baselineProfilesHeatMap,
                                         ApplicationProperties applicationProperties,
                                         FilterFactorMenuBuilder filterFactorMenuBuilder,
                                         BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                         AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                         SpeciesKingdomTrader speciesKingdomTrader,
                                         TracksUtil tracksUtil,
                                         BaselineExperimentUtil bslnUtil, PreferencesForBaselineExperiments preferencesForBaselineExperiments) {

        this.applicationProperties = applicationProperties;
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
        this.filterFactorMenuBuilder = filterFactorMenuBuilder;
        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.assayGroupFactorViewModelBuilder = assayGroupFactorViewModelBuilder;
        this.speciesKingdomTrader = speciesKingdomTrader;
        this.tracksUtil = tracksUtil;
        this.bslnUtil = bslnUtil;
        this.preferencesForBaselineExperiments = preferencesForBaselineExperiments;
    }

    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    private BaselineProfilesList fetchGeneProfilesAsGeneSets(BaselineRequestContext requestContext) {
        BaselineProfileStreamOptionsWrapperAsGeneSets options = new BaselineProfileStreamOptionsWrapperAsGeneSets(requestContext);
        return baselineProfilesHeatMap.fetch(options);
    }

    public BaselineProfilesList prepareModelAndPossiblyAddFactorMenuAndMaybeRUrlAndWidgetThings
            (BaselineRequestPreferences preferences, BindingResult
            result, Model model, HttpServletRequest request, boolean shouldAddFactorMenu, boolean
            shouldAddRDownloadUrl, boolean amIAWidget, boolean disableGeneLinks) {

        if(amIAWidget){
            // possibly we could always do this - investigate if it matters for not-a-widget

            //TODO: hacky work around to support clients using the geneQuery=A1A4S6+Q13177 syntax
            // ideally we should move queryStringToTags to javascript, and keep the former space separated syntax
            // instead of the current tab separated syntax for geneQuery
            preferences.setGeneQuery(GeneQuery.create(TagEditorConverter.queryStringToTags((String) request.getAttribute(HeatmapWidgetController.ORIGINAL_GENEQUERY))));
        }

        BaselineExperiment experiment =(BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);
        preferencesForBaselineExperiments.setPreferenceDefaults(preferences, experiment);

        BaselineRequestContext requestContext = preferencesForBaselineExperiments.buildRequestContext(experiment,
                preferences);

        model.addAttribute("isFortLauderdale", bslnUtil.hasFortLauderdale(experiment.getAccession()));
        model.addAllAttributes(experiment.getBaselineAttributes());

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        model.addAttribute("queryFactorName", experimentalFactors.getFactorDisplayName(preferences.getQueryFactorType()));
        model.addAttribute("serializedFilterFactors", preferences.getSerializedFilterFactors());

        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();

        Set<Factor> orderedFactors;
        Set<AssayGroupFactor> filteredAssayGroupFactors;
        if(experimentalFactors.getAllFactorsOrderedByXML() != null && !experimentalFactors.getAllFactorsOrderedByXML().isEmpty()) {
            filteredAssayGroupFactors = experimentalFactors.getComplementAssayGroupFactorsByXML(selectedFilterFactors);
            orderedFactors = experimentalFactors.getComplementFactorsByXML(selectedFilterFactors);
        } else {
            filteredAssayGroupFactors = experimentalFactors.getComplementAssayGroupFactors(selectedFilterFactors);
            orderedFactors = experimentalFactors.getComplementFactors(selectedFilterFactors);
        }

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        // It is in order.
        model.addAttribute("allQueryFactors", filteredAssayGroupFactors);

        String species = requestContext.getFilteredBySpecies();

        model.addAllAttributes(speciesKingdomTrader.getAttributesFor(species));

        if(!filteredAssayGroupFactors.isEmpty()) {
            model.addAttribute("enableEnsemblLauncher", tracksUtil.hasBaselineTracksPath(experiment.getAccession(), filteredAssayGroupFactors.iterator().next().getAssayGroupId()));
        } else {
            model.addAttribute("enableEnsemblLauncher", false);
        }

        BaselineProfilesList toReturn = null;
        if (!result.hasErrors()) {

            try {

                BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(requestContext);
                BaselineProfilesList profilesAsGeneSets = requestContext.geneQueryResponseContainsGeneSets() ?
                        fetchGeneProfilesAsGeneSets(requestContext) : null;

                addJsonForHeatMap(baselineProfiles, profilesAsGeneSets, filteredAssayGroupFactors, orderedFactors, model);

                if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
                    model.addAllAttributes(applicationProperties.getAnatomogramProperties(species,filteredAssayGroupFactors));

                } else {
                    model.addAttribute("hasAnatomogram", false);
                }

                toReturn= baselineProfiles;

            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }
        if(shouldAddFactorMenu) {
            addFactorMenu(model, experiment, requestContext);
        }
        if(shouldAddRDownloadUrl) {
            DownloadURLBuilder.addRDownloadUrlToModel(model, request);
        }
        if(!amIAWidget){
            model.addAttribute("isWidget", false);
        } else {

            model.addAttribute("isWidget", true);
            model.addAttribute("disableGeneLinks", disableGeneLinks);
            model.addAttribute("downloadURL", applicationProperties.buildDownloadURLForWidget(request, experiment.getAccession()));
            model.addAttribute("enableEnsemblLauncher", false);
        }
        return toReturn;
    }

    private void addJsonForHeatMap(BaselineProfilesList baselineProfiles, BaselineProfilesList geneSetProfiles,
                                   Set<AssayGroupFactor> filteredAssayGroupFactors, Set<Factor> orderedFactors,
                                   Model model) {
        if (baselineProfiles.isEmpty()) {
            return;
        }
        Gson gson = new Gson();

        ImmutableList<AssayGroupFactorViewModel> assayGroupFactorViewModels = assayGroupFactorViewModelBuilder.build(filteredAssayGroupFactors);

        String jsonAssayGroupFactors = gson.toJson(assayGroupFactorViewModels);
        model.addAttribute("jsonColumnHeaders", jsonAssayGroupFactors);
        BaselineProfilesViewModel profilesViewModel = baselineProfilesViewModelBuilder.build(baselineProfiles, orderedFactors);

        String jsonProfiles = gson.toJson(profilesViewModel);

        model.addAttribute("jsonProfiles", jsonProfiles);

        if (geneSetProfiles != null) {
            String jsonGeneSetProfiles = gson.toJson(baselineProfilesViewModelBuilder.build(geneSetProfiles, orderedFactors));
            model.addAttribute("jsonGeneSetProfiles", jsonGeneSetProfiles);
        }
    }

    private void addFactorMenu(Model model, BaselineExperiment experiment,BaselineRequestContext requestContext) {

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        //ToDo: this stuff should be refactored, menu should be a separate REST service
        SortedSet<String> menuFactorNames = experimentalFactors.getMenuFilterFactorNames();
        if (!menuFactorNames.isEmpty()) {
            Set<Factor> menuFactors;
            if(!experimentalFactors.getAllFactorsOrderedByXML().isEmpty()) {
                menuFactors = experimentalFactors.getAllFactorsOrderedByXML();
            } else {
                menuFactors = experimentalFactors.getAllFactors();
            }

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

    }

}