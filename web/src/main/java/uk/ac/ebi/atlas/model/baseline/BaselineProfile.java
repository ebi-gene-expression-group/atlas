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
import uk.ac.ebi.atlas.utils.NumberUtils;

import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class BaselineProfile extends Profile<Factor, BaselineExpression> {
    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    BaselineProfile(String id) {
        super(id);
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
        double expressionLevel = 0D;

        if (CollectionUtils.isEmpty(factors)) {
            return expressionLevel;
        }

        for (Factor condition : factors) {
            expressionLevel += getExpressionLevel(condition);
        }
        return expressionLevel / factors.size();
    }

    public BaselineProfile sumProfile(BaselineProfile otherProfile){
        for (Factor factor : otherProfile.getConditions()){
            double otherExpressionLevel = otherProfile.getExpressionLevel(factor);

            if(otherExpressionLevel != 0){
                double thisExpressionLevel = getExpressionLevel(factor);
                FactorGroup factorGroup = otherProfile.getExpression(factor).getFactorGroup();
                BaselineExpression totalExpression =
                        new BaselineExpression(thisExpressionLevel + otherExpressionLevel, factorGroup);
                add(factor.getType(), totalExpression);
            }
        }
        return this;
    }

    public BaselineProfile foldProfile(int foldFactor) {
        for (Factor factor : getConditions()){
            BaselineExpression expression = getExpression(factor);
            double foldLevel = fold(expression.getLevel(), foldFactor);
            BaselineExpression foldedExpression =
                    new BaselineExpression(foldLevel, expression.getFactorGroup());
            add(factor.getType(), foldedExpression);
        }
        return this;
    }

    private double fold(double value, int foldFactor) {
        return new NumberUtils().round(value / foldFactor);
    }

    @Override
    protected void updateProfileExpression(BaselineExpression geneExpression) {
        maxExpressionLevel = max(maxExpressionLevel, geneExpression.getLevel());
        minExpressionLevel = min(minExpressionLevel, geneExpression.getLevel());
    }

}
