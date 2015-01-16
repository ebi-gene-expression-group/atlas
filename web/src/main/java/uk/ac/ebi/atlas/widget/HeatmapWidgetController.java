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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineExperimentProfilesViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModel;
import uk.ac.ebi.atlas.search.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineTissueExperimentSearchResult;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder;

    FilterFactorsConverter filterFactorsConverter = new FilterFactorsConverter();

    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    //TODO: refactor, too many colloborators
    @Inject
    private HeatmapWidgetController(ExperimentTrader experimentTrader,
                                    ApplicationProperties applicationProperties, SpeciesLookupService speciesLookupService,
                                    BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                    BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder,
                                    AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                    BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.speciesLookupService = speciesLookupService;
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.baselineExperimentProfilesViewModelBuilder = baselineExperimentProfilesViewModelBuilder;
        this.assayGroupFactorViewModelBuilder = assayGroupFactorViewModelBuilder;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    // similar to ExperimentDispatcher but for the widget, ie: loads baseline experiment into model and request
    // /widgets/heatmap/protein is deprecated
    //ToDo: (OMannion) remove /widgets/heatmap/protein (still being used by BioJS demo page)
    // in favour of /widgets/heatmap/referenceExperiment
    @RequestMapping(value = {"/widgets/heatmap/protein", "/widgets/heatmap/referenceExperiment"})
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

        String experimentAccession = applicationProperties.getBaselineReferenceExperimentAccession(species);

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

    // used for testing only
    @RequestMapping(value = "/widgets/heatmap/bioentity")
    public String heatmapWidgetPage(
                                 @RequestParam(value = "geneQuery", required = true) String geneQuery,
                                 @RequestParam(value = "species", required = false) String species,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 Model model) {

        fetchMultiExperimentResultsAndPopulateModel(geneQuery, species, propertyType, model);

        //TODO: replace this with a version that uses the Biojs widget
        return "heatmap-widget-react";
    }

    @RequestMapping(value = "/widgets/heatmap/multiExperiment")
    public String multiExperimentJson(
            @RequestParam(value = "geneQuery", required = true) String geneQuery,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "propertyType", required = false) String propertyType,
            Model model, HttpServletResponse response) {


        fetchMultiExperimentResultsAndPopulateModel(geneQuery, species, propertyType, model);

        // set here instead of in JSP, because the JSP may be included elsewhere
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

    @RequestMapping(value = "/widgets/heatmap/baselineAnalytics")
    public String analyticsJson(
            @RequestParam(value = "geneQuery", required = true) String geneQuery,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "propertyType", required = false) String propertyType,

            Model model, HttpServletResponse response) {

        String ensemblSpecies = StringUtils.isBlank(species) ?
                speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery) : Species.convertToEnsemblSpecies(species);

        BaselineTissueExperimentSearchResult searchResult = baselineAnalyticsSearchService.findExpressionsForTissueExperiments(geneQuery, ensemblSpecies);

        populateModelWithMultiExperimentResults(geneQuery, ensemblSpecies, searchResult, model);

        // set here instead of in JSP, because the JSP may be included elsewhere
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

    private void fetchMultiExperimentResultsAndPopulateModel(String geneQuery, String species, String propertyType, Model model) {
        String ensemblSpecies = StringUtils.isBlank(species) ?
                speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery) : Species.convertToEnsemblSpecies(species);

        BaselineTissueExperimentSearchResult searchResult = baselineExperimentProfileSearchService.query(geneQuery, ensemblSpecies, true);

        populateModelWithMultiExperimentResults(geneQuery, ensemblSpecies, searchResult, model);
    }

    private void populateModelWithMultiExperimentResults(String geneQuery, String ensemblSpecies, BaselineTissueExperimentSearchResult searchResult, Model model) {
        SortedSet<Factor> orderedFactors = searchResult.getTissueFactorsAcrossAllExperiments();
        SortedSet<AssayGroupFactor> filteredAssayGroupFactors = convert(orderedFactors);

        ImmutableSet<String> allSvgPathIds = extractOntologyTerm(filteredAssayGroupFactors);
        addAnatomogram(allSvgPathIds, model, ensemblSpecies);

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        addJsonForHeatMap(experimentProfiles, filteredAssayGroupFactors, orderedFactors, model);

        model.addAttribute("species", ensemblSpecies);
        model.addAttribute("isWidget", true);
        model.addAttribute("isMultiExperiment", true);
        model.addAttribute("geneQuery", geneQuery);
    }

    //TODO: remove duplication with BaselineExperimentPageController
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

        ImmutableList<AssayGroupFactorViewModel> assayGroupFactorViewModels = assayGroupFactorViewModelBuilder.build(filteredAssayGroupFactors);
        String jsonAssayGroupFactors = gson.toJson(assayGroupFactorViewModels);
        model.addAttribute("jsonColumnHeaders", jsonAssayGroupFactors);

        BaselineProfilesViewModel profilesViewModel = baselineExperimentProfilesViewModelBuilder.build(baselineProfiles, orderedFactors);

        String jsonProfiles = gson.toJson(profilesViewModel);
        model.addAttribute("jsonProfiles", jsonProfiles);
    }

    //TODO: is this needed anymore, now that we don't have transcripts
    private void prepareModelForTranscripts(Model model, String species, Experiment experiment) {
        ExperimentalFactors experimentalFactors = ((BaselineExperiment) experiment).getExperimentalFactors();

        model.addAttribute("queryFactorType", experimentalFactors.getDefaultQueryFactorType());

        addFilterFactors(species, experiment, model);
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

    private void addFilterFactors(String species, Experiment experiment, Model model) {
        String mappedSpecies = experiment.getRequestSpeciesName(species);
        Set<Factor> factors = new HashSet<>();

        if(StringUtils.isNotEmpty(mappedSpecies)){
            Factor factor = new Factor("ORGANISM", mappedSpecies);
            factors.add(factor);
        }

        model.addAttribute("serializedFilterFactors", filterFactorsConverter.serialize(factors));
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView widgetSpecific404(Exception e) {
        ModelAndView mav = new ModelAndView("widget-error");
        mav.addObject("errorMessage", e.getMessage());

        return mav;
    }

}
