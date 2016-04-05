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

package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class RnaSeqExperimentPageController extends DifferentialExperimentPageController<DifferentialExperiment, DifferentialRequestPreferences, RnaSeqProfile> {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private ExperimentTrader experimentTrader;

    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public RnaSeqExperimentPageController(RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          RnaSeqProfilesHeatMap profilesHeatMap,
                                          DownloadURLBuilder downloadURLBuilder,
                                          DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                          SpeciesKingdomTrader speciesKingdomTrader,
                                          TracksUtil tracksUtil,
                                          GseaPlotsBuilder gseaPlotsBuilder) {
        super(rnaSeqRequestContextBuilder, profilesHeatMap, downloadURLBuilder, differentialProfilesViewModelBuilder, speciesKingdomTrader, tracksUtil, gseaPlotsBuilder);
    }

    @Override
    protected void initExtraPageConfigurations(Model model, DifferentialRequestPreferences requestPreferences, DifferentialExperiment experiment) {
        //No extra initalizations required
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
                                   @PathVariable String experimentAccession, BindingResult result, Model model, HttpServletRequest request) {

        if(request.getAttribute(EXPERIMENT_ATTRIBUTE) == null) {
            DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(experimentAccession);
            request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);
            model.addAllAttributes(experiment.getAttributes());
        }

        return super.showGeneProfiles(preferences, result, model, request);
    }

}