package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.LinkToGene;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.summary.ContrastSummaryBuilder;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

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

    public  DifferentialExperimentPageService(
            DifferentialRequestContextFactory<E, K, R> differentialRequestContextFactory,
            DifferentialProfilesHeatMap<Expr, E, P, R> profilesHeatMap,
            AtlasResourceHub atlasResourceHub) {
        this.differentialRequestContextFactory = differentialRequestContextFactory;
        this.profilesHeatMap = profilesHeatMap;
        this.atlasResourceHub = atlasResourceHub;

    }

    public JsonObject getResultsForExperiment(E experiment,String accessKey, K preferences) {

        JsonObject result = new JsonObject();
        R requestContext = differentialRequestContextFactory.create(experiment, preferences);
        List<Contrast> contrasts = requestContext.getDataColumnsToReturn();

        result.add("anatomogram", JsonNull.INSTANCE);
        for(Map.Entry<String, JsonElement> e: payloadAttributes(experiment, accessKey, preferences).entrySet()){
            result.add(e.getKey(), e.getValue());
        }

        try {

            DifferentialProfilesList<P> differentialProfiles = profilesHeatMap.fetch(requestContext);
            if (!differentialProfiles.isEmpty()) {
                result.add("columnGroupings", new JsonArray());
                result.add("columnHeaders", constructColumnHeaders(contrasts,experiment));
                result.add("profiles", new ExternallyViewableProfilesList<>(
                        differentialProfiles, new LinkToGene<>(), requestContext.getDataColumnsToReturn(),
                        p -> ExpressionUnit.Relative.FOLD_CHANGE).asJson());

                return result;
            } else {
                return noMatchError(preferences);
            }
        } catch (GenesNotFoundException e) {
            return noMatchError(preferences);
        }
    }

    JsonObject noMatchError(K preferences){
        return jsonError("No genes found matching query: '" + preferences.getGeneQuery().description() + "'");
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
