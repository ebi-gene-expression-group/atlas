package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BaselineProfile extends Profile<AssayGroup, BaselineExpression> {
    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    public BaselineProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    public Set<AssayGroup> getAssayGroupsWithExpressionLevelAtLeast(double threshold){
        Set<AssayGroup> result = new HashSet<>();
        for(AssayGroup condition : expressionsByCondition.keySet()){
            Double level = getExpressionLevel(condition);
            if (level != null && level >= threshold) {
                result.add(condition);
            }
        }
        return result;
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
        resetMaxMin();
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

    private void resetMaxMin() {
        maxExpressionLevel = 0;
        minExpressionLevel = Double.MAX_VALUE;
    }
    @Override
    protected void updateStateAfterAddingExpression(BaselineExpression geneExpression) {
        maxExpressionLevel = max(maxExpressionLevel, geneExpression.getLevel());
        minExpressionLevel = min(minExpressionLevel, geneExpression.getLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaselineProfile)) return false;
        BaselineProfile that = (BaselineProfile) o;
        return super.equals(that) &&
                Double.compare(that.getMaxExpressionLevel(), getMaxExpressionLevel()) == 0 &&
                Double.compare(that.getMinExpressionLevel(), getMinExpressionLevel()) == 0 ;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMaxExpressionLevel(), getMinExpressionLevel(), super.hashCode());
    }
}
