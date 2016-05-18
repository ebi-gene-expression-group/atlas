
package uk.ac.ebi.atlas.model.baseline;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/*
    A group of factors. Will be associated with an assay group
 */
public interface FactorGroup extends Iterable<Factor>, Comparable<FactorGroup> {

    Factor getFactorByType(String type);

    boolean containsAll(Set<Factor> factors);

    boolean overlapsWith(Collection<Factor> factors);

    List<Factor> remove(Collection<Factor> factors);

    List<Factor> remove(Factor factor);

    boolean contains(Factor factor);

    FactorGroup removeType(String factorType);

    int size();

    boolean isEmpty();

    String getOrganismFactorValue();

    public boolean containsOnlyOrganism();
}
