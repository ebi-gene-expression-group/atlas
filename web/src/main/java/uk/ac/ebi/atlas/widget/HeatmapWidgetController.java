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
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.model.AnatomogramType;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.*;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchDao;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
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
import java.sql.SQLRecoverableException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public final class HeatmapWidgetController extends HeatmapWidgetErrorHandler {

    public static final String ORIGINAL_GENEQUERY = "geneQuery";

    private ApplicationProperties applicationProperties;

    private SpeciesLookupService speciesLookupService;

    private final BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;

    private final BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder;

    private AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder;

    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    private final BaselineExperimentPageService baselineExperimentPageService;

    @Inject
    private HeatmapWidgetController(ApplicationProperties applicationProperties, SpeciesLookupService speciesLookupService,
                                    BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                    BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder,
                                    AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                    BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                    BaselineExperimentPageService baselineExperimentPageService) {
        this.applicationProperties = applicationProperties;
        this.speciesLookupService = speciesLookupService;
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.baselineExperimentProfilesViewModelBuilder = baselineExperimentProfilesViewModelBuilder;
        this.assayGroupFactorViewModelBuilder = assayGroupFactorViewModelBuilder;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.baselineExperimentPageService = baselineExperimentPageService;
    }



    @RequestMapping(value = "/widgets/heatmap/referenceExperiment", params = "type=RNASEQ_MRNA_BASELINE")
    public String fetchReferenceExperimentProfilesJson(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                                       @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks, BindingResult result, Model model, HttpServletRequest request,
                                                       HttpServletResponse response) {

        BaselineProfilesList baselineProfiles = baselineExperimentPageService
                .prepareModelAndPossiblyAddFactorMenuAndMaybeRUrlAndWidgetThings(preferences, result, model,
                        request, false , false, true, disableGeneLinks);

        if (result.hasErrors()) {
            throw new ResourceNotFoundException(result.getGlobalError().getDefaultMessage());
        }else if (baselineProfiles.isEmpty()) {
            throw new ResourceNotFoundException("No baseline expression found for " + preferences.getGeneQuery()
                    .description());
        }

        // set here instead of in JSP, because the JSP may be included elsewhere
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

    @RequestMapping(value = "/widgets/heatmap/multiExperiment")
    public String multiExperimentJson(
            @RequestParam(value = "geneQuery", required = true) GeneQuery geneQuery,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "propertyType", required = false) String propertyType,
            Model model, HttpServletResponse response) {

        String ensemblSpecies= StringUtils.isBlank(species)
                ?speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery.asString())
                : Species.convertToEnsemblSpecies(species);


        BaselineExperimentSearchResult searchResult = baselineExperimentProfileSearchService.query(geneQuery.asString(), ensemblSpecies, true);

        if (searchResult.isEmpty()) {
            throw new ResourceNotFoundException("No baseline expression in tissues found for "
                    + geneQuery.description());
        }

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
                speciesLookupService.fetchFirstSpeciesByField(propertyType, geneQuery.asString())
                : Species.convertToEnsemblSpecies(species);

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

        if (searchResult.containsFactorOfType("ORGANISM_PART")) {
            model.addAllAttributes(applicationProperties.getAnatomogramProperties(ensemblSpecies,filteredAssayGroupFactors));
        }

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        addJsonForHeatMap(experimentProfiles, filteredAssayGroupFactors, orderedFactors, model);

        model.addAttribute("species", ensemblSpecies);
        model.addAttribute("isWidget", true);
        model.addAttribute("isMultiExperiment", true);
        model.addAttribute("geneQuery", geneQuery);
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

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



        Set<Factor> nonExpressedFactors = Sets.newHashSet(orderedFactors);
        for (BaselineExperimentProfile baselineProfile : baselineProfiles) {
            double defaultCutoff =
                    baselineProfile.getExperimentType().equalsIgnoreCase(ExperimentType.PROTEOMICS_BASELINE.toString()) ?
                            BaselineAnalyticsSearchDao.DEFAULT_PROTEOMICS_CUT_OFF : BaselineAnalyticsSearchDao.DEFAULT_BASELINE_CUT_OFF;

            for (Factor factor : Sets.newHashSet(nonExpressedFactors)) {
                BaselineExpression expression = baselineProfile.getExpression(factor);
                if (expression != null && expression.isKnown() && expression.getLevel() >= defaultCutoff) {
                    nonExpressedFactors.remove(factor);
                }
            }
        }
        Set<String> nonExpressedColumnHeaders = Sets.newHashSetWithExpectedSize(nonExpressedFactors.size());
        for (Factor nonExpressedFactor : nonExpressedFactors) {
            nonExpressedColumnHeaders.add(nonExpressedFactor.getValue());
        }

        model.addAttribute("jsonNonExpressedColumnHeaders", gson.toJson(nonExpressedColumnHeaders));
    }



}
