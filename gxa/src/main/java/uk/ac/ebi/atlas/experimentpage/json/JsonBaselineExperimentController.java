package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageServiceFactory;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class JsonBaselineExperimentController extends JsonExperimentController {

    private BaselineExperimentPageService baselineExperimentPageService;

    @Inject
    public JsonBaselineExperimentController(
            ExperimentTrader experimentTrader,
            BaselineExperimentPageServiceFactory baselineExperimentPageServiceFactory,
            BaselineProfileInputStreamFactory baselineProfileInputStreamFactory) {
        super(experimentTrader);
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(baselineProfileInputStreamFactory);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_BASELINE")
    @ResponseBody
    public String baselineRnaSeqExperimentData(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                         @PathVariable String experimentAccession,
                                         @RequestParam(required = false) String accessKey,
                                         Model model, HttpServletRequest request) {
        return gson.toJson(baselineExperimentPageService.populateModelWithHeatmapData(
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences,
                model, request, false));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=PROTEOMICS_BASELINE")
    @ResponseBody
    public String baselineProteomicsExperimentData(@ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences,
                                         @PathVariable String experimentAccession,
                                         @RequestParam(required = false) String accessKey,
                                         Model model, HttpServletRequest request    ) {
        return gson.toJson(baselineExperimentPageService.populateModelWithHeatmapData(
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences, model, request, false));
    }
}
