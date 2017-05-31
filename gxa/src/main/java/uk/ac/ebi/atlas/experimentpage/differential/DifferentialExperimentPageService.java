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
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.summary.ContrastSummaryBuilder;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class DifferentialExperimentPageService
        <Expr extends DifferentialExpression, E extends DifferentialExperiment,
         K extends DifferentialRequestPreferences,
         P extends DifferentialProfile<Expr, P>,  R extends DifferentialRequestContext<E, K> >
        extends ExperimentPageService {

    private final AtlasResourceHub atlasResourceHub;
    private final DifferentialRequestContextFactory<E, K, R> differentialRequestContextFactory;
    private final DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap;
    private final TracksUtil tracksUtil;

    public  DifferentialExperimentPageService(
            DifferentialRequestContextFactory<E, K, R> differentialRequestContextFactory,
            DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap,
            TracksUtil tracksUtil, AtlasResourceHub atlasResourceHub, ApplicationProperties applicationProperties) {

        super(new HeatmapDataToJsonService(), applicationProperties);
        this.differentialRequestContextFactory = differentialRequestContextFactory;
        this.profilesHeatMap = profilesHeatMap;
        this.tracksUtil = tracksUtil;
        this.atlasResourceHub = atlasResourceHub;

    }

    public JsonObject populateModelWithHeatmapData(HttpServletRequest request, E experiment, K preferences,
                                                   BindingResult bindingResult, Model model) {
        final String serverURL = ApplicationProperties.buildServerURL(request);
        Function<P, URI> linkToGenes = new Function<P, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable P differentialProfile) {
                try {
                    return new URI(serverURL+"/genes/"+differentialProfile.getId());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        JsonObject result = new JsonObject();
        R requestContext = differentialRequestContextFactory.create(experiment, preferences);
        List<Contrast> contrasts = requestContext.getDataColumnsToReturn();
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
                    result.add("profiles", new ExternallyViewableProfilesList<>(
                            differentialProfiles, linkToGenes, requestContext.getDataColumnsToReturn()
                    ).asJson());

                    model.addAttribute("downloadProfilesURL", downloadURL(preferences.getGeneQuery(), request));

                    JsonObject heatmapConfig = heatmapDataToJsonService.configAsJsonObject(model.asMap());

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

}
