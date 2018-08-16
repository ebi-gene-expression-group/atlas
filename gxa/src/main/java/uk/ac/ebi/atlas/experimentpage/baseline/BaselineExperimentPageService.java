package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.baseline.profiles.BaselineExperimentProfilesListSerializer;
import uk.ac.ebi.atlas.experimentpage.baseline.profiles.BaselineExperimentProfilesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.List;
import java.util.Map;

public class BaselineExperimentPageService extends ExperimentPageService {
    private final BaselineExperimentProfilesService BaselineExperimentProfilesService;
    private final CoexpressedGenesService coexpressedGenesService;
    private final AnatomogramFactory anatomogramFactory;

    public BaselineExperimentPageService(BaselineExperimentProfilesService BaselineExperimentProfilesService,
                                         CoexpressedGenesService coexpressedGenesService) {
        super();
        this.anatomogramFactory = new AnatomogramFactory();
        this.BaselineExperimentProfilesService = BaselineExperimentProfilesService;
        this.coexpressedGenesService = coexpressedGenesService;
    }

    public <U extends ExpressionUnit.Absolute> JsonObject getResultsForExperiment(
            BaselineExperiment experiment, String accessKey, BaselineRequestPreferences<U> preferences) {

        BaselineRequestContext<U> requestContext = new BaselineRequestContext<>(preferences, experiment);

        JsonObject result = new JsonObject();
        result.add("columnHeaders", constructColumnHeaders(requestContext, experiment));
        result.add("columnGroupings", new JsonArray());

        GeneProfilesList<BaselineProfile> baselineProfilesList = fetchProfiles(experiment, preferences);
        result.add(
                "profiles",
                BaselineExperimentProfilesListSerializer.serialize(baselineProfilesList, requestContext));

        if (baselineProfilesList.size() == 1) {
            JsonArray jsonCoexpressions =
                    getJsonCoexpressions(baselineProfilesList.get(0), experiment, requestContext, preferences);

            if (jsonCoexpressions.size() > 0) {
                result.add("coexpressions", jsonCoexpressions);
            }
        }

        result.add(
                "anatomogram",
                anatomogramFactory.get(requestContext.getDataColumnsToReturn(), experiment).orElse(JsonNull.INSTANCE));

        for (Map.Entry<String, JsonElement> e :
                payloadAttributes(experiment, accessKey, preferences, ExperimentPageService.getTheOnlyId(baselineProfilesList)).entrySet()) {
            result.add(e.getKey(), e.getValue());
        }

        return result;
    }

    private GeneProfilesList<BaselineProfile> fetchProfiles(BaselineExperiment experiment,
                                                            BaselineRequestPreferences<?> preferences) {
        GeneProfilesList<BaselineProfile> baselineProfilesList =
                BaselineExperimentProfilesService.getTopGeneProfiles(
                        experiment.getAccession(),
                        experiment.getDataColumnDescriptors(),
                        preferences);

        baselineProfilesList.setTotalResultCount(
                BaselineExperimentProfilesService.fetchCount(experiment.getAccession(), preferences));

        return baselineProfilesList;
    }

    private JsonArray constructColumnHeaders(BaselineRequestContext<?> requestContext, BaselineExperiment experiment) {
        JsonArray result = new JsonArray();

        for (AssayGroup dataColumnDescriptor : requestContext.getDataColumnsToReturn()) {
            JsonObject o = new JsonObject();
            o.addProperty("assayGroupId", dataColumnDescriptor.getId());
            o.addProperty("factorValue", requestContext.displayNameForColumn(dataColumnDescriptor));
            o.add("factorValueOntologyTermId",
                    OntologyTerm.jsonForHeaders(
                            new RichFactorGroup(experiment.getFactors(dataColumnDescriptor)).getOntologyTerms()));
            o.add("assayGroupSummary",
                    new AssayGroupSummaryBuilder()
                    .forAssayGroup(experiment.getDataColumnDescriptor(dataColumnDescriptor.getId()))
                    .withExperimentDesign(experiment.getExperimentDesign())
                    .build().toJson());
            result.add(o);
        }

        return result;
    }

    private JsonArray getJsonCoexpressions(BaselineProfile baselineProfile,
                                           BaselineExperiment experiment,
                                           BaselineRequestContext<?> requestContext,
                                           BaselineRequestPreferences<?> preferences) {
        List<String> coexpressedGeneIds =
                coexpressedGenesService.fetchCoexpressions(experiment.getAccession(), baselineProfile.getId(), 49);

        if (coexpressedGeneIds.isEmpty()) {
            return new JsonArray();
        }

        JsonObject o = new JsonObject();
        o.addProperty("geneName", baselineProfile.getName());
        o.addProperty("geneId", baselineProfile.getId());

        o.add("jsonProfiles",
              BaselineExperimentProfilesListSerializer.serialize(
                      BaselineExperimentProfilesService.getGeneProfiles(
                              experiment.getAccession(),
                              experiment.getDataColumnDescriptors(),
                              preferences,
                              coexpressedGeneIds.toArray(new String[0])),
                      requestContext));

        JsonArray result = new JsonArray();
        result.add(o);
        return result;
    }
}