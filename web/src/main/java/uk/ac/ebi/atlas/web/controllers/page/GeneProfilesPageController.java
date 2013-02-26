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

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.FilterParameters;
import uk.ac.ebi.atlas.commands.GeneNotFoundException;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.RankingParameters;
import uk.ac.ebi.atlas.utils.FilterFactorMenu;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.GeneProfilesController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class GeneProfilesPageController extends GeneProfilesController {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private RankGeneProfilesCommand rankCommand;

    private RankingParameters rankingParameters;

    private ApplicationProperties applicationProperties;

    private ExperimentsCache experimentsCache;

    @Inject
    public GeneProfilesPageController(RankingParameters rankingParameters, RankGeneProfilesCommand rankCommand,
                                      ApplicationProperties applicationProperties,
                                      ExperimentsCache experimentsCache, FilterParameters.Builder filterParameterBuilder,
                                      GeneExpressionPrecondition geneExpressionPrecondition) {
        super(filterParameterBuilder, experimentsCache, geneExpressionPrecondition);
        this.rankingParameters = rankingParameters;
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}")
    public String showGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        FilterParameters filterParameters = createFilterParameters(experimentAccession, preferences);

        model.addAttribute("experimentAccession", experimentAccession);

        model.addAttribute("formattedQueryFactorType", filterParameters.formattedQueryFactorType());

        Set<Factor> selectedFilterFactors = filterParameters.getSelectedFilterFactors();

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
        SortedSet<Factor> allQueryFactors = experimentalFactors.getFilteredFactors(selectedFilterFactors);
        Set<String> menuFilterFactorTypes = experiment.getMenuFilterFactorTypes();

        // this is currently required for the request preferences filter drop-down multi-selection box
        model.addAttribute("allQueryFactors", allQueryFactors);

        // this is currently required for the request preferences filter drop-down multi-selection box
        model.addAttribute("allQueryFactorValues", Factor.getValues(allQueryFactors));

        Set<Factor> allFactors = experimentalFactors.getAllFactors();

        FilterFactorMenu filterFactorMenu = new FilterFactorMenu(experimentalFactors, allFactors);

        model.addAttribute("filterFactorMenu", filterFactorMenu);

        model.addAttribute("selectedFilterFactors", selectedFilterFactors);

        if (!result.hasErrors()) {


            prepareGeneExpressionPrecondition(experimentAccession, preferences, filterParameters);


            rankCommand.setFilteredParameters(filterParameters);

            rankingParameters.setSpecific(preferences.isSpecific());
            rankingParameters.setHeatmapMatrixSize(preferences.getHeatmapMatrixSize());

            try {
                GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

                model.addAttribute("geneProfiles", geneProfiles);


                String species = findShownSpecie(experiment, filterParameters);

                if ("ORGANISM_PART".equals(filterParameters.getQueryFactorType())) {
                    model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(species, true));

                    model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(species, false));
                }

                model.addAttribute("downloadUrl", buildDownloadURL(request));


            } catch (GeneNotFoundException e) {
                result.addError(new ObjectError("preferences", e.getMessage() + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

    private String findShownSpecie(Experiment experiment, FilterParameters filterParameters) {
        return experiment.isForSingleSpecie() ? experiment.getFirstSpecies() : filterParameters.getFilteredBySpecies();
    }

    String buildDownloadURL(HttpServletRequest request) {
        return Joiner.on("?").skipNulls()
                .join(new String[]{request.getRequestURI() + TSV_FILE_EXTENSION, request.getQueryString()}).toString();
    }
}
















