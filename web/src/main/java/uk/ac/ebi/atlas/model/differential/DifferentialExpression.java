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

import uk.ac.ebi.atlas.model.GeneExpression;

public class DifferentialExpression implements GeneExpression{

    private double pValue;

    private double foldChange;

    private Contrast contrast;

    public DifferentialExpression(double pValue, double foldChange, Contrast contrast) {
        this.pValue = pValue;
        this.foldChange = foldChange;
        this.contrast = contrast;
    }

    double getPValue() {
        return pValue;
    }

    public double getFoldChange() {
        return foldChange;
    }

    public Contrast getContrast() {
        return contrast;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if ((null == other) || (getClass() != other.getClass())) {
            return false;
        }

        DifferentialExpression that = (DifferentialExpression) other;

        if (Double.compare(that.foldChange, foldChange) != 0) return false;
        if (Double.compare(that.pValue, pValue) != 0) return false;
        if (!contrast.equals(that.contrast)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = pValue != +0.0d ? Double.doubleToLongBits(pValue) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = foldChange != +0.0d ? Double.doubleToLongBits(foldChange) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + contrast.hashCode();
        return result;
    }

    @Override
    public double getLevel() {
        return getPValue();
    }
}
