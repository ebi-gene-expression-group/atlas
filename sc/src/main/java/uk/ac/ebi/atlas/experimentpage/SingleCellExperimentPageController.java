package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SingleCellExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SingleCellExperimentPageController extends ExperimentPageController {

    private final ExperimentTrader experimentTrader;

    @Inject
    public SingleCellExperimentPageController(SingleCellExperimentTrader experimentTrader){
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}")
    @ResponseBody
    public String baselineExperimentData(@PathVariable String experimentAccession,
                                         HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        return gson.toJson(experimentTrader.getPublicExperiment(experimentAccession).getAttributes());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/assay_group/{assayGroupId}")
    @ResponseBody
    public String baselineExperimentAssay(@PathVariable String experimentAccession,
                                          @PathVariable String assayGroupId,
                                         HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

/*
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        AssayGroup assayGroup = experiment.getAssayGroups().getAssayGroup(assayGroupId);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        AssayGroupSummary assayGroupSummary = new AssayGroupSummaryBuilder().withExperimentDesign(experimentDesign).forAssayGroup(assayGroup)
                .build();

        return new Gson().toJson(assayGroupSummary);*/

        return "";
    }
}
