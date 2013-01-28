/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.FilterParameters;
import uk.ac.ebi.atlas.streams.RankingParameters;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.GeneProfilesController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@Scope("request")
public class GeneProfilesPageController extends GeneProfilesController {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private RankGeneProfilesCommand rankCommand;

    private ApplicationProperties applicationProperties;

    private ExperimentsCache experimentsCache;

    @Inject
    public GeneProfilesPageController(RankGeneProfilesCommand rankCommand, ApplicationProperties applicationProperties,
                                      ExperimentsCache experimentsCache, FilterParameters.Builder filterParameterBuilder) {
        super(filterParameterBuilder, experimentsCache);
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}")
    public String showGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        if (!result.hasErrors()) {

            FilterParameters filterParameters = createFilterParameters(experimentAccession, preferences);

            rankCommand.setFilteredParameters(filterParameters);

            RankingParameters parameters = new RankingParameters(preferences.isSpecific(),
                    preferences.getHeatmapMatrixSize());

            rankCommand.setRankingParameters(parameters);

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());

            model.addAttribute("requestURI", request.getRequestURI());

            model.addAttribute("experimentAccession", experimentAccession);

            model.addAttribute("formattedQueryFactorType", formatQueryFactorType(filterParameters.getQueryFactorType()));

            model.addAttribute("allFactorValues", experiment.getFactorValues(filterParameters.getQueryFactorType()));

            Set<FactorValue> filterByFactorValues = filterParameters.getFilterFactorValues();

            SortedSet<FactorValue> filteredFactorValues = experiment.getFilteredFactorValues(filterByFactorValues, filterParameters.getQueryFactorType());

            model.addAttribute("heatmapFactorValues", filteredFactorValues);

            // this is currently required for the request preferences filter drop-down multi-selection box
            model.addAttribute("heatmapFactorValueValues", FactorValue.getFactorValuesStrings(filteredFactorValues));

            String specie = experiment.getSpecie();

            model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, true));

            model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, false));

            model.addAttribute("downloadUrl", buildDownloadURL(request));

            // all the following is required for filtering by two factor values chosen from drop down menu
            SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations = experiment.getValidFactorValueCombinations();

            SortedMap<String, SortedSet<FactorValue>> allFactorNames = indexFactorValuesByName(validFactorValueCombinations.keySet());

            model.addAttribute("defaultFilterFactorValuesSize", experiment.getDefaultFilterFactorValues().size());

            model.addAttribute("filterByMenu", buildFilterByMenu(allFactorNames, validFactorValueCombinations, request));

            model.addAttribute("selectedFactorValues", extractSelectedFactorValues(allFactorNames, filterParameters));
        }

        return "experiment";
    }

    SortedMap<String, SortedSet<FactorValue>> indexFactorValuesByName(Set<FactorValue> factorValues) {
        // using factor names here for better readability and compatibility with experiment design page
        SortedMap<String, SortedSet<FactorValue>> allFactorNames = new TreeMap<>();
        for (FactorValue key : factorValues) {
            if (!allFactorNames.containsKey(key.getName())) {
                allFactorNames.put(key.getName(), new TreeSet<FactorValue>());
            }
            allFactorNames.get(key.getName()).add(key);
        }
        return allFactorNames;
    }

    SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> buildFilterByMenu(
            SortedMap<String, SortedSet<FactorValue>> allFactorNames,
            SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations,
            HttpServletRequest request) {

        // does the serialisation to JSON
        Gson gson = new Gson();

        // build filter by menu map here, structure:
        // factor name 1 > factor value 1 > factor name 2 > factor value 2 > link
        SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> filterByMenu = new TreeMap<>();
        for (String firstFactorName : allFactorNames.keySet()) {
            // first level: factor name
            if (!filterByMenu.containsKey(firstFactorName)) {
                filterByMenu.put(firstFactorName, new TreeMap<String, SortedMap<String, SortedMap<String, String>>>());
            }
            // second level: factor value choices per factor name, all are valid
            for (FactorValue firstFactorValue : allFactorNames.get(firstFactorName)) {

                // factor name 2 > factor value 2 > link
                SortedMap<String, SortedMap<String, String>> secondFilterFactorValue = new TreeMap<>();
                filterByMenu.get(firstFactorName).put(firstFactorValue.getValue(), secondFilterFactorValue);

                // index second level factor names
                SortedMap<String, SortedSet<FactorValue>> secondFactorNames =
                        indexFactorValuesByName(validFactorValueCombinations.get(firstFactorValue));

                for (String secondFactorName : secondFactorNames.keySet()) {
                    // third level: factor name
                    if (!secondFilterFactorValue.containsKey(secondFactorName)) {
                        secondFilterFactorValue.put(secondFactorName, new TreeMap<String, String>());
                    }

                    // get remainder of factor names
                    SortedSet<String> remainingFactorNames = new TreeSet<>(allFactorNames.keySet());
                    remainingFactorNames.remove(firstFactorName);
                    remainingFactorNames.remove(secondFactorName);
                    // TODO: what in case there are more than 3 possible factor types?

                    // forth level: factor value choices for second factor name
                    for (FactorValue secondFactorValue : secondFactorNames.get(secondFactorName)) {
                        // arbitrarily taking first of remaining factor names as query factor type
                        String factorType = allFactorNames.get(remainingFactorNames.first()).first().getType();
                        String link = gson.toJson(buildFilterFactorValueURL(factorType, firstFactorValue, secondFactorValue));
                        secondFilterFactorValue.get(secondFactorName).put(secondFactorValue.getValue(), link);
                    }
                }
            }
        }

        return filterByMenu;
    }

    SortedSet<FactorValue> extractSelectedFactorValues(SortedMap<String, SortedSet<FactorValue>> allFactorNames, FilterParameters parameters) {
        // construct label above filter by menu
        SortedSet<FactorValue> selectedFactorValues = new TreeSet<>();
        for (String name : allFactorNames.keySet()) {
            for (FactorValue factorValue : allFactorNames.get(name)) {
                // this is necessary because what comes back from RequestPreferences are not "complete" FactorValues
                // they are missing the factor name
                if (parameters.getFilterFactorValues().contains(factorValue)) {
                    selectedFactorValues.add(factorValue);
                }
            }
        }
        return selectedFactorValues;
    }

    String formatQueryFactorType(String queryFactorType) {
        // this formats the default factor type for display on web page
        String result = queryFactorType.replaceAll("_", " ").toLowerCase();
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }

    FilterFactorValues buildFilterFactorValueURL(String queryFactorType, FactorValue firstFactorValue, FactorValue secondFactorValue) {
        return new FilterFactorValues(queryFactorType, firstFactorValue, secondFactorValue);
    }

    String buildDownloadURL(HttpServletRequest request) {
        return request.getRequestURI() + TSV_FILE_EXTENSION + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

    private class FilterFactorValues {

        final String queryFactorType;

        final String filterFactorValuesURL;

        public FilterFactorValues(String queryFT, FactorValue firstFV, FactorValue secondFV) {
            this.queryFactorType = queryFT;
            this.filterFactorValuesURL = FactorValue.composeFactorValueURLRepresentation(firstFV) + "," + FactorValue.composeFactorValueURLRepresentation(secondFV);
        }

        public String getQueryFactorType() {
            return queryFactorType;
        }

        public String getFilterFactorValuesURL() {
            return filterFactorValuesURL;
        }
    }
}
















