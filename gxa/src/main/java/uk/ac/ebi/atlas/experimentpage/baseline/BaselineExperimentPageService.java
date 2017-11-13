package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaselineExperimentPageService extends ExperimentPageService {

    private final BaselineProfilesHeatmapsWranglerFactory baselineProfilesHeatmapWranglerFactory;
    private final AnatomogramFactory anatomogramFactory;

    public BaselineExperimentPageService(BaselineProfilesHeatmapsWranglerFactory baselineProfilesHeatmapWranglerFactory) {
        super();
        this.anatomogramFactory = new AnatomogramFactory();
        this.baselineProfilesHeatmapWranglerFactory = baselineProfilesHeatmapWranglerFactory;
    }

    public <Unit extends ExpressionUnit.Absolute> JsonObject getResultsForExperiment(
            BaselineExperiment experiment,
            String accessKey,
            BaselineRequestPreferences<Unit> preferences) {

        JsonObject result = new JsonObject();

        BaselineRequestContext<Unit> requestContext = new BaselineRequestContext<>(preferences, experiment);
        List<AssayGroup> dataColumnsToReturn = requestContext.getDataColumnsToReturn();

        BaselineProfilesHeatmapsWrangler<Unit> heatmapResults =
                baselineProfilesHeatmapWranglerFactory.create(preferences, experiment);

        result.add("columnHeaders", constructColumnHeaders(dataColumnsToReturn, requestContext, experiment));

        result.add("columnGroupings", new JsonArray());

        result.add("profiles", heatmapResults.getJsonProfiles());

        JsonArray jsonCoexpressions = heatmapResults.getJsonCoexpressions();
        if (jsonCoexpressions.size() > 0) {
            result.add("coexpressions", jsonCoexpressions);
        }

        result.add(
                "anatomogram",
                anatomogramFactory.get(requestContext.getDataColumnsToReturn(), experiment)
                        .orElse(JsonNull.INSTANCE));

        for (Map.Entry<String, JsonElement> e: payloadAttributes(experiment, accessKey, preferences, heatmapResults.getTheOnlyId()).entrySet()) {
            result.add(e.getKey(), e.getValue());
        }

        return result;
    }

    private JsonArray constructColumnHeaders(List<AssayGroup> dataColumnsToReturn, BaselineRequestContext
            baselineRequestContext,
            BaselineExperiment
            experiment){
        JsonArray result = new JsonArray();

        for(AssayGroup dataColumnDescriptor: dataColumnsToReturn){
            JsonObject o = new JsonObject();
            o.addProperty("assayGroupId", dataColumnDescriptor.getId());
            o.addProperty("factorValue", baselineRequestContext.displayNameForColumn(dataColumnDescriptor));
            o.add("factorValueOntologyTermId",
                    OntologyTerm.jsonForHeaders(new RichFactorGroup(experiment.getFactors(dataColumnDescriptor)).getOntologyTerms()));
            o.add("assayGroupSummary",
                    new AssayGroupSummaryBuilder()
                    .forAssayGroup(experiment.getDataColumnDescriptor(dataColumnDescriptor.getId()))
                    .withExperimentDesign(experiment.getExperimentDesign())
                    .build().toJson());
            result.add(o);
        }


        return result;}


}