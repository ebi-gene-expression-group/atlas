package uk.ac.ebi.atlas.model.experiment.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Profile;

public class BaselineProfile extends Profile<AssayGroup, BaselineExpression, BaselineProfile> {

    private BaselineProfile(){}

    public BaselineProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    @Override
    protected BaselineProfile createEmptyCopy() {
        return new BaselineProfile(getId(), getName());
    }

}
