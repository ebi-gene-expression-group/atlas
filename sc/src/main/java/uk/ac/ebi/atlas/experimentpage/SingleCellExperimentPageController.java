package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SingleCellExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
                                         @RequestParam(required = false) String accessKey,
                                         Model model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        return gson.toJson(experimentTrader.getPublicExperiment(experimentAccession).getAttributes());
    }
}
