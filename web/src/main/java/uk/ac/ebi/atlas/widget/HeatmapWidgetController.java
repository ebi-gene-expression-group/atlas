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
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.model.AnatomogramType;
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
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.Type;
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
    public static final String ORIGINAL_GENEQUERY = "geneQuery";

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

    @RequestMapping(value = {"/widgets/heatmap/referenceExperiment"})
    public String dispatchWidget(HttpServletRequest request,
                                 @RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 @RequestParam(value = "species", required = false) String species,
                                 @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks,
                                 @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                 Model model, HttpServletResponse response) {

        try {
            if (StringUtils.isBlank(species)) {
                species = speciesLookupService.fetchFirstSpeciesByField(propertyType, bioEntityAccession);
            }
        } catch (ResourceNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("errorMessage", "No genes found matching query: " + bioEntityAccession);
            return "widget-error";
        }

        String experimentAccession = applicationProperties.getBaselineReferenceExperimentAccession(species);

        if (StringUtils.isEmpty(experimentAccession)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("errorMessage", "No baseline experiment for species " + species);
            model.addAttribute("identifier", bioEntityAccession);
            return "widget-error";
        }

        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

        prepareModel(request, model, experiment);

        prepareModelForTranscripts(model, species, experiment);

        //TODO: hacky, fix this, see RnaSeqBaselineExperimentPageController
        request.setAttribute(ORIGINAL_GENEQUERY, bioEntityAccession);

        // forward to /widgets/heatmap/referenceExperiment?type=RNASEQ_MRNA_BASELINE in BaselineExperimentPageController
        // eg: forward:/widgets/heatmap/referenceExperiment?type=RNASEQ_MRNA_BASELINE&serializedFilterFactors=ORGANISM:Monodelphis domestica&disableGeneLinks=true
        // existing request parameters to this method (ie: geneQuery, propertyType, rootContext) are also passed along by the forward,
        // plus type and serializedFilterFactors
        // the model attributes are also preserved by a forward
        return "forward:" + getRequestURL(request) + buildQueryString(species, experiment, disableGeneLinks);
    }

    @RequestMapping(value = "/widgets/heatmap/multiExperiment")
    public String multiExperimentJson(
            @RequestParam(value = "geneQuery", required = true) GeneQuery geneQuery,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "propertyType", required = false) String propertyType,
            Model model, HttpServletResponse response) {

        String ensemblSpecies;
        try {
            ensemblSpecies = StringUtils.isBlank(species) ?
                speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery.asString()) : Species.convertToEnsemblSpecies(species);

        } catch (ResourceNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("errorMessage", "No genes found matching query: " + geneQuery.description());
            return "widget-error";
        }

        BaselineExperimentSearchResult searchResult = baselineExperimentProfileSearchService.query(geneQuery.asString(), ensemblSpecies, true);

        if (searchResult.isEmpty()) {
            model.addAttribute("errorMessage", "No baseline expression in tissues found for " + geneQuery.description());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "widget-error";
        }

        model.addAttribute("isSingleGene", true);

        populateModelWithMultiExperimentResults(geneQuery, ensemblSpecies, searchResult, model);

        // set here instead of in JSP, because the JSP may be included elsewhere
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

    @RequestMapping(value = "/widgets/heatmap/baselineAnalytics")
    public String analyticsJson(
            @RequestParam(value = "geneQuery", required = true) GeneQuery geneQuery,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "propertyType", required = false) String propertyType,
            @RequestParam(value = "source", required = false) String source,
            Model model, HttpServletResponse response) {

        String ensemblSpecies = StringUtils.isBlank(species) ?
                speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery.asString()) : Species.convertToEnsemblSpecies(species);

        String defaultFactorQueryType = StringUtils.isBlank(source) ? "ORGANISM_PART" : source;
        BaselineExperimentSearchResult searchResult = baselineAnalyticsSearchService.findExpressions(geneQuery, ensemblSpecies, defaultFactorQueryType);

        populateModelWithMultiExperimentResults(geneQuery, ensemblSpecies, searchResult, model);

        // set here instead of in JSP, because the JSP may be included elsewhere
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

    private void populateModelWithMultiExperimentResults(GeneQuery geneQuery, String ensemblSpecies, BaselineExperimentSearchResult searchResult, Model model) {
        SortedSet<Factor> orderedFactors = searchResult.getFactorsAcrossAllExperiments();
        SortedSet<AssayGroupFactor> filteredAssayGroupFactors = convert(orderedFactors);

        ImmutableSet<String> allSvgPathIds = extractOntologyTerm(filteredAssayGroupFactors);
        addAnatomogram(allSvgPathIds, model, ensemblSpecies);
        setToggleImageButton(model, ensemblSpecies);

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
        String maleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, AnatomogramType.MALE);
        model.addAttribute("maleAnatomogramFile", maleAnatomogramFileName);

        String femaleAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, AnatomogramType.FEMALE);
        model.addAttribute("femaleAnatomogramFile", femaleAnatomogramFileName);

        String brainAnatomogramFileName = applicationProperties.getAnatomogramFileName(species, AnatomogramType.BRAIN);
        model.addAttribute("brainAnatomogramFile", brainAnatomogramFileName);

        model.addAttribute("hasAnatomogram", maleAnatomogramFileName != null || femaleAnatomogramFileName != null || brainAnatomogramFileName != null);

        String jsonAllSvgPathIds = new Gson().toJson(allSvgPathIds);
        model.addAttribute("allSvgPathIds", jsonAllSvgPathIds);
    }

    private void setToggleImageButton(Model model, String species) {
        if(species.equals("oryza sativa") || species.equals("oryza sativa japonica group")){
            model.addAttribute("toggleButtonMaleImageTemplate", "/resources/images/whole_plant");
            model.addAttribute("toggleButtonFemaleImageTemplate", "/resources/images/flower_parts");
        }
        else {
            model.addAttribute("toggleButtonMaleImageTemplate", "/resources/images/male");
            model.addAttribute("toggleButtonFemaleImageTemplate", "/resources/images/female");
            model.addAttribute("toggleButtonBrainImageTemplate", "/resources/images/brain");
        }
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

        JsonObject jsonObject = new JsonObject();
        Type type = new TypeToken<ImmutableList<AssayGroupFactorViewModel>>() {}.getType();
        JsonElement jsonElement = gson.toJsonTree(assayGroupFactorViewModels, type);
        jsonObject.add("primary", jsonElement);

        model.addAttribute("jsonMultipleColumnHeaders", jsonObject);

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
