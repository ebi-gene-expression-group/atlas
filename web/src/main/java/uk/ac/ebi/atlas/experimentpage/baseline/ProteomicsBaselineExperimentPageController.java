package uk.ac.ebi.atlas.experimentpage.baseline;

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
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
@Scope("request")
public class ProteomicsBaselineExperimentPageController extends BaselineExperimentController {

    private BaselineExperimentPageService baselineExperimentPageService;
    private ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();
    private ExperimentTrader experimentTrader;

    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public ProteomicsBaselineExperimentPageController(BaselineExperimentPageServiceFactory baselineExperimentPageServiceFactory,
                                                      ProteomicsBaselineProfileInputStreamFactory proteomicsBaselineProfileInputStreamFactory) {
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(proteomicsBaselineProfileInputStreamFactory);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=PROTEOMICS_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences,
                                     @PathVariable String experimentAccession,
                                     @RequestParam Map<String,String> allParameters,
                                     @RequestParam(required = false) String accessKey,
                                     Model model, HttpServletRequest request) {

        try {
            model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

            baselineExperimentPageService.prepareRequestPreferencesAndHeaderData(
                    (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences,model, request,false
            );
        } catch (UnsupportedEncodingException e) {
        return "error-page";
        }

        return "experiment";
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}", params = "type=PROTEOMICS_BASELINE")
    public String baselineExperimentData(@ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences,
                                         @PathVariable String experimentAccession,
                                         @RequestParam(required = false) String accessKey,
                                         BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        experimentPageCallbacks.adjustReceivedObjects(preferences);

        try {
            baselineExperimentPageService.populateModelWithHeatmapData(
                    (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                    preferences, model, request, false, false);
        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
        } catch (UnsupportedEncodingException e) {
            result.addError(new ObjectError("requestPreferences", "Error decoding gene query: '" + preferences.getGeneQuery().toJson()));
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

}