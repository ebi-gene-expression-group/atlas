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

            RankingParameters parameters = new RankingParameters();
            parameters.setGeneQuery(preferences.getGeneQuery())
                    .setQueryFactorType(preferences.getQueryFactorType())
                    .setQueryFactorValues(preferences.getQueryFactorValues())
                    .setFilterFactorValues(preferences.getFilterFactorValues())
                    .setCutoff(preferences.getCutoff());
            parameters.setSpecific(preferences.isSpecific());
            parameters.setHeatmapMatrixSize(preferences.getHeatmapMatrixSize());

            rankCommand.setParameters(parameters);

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());

            model.addAttribute("requestURI", request.getRequestURI());

            model.addAttribute("experimentAccession", experimentAccession);

            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            // this formats the default factor type for display on web page
            String queryFactorType = parameters.getQueryFactorType();
            if (queryFactorType == null || queryFactorType.trim().length() == 0)
                queryFactorType = experiment.getDefaultFactorType();
            queryFactorType = queryFactorType.replaceAll("_", " ").toLowerCase();
            queryFactorType = queryFactorType.substring(0, 1).toUpperCase() + queryFactorType.substring(1);
            model.addAttribute("formattedQueryFactorType", queryFactorType);

            model.addAttribute("allFactorValues", experiment.getFactorValues(parameters.getQueryFactorType()));

            Set<FactorValue> filterByFactorValues = parameters.getFilterFactorValues();
            model.addAttribute("heatmapFactorValues", experiment.getFilteredFactorValues(filterByFactorValues,
                    parameters.getQueryFactorType()));

            String specie = experiment.getSpecie();

            model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, true));

            model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, false));

            model.addAttribute("downloadUrl", buildDownloadURL(request));

            String defaultFactorType = experiment.getDefaultFactorType();

            SortedMap<String, SortedSet<FactorValue>> allFactorTypes = new TreeMap<>();

            SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations = experiment.getValidFactorValueCombinations();
            for (FactorValue key : validFactorValueCombinations.keySet()) {
                if (!allFactorTypes.containsKey(key.getType()))
                    allFactorTypes.put(key.getName(), new TreeSet<FactorValue>());
                allFactorTypes.get(key.getName()).add(key);
            }

            // build filter by menu map here
            SortedMap<String, SortedMap<String, SortedMap<String, SortedSet<String>>>> filterByMenu = new TreeMap<>();
            for (String firstFactorType : allFactorTypes.keySet()) {
                // first level: factor type
                if (!filterByMenu.containsKey(firstFactorType))
                    filterByMenu.put(firstFactorType, new TreeMap<String, SortedMap<String, SortedSet<String>>>());
                // second level: factor value choices per factor type, all are valid
                for (FactorValue firstFactorValue : allFactorTypes.get(firstFactorType)) {
                    SortedMap<String, SortedSet<String>> secondFilterFactorValue = new TreeMap<>();
                    filterByMenu.get(firstFactorType).put(firstFactorValue.getValue(), secondFilterFactorValue);

                    // index by remaining factor types
                    SortedMap<String, SortedSet<FactorValue>> secondFactorTypes = new TreeMap<>();
                    for (FactorValue secondFactorValue : validFactorValueCombinations.get(firstFactorValue)) {
                        if (!firstFactorType.equals(secondFactorValue.getName())) {
                            if (!secondFactorTypes.containsKey(secondFactorValue.getName()))
                                secondFactorTypes.put(secondFactorValue.getName(), new TreeSet<FactorValue>());
                            secondFactorTypes.get(secondFactorValue.getName()).add(secondFactorValue);
                        }
                    }

                    for (String secondFactorType : secondFactorTypes.keySet()) {
                        // third level: factor type
                        if (!secondFilterFactorValue.containsKey(secondFactorType))
                            secondFilterFactorValue.put(secondFactorType, new TreeSet<String>());
                        // forth level: factor value choices for remaining factor type
                        for (FactorValue secondFactorValue : secondFactorTypes.get(secondFactorType)) {
                            secondFilterFactorValue.get(secondFactorType).add(secondFactorValue.getValue());
                        }
                    }
                }
            }

            model.addAttribute("filterByMenu", filterByMenu);

        }

        return "experiment";
    }

    String buildDownloadURL(HttpServletRequest request) {
        return request.getRequestURI() + TSV_FILE_EXTENSION + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }


}
















