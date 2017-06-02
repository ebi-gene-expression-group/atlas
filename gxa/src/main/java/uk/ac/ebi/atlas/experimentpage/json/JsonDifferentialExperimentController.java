package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.differential.MicroarrayProfilesHeatMap;
import uk.ac.ebi.atlas.experimentpage.differential.RnaSeqProfilesHeatMap;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@Scope("request")
public class JsonDifferentialExperimentController extends JsonExperimentController {

    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }

    private final
        DifferentialExperimentPageService<DifferentialExpression, DifferentialExperiment,
                DifferentialRequestPreferences, RnaSeqProfile, RnaSeqRequestContext>
            diffRnaSeqExperimentPageService;
    private final
        DifferentialExperimentPageService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestPreferences,
                MicroarrayProfile, MicroarrayRequestContext>
            diffMicroarrayExperimentPageService;


    @Inject
    public JsonDifferentialExperimentController(ExperimentTrader experimentTrader,
                                                RnaSeqProfilesHeatMap diffRnaSeqProfilesHeatMap,
                                                MicroarrayProfilesHeatMap microarrayProfilesHeatMap,
                                                AtlasResourceHub atlasResourceHub) {
        super(experimentTrader);

        diffRnaSeqExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.RnaSeq(),
                        diffRnaSeqProfilesHeatMap,
                        atlasResourceHub);

        diffMicroarrayExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.Microarray(),
                        microarrayProfilesHeatMap,
                        atlasResourceHub);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=MICROARRAY_ANY")
    @ResponseBody
    public String differentialMicroarrayExperimentData(
            @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(required = false) String accessKey) {
        return gson.toJson(diffMicroarrayExperimentPageService.getResultsForExperiment(
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    @ResponseBody
    public String differentialRnaSeqExperimentData(
            @ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(required = false) String accessKey) {
        return gson.toJson(diffRnaSeqExperimentPageService.getResultsForExperiment(
                (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences));
    }
}
