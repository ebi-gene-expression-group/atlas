package uk.ac.ebi.atlas.experimentpage.tooltip;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummary;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Controller
@Scope("request")
public class AssayGroupSummaryController {
    private final ExperimentTrader experimentTrader;

    @Inject
    public AssayGroupSummaryController(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/rest/assayGroup-summary", produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipAssayGroupContent(@RequestParam(value = "experimentAccession") String experimentAccession,
                                              @RequestParam(value = "accessKey", required = false) String accessKey,
                                              @RequestParam(value = "assayGroupId") String assayGroupId) {
        BaselineExperiment experiment =
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        AssayGroup assayGroup = experiment.getDataColumnDescriptor(assayGroupId);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        AssayGroupSummary assayGroupSummary =
                new AssayGroupSummaryBuilder()
                        .withExperimentDesign(experimentDesign)
                        .forAssayGroup(assayGroup)
                        .build();

        return GSON.toJson(assayGroupSummary);
    }
}
