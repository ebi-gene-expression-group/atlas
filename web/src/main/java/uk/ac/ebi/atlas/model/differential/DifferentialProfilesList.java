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

    public double getMaxUpRegulatedExpressionLevel(){
        double maxUpRegulatedExpressionLevel = -Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            maxUpRegulatedExpressionLevel = max(maxUpRegulatedExpressionLevel, differentialProfile.getMaxUpRegulatedExpressionLevel());
        }
        return maxUpRegulatedExpressionLevel == -Double.MAX_VALUE ? Double.NaN : maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel(){
        double minUpRegulatedExpressionLevel = Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            minUpRegulatedExpressionLevel = min(minUpRegulatedExpressionLevel, differentialProfile.getMinUpRegulatedExpressionLevel());
        }
        return minUpRegulatedExpressionLevel == Double.MAX_VALUE ? Double.NaN : minUpRegulatedExpressionLevel;
    }


    public double getMaxDownRegulatedExpressionLevel(){
        double maxDownRegulatedExpressionLevel = -Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            maxDownRegulatedExpressionLevel = max(maxDownRegulatedExpressionLevel, differentialProfile.getMaxDownRegulatedExpressionLevel());
        }
        return maxDownRegulatedExpressionLevel == -Double.MAX_VALUE ? Double.NaN : maxDownRegulatedExpressionLevel;
    }

    public double getMinDownRegulatedExpressionLevel(){
        double minDownRegulatedExpressionLevel = Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            minDownRegulatedExpressionLevel = min(minDownRegulatedExpressionLevel, differentialProfile.getMinDownRegulatedExpressionLevel());
        }
        return minDownRegulatedExpressionLevel == Double.MAX_VALUE ? Double.NaN : minDownRegulatedExpressionLevel;
    }
}
