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
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BaselineProfile extends GeneProfile<Factor, BaselineExpression> {
    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    public BaselineProfile(String geneId) {
        super(geneId);
    }

    BaselineProfile add(BaselineExpression expression, String queryFactorType) {

        this.addExpression(expression.getFactor(queryFactorType), expression);
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

    @Override
    protected void updateProfileExpression(BaselineExpression geneExpression) {
        maxExpressionLevel = max(maxExpressionLevel, geneExpression.getLevel());
        minExpressionLevel = min(minExpressionLevel, geneExpression.getLevel());
    }

}
