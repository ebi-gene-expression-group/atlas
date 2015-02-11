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
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWrapperAsGeneSets;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesEnsemblTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public abstract class BaselineExperimentPageController extends BaselineExperimentController {

    private final TracksUtil tracksUtil;

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final ApplicationProperties applicationProperties;

    private final FilterFactorMenuBuilder filterFactorMenuBuilder;

    private BaselineRequestContext requestContext;

    private BaselineExperiment experiment;

    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;

    private final SpeciesEnsemblTrader speciesEnsemblTrader;

    private final AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder;

    public BaselineExperimentPageController(BaselineProfilesHeatMap baselineProfilesHeatMap,
                                            ApplicationProperties applicationProperties,
                                            BaselineRequestContextBuilder requestContextBuilder,
                                            FilterFactorsConverter filterFactorsConverter,
                                            FilterFactorMenuBuilder filterFactorMenuBuilder,
                                            BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                            AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                            SpeciesEnsemblTrader speciesEnsemblTrader,
                                            TracksUtil tracksUtil) {

        super(requestContextBuilder, filterFactorsConverter);
        this.applicationProperties = applicationProperties;
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
        this.filterFactorMenuBuilder = filterFactorMenuBuilder;
        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.assayGroupFactorViewModelBuilder = assayGroupFactorViewModelBuilder;
        this.speciesEnsemblTrader = speciesEnsemblTrader;
        this.tracksUtil = tracksUtil;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    private BaselineProfilesList fetchGeneProfilesAsGeneSets() {
        BaselineProfileStreamOptionsWrapperAsGeneSets options = new BaselineProfileStreamOptionsWrapperAsGeneSets(requestContext);
        return baselineProfilesHeatMap.fetch(options);
    }

    BaselineProfilesList prepareModel(BaselineRequestPreferences preferences, BindingResult result, Model model, HttpServletRequest request) {

        initializeContext(preferences, request);

        model.addAttribute("experimentAccession", experiment.getAccession());

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        model.addAttribute("queryFactorName", experimentalFactors.getFactorDisplayName(preferences.getQueryFactorType()));

        model.addAttribute("serializedFilterFactors", preferences.getSerializedFilterFactors());

        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();

        SortedSet<AssayGroupFactor> filteredAssayGroupFactors = experimentalFactors.getComplementAssayGroupFactors(selectedFilterFactors);

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        // It is in order.
        model.addAttribute("allQueryFactors", filteredAssayGroupFactors);

        String species = requestContext.getFilteredBySpecies();

        // required to show link to a data provider on baseline page (if it was provided in <expAcc>-factors.xml file)
        model.addAttribute("dataProviderURL", experiment.getDataProviderURL());
        model.addAttribute("dataProviderDescription", experiment.getDataProviderDescription());

        //required by autocomplete and heatmap
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

                //TODO: remove model attributes for profiles when widget is converted over to React and /widgets/heatmap/protein is removed
                BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(requestContext);
                model.addAttribute("geneProfiles", baselineProfiles);

                BaselineProfilesList profilesAsGeneSets = requestContext.geneQueryResponseContainsGeneSets() ? fetchGeneProfilesAsGeneSets() : null;
                if (profilesAsGeneSets != null) {
                    model.addAttribute("profilesAsGeneSets", profilesAsGeneSets);
                }

                addJsonForHeatMap(baselineProfiles, profilesAsGeneSets, filteredAssayGroupFactors, experimentalFactors.getComplementFactors(selectedFilterFactors), model);

                if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
                    ImmutableSet<String> allSvgPathIds = extractOntologyTerm(filteredAssayGroupFactors);
                    addAnatomogram(allSvgPathIds, model, species);

                } else {
                    model.addAttribute("hasAnatomogram", false);
                }

                return baselineProfiles;

            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }

        return null;
    }

    private ImmutableSet<String> extractOntologyTerm(SortedSet<AssayGroupFactor> filteredAssayGroupFactors) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (AssayGroupFactor assayGroupFactor : filteredAssayGroupFactors) {
            String valueOntologyTermId = assayGroupFactor.getValueOntologyTermId();
            if (valueOntologyTermId != null) {
                builder.add(valueOntologyTermId);
            }
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


    private void addJsonForHeatMap(BaselineProfilesList baselineProfiles, BaselineProfilesList geneSetProfiles, SortedSet<AssayGroupFactor> filteredAssayGroupFactors, SortedSet<Factor> orderedFactors, Model model) {
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

    private void initializeContext(BaselineRequestPreferences preferences, HttpServletRequest request) {
        experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        setPreferenceDefaults(preferences, experiment);

        requestContext = buildRequestContext(experiment, preferences);
    }

    void addFactorMenu(Model model) {

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

    }

}