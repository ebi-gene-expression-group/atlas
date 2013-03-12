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
import com.google.common.collect.Sets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GeneNotFoundException;
import uk.ac.ebi.atlas.commands.RankDifferentialProfilesCommand;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.validation.Valid;
import java.util.Set;
import java.util.SortedSet;

@Controller
public class DifferentialQueryPageController {

    private final RankDifferentialProfilesCommand rankCommand;


    @Inject
    public DifferentialQueryPageController(RankDifferentialProfilesCommand rankCommand) {
        this.rankCommand = rankCommand;
    }


    @RequestMapping(value = "/experiments/{experimentAccession}", params={"type=DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid RequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        DifferentialExperiment differentialExperiment = (DifferentialExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);
        model.addAttribute("queryFactorName", "Contrast");

        SortedSet<Contrast> contrasts = differentialExperiment.getContrasts();
        model.addAttribute("allQueryFactorValues", getContrastNames(contrasts));
        model.addAttribute("allQueryFactors", contrasts);

        //required by autocomplete
        model.addAttribute("species", differentialExperiment.getSpecies());
        if (!result.hasErrors()) {

            try {
                DifferentialProfilesList differentialProfiles = rankCommand.execute(differentialExperiment);

                model.addAttribute("geneProfiles", differentialProfiles);

                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));


            } catch (GeneNotFoundException e) {
                result.addError(new ObjectError("preferences", e.getMessage() + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

    SortedSet<String> getContrastNames(Set<Contrast> contrasts){
        SortedSet<String> contrastNames = Sets.newTreeSet();
        for (Contrast contrast: contrasts){
            contrastNames.add(contrast.getDisplayName());
        }
        return contrastNames;
    }


}