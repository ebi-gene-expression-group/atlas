package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class RnaSeqExperimentPageController extends DifferentialExperimentPageController {

    private final ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();
    private final ExperimentTrader experimentTrader;
    private final
            DifferentialExperimentPageService<DifferentialExperiment, DifferentialRequestPreferences, RnaSeqProfile>
            differentialExperimentPageService;

    @Inject
    public RnaSeqExperimentPageController(ExperimentTrader experimentTrader,
                                          RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          RnaSeqProfilesHeatMap profilesHeatMap,
                                          DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                          TracksUtil tracksUtil,
                                          AtlasResourceHub atlasResourceHub,
                                          ApplicationProperties applicationProperties) {
        this.experimentTrader = experimentTrader;
        differentialExperimentPageService = new DifferentialExperimentPageService<>(rnaSeqRequestContextBuilder, profilesHeatMap,
                differentialProfilesViewModelBuilder,
                tracksUtil, atlasResourceHub,applicationProperties);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
                                   @RequestParam Map<String, String> allParameters,
                                   @RequestParam(required = false) String accessKey,
                                   @PathVariable String experimentAccession, Model model, HttpServletRequest request) {
        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

        differentialExperimentPageService.prepareRequestPreferencesAndHeaderData(
                (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences, model, request
        );

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        return "experiment";

    }

}