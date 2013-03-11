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

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GeneNotFoundException;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneProfilesList;
import uk.ac.ebi.atlas.utils.FilterFactorMenu;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.BaselineQueryController;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class BaselineQueryPageController extends BaselineQueryController {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private RankGeneProfilesCommand rankCommand;

    private ApplicationProperties applicationProperties;

    private FilterFactorsConverter filterFactorsConverter;

    @Inject
    public BaselineQueryPageController(RankGeneProfilesCommand rankCommand,
                                       ApplicationProperties applicationProperties,
                                       RequestContextBuilder requestContextBuilder,
                                       FilterFactorsConverter filterFactorsConverter) {

        super(requestContextBuilder, filterFactorsConverter);
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params={"type=baseline"})
    public String showGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        BaselineExperiment experiment = (BaselineExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initPreferences(preferences, experiment);

        RequestContext requestContext = initRequestContext(experimentAccession, preferences);

        model.addAttribute("experimentAccession", experimentAccession);

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        model.addAttribute("queryFactorName", StringUtils.capitalize(experimentalFactors.getFactorName(preferences.getQueryFactorType())));

        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();


        SortedSet<Factor> allQueryFactors = experimentalFactors.getFilteredFactors(selectedFilterFactors);

        // this is currently required for the request preferences filter drop-down multi-selection box
        model.addAttribute("allQueryFactors", allQueryFactors);

        // this is currently required for the request preferences filter drop-down multi-selection box
        model.addAttribute("allQueryFactorValues", Factor.getValues(allQueryFactors));

        SortedSet<String> menuFactorNames = experimentalFactors.getMenuFilterFactorNames();

        String species = requestContext.getFilteredBySpecies();

        //required by autocomplete
        model.addAttribute("species", species);

        //ToDo: this stuff should be refactored, menu should be a separate REST service
        if (!menuFactorNames.isEmpty()) {

            Set<Factor> menuFactors = experimentalFactors.getAllFactors();

            FilterFactorMenu filterFactorMenu = new FilterFactorMenu(experimentalFactors, menuFactors);

            filterFactorMenu.setFactorConverter(filterFactorsConverter);

            model.addAttribute("filterFactorMenu", filterFactorMenu);

            model.addAttribute("menuFactorNames", menuFactorNames);
        }

        //ToDo: looks bad, a custom EL function or jsp tag function to resolve names would be much better
        Map<String, String> factorNameToValue = new HashMap();
        for (Factor selectedFilterFactor : selectedFilterFactors) {
            factorNameToValue.put(experimentalFactors.getFactorName(selectedFilterFactor.getType()), selectedFilterFactor.getValue());
        }
        model.addAttribute("selectedFilterFactors", factorNameToValue);

        if (!result.hasErrors()) {

            try {
                GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

                model.addAttribute("geneProfiles", geneProfiles);

                //ToDo: check if this can be externalized in the view with a cutom EL or tag function
                if ("ORGANISM_PART".equals(requestContext.getQueryFactorType())) {
                    model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(species, true));

                    model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(species, false));
                }

                //ToDo: maybe this can be directly built client side in javascript or EL
                model.addAttribute("downloadUrl", buildDownloadURL(request));


            } catch (GeneNotFoundException e) {
                result.addError(new ObjectError("preferences", e.getMessage() + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

    String buildDownloadURL(HttpServletRequest request) {
        //It's important that here we use the original query string, not the forwarded one
        String originalQueryString = ((HttpServletRequest)((HttpServletRequestWrapper) request).getRequest()).getQueryString();
        return Joiner.on("?").skipNulls()
                .join(new String[]{request.getRequestURI() + TSV_FILE_EXTENSION, originalQueryString}).toString();
    }
}
















