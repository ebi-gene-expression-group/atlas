/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.barcharts;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.*;

@Named("barChartTrader")
@Scope("prototype")
public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;

    private NavigableMap<Double, Map<Set<Factor>, BitSet>> factorSetGeneExpressionIndexes = new TreeMap<>();

    protected BarChartTrader() {
    }

    public BarChartTrader(NavigableMap<Double, Map<Set<Factor>, BitSet>> factorSetGeneExpressionIndexes) {
        this.factorSetGeneExpressionIndexes = factorSetGeneExpressionIndexes;
    }


    public NavigableMap<Double, Integer> getChart(Set<Factor> limitingFactorSet, Set<Factor> selectedFactors) {

        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : factorSetGeneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(factorSetGeneExpressionIndexes.get(scaledCutoff), limitingFactorSet, selectedFactors));

        }

        return barChartPoints;
    }


    protected static int countGenesAboveCutoff(Map<Set<Factor>, BitSet> geneBitSets, Set<Factor> limitingFactorSet, Set<Factor> selectedFactors) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        for (Set<Factor> bitSetFactors : geneBitSets.keySet()) {
            if (forLimitingFactorValues(bitSetFactors, limitingFactorSet) && forQueryFactorValues(bitSetFactors, selectedFactors)) {
                //add
                expressedGenesBitSet.or(geneBitSets.get(bitSetFactors));
            }
        }
        return expressedGenesBitSet.cardinality();
    }


    protected static boolean forLimitingFactorValues(Set<Factor> factorSet, Set<Factor> limitingFactorSet) {
        return CollectionUtils.isEmpty(limitingFactorSet) || factorSet.containsAll(limitingFactorSet);
    }

    protected static boolean forQueryFactorValues(Set<Factor> factorSet, Set<Factor> queryFactors) {
        return CollectionUtils.isEmpty(queryFactors) ||  !Collections.disjoint(factorSet, queryFactors);
    }


}
