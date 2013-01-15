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
import uk.ac.ebi.atlas.model.FilterParameters;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

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

            FilterParameters filterParameters = new FilterParameters(preferences.getGeneQuery(),
                    preferences.getOrganismParts(),
                    preferences.getFilterFactorValues(),
                    preferences.getCutoff());

            rankCommand.setFilterParameters(filterParameters);

            rankCommand.setRequestPreferences(preferences);

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());

            model.addAttribute("requestURI", request.getRequestURI());

            model.addAttribute("experimentAccession", experimentAccession);

            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            // this formats the default factor type for display on web page
            String queryFactorType = preferences.getQueryFactorType();
            if (queryFactorType == null || queryFactorType.trim().length() == 0)
                queryFactorType = experiment.getDefaultFactorType();
            queryFactorType = queryFactorType.replaceAll("_", " ").toLowerCase();
            queryFactorType = queryFactorType.substring(0, 1).toUpperCase() + queryFactorType.substring(1);
            model.addAttribute("formattedQueryFactorType", queryFactorType);

            model.addAttribute("allFactorValues", experiment.getFactorValues(preferences.getQueryFactorType()));

            Set<FactorValue> filterByFactorValues = filterParameters.getFilterFactorValues();
            model.addAttribute("heatmapFactorValues", experiment.getFilteredFactorValues(filterByFactorValues,
                    preferences.getQueryFactorType()));

            String specie = experiment.getSpecie();

            model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, true));

            model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, false));

            model.addAttribute("downloadUrl", buildDownloadURL(request));

        }

        return "experiment";
    }

    String buildDownloadURL(HttpServletRequest request) {
        return request.getRequestURI() + TSV_FILE_EXTENSION + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }


}
















