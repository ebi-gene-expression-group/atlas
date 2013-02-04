package uk.ac.ebi.atlas.model;

import java.util.Collection;
import java.util.Set;


/*
    Overview: is a group of factors that combine together in an ExperimentRun
 */
public interface FactorGroup extends Iterable<Factor>{

    public Factor getFactorByType(String type);

    public boolean containsAll(Set<Factor> factors);

    public boolean isDisjointFrom(Collection<Factor> factors);

}
