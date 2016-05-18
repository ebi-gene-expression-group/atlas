
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
