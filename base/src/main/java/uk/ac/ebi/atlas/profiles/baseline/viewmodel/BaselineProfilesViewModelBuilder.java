package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class BaselineProfilesViewModelBuilder {

    private final BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder;

    @Inject
    public BaselineProfilesViewModelBuilder(BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder) {
        this.baselineExpressionViewModelBuilder = baselineExpressionViewModelBuilder;
    }

    public JsonObject build(BaselineProfilesList profiles, List<Factor> orderedFactors) {
        JsonObject result = new JsonObject();
        result.addProperty("searchResultTotal", profiles.getTotalResultCount());
        result.add("rows", buildGenes(profiles, orderedFactors, profiles.getMinExpressionLevel(), profiles
                .getMaxExpressionLevel()));
        return result;
    }

    JsonArray buildGenes(List<BaselineProfile> baselineProfiles, List<Factor>
            orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        JsonArray result = new JsonArray();
        for (BaselineProfile baselineProfile : baselineProfiles) {
            result.add(build(baselineProfile, orderedFactors,
                    minExpressionLevel, maxExpressionLevel));
        }
        return result;
    }

    private JsonObject build(Profile<Factor, BaselineExpression> profile, List<Factor>
            orderedFactors, double minExpressionLevel, double maxExpressionLevel) {
        JsonObject result = new JsonObject();
        result.addProperty("id", profile.getId());
        result.addProperty("name",profile.getName());
        result.add("expressions", baselineExpressionViewModelBuilder.buildExpressions(profile, orderedFactors,
                minExpressionLevel, maxExpressionLevel));
        return result;
    }

}
