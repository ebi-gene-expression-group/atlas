
package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class RnaSeqExperimentPageController extends DifferentialExperimentPageController<DifferentialExperiment, DifferentialRequestPreferences, RnaSeqProfile> {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private ExperimentTrader experimentTrader;

    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public RnaSeqExperimentPageController(RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          RnaSeqProfilesHeatMap profilesHeatMap,
                                          DownloadURLBuilder downloadURLBuilder,
                                          DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                          SpeciesKingdomTrader speciesKingdomTrader,
                                          TracksUtil tracksUtil,
                                          GseaPlotsBuilder gseaPlotsBuilder) {
        super(rnaSeqRequestContextBuilder, profilesHeatMap, downloadURLBuilder, differentialProfilesViewModelBuilder, speciesKingdomTrader, tracksUtil, gseaPlotsBuilder);
    }

    @Override
    protected void initExtraPageConfigurations(Model model, DifferentialRequestPreferences requestPreferences, DifferentialExperiment experiment) {
        //No extra initalizations required
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
                                   @PathVariable String experimentAccession, BindingResult result, Model model, HttpServletRequest request) {

        if(request.getAttribute(EXPERIMENT_ATTRIBUTE) == null) {
            DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(experimentAccession);
            request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);
        }

        return super.showGeneProfiles(preferences, result, model, request);
    }

}