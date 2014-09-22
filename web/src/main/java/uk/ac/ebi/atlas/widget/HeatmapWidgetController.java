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

package uk.ac.ebi.atlas.widget;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineExperimentProfilesViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModel;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineTissueExperimentSearchResult;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public final class HeatmapWidgetController {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private static final String ALL_SPECIES_ATTRIBUTE = "allSpecies";
    private static final String PUBMED_IDS_ATTRIBUTE = "pubMedIds";
    private static final String EXPERIMENT_DESCRIPTION_ATTRIBUTE = "experimentDescription";
    private static final String HAS_EXTRA_INFO_ATTRIBUTE = "hasExtraInfo";
    private static final String EXPERIMENT_TYPE_ATTRIBUTE = "type";

    private ApplicationProperties applicationProperties;

    private SpeciesLookupService speciesLookupService;

    private ExperimentTrader experimentTrader;

    private final BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;

    private final BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder;

    @Inject
    private HeatmapWidgetController(ExperimentTrader experimentTrader,
                                    ApplicationProperties applicationProperties, SpeciesLookupService speciesLookupService, BaselineExperimentProfileSearchService baselineExperimentProfileSearchService, BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder) {
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.speciesLookupService = speciesLookupService;
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.baselineExperimentProfilesViewModelBuilder = baselineExperimentProfilesViewModelBuilder;
    }

    // similar to ExperimentDispatcher but for the widget, ie: loads baseline experiment into model and request
    //ToDo: (OMannion) deprecate protein in favour of bioentity
    @RequestMapping(value = "/widgets/heatmap/protein")
    public String dispatchWidget(HttpServletRequest request,
                                 @RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 @RequestParam(value = "species", required = false) String species,
                                 @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks,
                                 @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                 Model model) {

        try {
            if (StringUtils.isBlank(species)) {
                species = speciesLookupService.fetchFirstSpeciesByField(propertyType, bioEntityAccession);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "No genes found matching query: " + bioEntityAccession);
            return "widget-error";
        }

        String experimentAccession = applicationProperties.getBaselineWidgetExperimentAccessionBySpecies(species);

        if (StringUtils.isEmpty(experimentAccession)) {
            model.addAttribute("errorMessage", "No baseline experiment for species " + species);
            model.addAttribute("identifier", bioEntityAccession);
            return "widget-error";
        }

        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

        prepareModel(request, model, experiment);

        prepareModelForTranscripts(model, species, experiment);

        // forward to /widgets/heatmap/protein?type=RNASEQ_MRNA_BASELINE in BaselineExperimentPageController
        // eg: forward:/widgets/heatmap/protein?type=RNASEQ_MRNA_BASELINE&serializedFilterFactors=ORGANISM:Monodelphis domestica&disableGeneLinks=true
        // existing request parameters to this method (ie: geneQuery, propertyType, rootContext) are also passed along by the forward,
        // plus type and serializedFilterFactors
        // the model attributes are also preserved by a forward
        return "forward:" + getRequestURL(request) + buildQueryString(species, experiment, disableGeneLinks);
    }

    //TODO: remove rootContext with BioJS no longer uses HTML insert
    @RequestMapping(value = "/widgets/heatmap/bioentity")
    public String dispatchWidgetBioentity(
                                 @RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 @RequestParam(value = "species", required = false) String species,
                                 @RequestParam(value = "rootContext", required = false) String rootContext,
                                 Model model) {

        String solrSpecies;

        try {
            if (StringUtils.isBlank(species)) {
                solrSpecies = speciesLookupService.fetchFirstSpeciesByField(propertyType, bioEntityAccession);
            } else {
                solrSpecies = species.toLowerCase();
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "No genes found matching query: " + bioEntityAccession);
            return "widget-error";
        }

        //required by heatmap
        model.addAttribute("species", solrSpecies);

        model.addAttribute("isWidget", true);
        model.addAttribute("isMultiExperiment", true);
        model.addAttribute("geneQuery", bioEntityAccession);

        BaselineTissueExperimentSearchResult searchResult;

        try {
            searchResult = baselineExperimentProfileSearchService.query(bioEntityAccession, solrSpecies, true);
        } catch (GenesNotFoundException e) {
            model.addAttribute("errorMessage", "No genes found matching query: '" + bioEntityAccession + "'");
            model.addAttribute("identifier", bioEntityAccession);
            return "widget-error";
        }

        SortedSet<Factor> orderedFactors = searchResult.getTissueFactorsAcrossAllExperiments();
        SortedSet<AssayGroupFactor> filteredAssayGroupFactors = convert(orderedFactors);

        ImmutableSet<String> allSvgPathIds = extractOntologyTerm(filteredAssayGroupFactors);
        addAnatomogram(allSvgPathIds, model, solrSpecies);

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        addJsonForHeatMap(experimentProfiles, filteredAssayGroupFactors, orderedFactors, model);

        return "heatmap-widget-react";
    }

    //TODO: remove duplication with BaselineExperimentPageController
    private ImmutableSet<String> extractOntologyTerm(SortedSet<AssayGroupFactor> filteredAssayGroupFactors) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (AssayGroupFactor assayGroupFactor : filteredAssayGroupFactors) {
            builder.add(assayGroupFactor.getValueOntologyTerm());
        }
        return builder.build();
    }


    private void addAnatomogram(ImmutableSet<String> allSvgPathIds, Model model, String species) {
        //TODO: check if this can be externalized in the view with a cutom EL or tag function
        //or another code block because it's repeated with BaselineExperimentPageController
        String maleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, true);
        model.addAttribute("maleAnatomogramFile", maleAnatomogramFileName);

        String femaleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, false);
        model.addAttribute("femaleAnatomogramFile", femaleAnatomogramFileName);

        model.addAttribute("hasAnatomogram", maleAnatomogramFileName != null || femaleAnatomogramFileName != null);

        String jsonAllSvgPathIds = new Gson().toJson(allSvgPathIds);
        model.addAttribute("allSvgPathIds", jsonAllSvgPathIds);
    }


    private SortedSet<AssayGroupFactor> convert(SortedSet<Factor> orderedFactors) {
        ImmutableSortedSet.Builder<AssayGroupFactor> builder = ImmutableSortedSet.naturalOrder();

        for (Factor factor : orderedFactors) {
            AssayGroupFactor assayGropuFactor = new AssayGroupFactor("none",factor);
            builder.add(assayGropuFactor);
        }

        return builder.build();
    }

    private void addJsonForHeatMap(BaselineExperimentProfilesList baselineProfiles, SortedSet<AssayGroupFactor> filteredAssayGroupFactors, SortedSet<Factor> orderedFactors, Model model) {
        if (baselineProfiles.isEmpty()) {
            return;
        }

        Gson gson = new Gson();

        String jsonAssayGroupFactors = gson.toJson(filteredAssayGroupFactors);
        model.addAttribute("jsonColumnHeaders", jsonAssayGroupFactors);

        BaselineProfilesViewModel profilesViewModel = baselineExperimentProfilesViewModelBuilder.build(baselineProfiles, orderedFactors);

        String jsonProfiles = gson.toJson(profilesViewModel);
        model.addAttribute("jsonProfiles", jsonProfiles);
    }



    private void prepareModelForTranscripts(Model model, String species, Experiment experiment) {
        ExperimentalFactors experimentalFactors = ((BaselineExperiment) experiment).getExperimentalFactors();

        model.addAttribute("queryFactorType", experimentalFactors.getDefaultQueryFactorType());

        loadJSONFilterFactors(species, experiment, model);
    }

    private void prepareModel(HttpServletRequest request, Model model, Experiment experiment) {
        request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);

        Set<String> allSpecies = experiment.getOrganisms();

        model.addAttribute(EXPERIMENT_TYPE_ATTRIBUTE, experiment.getType());

        model.addAttribute(ALL_SPECIES_ATTRIBUTE, StringUtils.join(allSpecies, ", "));

        model.addAttribute(EXPERIMENT_DESCRIPTION_ATTRIBUTE, experiment.getDescription());

        model.addAttribute(HAS_EXTRA_INFO_ATTRIBUTE, experiment.hasExtraInfoFile());

        model.addAttribute(PUBMED_IDS_ATTRIBUTE, experiment.getPubMedIds());
    }

    private String getRequestURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        return StringUtils.substringAfter(requestURI, contextPath);
    }

    private String buildQueryString(String species, Experiment experiment, boolean disableGeneLinks) {
        String mappedSpecies = experiment.getRequestSpeciesName(species);
        String organismParameters = StringUtils.isEmpty(mappedSpecies) ? "" : "&serializedFilterFactors=ORGANISM:" + mappedSpecies;
        return "?type=" + experiment.getType().getParent() + organismParameters + (disableGeneLinks ? "&disableGeneLinks=true" : "");
    }

    private void loadJSONFilterFactors(String species, Experiment experiment, Model model) {
        String mappedSpecies = experiment.getRequestSpeciesName(species);
        Set<Factor> factors = new HashSet<>();

        if(StringUtils.isNotEmpty(mappedSpecies)){
            Factor factor = new Factor("ORGANISM", mappedSpecies);
            factors.add(factor);
        }
        model.addAttribute("selectedFilterFactorsJson", new Gson().toJson(factors));
    }

}
