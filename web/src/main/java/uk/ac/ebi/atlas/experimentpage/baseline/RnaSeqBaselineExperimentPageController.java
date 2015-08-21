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
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModelBuilder;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.*;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;
import uk.ac.ebi.atlas.widget.HeatmapWidgetController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@Scope("request")
public class RnaSeqBaselineExperimentPageController extends BaselineExperimentPageController {

    private final ApplicationProperties applicationProperties;

    @Inject
    public RnaSeqBaselineExperimentPageController(BaselineProfilesHeatMap baselineProfilesHeatMap,
                                                  ApplicationProperties applicationProperties,
                                                  BaselineRequestContextBuilder requestContextBuilder,
                                                  FilterFactorsConverter filterFactorsConverter,
                                                  FilterFactorMenuBuilder filterFactorMenuBuilder,
                                                  BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                                  AssayGroupFactorViewModelBuilder assayGroupFactorViewModelBuilder,
                                                  SpeciesKingdomTrader speciesKingdomTrader,
                                                  TracksUtil tracksUtil) {
        super(baselineProfilesHeatMap, applicationProperties, requestContextBuilder, filterFactorsConverter, filterFactorMenuBuilder,
                baselineProfilesViewModelBuilder, assayGroupFactorViewModelBuilder, speciesKingdomTrader, tracksUtil);
        this.applicationProperties = applicationProperties;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        prepareModel(preferences, result, model, request);

        addFactorMenu(model);

        model.addAttribute("isWidget", false);

        // TODO https://www.pivotaltracker.com/story/show/101029574
        model.addAttribute("isSingleGene", false);

        return "experiment";
    }

    @RequestMapping(value = "/widgets/heatmap/referenceExperiment", params = "type=RNASEQ_MRNA_BASELINE")
    public String fetchReferenceExperimentProfilesJson(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                                       @RequestParam(value = "disableGeneLinks", required = false) boolean disableGeneLinks, BindingResult result, Model model, HttpServletRequest request,
                                                       HttpServletResponse response) {

        //TODO: hacky work around to support clients using the geneQuery=A1A4S6+Q13177 syntax
        // ideally we should move queryStringToTags to javascript, and keep the former space separated syntax
        // instead of the current tab separated syntax for geneQuery
        preferences.setGeneQuery(GeneQuery.create(TagEditorConverter.queryStringToTags((String) request.getAttribute(HeatmapWidgetController.ORIGINAL_GENEQUERY))));

        BaselineProfilesList baselineProfiles = prepareModel(preferences, result, model, request);

        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        model.addAttribute("isWidget", true);
        model.addAttribute("disableGeneLinks", disableGeneLinks);
        model.addAttribute("downloadURL", applicationProperties.buildDownloadURLForWidget(request, experiment.getAccession()));
        model.addAttribute("enableEnsemblLauncher", false);

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