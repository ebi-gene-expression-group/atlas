package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentpage.LinkToGene;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;

public class BaselineExperimentProfilesListSerializer {
    public static JsonObject serialize(GeneProfilesList<? extends Profile<AssayGroup, ?, ?>> profilesList,
                                       BaselineRequestContext<? extends ExpressionUnit.Absolute> requestContext) {
        return new
                ExternallyViewableProfilesList<>(
                        profilesList,
                        new LinkToGene<>(),
                        requestContext.getDataColumnsToReturn(),
                        baselineProfile -> requestContext.getExpressionUnit())
                .asJson();
    }
}
