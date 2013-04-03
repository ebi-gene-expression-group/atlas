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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.SortedSet;

@Controller
@Scope("request")
public class MicroarrayQueryPageController{

    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;
    private RankMicroarrayProfilesCommand rankMicroarrayProfilesCommand;

    @Inject
    public MicroarrayQueryPageController(MicroarrayRequestContextBuilder requestContextBuilder, RankMicroarrayProfilesCommand rankMicroarrayProfilesCommand) {
        this.microarrayRequestContextBuilder = requestContextBuilder;
        this.rankMicroarrayProfilesCommand = rankMicroarrayProfilesCommand;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params={"type=MICROARRAY"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences
                                        , BindingResult result, Model model, HttpServletRequest request) {

        MicroarrayExperiment experiment = (MicroarrayExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

//      if there is only one array design we want to preselect it...
        if(experiment.getArrayDesignAccessions().size() == 1){
            preferences.setArrayDesignName(experiment.getArrayDesignAccessions().first());
        }

        MicroarrayRequestContext requestContext = microarrayRequestContextBuilder.forExperiment(experiment)
                .withPreferences(preferences).build();

        ////////////////From here the code is exact duplicate of DifferentialQueryPageController

        SortedSet<Contrast> contrasts = experiment.getContrasts();

        model.addAttribute("allQueryFactors", contrasts);

//      if there is only one contrast we want to preselect it... from Robert feedback
        if(contrasts.size() == 1){
            preferences.setQueryFactorValues(experiment.getContrastIds());
        }

        //required by autocomplete
        model.addAttribute("species", requestContext.getFilteredBySpecies());

        model.addAttribute("queryFactorName", "Contrast");

        model.addAttribute("allQueryFactors", contrasts);
        model.addAttribute("regulationValues", Regulation.values());

        if (!result.hasErrors()) {

            try {
                GeneProfilesList<DifferentialProfile> differentialProfiles = rankMicroarrayProfilesCommand.execute(experiment.getAccession());

                model.addAttribute("geneProfiles", differentialProfiles);

                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));


            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";

    }

}
