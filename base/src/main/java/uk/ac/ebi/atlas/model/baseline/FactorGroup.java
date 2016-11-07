package uk.ac.ebi.atlas.model.baseline;

import java.util.Collection;
import java.util.List;
import java.util.Set;

// A group of factors. Will be associated with an assay group.
// Backed by a map of type -> factor, not meant to be mutated after creation.

public interface FactorGroup extends Iterable<Factor>, Comparable<FactorGroup> {

    Factor factorOfType(String type);

    boolean containsAll(Set<Factor> factors);

    boolean overlapsWith(Collection<Factor> factors);

    List<Factor> without(Collection<Factor> factors);

    boolean contains(Factor factor);

    FactorGroup withoutType(String factorType);

    int size();

    boolean isEmpty();

    boolean containsOnlyOrganism();
}
