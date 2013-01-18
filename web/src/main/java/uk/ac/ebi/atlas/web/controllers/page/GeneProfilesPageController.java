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
import uk.ac.ebi.atlas.model.RankingParameters;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@Scope("request")
public class GeneProfilesPageController {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private RankGeneProfilesCommand rankCommand;

    private ApplicationProperties applicationProperties;

    private ExperimentsCache experimentsCache;

    @Inject
    public GeneProfilesPageController(RankGeneProfilesCommand rankCommand, ApplicationProperties applicationProperties,
                                      ExperimentsCache experimentsCache) {
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}")
    public String showGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        if (!result.hasErrors()) {

            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            RankingParameters parameters = new RankingParameters();
            parameters.setGeneQuery(preferences.getGeneQuery())
                    .setQueryFactorValues(preferences.getQueryFactorValues())
                    .setCutoff(preferences.getCutoff());
            parameters.setSpecific(preferences.isSpecific());
            parameters.setHeatmapMatrixSize(preferences.getHeatmapMatrixSize());

            // check for query factor type present, otherwise use default
            if (preferences.getQueryFactorType() == null ||
                    preferences.getQueryFactorType().trim().length() == 0) {
                parameters.setQueryFactorType(experiment.getDefaultQueryFactorType());
            } else {
                parameters.setQueryFactorType(preferences.getQueryFactorType());
            }

            // check for filter factor values present, otherwise use default
            if (preferences.getFilterFactorValues() == null ||
                    preferences.getFilterFactorValues().size() == 0) {
                parameters.setFilterFactorValuesObjects(experiment.getDefaultFilterFactorValues());
            } else {
                parameters.setFilterFactorValues(preferences.getFilterFactorValues());
            }

            rankCommand.setParameters(parameters);

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());

            model.addAttribute("requestURI", request.getRequestURI());

            model.addAttribute("experimentAccession", experimentAccession);

            model.addAttribute("formattedQueryFactorType", formatQueryFactorType(parameters.getQueryFactorType()));

            model.addAttribute("allFactorValues", experiment.getFactorValues(parameters.getQueryFactorType()));

            Set<FactorValue> filterByFactorValues = parameters.getFilterFactorValues();
            model.addAttribute("heatmapFactorValues", experiment.getFilteredFactorValues(filterByFactorValues,
                    parameters.getQueryFactorType()));

            String specie = experiment.getSpecie();

            model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, true));

            model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, false));

            model.addAttribute("downloadUrl", buildDownloadURL(request));

            // using factor names here for better readability and compatibility with experiment design page
            SortedMap<String, SortedSet<FactorValue>> allFactorNames = new TreeMap<>();

            SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations = experiment.getValidFactorValueCombinations();
            for (FactorValue key : validFactorValueCombinations.keySet()) {
                if (!allFactorNames.containsKey(key.getName()))
                    allFactorNames.put(key.getName(), new TreeSet<FactorValue>());
                allFactorNames.get(key.getName()).add(key);
            }

            model.addAttribute("factorTypesCount", allFactorNames.size());

            // build filter by menu map here, structure:
            // factor name 1 > factor value 1 > factor name 2 > factor value 2 > link
            SortedMap<String, SortedMap<String, SortedMap<String, SortedMap<String, String>>>> filterByMenu = new TreeMap<>();
            for (String firstFactorName : allFactorNames.keySet()) {
                // first level: factor name
                if (!filterByMenu.containsKey(firstFactorName))
                    filterByMenu.put(firstFactorName, new TreeMap<String, SortedMap<String, SortedMap<String, String>>>());
                // second level: factor value choices per factor name, all are valid
                for (FactorValue firstFactorValue : allFactorNames.get(firstFactorName)) {

                    // factor name 2 > factor value 2 > link
                    SortedMap<String, SortedMap<String, String>> secondFilterFactorValue = new TreeMap<>();
                    filterByMenu.get(firstFactorName).put(firstFactorValue.getValue(), secondFilterFactorValue);

                    // index second level factor names
                    SortedMap<String, SortedSet<FactorValue>> secondFactorNames = new TreeMap<>();
                    for (FactorValue secondFactorValue : validFactorValueCombinations.get(firstFactorValue)) {
                        if (!firstFactorName.equals(secondFactorValue.getName())) {
                            if (!secondFactorNames.containsKey(secondFactorValue.getName()))
                                secondFactorNames.put(secondFactorValue.getName(), new TreeSet<FactorValue>());
                            secondFactorNames.get(secondFactorValue.getName()).add(secondFactorValue);
                        }
                    }

                    for (String secondFactorName : secondFactorNames.keySet()) {
                        // third level: factor name
                        if (!secondFilterFactorValue.containsKey(secondFactorName))
                            secondFilterFactorValue.put(secondFactorName, new TreeMap<String, String>());

                        // get remainder of factor names
                        SortedSet<String> remainingFactorNames = new TreeSet<>(allFactorNames.keySet());
                        remainingFactorNames.remove(firstFactorName);
                        remainingFactorNames.remove(secondFactorName);
                        // TODO: what in case there are more than 3 possible factor types?

                        // forth level: factor value choices for second factor name
                        for (FactorValue secondFactorValue : secondFactorNames.get(secondFactorName)) {
                            // arbitrarily taking first of remaining factor names as query factor type
                            String factorType = allFactorNames.get(remainingFactorNames.first()).first().getType();
                            String link = buildFilterFactorValueURL(request, factorType, firstFactorValue, secondFactorValue);
                            secondFilterFactorValue.get(secondFactorName).put(secondFactorValue.getValue(), link);
                        }
                    }
                }
            }

            model.addAttribute("filterByMenu", filterByMenu);

            // construct label above filter by menu
            SortedMap<String, String> labels = new TreeMap<>();
            for (String name : allFactorNames.keySet()) {
                for (FactorValue factorValue : allFactorNames.get(name)) {
                    // this is necessary because what comes back from RequestPreferences are not "complete" FactorValues
                    // they are missing the factor name
                    if (parameters.getFilterFactorValues().contains(factorValue)) {
                        labels.put(name, factorValue.getValue());
                    }
                }
            }

            // can be done better
            model.addAttribute("filterByLabel", labels);
        }

        return "experiment";
    }

    String formatQueryFactorType(String queryFactorType) {
        // this formats the default factor type for display on web page
        queryFactorType = queryFactorType.replaceAll("_", " ").toLowerCase();
        queryFactorType = queryFactorType.substring(0, 1).toUpperCase() + queryFactorType.substring(1);
        return queryFactorType;
    }

    String buildFilterFactorValueURL(HttpServletRequest request, String queryFactorType, FactorValue firstFactorValue, FactorValue secondFactorValue) {
        // we disregard here previous query string, as site will load completely fresh
        // TODO: we might want to include some previous query
        return request.getRequestURI() + "?" + "queryFactorType=" + queryFactorType
                + "&filterFactorValues=" + firstFactorValue.getType() + ":" + firstFactorValue.getValue()
                + "&filterFactorValues=" + secondFactorValue.getType() + ":" + secondFactorValue.getValue();
    }

    String buildDownloadURL(HttpServletRequest request) {
        return request.getRequestURI() + TSV_FILE_EXTENSION + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }


}
















