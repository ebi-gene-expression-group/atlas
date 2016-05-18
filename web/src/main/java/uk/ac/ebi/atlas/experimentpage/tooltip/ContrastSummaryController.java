
package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
@Scope("request")
public class ContrastSummaryController {

    private ExperimentTrader experimentTrader;

    private ContrastSummaryBuilder contrastSummaryBuilder;

    @Inject
    public ContrastSummaryController(ExperimentTrader experimentTrader, ContrastSummaryBuilder contrastSummaryBuilder) {
        this.experimentTrader = experimentTrader;
        this.contrastSummaryBuilder = contrastSummaryBuilder;
    }

    @RequestMapping(value = "/rest/contrast-summary", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContrastContent(@RequestParam(value = "experimentAccession") String experimentAccession,
                                    @RequestParam(value = "accessKey", required = false) String accessKey,
                                    @RequestParam(value = "contrastId") String contrastId) {

        DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        Contrast contrast = differentialExperiment.getContrast(contrastId);
        if (contrast == null) {
            throw new IllegalStateException("No contrast with ID " + contrastId + " found.");
        }

        ExperimentDesign experimentDesign = differentialExperiment.getExperimentDesign();

        ContrastSummary contrastSummary = contrastSummaryBuilder.withExperimentDesign(experimentDesign)
                .forContrast(contrast)
                .withExperimentDescription(differentialExperiment.getDescription())
                .build();

        return new Gson().toJson(contrastSummary);
    }

}
