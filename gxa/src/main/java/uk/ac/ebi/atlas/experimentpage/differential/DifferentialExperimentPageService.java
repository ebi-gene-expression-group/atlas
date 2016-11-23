package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uk.ac.ebi.atlas.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextBuilder;
import uk.ac.ebi.atlas.model.experiment.summary.ContrastSummaryBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

public class DifferentialExperimentPageService<E extends DifferentialExperiment, K extends
        DifferentialRequestPreferences, P extends DifferentialProfile<?>> extends ExperimentPageService {

    private final DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder;
    private final AtlasResourceHub atlasResourceHub;
    private DifferentialRequestContextBuilder differentialRequestContextBuilder;
    private DifferentialProfilesHeatMap<E, P, DifferentialRequestContext<E>> profilesHeatMap;
    private TracksUtil tracksUtil;
    private final ApplicationProperties applicationProperties;

    @SuppressWarnings("unchecked")
    protected DifferentialExperimentPageService(DifferentialRequestContextBuilder
                                                           differentialRequestContextBuilder,
                                                DifferentialProfilesHeatMap<E, P, DifferentialRequestContext<E>>
                                                        profilesHeatMap,
                                                DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                                TracksUtil tracksUtil,
                                                AtlasResourceHub atlasResourceHub, ApplicationProperties applicationProperties) {
        super(atlasResourceHub, new HeatmapDataToJsonService(applicationProperties));
        this.differentialRequestContextBuilder = differentialRequestContextBuilder;
        this.profilesHeatMap = profilesHeatMap;
        this.differentialProfilesViewModelBuilder = differentialProfilesViewModelBuilder;
        this.tracksUtil = tracksUtil;
        this.atlasResourceHub = atlasResourceHub;
        this.applicationProperties = applicationProperties;
    }


    // called from sub classes
    public void prepareRequestPreferencesAndHeaderData(E experiment, K preferences, Model model, HttpServletRequest request) {

        initRequestPreferences(preferences, experiment);
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));
        model.addAttribute("queryFactorName", "Comparison");
        model.addAttribute("allQueryFactors", experiment.getContrasts());
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(experiment.getDifferentialAttributes());
        model.addAllAttributes(new DownloadURLBuilder(experiment.getAccession()).dataDownloadUrls(request.getRequestURI()));
        model.addAllAttributes(headerAttributes(experiment, preferences));
    }

    public JsonObject populateModelWithHeatmapData(HttpServletRequest request, E experiment, K preferences,
                                                   BindingResult bindingResult, Model model) {
        JsonObject result = new JsonObject();
        DifferentialRequestContext requestContext = initRequestContext(experiment, preferences);
        Set<Contrast> contrasts = experiment.getContrasts();
        model.addAttribute("queryFactorName", "Comparison");
        model.addAttribute("geneQuery", preferences.getGeneQuery());
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(experiment.getDifferentialAttributes());

        model.addAttribute("enableEnsemblLauncher", tracksUtil.hasDiffTracksPath(experiment.getAccession(), contrasts.iterator().next().getId()));

        result.add("anatomogram", JsonNull.INSTANCE);
        model.addAttribute("experimentDescription", gson.toJson(JsonNull.INSTANCE));
        for(Map.Entry<String, JsonElement> e: payloadAttributes(experiment, preferences).entrySet()){
            result.add(e.getKey(), e.getValue());
        }
        if (!bindingResult.hasErrors()) {

            try {

                DifferentialProfilesList differentialProfiles = profilesHeatMap.fetch(requestContext);
                if (!differentialProfiles.isEmpty()) {
                    model.addAttribute("gseaPlots", gson.toJson(atlasResourceHub.createJsonByContrastIdForTheOldHeatmap(experiment
                            .getAccession(), contrasts)));
                    result.add("columnGroupings", new JsonArray());
                    result.add("columnHeaders", constructColumnHeaders(contrasts,experiment));
                    result.add("profiles",differentialProfilesViewModelBuilder.build
                            (differentialProfiles, contrasts));
                    result.add("geneSetProfiles", JsonNull.INSTANCE);
                    result.add("jsonCoexpressions", new JsonArray());
                    result.add("config",heatmapDataToJsonService.configAsJsonObject(request,preferences.getGeneQuery(), SemanticQuery.create
                            (), model.asMap()));
                    return result;
                } else {
                    //copypasted:(
                    String msg = "No genes found matching query: '" + preferences.getGeneQuery().description() + "'";
                    bindingResult.addError(new ObjectError("requestPreferences",msg ));
                    return heatmapDataToJsonService.jsonError(msg);
                }
            } catch (GenesNotFoundException e) {
                String msg = "No genes found matching query: '" + preferences.getGeneQuery().description() + "'";
                bindingResult.addError(new ObjectError("requestPreferences",msg ));//I'm not sure if this works- on error
                // Spring MVC magic kicks in?
                return heatmapDataToJsonService.jsonError(msg);
            }

        } else {
            return heatmapDataToJsonService.jsonError(FluentIterable.from(bindingResult.getAllErrors()).transform(new Function<ObjectError, String>() {
                @Nullable
                @Override
                public String apply(@Nullable ObjectError objectError) {
                    return objectError.toString();
                }
            }).join(Joiner.on(',')));
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

    private void initRequestPreferences(K requestPreferences, E experiment) {
        //if there is only one contrast we want to preselect it... from Robert feedback
        if (experiment.getContrasts().size() == 1) {
            requestPreferences.setQueryFactorValues(experiment.getContrastIds());
        }
    }

    private DifferentialRequestContext initRequestContext(E experiment, DifferentialRequestPreferences requestPreferences) {
        return differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences).build();

    }

}
