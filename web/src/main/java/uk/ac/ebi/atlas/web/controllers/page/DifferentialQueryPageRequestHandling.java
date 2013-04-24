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

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankProfilesCommand;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContextBuilder;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.SortedSet;

public abstract class DifferentialQueryPageRequestHandling<T extends DifferentialExperiment, K extends DifferentialRequestPreferences> {

    private DownloadURLBuilder downloadURLBuilder;
    private DifferentialRequestContextBuilder differentialRequestContextBuilder;
    private RankProfilesCommand<GeneProfilesList, GeneProfile> rankProfilesCommand;


    protected DifferentialQueryPageRequestHandling(DifferentialRequestContextBuilder requestContextBuilder,
                                                   RankProfilesCommand rankProfilesCommand,
                                                   DownloadURLBuilder downloadURLBuilder) {
        this.differentialRequestContextBuilder = requestContextBuilder;
        this.rankProfilesCommand = rankProfilesCommand;
        this.downloadURLBuilder = downloadURLBuilder;
    }

    public String showGeneProfiles(K requestPreferences, BindingResult result, Model model, HttpServletRequest request) {

        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initRequestPreferences(requestPreferences, experiment);

        DifferentialRequestContext requestContext = initRequestContext(experiment, requestPreferences);

        SortedSet<Contrast> contrasts = experiment.getContrasts();

        model.addAttribute("allQueryFactors", contrasts);

        //required by autocomplete
        model.addAttribute("species", requestContext.getFilteredBySpecies());

        model.addAttribute("queryFactorName", "Contrast");

        model.addAttribute("allQueryFactors", contrasts);
        model.addAttribute("regulationValues", Regulation.values());

        if (!result.hasErrors()) {

            try {
                GeneProfilesList<DifferentialProfile> differentialProfiles = rankProfilesCommand.execute(experiment.getAccession());

                model.addAttribute("geneProfiles", differentialProfiles);

                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));

                model.addAttribute("rawDownloadUrl", downloadURLBuilder.buildDownloadRawUrl(request));

                model.addAttribute("normalizedUrl", downloadURLBuilder.buildDownloadNormalizedDataUrl(request));

                model.addAttribute("analyticsDownloadUrl", downloadURLBuilder.buildDownloadAllAnalyticsUrl(request));


            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + requestPreferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

    private void initRequestPreferences(K requestPreferences, T experiment) {
        //if there is only one contrast we want to preselect it... from Robert feedback
        if (experiment.getContrasts().size() == 1) {
            requestPreferences.setQueryFactorValues(experiment.getContrastIds());
        }
        initExtraRequestPreferences(requestPreferences, experiment);

    }

    protected abstract void initExtraRequestPreferences(K requestPreferences, T experiment);

    private DifferentialRequestContext initRequestContext(T experiment, DifferentialRequestPreferences requestPreferences) {
        return differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences).build();

    }

}