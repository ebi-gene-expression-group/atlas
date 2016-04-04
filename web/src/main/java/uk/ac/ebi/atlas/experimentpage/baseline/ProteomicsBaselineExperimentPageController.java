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

package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineExperimentUtil;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class ProteomicsBaselineExperimentPageController extends BaselineExperimentPageController {

    @Inject
    public ProteomicsBaselineExperimentPageController(ProteomicsBaselineProfilesHeatMap baselineProfilesHeatMap,
                                                      ApplicationProperties applicationProperties,
                                                      BaselineRequestContextBuilder requestContextBuilder,
                                                      FilterFactorsConverter filterFactorsConverter,
                                                      FilterFactorMenuBuilder filterFactorMenuBuilder,
                                                      BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                                      AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                                      SpeciesKingdomTrader speciesKingdomTrader,
                                                      TracksUtil tracksUtil,
                                                      BaselineExperimentUtil bslnUtil) {

        super(baselineProfilesHeatMap, applicationProperties, requestContextBuilder, filterFactorsConverter, filterFactorMenuBuilder,
                baselineProfilesViewModelBuilder, assayGroupFactorViewModelBuilder, speciesKingdomTrader, tracksUtil, bslnUtil);
    }


    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=PROTEOMICS_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {
        prepareModelAndPossiblyAddFactorMenuAndMaybeRUrlAndWidgetThings(preferences, result, model, request, true,
                false,false, false);

        return "experiment";
    }

}