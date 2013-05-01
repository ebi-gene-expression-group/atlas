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


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.math.util.MathUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class BaselineProfilesList extends GeneProfilesList<BaselineProfile> {

    public BaselineProfilesList(Collection<BaselineProfile> collection) {
        super(collection);
    }

    public double getMaxExpressionLevel() {
        double maxExpressionLevel = 0;
        for (BaselineProfile geneProfile : this) {
            if (maxExpressionLevel < geneProfile.getMaxExpressionLevel()) {
                maxExpressionLevel = geneProfile.getMaxExpressionLevel();
            }
        }
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        double minExpressionLevel = Double.MAX_VALUE;
        for (BaselineProfile geneProfile : this) {
            if (geneProfile.getMinExpressionLevel() < minExpressionLevel) {
                minExpressionLevel = geneProfile.getMinExpressionLevel();
            }
        }
        return minExpressionLevel;
    }

    public BaselineProfilesList collapsedProfilesList(Set<Factor> allQueryFactors) {


        // initialize expression map to sum up all expressions
        Map<Factor, Double> expressionsPerFactor = Maps.newHashMap();
        for (Factor queryFactor : allQueryFactors) {
            expressionsPerFactor.put(queryFactor, 0.0);
        }

        // sum all expressions across all factors in profiles list
        for (BaselineProfile profile : this) {
            for (Factor queryFactor : allQueryFactors) {
                double expressionLevel = profile.getExpressionLevel(queryFactor);
                Double previousExpressionLevel = expressionsPerFactor.get(queryFactor);
                expressionsPerFactor.put(queryFactor, previousExpressionLevel + expressionLevel);
            }
        }

        // normalize by total number of expressions
        for (Factor queryFactor : allQueryFactors) {
            Double previousExpressionLevel = expressionsPerFactor.get(queryFactor);
            double value = previousExpressionLevel / this.getTotalResultCount();
            if (value < 1) {
                value = MathUtils.round(value, 1);
            } else {
                value = MathUtils.round(value, 0);
            }
            expressionsPerFactor.put(queryFactor, value);
        }

        // build new expression profile
        BaselineProfile collapsedProfile = new BaselineProfile("collapsed");
        for (Factor queryFactor : allQueryFactors) {
            Double expressionLevel = expressionsPerFactor.get(queryFactor);
            FactorSet factors = new FactorSet();
            factors.add(queryFactor);
            BaselineExpression expression = new BaselineExpression(expressionLevel, factors);
            collapsedProfile.addExpression(queryFactor, expression);
        }

        BaselineProfilesList baselineProfiles = new BaselineProfilesList(Lists.newArrayList(collapsedProfile));
        baselineProfiles.setTotalResultCount(1);
        return baselineProfiles;
    }

}
