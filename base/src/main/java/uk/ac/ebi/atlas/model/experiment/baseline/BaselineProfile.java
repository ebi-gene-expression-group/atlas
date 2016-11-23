package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Objects;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class BaselineProfile extends Profile<Factor, BaselineExpression> {
    private static final double MIN_LEVEL = 0D;
    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    public BaselineProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    public BaselineProfile add(String queryFactorType, BaselineExpression expression) {

        addExpression(expression.getFactor(queryFactorType), expression);
        return this;
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    public double getAverageExpressionLevelOn(Set<Factor> factors) {
        checkArgument(!CollectionUtils.isEmpty(factors), "This method must be invoked with a non empty set of conditions");

        double expressionLevel = 0D;

        for (Factor condition : factors) {
            if(isKnownLevel(condition)){
                Double level = getKnownExpressionLevel(condition);
                if (level != null) {
                    expressionLevel += level;
                }
            }
        }
        return expressionLevel / factors.size();
    }

    public double getMaxExpressionLevelOn(Set<Factor> factors) {
        checkArgument(!CollectionUtils.isEmpty(factors), "factors set is supposed to be not empty");

        double expressionLevel = MIN_LEVEL;

        for (Factor condition : factors) {
            Double level = getKnownExpressionLevel(condition);
            if (level != null) {
                expressionLevel = max(expressionLevel, level);
            }
        }
        return expressionLevel;
    }

    public Set<Factor> getFactorsWithExpressionLevelAtLeast(double threshold){
        Set<Factor> result = new HashSet<>();
        for(Factor condition : expressionsByCondition.keySet()){
            Double level = getKnownExpressionLevel(condition);
            if (level != null && level >= threshold) {
                result.add(condition);
            }
        }
        return result;
    }


    // add the expression levels of another profile to this one
    public BaselineProfile sumProfile(BaselineProfile otherProfile) {
        for (Factor factor : otherProfile.getConditions()) {
            BaselineExpression otherExpression = otherProfile.getExpression(factor);
            BaselineExpression thisExpression = getExpression(factor);

            if (thisExpression == null) {
                add(factor.getType(), otherExpression);
            } else if (thisExpression.isKnown()) {
                FactorGroup otherFactorGroup = otherExpression.getFactorGroup();
                FactorGroup thisFactorGroup = thisExpression.getFactorGroup();

                checkArgument(thisFactorGroup.equals(otherFactorGroup), "%s != %s", thisFactorGroup, otherFactorGroup);

                BaselineExpression totalExpression = otherExpression.isKnown() ?
                        new BaselineExpression(thisExpression.getLevel() + otherExpression.getLevel(), thisFactorGroup)
                        : new BaselineExpression(otherExpression.getLevelAsString(), thisFactorGroup);

                add(factor.getType(), totalExpression);
            }
        }
        return this;
    }

    // divide all expression levels by foldFactor
    public BaselineProfile foldProfile(int foldFactor) {
        resetMaxMin();
        for (Factor factor : getConditions()) {
            BaselineExpression expression = getExpression(factor);
            if (expression.isKnown()) {
                double foldLevel = fold(expression.getLevel(), foldFactor);
                BaselineExpression foldedExpression =
                        new BaselineExpression(foldLevel, expression.getFactorGroup());
                add(factor.getType(), foldedExpression);
            }
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
    protected void addExpression(BaselineExpression geneExpression) {
        if (geneExpression.isKnown()) {
            maxExpressionLevel = max(maxExpressionLevel, geneExpression.getLevel());
            minExpressionLevel = min(minExpressionLevel, geneExpression.getLevel());
        }
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
