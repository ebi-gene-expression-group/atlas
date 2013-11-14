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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.dto.tooltip.AssayGroupSummary;
import uk.ac.ebi.atlas.dto.tooltip.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.dto.tooltip.ContrastSummary;
import uk.ac.ebi.atlas.dto.tooltip.ContrastSummaryBuilder;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;

@Controller
@Scope("request")
public class ContrastSummaryController {

    private ExperimentTrader experimentTrader;

    private ContrastSummaryBuilder contrastSummaryBuilder;

    private AssayGroupSummaryBuilder assayGroupSummaryBuilder;

    @Inject
    public ContrastSummaryController(ExperimentTrader experimentTrader, ContrastSummaryBuilder contrastSummaryBuilder, AssayGroupSummaryBuilder assayGroupSummaryBuilder) {
        this.experimentTrader = experimentTrader;
        this.contrastSummaryBuilder = contrastSummaryBuilder;
        this.assayGroupSummaryBuilder = assayGroupSummaryBuilder;
    }

    @RequestMapping(value = "/rest/contrast-summary", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContrastContent(@RequestParam(value = "experimentAccession") String experimentAccession,
                                    @RequestParam(value = "accessKey", required = false) String accessKey,
                                    @RequestParam(value = "contrastId") String contrastId) {

        DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        Contrast contrast = differentialExperiment.getContrast(contrastId);
        if (contrast == null) {
            throw new IllegalStateException("No contract with id " + contrastId + " found.");
        }

        ExperimentDesign experimentDesign = differentialExperiment.getExperimentDesign();

        ContrastSummary contrastSummary = contrastSummaryBuilder.withExperimentDesign(experimentDesign)
                .forContrast(contrast)
                .withExperimentDescription(differentialExperiment.getDescription())
                .build();

        return new Gson().toJson(contrastSummary);
    }

    @RequestMapping(value = "/rest/assayGroup-summary", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipAssayGroupContent(@RequestParam(value = "experimentAccession") String experimentAccession,
                                    @RequestParam(value = "accessKey", required = false) String accessKey,
                                    @RequestParam(value = "assayGroupId") String assayGroupId) {

        //ToDo: check type
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        AssayGroup assayGroup = experiment.getAssayGroups().getAssayGroup(assayGroupId);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        AssayGroupSummary assayGroupSummary = assayGroupSummaryBuilder.withExperimentDesign(experimentDesign).forAssayGroup(assayGroup).build();

        return new Gson().toJson(assayGroupSummary);
    }


}
