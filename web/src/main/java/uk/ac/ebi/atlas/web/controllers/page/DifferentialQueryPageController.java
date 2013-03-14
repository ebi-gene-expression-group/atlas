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


import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankDifferentialProfilesCommand;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class DifferentialQueryPageController {

    //private RequestContextBuilder requestContextBuilder;
    private SolrClient solrClient;
    private InputStreamFactory inputStreamFactory;

    @Inject
    public DifferentialQueryPageController(RequestContextBuilder requestContextBuilder, SolrClient solrClient, InputStreamFactory inputStreamFactory){
      //  this.requestContextBuilder = requestContextBuilder;
        this.solrClient = solrClient;
        this.inputStreamFactory = inputStreamFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params={"type=DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {


        DifferentialExperiment differentialExperiment = (DifferentialExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        //requestContextBuilder.forExperiment(differentialExperiment.getAccession()).withPreferences(preferences).build();

        model.addAttribute("queryFactorName", "Contrast");

        SortedSet<Contrast> contrasts = differentialExperiment.getContrasts();

        model.addAttribute("allQueryFactors", contrasts);

        if(contrasts.size() == 1){
            preferences.setQueryFactorValues(getContrastNames(contrasts));
        }

        //required by autocomplete
        model.addAttribute("species", differentialExperiment.getSpecies());
        if (!result.hasErrors()) {

            try {

                Set<Contrast> selectedQueryContrasts = Sets.newHashSet();
                for (String queryContrastId : preferences.getQueryFactorValues()){
                    selectedQueryContrasts.add(differentialExperiment.getContrast(queryContrastId));
                }

                RankDifferentialProfilesCommand rankCommand =
                        new RankDifferentialProfilesCommand(selectedQueryContrasts, preferences.getCutoff(), preferences.getHeatmapMatrixSize(),
                                preferences.getGeneQuery(), solrClient, differentialExperiment.getSpecies().iterator().next(),inputStreamFactory);

                GeneProfilesList<DifferentialProfile> differentialProfiles = rankCommand.execute(differentialExperiment);

                model.addAttribute("geneProfiles", differentialProfiles);

                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));


            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("preferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

    SortedSet<String> getContrastNames(Set<Contrast> contrasts){
        SortedSet<String> contrastNames = Sets.newTreeSet();
        for (Contrast contrast: contrasts){
            contrastNames.add(contrast.getId());
        }
        return contrastNames;
    }


}