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

package uk.ac.ebi.atlas.model.differential;


import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfile<T extends DifferentialExpression> extends Profile<Contrast, T> implements DifferentialExpressionLimits{

    private static final double MIN_EXPRESSION_LEVEL = 1D;
    private static final int MAX_PVALUE = 1;
    private double maxUpRegulatedExpressionLevel = 0D;
    private double minUpRegulatedExpressionLevel = Double.MAX_VALUE;
    private double maxDownRegulatedExpressionLevel = 0D;
    private double minDownRegulatedExpressionLevel = Double.MAX_VALUE;

    private int downRegulatedExpressionsCount;
    private int upRegulatedExpressionsCount;


    public DifferentialProfile(String geneId) {
        super(geneId);
    }

    DifferentialProfile add(T expression) {
        addExpression(expression.getContrast(), expression);
        return this;
    }

    public double getMaxUpRegulatedExpressionLevel() {
        return maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel() {
        return minUpRegulatedExpressionLevel;
    }

    public double getMaxDownRegulatedExpressionLevel() {
        return maxDownRegulatedExpressionLevel;
    }

    public double getMinDownRegulatedExpressionLevel() {
        return minDownRegulatedExpressionLevel;
    }

    public int getSpecificity(Regulation regulation) {
        if(Regulation.UP == regulation){
            return upRegulatedExpressionsCount;
        }
        if(Regulation.DOWN == regulation){
            return downRegulatedExpressionsCount;
        }
        return getSpecificity();
    }

    public double getAverageExpressionLevelOn(Set<Contrast> conditions, Regulation regulation) {
        checkArgument(!CollectionUtils.isEmpty(conditions),
                "This method must be invoked with all conditions when the set of selected conditions is empty");

        double expressionLevel = 0D;

        for (Contrast condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel += level;
            } else {
                expressionLevel += MIN_EXPRESSION_LEVEL;
            }

        }

        return expressionLevel / conditions.size();
    }

    @Override
    protected void updateProfileExpression(DifferentialExpression differentialExpression) {
        if (differentialExpression.isOverExpressed()) {
            updateUpRegulatedProfileExpression(differentialExpression.getLevel());
            upRegulatedExpressionsCount++;
        } else if (differentialExpression.isUnderExpressed()) {
            updateDownRegulatedProfileExpression(differentialExpression.getLevel());
            downRegulatedExpressionsCount++;
        }
    }

    void updateUpRegulatedProfileExpression(double expressionLevel) {
        maxUpRegulatedExpressionLevel = max(maxUpRegulatedExpressionLevel, expressionLevel);
        minUpRegulatedExpressionLevel = min(minUpRegulatedExpressionLevel, expressionLevel);
    }

    void updateDownRegulatedProfileExpression(double expressionLevel) {
        maxDownRegulatedExpressionLevel = max(maxDownRegulatedExpressionLevel, expressionLevel);
        minDownRegulatedExpressionLevel = min(minDownRegulatedExpressionLevel, expressionLevel);
    }

    public double getMinExpressionLevel() {
        return min(minUpRegulatedExpressionLevel, minDownRegulatedExpressionLevel);
    }

    public double getMinExpressionLevelOn(Set<Contrast> queryContrasts, Regulation regulation) {
        //checkArgument(CollectionUtils.isNotEmpty(queryContrasts));
        if(queryContrasts.isEmpty()){
            return MAX_PVALUE;
        }

        double expressionLevel = MAX_PVALUE;

        for (Contrast condition : queryContrasts) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel = min(expressionLevel, level);
            }
        }
        return expressionLevel;
    }
}
