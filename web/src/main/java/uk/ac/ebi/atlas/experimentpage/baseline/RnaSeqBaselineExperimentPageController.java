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
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesEnsemblTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class RnaSeqBaselineExperimentPageController extends BaselineExperimentPageController {

    @Inject
    public RnaSeqBaselineExperimentPageController(BaselineProfilesHeatMap baselineProfilesHeatMap,
                                                  ApplicationProperties applicationProperties,
                                                  BaselineRequestContextBuilder requestContextBuilder,
                                                  FilterFactorsConverter filterFactorsConverter,
                                                  FilterFactorMenuBuilder filterFactorMenuBuilder,
                                                  BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                                  AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                                  SpeciesEnsemblTrader speciesEnsemblTrader,
                                                  TracksUtil tracksUtil) {

        super(baselineProfilesHeatMap, applicationProperties, requestContextBuilder, filterFactorsConverter, filterFactorMenuBuilder,
                baselineProfilesViewModelBuilder, assayGroupFactorViewModelBuilder, speciesEnsemblTrader, tracksUtil);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        prepareModel(preferences, result, model, request);

        addFactorMenu(model);

        model.addAttribute("isWidget", false);

        return "experiment";
    }

    @RequestMapping(value = "/widgets/heatmap/protein", params = "type=RNASEQ_MRNA_BASELINE")
    public String showGeneProfilesWidget(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks, BindingResult result, Model model, HttpServletRequest request) {

        prepareModel(preferences, result, model, request);

        model.addAttribute("isWidget", true);
        model.addAttribute("disableGeneLinks", disableGeneLinks);
        return "heatmap-widget";
    }

}