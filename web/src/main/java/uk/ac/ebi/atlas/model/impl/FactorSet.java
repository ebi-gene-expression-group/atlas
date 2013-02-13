package uk.ac.ebi.atlas.model.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.FactorGroup;

import java.util.*;

public class FactorSet implements FactorGroup{

    private Map<String, Factor> factorsByType = new HashMap<>();

    public FactorSet(Set<Factor> factors){
        for (Factor factor: factors){
           factorsByType.put(factor.getType(), factor);
        }
    }

    public FactorSet(){}


    public FactorSet add(Factor factor){
        factorsByType.put(factor.getType(), factor);
        return this;
    }

    @Override
    public Iterator<Factor> iterator() {
        return Iterators.unmodifiableIterator(factorsByType.values().iterator());
    }


    @Override
    public Factor getFactorByType(String type) {
        return factorsByType.get(type);
    }

    @Override
    public boolean containsAll(Set<Factor> factors) {
        return this.factorsByType.values().containsAll(factors);
    }

    @Override
    public boolean equals(Object other){
        if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.factorsByType, ((FactorSet) other).factorsByType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(factorsByType);
    }

    @Override
    public String toString(){
        return Objects.toStringHelper(this.getClass())
                    .add("factors",factorsByType.values())
                    .toString();
    }

    @Override
    public boolean overlapsWith(Collection<Factor> factors) {
        return !Collections.disjoint(this.factorsByType.values(), factors);
    }
}
