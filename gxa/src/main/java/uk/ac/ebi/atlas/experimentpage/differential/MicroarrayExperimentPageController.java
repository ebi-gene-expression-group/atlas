package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class MicroarrayExperimentPageController extends DifferentialExperimentPageController{

    private ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();

    private ExperimentTrader experimentTrader;

    private DifferentialExperimentPageService<MicroarrayExperiment, MicroarrayRequestPreferences, MicroarrayProfile>
            differentialExperimentPageService;
    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public MicroarrayExperimentPageController(MicroarrayRequestContextBuilder requestContextBuilder,
                                              MicroarrayProfilesHeatMap profilesHeatMap,
                                              DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                              TracksUtil tracksUtil, AtlasResourceHub atlasResourceHub, ApplicationProperties applicationProperties) {
        differentialExperimentPageService =
                new DifferentialExperimentPageService<>(requestContextBuilder, profilesHeatMap,
                differentialProfilesViewModelBuilder,
                tracksUtil, atlasResourceHub,applicationProperties);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=MICROARRAY_ANY"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences,
                                   @RequestParam Map<String, String> allParameters,
                                   @RequestParam(required = false) String accessKey,
                                   @PathVariable String experimentAccession, Model model, HttpServletRequest request) {

        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

        differentialExperimentPageService.prepareRequestPreferencesAndHeaderData(
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences, model,request
        );

        return "experiment";
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}", params = {"type=MICROARRAY_ANY"})
    @ResponseBody
    public String showGeneProfilesData(@ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences,
                                       @PathVariable String experimentAccession,
                                       @RequestParam(required = false) String accessKey,
                                       BindingResult result, Model model,HttpServletRequest request,
                                       HttpServletResponse response) {
//        experimentPageCallbacks.adjustReceivedObjects(preferences);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return gson.toJson(differentialExperimentPageService.populateModelWithHeatmapData(request,
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                preferences, result, model
        ));
    }
}
