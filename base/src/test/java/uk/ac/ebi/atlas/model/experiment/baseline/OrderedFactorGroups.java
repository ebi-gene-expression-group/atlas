package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import java.util.Iterator;
import java.util.List;

public class OrderedFactorGroups implements Iterable<FactorGroup> {

    private final List<FactorGroup> factorGroups;
    private final int size;

    public OrderedFactorGroups(List<FactorGroup> factorGroups) {
        this.factorGroups = factorGroups;
        this.size = Iterables.size(factorGroups);
    }

    @Override
    public Iterator<FactorGroup> iterator() {
        return factorGroups.iterator();

    }

    public int size() {
        return size;
    }

    public FactorGroup get(int index) {
        return factorGroups.get(index);
    }

    public ImmutableSet<Factor> extractFactors() {
        ImmutableSet.Builder<Factor> builder = ImmutableSet.builder();
        for (FactorGroup factorGroup : factorGroups) {
            for (Factor factor : factorGroup) {
                builder.add(factor);
            }
        }
        return builder.build();
    }

}
