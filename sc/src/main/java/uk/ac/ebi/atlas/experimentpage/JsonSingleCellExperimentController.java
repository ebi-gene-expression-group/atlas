package uk.ac.ebi.atlas.experimentpage;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
public class JsonSingleCellExperimentController extends JsonExperimentController {

    @Inject
    public JsonSingleCellExperimentController(ExperimentTrader experimentTrader) {
        super(experimentTrader);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/reference",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String tSnePlotReference(
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        // Return hard-coded t-SNE plot data: coordinates

        return "";
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/expression",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String tSnePlotExpression(
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        // Return hard-coded t-SNE plot data: coordinates

        return "";
    }


}
