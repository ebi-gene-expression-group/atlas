package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Deprecated
@Controller
@Scope("request")
public class MicroarrayExperimentPageController extends DifferentialExperimentPageController{

    private ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();

    private ExperimentTrader experimentTrader;
    private DifferentialExperimentPageService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestPreferences, MicroarrayProfile, MicroarrayRequestContext>
            differentialExperimentPageService;

    @Inject
    public MicroarrayExperimentPageController(ExperimentTrader experimentTrader,
                                              MicroarrayProfilesHeatMap profilesHeatMap,
                                              TracksUtil tracksUtil, AtlasResourceHub atlasResourceHub, ApplicationProperties applicationProperties) {
        this.experimentTrader = experimentTrader;
        differentialExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.Microarray(), profilesHeatMap,
                tracksUtil, atlasResourceHub,applicationProperties);
    }

    @RequestMapping(value = "/old-experiments/{experimentAccession}", params = {"type=MICROARRAY_ANY"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences,
                                   @RequestParam Map<String, String> allParameters,
                                   @RequestParam(required = false) String accessKey,
                                   @PathVariable String experimentAccession, Model model, HttpServletRequest request) {

        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

        differentialExperimentPageService.prepareRequestPreferencesAndHeaderData(
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences, model, request
        );

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        return "experiment";

    }

}
