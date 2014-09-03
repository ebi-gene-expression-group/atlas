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

import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.Collection;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfilesList<T extends DifferentialProfile> extends GeneProfilesList<T> implements DifferentialExpressionLimits {

    public DifferentialProfilesList() {
        // default, with no starting collection
    }

    public DifferentialProfilesList(Collection<T> collection) {
        super(collection);
    }

    //TODO: memoize min and max levels, because they are called multiple times from the JSP and recalculated each time
    public double getMaxUpRegulatedExpressionLevel(){
        double maxUpRegulatedExpressionLevel = -Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMaxUpRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                maxUpRegulatedExpressionLevel = max(maxUpRegulatedExpressionLevel, expressionLevel);
            }
        }
        return maxUpRegulatedExpressionLevel == -Double.MAX_VALUE ? Double.NaN : maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel(){
        Double minUpRegulatedExpressionLevel = null;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMinUpRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                minUpRegulatedExpressionLevel = (minUpRegulatedExpressionLevel == null) ? expressionLevel : min(minUpRegulatedExpressionLevel, expressionLevel);
            }
        }
        return minUpRegulatedExpressionLevel == null ? Double.NaN : minUpRegulatedExpressionLevel;
    }


    public double getMaxDownRegulatedExpressionLevel(){
        double maxDownRegulatedExpressionLevel = -Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMaxDownRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                maxDownRegulatedExpressionLevel = max(maxDownRegulatedExpressionLevel, Math.abs(expressionLevel));
            }
        }
        return maxDownRegulatedExpressionLevel == -Double.MAX_VALUE ? Double.NaN : negate(maxDownRegulatedExpressionLevel);
    }

    public double getMinDownRegulatedExpressionLevel(){
        Double minDownRegulatedExpressionLevel = null;
        for (DifferentialProfile differentialProfile : this) {
            double expressionLevel = differentialProfile.getMinDownRegulatedExpressionLevel();
            if (!Double.isNaN(expressionLevel)) {
                minDownRegulatedExpressionLevel = (minDownRegulatedExpressionLevel == null) ? Math.abs(expressionLevel) : min(minDownRegulatedExpressionLevel, Math.abs(expressionLevel));
            }
        }
        return minDownRegulatedExpressionLevel == null ? Double.NaN : negate(minDownRegulatedExpressionLevel);
    }

    public static double negate(double value) {
        return value == 0 ? 0 : -value;
    }
}
