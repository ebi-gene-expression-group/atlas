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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.SortedSet;

@Controller
@Scope("request")
public class MicroarrayExperimentPageController extends DifferentialExperimentPageController<MicroarrayExperiment, MicroarrayRequestPreferences, MicroarrayProfile> {
    private static final String ALL_ARRAY_DESIGNS_ATTRIBUTE = "allArrayDesigns";
    private static final String QC_ARRAY_DESIGNS_ATTRIBUTE = "qcArrayDesigns";

    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    public MicroarrayExperimentPageController(MicroarrayRequestContextBuilder requestContextBuilder,
                                              MicroarrayProfilesHeatMap profilesHeatMap,
                                              DownloadURLBuilder downloadURLBuilder,
                                              ArrayDesignTrader arrayDesignTrader,
                                              DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder, SpeciesKingdomTrader speciesKingdomTrader, TracksUtil tracksUtil, GseaPlotsBuilder gseaPlotsBuilder) {
        super(requestContextBuilder, profilesHeatMap, downloadURLBuilder, differentialProfilesViewModelBuilder, speciesKingdomTrader, tracksUtil, gseaPlotsBuilder);

        this.arrayDesignTrader = arrayDesignTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=MICROARRAY_ANY"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        //TODO https://www.pivotaltracker.com/story/show/101029574
        model.addAttribute("isSingleGene", false);

        return super.showGeneProfiles(preferences, result, model, request);

    }

    @Override
    protected void initExtraPageConfigurations(Model model, MicroarrayRequestPreferences requestPreferences, MicroarrayExperiment experiment) {
        SortedSet<String> arrayDesignNames = arrayDesignTrader.getArrayDesignNames(experiment.getArrayDesignAccessions());
        model.addAttribute(ALL_ARRAY_DESIGNS_ATTRIBUTE, arrayDesignNames);

        //For showing the QC REPORTS button in the header
        model.addAttribute(QC_ARRAY_DESIGNS_ATTRIBUTE, experiment.getArrayDesignAccessions());
    }

}
