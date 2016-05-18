
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.util.*;

public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;
    private static final int MINIMUM_SET_SIZE = 50;

    private int minimumSetSize = MINIMUM_SET_SIZE;

    // for each cutoff, there is a map of genes expressed (in a BitSet) by factor group
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

        // get the union of genes expressed for all FactorGroups that are in the slice and have been selected
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
