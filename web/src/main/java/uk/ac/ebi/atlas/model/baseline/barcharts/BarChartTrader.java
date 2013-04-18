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

package uk.ac.ebi.atlas.model.baseline.barcharts;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.util.*;

public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;
    private static final int MINIMUM_SET_SIZE = 50;

    private int minimumSetSize = MINIMUM_SET_SIZE;

    private NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes = new TreeMap<>();

    public BarChartTrader(NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes) {
        this.factorGroupGeneExpressionIndexes = factorGroupGeneExpressionIndexes;
    }

    public NavigableMap<Double, Integer> getChart(Set<Factor> filterFactors, Set<Factor> selectedQueryFactors) {

        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : factorGroupGeneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(factorGroupGeneExpressionIndexes.get(scaledCutoff), filterFactors, selectedQueryFactors));

        }

        return barChartPoints;
    }

    protected int countGenesAboveCutoff(Map<FactorGroup, BitSet> geneBitSets, Set<Factor> filterFactors, Set<Factor> selectedFactors) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        for (FactorGroup factorGroup : geneBitSets.keySet()) {

            boolean factorGroupContainsAllFilterFactors = CollectionUtils.isEmpty(filterFactors) || factorGroup.containsAll(filterFactors);
            boolean factorGroupOverlapsSelectedFactors = CollectionUtils.isEmpty(selectedFactors) || factorGroup.overlapsWith(selectedFactors);

            if (factorGroupContainsAllFilterFactors && factorGroupOverlapsSelectedFactors) {
                expressedGenesBitSet.or(geneBitSets.get(factorGroup));
            }
        }
        return expressedGenesBitSet.cardinality();
    }

    protected void trimIndexes() {

        Set<Double> doubles = Sets.newHashSet(factorGroupGeneExpressionIndexes.keySet());
        for (Double scaledCutoff : doubles) {

            if (countGenesAboveCutoff(factorGroupGeneExpressionIndexes.get(scaledCutoff), null, null) < minimumSetSize) {
                factorGroupGeneExpressionIndexes.remove(scaledCutoff);
            }

        }
    }

    protected void setMinimumSetSize(int minimumSetSize) {
        this.minimumSetSize = minimumSetSize;
    }
}
