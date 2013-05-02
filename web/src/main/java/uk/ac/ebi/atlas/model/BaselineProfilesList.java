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


import com.google.common.base.Function;
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

        BaselineProfile averageProfile = calculateAverageExpressionProfile(allQueryFactors);

        BaselineProfilesList baselineProfiles = new BaselineProfilesList(
                Lists.newArrayList(averageProfile));
        baselineProfiles.setTotalResultCount(1);
        return baselineProfiles;
    }

    BaselineProfile calculateMaxExpressionProfile(Set<Factor> allQueryFactors) {

        // initialize max map with maximum Double
        Map<Factor, Double> maxsPerFactor = Maps.toMap(allQueryFactors, new Function<Factor, Double>() {
            @Override
            public Double apply(Factor factor) {
                return Double.NEGATIVE_INFINITY;
            }
        });

        // find max across all factors in profiles list
        for (final BaselineProfile profile : this) {
            maxsPerFactor = Maps.transformEntries(maxsPerFactor, new Maps.EntryTransformer<Factor, Double, Double>() {
                @Override
                public Double transformEntry(Factor factor, Double currentValue) {
                    double expressionLevel = profile.getExpressionLevel(factor);
                    if (expressionLevel > currentValue) {
                        return expressionLevel;
                    } else {
                        return currentValue;
                    }
                }
            });
        }

        return transformMapIntoProfile("max", maxsPerFactor);
    }

    BaselineProfile calculateMinExpressionProfile(Set<Factor> allQueryFactors) {

        // initialize min map with maximum Double
        Map<Factor, Double> minsPerFactor = Maps.toMap(allQueryFactors, new Function<Factor, Double>() {
            @Override
            public Double apply(Factor factor) {
                return Double.POSITIVE_INFINITY;
            }
        });

        // find min across all factors in profiles list
        for (final BaselineProfile profile : this) {
            minsPerFactor = Maps.transformEntries(minsPerFactor, new Maps.EntryTransformer<Factor, Double, Double>() {
                @Override
                public Double transformEntry(Factor factor, Double currentValue) {
                    double expressionLevel = profile.getExpressionLevel(factor);
                    if (expressionLevel < currentValue && expressionLevel > 0) {
                        return expressionLevel;
                    } else {
                        return currentValue;
                    }
                }
            });
        }

        return transformMapIntoProfile("min", minsPerFactor);
    }

    BaselineProfile calculateCountsExpressionProfile(Set<Factor> allQueryFactors) {

        // initialize counts map to sum up all profiles
        Map<Factor, Integer> countsPerFactor = Maps.toMap(allQueryFactors, new Function<Factor, Integer>() {
            @Override
            public Integer apply(Factor factor) {
                return 0;
            }
        });

        // sum all counts across all factors in profiles list
        for (final BaselineProfile profile : this) {
            countsPerFactor = Maps.transformEntries(countsPerFactor, new Maps.EntryTransformer<Factor, Integer, Integer>() {
                @Override
                public Integer transformEntry(Factor factor, Integer currentValue) {
                    double expressionLevel = profile.getExpressionLevel(factor);
                    if (expressionLevel > 0) {
                        return currentValue + 1;
                    } else {
                        return currentValue;
                    }
                }
            });
        }

        return transformMapIntoProfile("counts", countsPerFactor);
    }

    BaselineProfile calculateAverageExpressionProfile(Set<Factor> allQueryFactors) {

        // initialize expression map to sum up all expressions
        Map<Factor, Double> expressionsPerFactor = Maps.toMap(allQueryFactors, new Function<Factor, Double>() {
            @Override
            public Double apply(Factor factor) {
                return 0.0;
            }
        });

        // sum all expressions across all factors in profiles list
        for (final BaselineProfile profile : this) {
            expressionsPerFactor = Maps.transformEntries(expressionsPerFactor, new Maps.EntryTransformer<Factor, Double, Double>() {
                @Override
                public Double transformEntry(Factor factor, Double currentValue) {
                    double expressionLevel = profile.getExpressionLevel(factor);
                    return currentValue + expressionLevel;
                }
            });
        }

        // normalize by total number of expressions
        final int totalResultCount = this.getTotalResultCount();
        expressionsPerFactor = Maps.transformValues(expressionsPerFactor, new Function<Double, Double>() {
            @Override
            public Double apply(Double currentValue) {
                return currentValue / totalResultCount;
            }
        });

        // round values in map for nicer display
        expressionsPerFactor = Maps.transformValues(expressionsPerFactor, new Function<Double, Double>() {
            @Override
            public Double apply(Double value) {
                if (value < 1) {
                    return MathUtils.round(value, 1);
                } else {
                    return MathUtils.round(value, 0);
                }
            }
        });

        return transformMapIntoProfile("averages", expressionsPerFactor);
    }

    BaselineProfile transformMapIntoProfile(String profileName, Map<Factor, ? extends Number> map) {

        // build new expression profile
        BaselineProfile collapsedProfile = new BaselineProfile(profileName);
        for (Factor factor : map.keySet()) {
            Double expressionLevel = map.get(factor).doubleValue();
            FactorSet factors = new FactorSet();
            factors.add(factor);
            BaselineExpression expression = new BaselineExpression(expressionLevel, factors);
            collapsedProfile.addExpression(factor, expression);
        }

        return collapsedProfile;
    }

}
