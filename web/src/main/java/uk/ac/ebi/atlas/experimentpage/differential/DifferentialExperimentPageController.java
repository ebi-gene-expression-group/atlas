
package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.solr.common.SolrException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public abstract class DifferentialExperimentPageController<T extends DifferentialExperiment, K extends
        DifferentialRequestPreferences, P extends DifferentialProfile<?>> {

    private final DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder;
    private final SpeciesKingdomTrader speciesKingdomTrader;
    private final GseaPlotsBuilder gseaPlotsBuilder;
    private DownloadURLBuilder downloadURLBuilder;
    private DifferentialRequestContextBuilder differentialRequestContextBuilder;
    private DifferentialProfilesHeatMap<P, DifferentialRequestContext<?>> profilesHeatMap;
    private TracksUtil tracksUtil;

    private Gson gson = new GsonBuilder()
            .create();

    @SuppressWarnings("unchecked")
    protected DifferentialExperimentPageController(DifferentialRequestContextBuilder
                                                           differentialRequestContextBuilder,
                                                   DifferentialProfilesHeatMap<P, DifferentialRequestContext<?>> profilesHeatMap,
                                                   DownloadURLBuilder downloadURLBuilder, DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                                   SpeciesKingdomTrader speciesKingdomTrader, TracksUtil tracksUtil, GseaPlotsBuilder gseaPlotsBuilder) {
        this.differentialRequestContextBuilder = differentialRequestContextBuilder;
        this.profilesHeatMap = profilesHeatMap;
        this.downloadURLBuilder = downloadURLBuilder;
        this.differentialProfilesViewModelBuilder = differentialProfilesViewModelBuilder;
        this.speciesKingdomTrader = speciesKingdomTrader;
        this.tracksUtil = tracksUtil;
        this.gseaPlotsBuilder = gseaPlotsBuilder;
    }

    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }

    // called from sub classes
    public String showGeneProfiles(K requestPreferences, BindingResult result, Model model, HttpServletRequest request) {

        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initRequestPreferences(model, requestPreferences, experiment);

        DifferentialRequestContext requestContext = initRequestContext(experiment, requestPreferences);

        String species = requestContext.getFilteredBySpecies();

        Set<Contrast> contrasts = experiment.getContrasts();

        model.addAllAttributes(experiment.getDifferentialAttributes());
        model.addAllAttributes(speciesKingdomTrader.getAttributesFor(species));

        model.addAttribute("enableEnsemblLauncher", tracksUtil.hasDiffTracksPath(experiment.getAccession(), contrasts.iterator().next().getId()));

        if (!result.hasErrors()) {

            try {

                DifferentialProfilesList differentialProfiles = profilesHeatMap.fetch(requestContext);
                if (!differentialProfiles.isEmpty()) {
                    model.addAttribute("gseaPlots", gson.toJson(gseaPlotsBuilder.createJsonByContrastId(experiment
                            .getAccession(), contrasts)));
                    model.addAttribute("jsonColumnHeaders", gson.toJson(contrasts));
                    model.addAttribute("jsonProfiles", gson.toJson(differentialProfilesViewModelBuilder.build
                            (differentialProfiles, contrasts)));
                }
                model.addAllAttributes(downloadURLBuilder.dataDownloadUrls(request));

            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + requestPreferences.getGeneQuery().description() + "'"));
            }

        }

        return "experiment";
    }

    private void initRequestPreferences(Model model, K requestPreferences, T experiment) {
        //if there is only one contrast we want to preselect it... from Robert feedback
        if (experiment.getContrasts().size() == 1) {
            requestPreferences.setQueryFactorValues(experiment.getContrastIds());
        }
        initExtraPageConfigurations(model, requestPreferences, experiment);

    }

    protected abstract void initExtraPageConfigurations(Model model, K requestPreferences, T experiment);

    private DifferentialRequestContext initRequestContext(T experiment, DifferentialRequestPreferences requestPreferences) {
        return differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences).build();

    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
