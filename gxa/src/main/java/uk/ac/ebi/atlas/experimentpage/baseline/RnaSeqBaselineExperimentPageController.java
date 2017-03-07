package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Deprecated
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
                                                  RnaSeqBaselineProfileStreamFactory baselineProfileStreamFactory) {
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(baselineProfileStreamFactory);
    }

    @RequestMapping(value = "/old-experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                     @PathVariable String experimentAccession,
                                     @RequestParam Map<String,String> allParameters,
                                     @RequestParam(required = false) String accessKey,
                                     Model model, HttpServletRequest request) {

        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

        baselineExperimentPageService.prepareRequestPreferencesAndHeaderData(
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences, model, request
        );

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        return "experiment";

    }

}