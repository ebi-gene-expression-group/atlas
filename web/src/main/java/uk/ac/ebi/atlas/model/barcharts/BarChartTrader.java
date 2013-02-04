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
import uk.ac.ebi.atlas.model.FactorGroup;

import javax.inject.Named;
import java.util.*;

@Named("barChartTrader")
@Scope("prototype")
public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;

    private NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes = new TreeMap<>();

    protected BarChartTrader() {
    }

    public BarChartTrader(NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes) {
        this.factorGroupGeneExpressionIndexes = factorGroupGeneExpressionIndexes;
    }


    public NavigableMap<Double, Integer> getChart(Set<Factor> filterFactors, Set<Factor> selectedFactors) {

        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : factorGroupGeneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(factorGroupGeneExpressionIndexes.get(scaledCutoff), filterFactors, selectedFactors));

        }

        return barChartPoints;
    }


    //ToDo: static methods... this method obviously shows that we should create a specialized container class for geneBitSets exposing these kind of services. Delegating to static methods only drives to non-design.
    protected static int countGenesAboveCutoff(Map<FactorGroup, BitSet> geneBitSets, Set<Factor> filterFactors, Set<Factor> selectedFactors) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        for (FactorGroup bitSetFactors : geneBitSets.keySet()) {
            if (forFilterFactors(bitSetFactors, filterFactors) && forQueryFactors(bitSetFactors, selectedFactors)) {
                //add
                expressedGenesBitSet.or(geneBitSets.get(bitSetFactors));
            }
        }
        return expressedGenesBitSet.cardinality();
    }

    //ToDo: what does this method name means?????
    protected static boolean forFilterFactors(FactorGroup factorGroup, Set<Factor> filterFactors) {
        return CollectionUtils.isEmpty(filterFactors) || factorGroup.containsAll(filterFactors);
    }

    //ToDo: what does this method name means?@?@?@?
    protected static boolean forQueryFactors(FactorGroup factorGroup, Set<Factor> queryFactors) {
        return CollectionUtils.isEmpty(queryFactors) ||  !factorGroup.isDisjointFrom(queryFactors);
    }


}
