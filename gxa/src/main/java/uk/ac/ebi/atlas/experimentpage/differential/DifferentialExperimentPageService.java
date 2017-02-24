package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
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
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.summary.ContrastSummaryBuilder;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class DifferentialExperimentPageService
        <Expr extends DifferentialExpression, E extends DifferentialExperiment,
         K extends DifferentialRequestPreferences,
         P extends DifferentialProfile<Expr>,  R extends DifferentialRequestContext<E> >
        extends ExperimentPageService {

    private final DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder;
    private final AtlasResourceHub atlasResourceHub;
    private final DifferentialRequestContextBuilder<E, R, K> differentialRequestContextBuilder;
    private final DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap;
    private final TracksUtil tracksUtil;

    public  DifferentialExperimentPageService(
            DifferentialRequestContextBuilder<E, R, K> differentialRequestContextBuilder,
            DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap,
            DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
            TracksUtil tracksUtil, AtlasResourceHub atlasResourceHub, ApplicationProperties applicationProperties) {

        super(atlasResourceHub, new HeatmapDataToJsonService(applicationProperties), applicationProperties);
        this.differentialRequestContextBuilder = differentialRequestContextBuilder;
        this.profilesHeatMap = profilesHeatMap;
        this.differentialProfilesViewModelBuilder = differentialProfilesViewModelBuilder;
        this.tracksUtil = tracksUtil;
        this.atlasResourceHub = atlasResourceHub;

    }

    // called from sub classes
    public void prepareRequestPreferencesAndHeaderData(E experiment, K preferences, Model model, HttpServletRequest request) {

        initRequestPreferences(preferences, experiment);
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));
        model.addAttribute("queryFactorName", "Comparison");
        model.addAttribute("allQueryFactors", experiment.getDataColumnDescriptors());
        model.addAllAttributes(experiment.getAttributes());
        model.addAllAttributes(experiment.getDifferentialAttributes());
        model.addAllAttributes(new DownloadURLBuilder(experiment.getAccession()).dataDownloadUrls(request.getRequestURI()));
        model.addAllAttributes(headerAttributes(experiment));
    }

    public JsonObject populateModelWithHeatmapData(HttpServletRequest request, E experiment, K preferences,
                                                   BindingResult bindingResult, Model model) {
        JsonObject result = new JsonObject();
        R requestContext = initRequestContext(experiment, preferences);
        List<Contrast> contrasts = experiment.getDataColumnDescriptors();
        model.addAttribute("queryFactorName", "Comparison");
        model.addAttribute("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
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

                DifferentialProfilesList<P> differentialProfiles = profilesHeatMap.fetch(requestContext);
                if (!differentialProfiles.isEmpty()) {
                    result.add("columnGroupings", new JsonArray());
                    result.add("columnHeaders", constructColumnHeaders(contrasts,experiment));
                    result.add("profiles",differentialProfilesViewModelBuilder.build
                            (differentialProfiles, contrasts));

                    //TODO remove me after old heatmap goes away, the new heatmap handles no data gracefully
                    if(differentialProfilesViewModelBuilder.build(differentialProfiles, contrasts)
                            .get("rows").getAsJsonArray().size() == 0 ){
                        return heatmapDataToJsonService.jsonError("No genes found matching query: '" + preferences.getGeneQuery() + "'");
                    }
                    result.add("geneSetProfiles", JsonNull.INSTANCE);
                    result.add("jsonCoexpressions", new JsonArray());
                    model.addAttribute("downloadProfilesURL", downloadURL(preferences.getGeneQuery(), request));

                    JsonObject heatmapConfig = heatmapDataToJsonService.configAsJsonObject(request, model.asMap());

                    result.add("config", heatmapConfig);
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
                public String apply(ObjectError objectError) {
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
        if (experiment.getDataColumnDescriptors().size() == 1) {
            requestPreferences.setQueryFactorValues(ImmutableSet.of(
                    experiment.getDataColumnDescriptors().iterator().next().getId()));
        }
    }

    private R initRequestContext(E experiment, K requestPreferences) {
        return differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences).build();

    }

}
