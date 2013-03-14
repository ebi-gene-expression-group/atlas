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


import com.google.common.base.Predicate;

import javax.inject.Named;

@Named
public class DifferentialExpressionPrecondition implements Predicate<DifferentialExpression> {

    private Regulation regulation;
    private double cutoff;

    @Override
    public boolean apply(DifferentialExpression differentialExpression) {
        double expressionLevel = differentialExpression.getLevel();
        double foldChange = differentialExpression.getFoldChange();

        if (Regulation.UP == regulation){
            return isOverexpressed(expressionLevel, foldChange);
        }
        if (Regulation.DOWN == regulation){
            return isUnderExpressed(expressionLevel, foldChange);
        }
        return isUnderExpressed(expressionLevel, foldChange) || isOverexpressed(expressionLevel, foldChange);
    }

    private boolean isOverexpressed(double level, double foldChange) {
        return level <= cutoff && foldChange > 0;
    }

    private boolean isUnderExpressed(double level, double foldChange){
        return level <= cutoff && foldChange < 0;
    }


    public DifferentialExpressionPrecondition setCutoff(double cutoff){
        this.cutoff = cutoff;
        return this;
    }

    public DifferentialExpressionPrecondition setRegulation(Regulation regulation){
        this.regulation = regulation;
        return this;
    }

}
