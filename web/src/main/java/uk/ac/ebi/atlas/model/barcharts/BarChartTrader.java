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
import uk.ac.ebi.atlas.model.FactorValue;

import javax.inject.Named;
import java.util.*;

//ToDo: to be continued in order to handle the includeGenesExpressedAlsoOnNonSelectedFactorValue parameter...
@Named("barChartTrader")
@Scope("prototype")
public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;

    private NavigableMap<Double, Map<Set<FactorValue>, BitSet>> factorSetGeneExpressionIndexes = new TreeMap<>();

    protected BarChartTrader() {
    }

    public BarChartTrader(NavigableMap<Double, Map<Set<FactorValue>, BitSet>> factorSetGeneExpressionIndexes) {
        this.factorSetGeneExpressionIndexes = factorSetGeneExpressionIndexes;
    }


    public NavigableMap<Double, Integer> getChart(Set<FactorValue> limitingFactorValueSet, Set<FactorValue> selectedFactorValues) {

        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : factorSetGeneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(factorSetGeneExpressionIndexes.get(scaledCutoff), limitingFactorValueSet, selectedFactorValues));

        }

        return barChartPoints;
    }


    protected static int countGenesAboveCutoff(Map<Set<FactorValue>, BitSet> geneBitSets, Set<FactorValue> limitingFactorValueSet, Set<FactorValue> selectedFactorValues) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        for (Set<FactorValue> bitSetFactorValues : geneBitSets.keySet()) {
            if (forLimitingFactorValues(bitSetFactorValues, limitingFactorValueSet) && forQueryFactorValues(bitSetFactorValues, selectedFactorValues)) {
                //add
                expressedGenesBitSet.or(geneBitSets.get(bitSetFactorValues));
            }
        }
        return expressedGenesBitSet.cardinality();
    }


    protected static boolean forLimitingFactorValues(Set<FactorValue> factorValueSet, Set<FactorValue> limitingFactorValueSet) {
        return CollectionUtils.isEmpty(limitingFactorValueSet) || factorValueSet.containsAll(limitingFactorValueSet);
    }

    protected static boolean forQueryFactorValues(Set<FactorValue> factorValueSet, Set<FactorValue> queryFactorValues) {
        return CollectionUtils.isEmpty(queryFactorValues) ||  !Collections.disjoint(factorValueSet ,queryFactorValues);
    }


}
