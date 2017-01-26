package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class RnaSeqBaselineExperimentPageController extends BaselineExperimentPageController {

    private BaselineExperimentPageService baselineExperimentPageService;
    private ExperimentTrader experimentTrader;
    private ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();

    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public RnaSeqBaselineExperimentPageController(BaselineExperimentPageServiceFactory baselineExperimentPageServiceFactory,
                                                  @Qualifier("baselineProfileInputStreamFactory")BaselineProfileInputStreamFactory baselineProfileInputStreamFactory) {
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(baselineProfileInputStreamFactory);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                     @PathVariable String experimentAccession,
                                     @RequestParam Map<String,String> allParameters,
                                     @RequestParam(required = false) String accessKey,
                                     Model model, HttpServletRequest request) {

        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

        baselineExperimentPageService.prepareRequestPreferencesAndHeaderData(
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences, model, request, false
        );

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        return "experiment";
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    @ResponseBody
    public String baselineExperimentData(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                         @PathVariable String experimentAccession,
                                         @RequestParam(required = false) String accessKey,
                                         Model model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        return gson.toJson(baselineExperimentPageService.populateModelWithHeatmapData(
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences,
                model, request, false));
    }

}