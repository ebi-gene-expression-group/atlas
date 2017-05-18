package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageServiceFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialRequestPreferencesValidator;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class JsonBaselineExperimentController extends JsonExperimentController {

    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    private final BaselineExperimentPageService rnaSeqBaselineExperimentPageService;
    private final BaselineExperimentPageService proteomicsBaselineExperimentPageService;

    @Inject
    public JsonBaselineExperimentController(
            ExperimentTrader experimentTrader,
            BaselineExperimentPageServiceFactory baselineExperimentPageServiceFactory,
            RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory,
            ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory) {
        super(experimentTrader);
        this.rnaSeqBaselineExperimentPageService = baselineExperimentPageServiceFactory.create(rnaSeqBaselineProfileStreamFactory);
        this.proteomicsBaselineExperimentPageService = baselineExperimentPageServiceFactory.create(proteomicsBaselineProfileStreamFactory);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_BASELINE")
    @ResponseBody
    public String baselineRnaSeqExperimentData(
            @ModelAttribute("preferences") @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(required = false) String accessKey,
            Model model, HttpServletRequest request) {
        return gson.toJson(
                rnaSeqBaselineExperimentPageService.populateModelWithHeatmapData(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences,
                        model, request, false));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=PROTEOMICS_BASELINE")
    @ResponseBody
    public String baselineProteomicsExperimentData(
            @ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(required = false) String accessKey,
            Model model, HttpServletRequest request    ) {
        return gson.toJson(
                proteomicsBaselineExperimentPageService.populateModelWithHeatmapData(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                        preferences, model, request, false));
    }
}
