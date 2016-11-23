package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummary;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SingleCellExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SingleCellExperimentPageController extends ExperimentPageController {

    private final ExperimentTrader experimentTrader;

    private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


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

    @RequestMapping(value = "/experiments/{experimentAccession}/cell_types")
    @ResponseBody
    public String baselineExperimentAssays(@PathVariable String experimentAccession,
                                          HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        return gson.toJson(experiment.getAssayGroups());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/cell_types/{assayGroupId}")
    @ResponseBody
    public String baselineExperimentAssay(@PathVariable String experimentAccession,
                                          @PathVariable String assayGroupId,
                                         HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        return gson.toJson(new AssayGroupSummaryBuilder()
                .withExperimentDesign(experiment.getExperimentDesign())
                .forAssayGroup(experiment.getAssayGroups().getAssayGroup(assayGroupId))
                .build());

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/cell_ids/{cellId}")
    @ResponseBody
    public String baselineExperimentCell(@PathVariable String experimentAccession,
                                          @PathVariable String cellId,
                                          HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        return gson.toJson(experimentTrader.getPublicExperiment
                (experimentAccession).getExperimentDesign().getSampleCharacteristicsValues(cellId));

    }
}
