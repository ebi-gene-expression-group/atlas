package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.tooltip.ContrastSummaryBuilder;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

public class DifferentialExperimentPageService<T extends DifferentialExperiment, K extends
        DifferentialRequestPreferences, P extends DifferentialProfile<?>> extends ExperimentPageService {

    private final DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder;
    private final AtlasResourceHub atlasResourceHub;
    private DifferentialRequestContextBuilder differentialRequestContextBuilder;
    private DifferentialProfilesHeatMap<P, DifferentialRequestContext<?>> profilesHeatMap;
    private TracksUtil tracksUtil;
    private final ApplicationProperties applicationProperties;

    private Gson gson = new GsonBuilder()
            .create();

    @SuppressWarnings("unchecked")
    protected DifferentialExperimentPageService(DifferentialRequestContextBuilder
                                                           differentialRequestContextBuilder,
                                                DifferentialProfilesHeatMap<P, DifferentialRequestContext<?>> profilesHeatMap,
                                                DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                                TracksUtil tracksUtil,
                                                AtlasResourceHub atlasResourceHub, ApplicationProperties applicationProperties) {

        this.differentialRequestContextBuilder = differentialRequestContextBuilder;
        this.profilesHeatMap = profilesHeatMap;
        this.differentialProfilesViewModelBuilder = differentialProfilesViewModelBuilder;
        this.tracksUtil = tracksUtil;
        this.atlasResourceHub = atlasResourceHub;
        this.applicationProperties = applicationProperties;
    }


    // called from sub classes
    public void prepareRequestPreferencesAndHeaderData(T experiment, K requestPreferences, Model model,HttpServletRequest request) {

        initRequestPreferences(requestPreferences, experiment);
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));
        model.addAttribute("queryFactorName", "Comparison");
        model.addAttribute("allQueryFactors", experiment.getContrasts());
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(experiment.getDifferentialAttributes());
        model.addAllAttributes(new DownloadURLBuilder(experiment.getAccession()).dataDownloadUrls(request.getRequestURI()));
    }

    public void populateModelWithHeatmapData(T experiment, K requestPreferences, BindingResult result, Model model) {
        DifferentialRequestContext requestContext = initRequestContext(experiment, requestPreferences);
        Set<Contrast> contrasts = experiment.getContrasts();
        model.addAttribute("queryFactorName", "Comparison");
        model.addAttribute("geneQuery", requestPreferences.getGeneQuery());
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(experiment.getDifferentialAttributes());

        model.addAttribute("enableEnsemblLauncher", tracksUtil.hasDiffTracksPath(experiment.getAccession(), contrasts.iterator().next().getId()));

        model.addAttribute("anatomogram", gson.toJson(JsonNull.INSTANCE));
        model.addAttribute("experimentDescription", gson.toJson(JsonNull.INSTANCE));
        model.addAttribute("jsonExperiment", gson.toJson(prepareExperimentDescription(experiment, requestPreferences)));
        if (!result.hasErrors()) {

            try {

                DifferentialProfilesList differentialProfiles = profilesHeatMap.fetch(requestContext);
                if (!differentialProfiles.isEmpty()) {
                    model.addAttribute("gseaPlots", gson.toJson(atlasResourceHub.createJsonByContrastIdForTheOldHeatmap(experiment
                            .getAccession(), contrasts)));
                    model.addAttribute("jsonColumnHeaders", gson.toJson(constructColumnHeaders(contrasts,experiment)));
                    model.addAttribute("jsonProfiles", gson.toJson(differentialProfilesViewModelBuilder.build
                            (differentialProfiles, contrasts)));
                }

            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + requestPreferences.getGeneQuery().asAnalyticsIndexQueryClause() + "'"));
            }

        }
    }

    private JsonArray constructColumnHeaders(Iterable<Contrast> contrasts, DifferentialExperiment
            differentialExperiment){
        JsonArray result = new JsonArray();
        Map<String, JsonArray> contrastImages = atlasResourceHub.contrastImages(differentialExperiment);
        for(Contrast contrast: contrasts){
            JsonObject o = contrast.toJson();
            o.add("contrastSummary", new ContrastSummaryBuilder()
                    .forContrast(contrast)
                    .withExperimentDesign(differentialExperiment.getExperimentDesign())
                    .withExperimentDescription(differentialExperiment.getDescription())
                    .build().toJson());
            o.add("resources", contrastImages.get(contrast.getId()));
            result.add(o);
        }
        return result;
    }

    private void initRequestPreferences(K requestPreferences, T experiment) {
        //if there is only one contrast we want to preselect it... from Robert feedback
        if (experiment.getContrasts().size() == 1) {
            requestPreferences.setQueryFactorValues(experiment.getContrastIds());
        }
    }

    private DifferentialRequestContext initRequestContext(T experiment, DifferentialRequestPreferences requestPreferences) {
        return differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences).build();

    }

}
