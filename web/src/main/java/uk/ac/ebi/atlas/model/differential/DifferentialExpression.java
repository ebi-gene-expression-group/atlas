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

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.Expression;

public class DifferentialExpression implements Expression {

    private double pValue;

    private double foldChange;

    private Contrast contrast;

    public DifferentialExpression(double pValue, double foldChange, Contrast contrast) {
        this.pValue = pValue;
        this.foldChange = foldChange;
        this.contrast = contrast;
    }

    private double getPValue() {
        return pValue;
    }

    public double getFoldChange() {
        return foldChange;
    }

    //ToDo: Try to replace with INFINITE_POSITIVE and NEGATIVE
    public boolean isNotApplicable() {
        return (getFoldChange() == Double.MAX_VALUE) || (getFoldChange() == Double.MIN_VALUE);
    }

    public boolean isRegulatedLike(Regulation regulation) {
        return Regulation.UP_DOWN.equals(regulation)
                || isLikeUpRegulation(regulation)
                || isLikeDownRegulation(regulation);
    }

    private boolean isLikeUpRegulation(Regulation regulation) {
        return Regulation.UP.equals(regulation) && isOverExpressed();
    }

    private boolean isLikeDownRegulation(Regulation regulation) {
        return Regulation.DOWN.equals(regulation) && isUnderExpressed();
    }

    public Contrast getContrast() {
        return contrast;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ((null == object) || (getClass() != object.getClass())) {
            return false;
        }

        DifferentialExpression other = (DifferentialExpression) object;

        return Objects.equal(foldChange, other.foldChange) &&
                Objects.equal(pValue, other.pValue) &&
                Objects.equal(contrast, other.contrast);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pValue, foldChange, contrast);
    }

    @Override
    public double getLevel() {
        return getPValue();
    }

    @Override
    public boolean isKnown() {
        return true;
    }

    public boolean isOverExpressed() {
        return foldChange > 0;
    }

    public boolean isUnderExpressed() {
        return foldChange < 0;
    }
}
