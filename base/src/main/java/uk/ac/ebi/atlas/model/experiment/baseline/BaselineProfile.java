package uk.ac.ebi.atlas.model.experiment.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

public class BaselineProfile extends Profile<AssayGroup, BaselineExpression, BaselineProfile> {

    private BaselineProfile(){}

    public BaselineProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    @Override
    protected BaselineProfile createEmptyCopy() {
        return new BaselineProfile(getId(), getName());
    }

    // add the expression levels of another profile to this one
    public BaselineProfile sumProfile(BaselineProfile otherProfile) {
        for (AssayGroup assayGroup : otherProfile.getConditions()) {
            BaselineExpression otherExpression = otherProfile.getExpression(assayGroup);
            BaselineExpression thisExpression = getExpression(assayGroup);

            if (thisExpression == null) {
                add(assayGroup, otherExpression);
            } else {
                add(assayGroup, new BaselineExpression(thisExpression.getLevel() + otherExpression.getLevel()));
            }
        }
        return this;
    }

    // divide all expression levels by foldFactor
    public BaselineProfile foldProfile(int foldFactor) {
        for (AssayGroup assayGroup : getConditions()) {
            BaselineExpression expression = getExpression(assayGroup);
            double foldLevel = fold(expression.getLevel(), foldFactor);
            BaselineExpression foldedExpression =
                    new BaselineExpression(foldLevel, expression.getDataColumnDescriptorId());
            add(assayGroup, foldedExpression);
        }
        return this;
    }

    private static double fold(double value, int foldFactor) {
        return BaselineExpressionLevelRounder.round(value / foldFactor);
    }

}
