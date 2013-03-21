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

package uk.ac.ebi.atlas.model;

import uk.ac.ebi.atlas.model.differential.DifferentialProfile;

import java.util.Collection;

public class DifferentialProfilesList extends GeneProfilesList<DifferentialProfile> {


    public DifferentialProfilesList(Collection collection) {
        super(collection);
    }

    public double getMaxUpRegulatedExpressionLevel(){
        double maxUpRegulatedExpressionLevel = 0;
        for (DifferentialProfile differentialProfile : this) {
            if (maxUpRegulatedExpressionLevel < differentialProfile.getMaxUpRegulatedExpressionLevel()) {
                maxUpRegulatedExpressionLevel = differentialProfile.getMaxUpRegulatedExpressionLevel();
            }
        }
        return maxUpRegulatedExpressionLevel != 0 ? maxUpRegulatedExpressionLevel : Double.NaN;
    }

    public double getMinUpRegulatedExpressionLevel(){
        double minUpRegulatedExpressionLevel = Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            if (differentialProfile.getMinUpRegulatedExpressionLevel() < minUpRegulatedExpressionLevel) {
                minUpRegulatedExpressionLevel = differentialProfile.getMinUpRegulatedExpressionLevel();
            }
        }
        return minUpRegulatedExpressionLevel != Double.MAX_VALUE ? minUpRegulatedExpressionLevel : Double.NaN;
    }


    public double getMaxDownRegulatedExpressionLevel(){
        double maxDownRegulatedExpressionLevel = 0;
        for (DifferentialProfile differentialProfile : this) {
            if (maxDownRegulatedExpressionLevel < differentialProfile.getMaxDownRegulatedExpressionLevel()) {
                maxDownRegulatedExpressionLevel = differentialProfile.getMaxDownRegulatedExpressionLevel();
            }
        }
        return maxDownRegulatedExpressionLevel != 0 ? maxDownRegulatedExpressionLevel : Double.NaN;
    }

    public double getMinDownRegulatedExpressionLevel(){
        double minDownRegulatedExpressionLevel = Double.MAX_VALUE;
        for (DifferentialProfile differentialProfile : this) {
            if (differentialProfile.getMinDownRegulatedExpressionLevel() < minDownRegulatedExpressionLevel) {
                minDownRegulatedExpressionLevel = differentialProfile.getMinDownRegulatedExpressionLevel();
            }
        }
        return minDownRegulatedExpressionLevel != Double.MAX_VALUE ? minDownRegulatedExpressionLevel : Double.NaN;
    }
}
