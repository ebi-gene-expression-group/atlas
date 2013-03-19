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
import uk.ac.ebi.atlas.commands.GeneProfilesInputStreamCommand;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankDifferentialProfilesExecutor;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContextBuilder;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesInputStream;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.SortedSet;

@Controller
@Scope("request")
public class DifferentialQueryPageController {

    private DifferentialRequestContextBuilder requestContextBuilder;
    private RankDifferentialProfilesExecutor commandExecutor;

    private GeneProfilesInputStreamCommand<GeneProfilesList<DifferentialProfile>, ObjectInputStream<DifferentialProfile>> geneProfilesInputStreamCommand;

    private InputStreamFactory inputStreamFactory;


    @Inject
    public DifferentialQueryPageController(DifferentialRequestContextBuilder requestContextBuilder,
                                           RankDifferentialProfilesExecutor rankDifferentialProfilesCommandExecutor, GeneProfilesInputStreamCommand<GeneProfilesList<DifferentialProfile>, ObjectInputStream<DifferentialProfile>> geneProfilesInputStreamCommand, InputStreamFactory inputStreamFactory){
        this.requestContextBuilder = requestContextBuilder;
        this.commandExecutor = rankDifferentialProfilesCommandExecutor;
        this.geneProfilesInputStreamCommand = geneProfilesInputStreamCommand;
        this.inputStreamFactory = inputStreamFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params={"type=DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        DifferentialExperiment experiment = (DifferentialExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        SortedSet<Contrast> contrasts = experiment.getContrasts();

        model.addAttribute("allQueryFactors", contrasts);

//        if there is only one contrast we want to preselect it... from Robert feedback
        if(contrasts.size() == 1){
            preferences.setQueryFactorValues(experiment.getContrastIds());
        }

        RequestContext requestContext = requestContextBuilder.forExperiment(experiment)
                             .withPreferences(preferences).build();

        //required by autocomplete
        model.addAttribute("species", requestContext.getFilteredBySpecies());

        model.addAttribute("queryFactorName", "Contrast");

        model.addAttribute("allQueryFactors", contrasts);
        model.addAttribute("regulationValues", Regulation.values());

        if (!result.hasErrors()) {

            try {

                DifferentialProfilesInputStream inputStream = inputStreamFactory.createDifferentialProfileInputStream(experiment.getAccession());
                geneProfilesInputStreamCommand.setRequestContext(requestContext);
                geneProfilesInputStreamCommand.setCommandExecutor(commandExecutor);

                GeneProfilesList<DifferentialProfile> differentialProfiles = geneProfilesInputStreamCommand.apply(inputStream);

                model.addAttribute("geneProfiles", differentialProfiles);

                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));


            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("preferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

}