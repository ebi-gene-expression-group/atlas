/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.model.baseline;

import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

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

}
