package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GenericBaselineProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;

@Named
@Scope("prototype")
public class BaselineExperimentProfilesViewModelBuilder {

    private final BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder;

    @Inject
    public BaselineExperimentProfilesViewModelBuilder(BaselineExpressionViewModelBuilder
                                                                  baselineExpressionViewModelBuilder) {
        this.baselineExpressionViewModelBuilder = baselineExpressionViewModelBuilder;
    }

    public JsonElement buildJson(GenericBaselineProfilesList<BaselineExperimentProfile> profiles, SortedSet<Factor>
            orderedFactors) {
        JsonObject result = new JsonObject();
        result.addProperty("searchResultTotal", profiles.getTotalResultCount());
        result.add("rows", buildExperimentsJson(profiles, orderedFactors, profiles.getMinExpressionLevel(), profiles
                .getMaxExpressionLevel()));
        return result;
    }

    private JsonArray buildExperimentsJson(List<BaselineExperimentProfile> baselineProfiles,
                                           SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        JsonArray result = new JsonArray();
        for (BaselineExperimentProfile baselineProfile : baselineProfiles) {
            result.add(buildExperimentJson(baselineProfile, orderedFactors, minExpressionLevel, maxExpressionLevel));
        }
        return result;
    }

    private JsonElement buildExperimentJson(BaselineExperimentProfile profile,
                                            SortedSet<Factor> orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        JsonObject result = new JsonObject();
        result.addProperty("id", profile.getId());
        result.addProperty("name",profile.getShortName());
        result.addProperty("experimentType", profile.getExperimentType());
        result.add(
                "expressions", baselineExpressionViewModelBuilder.buildExpressions(profile, orderedFactors,
                minExpressionLevel, maxExpressionLevel)
        );
        result.addProperty("serializedFilterFactors", FilterFactorsConverter.serialize(profile.getFilterFactors()));
        return result;
    }


}
