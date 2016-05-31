package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class RnaSeqBaselineExperimentPageController extends BaselineExperimentController {

    BaselineExperimentPageService baselineExperimentPageService;

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private ExperimentTrader experimentTrader;

    private ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();

    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public RnaSeqBaselineExperimentPageController(BaselineExperimentPageServiceFactory
                                                              baselineExperimentPageServiceFactory, @Qualifier
            ("baselineProfileInputStreamFactory")BaselineProfileInputStreamFactory baselineProfileInputStreamFactory) {
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(baselineProfileInputStreamFactory);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                     @PathVariable String experimentAccession,
                                     @RequestParam Map<String,String> allParameters,
                                     BindingResult result, Model model, HttpServletRequest request) {

        try {
            baselineExperimentPageService.prepareModel((BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession),
                    preferences, model, request, false, false);
        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
        }

        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters,
                request.getRequestURI()));
        return "experiment";
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}", params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineExperimentData(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                         @PathVariable String experimentAccession,
                                      BindingResult result, Model model, HttpServletRequest request,
                                         HttpServletResponse response) {
        experimentPageCallbacks.adjustReceivedObjects(preferences);

        if(request.getAttribute(EXPERIMENT_ATTRIBUTE) == null) {
            request.setAttribute(EXPERIMENT_ATTRIBUTE, experimentTrader.getPublicExperiment(experimentAccession));
        }
        try {
            baselineExperimentPageService.prepareModel((BaselineExperiment) experimentTrader.getPublicExperiment
                    (experimentAccession), preferences, model, request, false, false);
        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }


}