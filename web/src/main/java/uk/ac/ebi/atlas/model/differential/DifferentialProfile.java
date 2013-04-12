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
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.EnumMap;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfile<T extends DifferentialExpression> extends GeneProfile<Contrast, T> {


    private double maxUpRegulatedExpressionLevel = 0D;
    private double minUpRegulatedExpressionLevel = Double.MAX_VALUE;
    private double maxDownRegulatedExpressionLevel = 0D;
    private double minDownRegulatedExpressionLevel = Double.MAX_VALUE;

    private EnumMap<Regulation, Integer> specificityForRegulation = new EnumMap(Regulation.class);

    public DifferentialProfile(String geneId) {
        super(geneId);
    }

    DifferentialProfile add(T expression) {
        this.addExpression(expression.getContrast(), expression);
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
        Integer specificity = specificityForRegulation.get(regulation);
        return specificity == null ? 0 : specificity;
    }

    public double getAverageExpressionLevelOn(Set<Contrast> conditions, Regulation regulation) {
        double expressionLevel = 0D;

        if (CollectionUtils.isEmpty(conditions)) {
            return expressionLevel;
        }

        for (Contrast condition : conditions) {
            DifferentialExpression expression = getExpression(condition);
            if (expression != null && expression.isRegulatedLike(regulation)) {
                expressionLevel += expression.getLevel();
            }
        }
        return expressionLevel / conditions.size();
    }


    @Override
    protected void updateProfileExpression(DifferentialExpression differentialExpression) {
        if (differentialExpression.isOverExpressed()) {
            updateUpRegulatedProfileExpression(differentialExpression.getLevel());
            updateSpecificityForRegulation(Regulation.UP);
        }

        if (differentialExpression.isUnderExpressed()) {
            updateDownRegulatedProfileExpression(differentialExpression.getLevel());
            updateSpecificityForRegulation(Regulation.DOWN);
        }
        updateSpecificityForRegulation(Regulation.UP_DOWN);
    }

    private void updateSpecificityForRegulation(Regulation regulation) {
        Integer specificity = specificityForRegulation.get(regulation);
        if (specificity == null) {
            specificity = 0;
        }
        specificityForRegulation.put(regulation, specificity + 1);
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

}
