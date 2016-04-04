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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.baseline.PreferencesForBaselineExperiments;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineExperimentUtil;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@Scope("request")
public class RnaSeqBaselineExperimentPageController extends BaselineExperimentController {

    BaselineExperimentPageService baselineExperimentPageService;
    @Inject
    public RnaSeqBaselineExperimentPageController(BaselineExperimentPageService baselineExperimentPageService) {
        this.baselineExperimentPageService = baselineExperimentPageService;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        baselineExperimentPageService
        .prepareModelAndPossiblyAddFactorMenuAndMaybeRUrlAndWidgetThings(preferences, result, model, request, true,
                true, false, false);

        return "experiment";
    }

    @RequestMapping(value = "/widgets/heatmap/referenceExperiment", params = "type=RNASEQ_MRNA_BASELINE")
    public String fetchReferenceExperimentProfilesJson(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                                       @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks, BindingResult result, Model model, HttpServletRequest request,
                                                       HttpServletResponse response) {

        BaselineProfilesList baselineProfiles = baselineExperimentPageService
                .prepareModelAndPossiblyAddFactorMenuAndMaybeRUrlAndWidgetThings(preferences, result, model,
                request, false , false, true, disableGeneLinks);

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", result.getGlobalError().getDefaultMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "widget-error";
        }

        if (baselineProfiles.isEmpty()) {
            model.addAttribute("errorMessage", "No baseline expression found for " + preferences.getGeneQuery().description());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "widget-error";
        }

        // set here instead of in JSP, because the JSP may be included elsewhere
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

}