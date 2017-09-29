package uk.ac.ebi.atlas.model.experiment.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Profile;

public class BaselineExpressionPerReplicateProfile extends Profile<AssayGroup, BaselineExpressionPerBiologicalReplicate, BaselineExpressionPerReplicateProfile> {


    private BaselineExpressionPerReplicateProfile() {
    }

    public BaselineExpressionPerReplicateProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    @Override
    protected BaselineExpressionPerReplicateProfile createEmptyCopy() {
        return new BaselineExpressionPerReplicateProfile(getId(), getName());
    }
}
