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

package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
@Scope("request")
public class AssayGroupSummaryController {

    private ExperimentTrader experimentTrader;

    private AssayGroupSummaryBuilder assayGroupSummaryBuilder;

    @Inject
    public AssayGroupSummaryController(ExperimentTrader experimentTrader, AssayGroupSummaryBuilder assayGroupSummaryBuilder) {
        this.experimentTrader = experimentTrader;
        this.assayGroupSummaryBuilder = assayGroupSummaryBuilder;
    }

    @RequestMapping(value = "/rest/assayGroup-summary", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipAssayGroupContent(@RequestParam(value = "experimentAccession") String experimentAccession,
                                    @RequestParam(value = "accessKey", required = false) String accessKey,
                                    @RequestParam(value = "assayGroupId") String assayGroupId) {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        AssayGroup assayGroup = experiment.getAssayGroups().getAssayGroup(assayGroupId);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        AssayGroupSummary assayGroupSummary = assayGroupSummaryBuilder.withExperimentDesign(experimentDesign).forAssayGroup(assayGroup).build();

        return new Gson().toJson(assayGroupSummary);
    }


}
