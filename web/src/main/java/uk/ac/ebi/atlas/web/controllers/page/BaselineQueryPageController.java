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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankBaselineProfilesCommand;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.BaselineProfilesList;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.FilterFactorMenuBuilder;
import uk.ac.ebi.atlas.utils.FilterFactorMenuVoice;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.BaselineQueryController;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class BaselineQueryPageController extends BaselineQueryController {

    private RankBaselineProfilesCommand rankCommand;

    private ApplicationProperties applicationProperties;

    private FilterFactorMenuBuilder filterFactorMenuBuilder;

    @Inject
    public BaselineQueryPageController(RankBaselineProfilesCommand rankCommand,
                                       ApplicationProperties applicationProperties,
                                       BaselineRequestContextBuilder requestContextBuilder,
                                       FilterFactorsConverter filterFactorsConverter,
                                       FilterFactorMenuBuilder filterFactorMenuBuilder) {

        super(requestContextBuilder, filterFactorsConverter);
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
        this.filterFactorMenuBuilder = filterFactorMenuBuilder;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=BASELINE"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initPreferences(preferences, experiment);

        BaselineRequestContext requestContext = initRequestContext(experiment, preferences);

        model.addAttribute("experimentAccession", experiment.getAccession());

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        model.addAttribute("queryFactorName", StringUtils.capitalize(experimentalFactors.getFactorName(preferences.getQueryFactorType())));

        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();

        SortedSet<Factor> allQueryFactors = experimentalFactors.getFilteredFactors(selectedFilterFactors);

        // this is currently required for the request requestPreferences filter drop-down multi-selection box
        model.addAttribute("allQueryFactors", allQueryFactors);

        SortedSet<String> menuFactorNames = experimentalFactors.getMenuFilterFactorNames();

        String species = requestContext.getFilteredBySpecies();

        //required by autocomplete
        model.addAttribute("species", species);

        //ToDo: this stuff should be refactored, menu should be a separate REST service
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
        for (Factor selectedFilterFactor : selectedFilterFactors) {
            selectedFilterFactorNamesAndValues.put(experimentalFactors.getFactorName(selectedFilterFactor.getType()), selectedFilterFactor.getValue());
        }
        model.addAttribute("selectedFilterFactorNamesAndValues", selectedFilterFactorNamesAndValues);

        model.addAttribute("selectedFilterFactorsJson", new Gson().toJson(requestContext.getSelectedFilterFactors()));

        if (!result.hasErrors()) {

            try {

                BaselineProfilesList geneProfiles = rankCommand.execute(experiment.getAccession());

                if (preferences.isGeneSetMatch()) {
                    model.addAttribute("geneProfiles", geneProfiles.collapsedProfilesList(allQueryFactors));
                } else {
                    model.addAttribute("geneProfiles", geneProfiles);
                }

                //ToDo: check if this can be externalized in the view with a cutom EL or tag function
                if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
                    model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(species, true));

                    model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(species, false));
                }

                //ToDo: maybe this can be directly built client side in javascript or EL
                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));


            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

}
















