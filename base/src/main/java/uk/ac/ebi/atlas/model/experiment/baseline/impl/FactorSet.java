package uk.ac.ebi.atlas.model.experiment.baseline.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;

import java.util.*;

public class FactorSet implements FactorGroup {

    public final Map<String, Factor> factorsByType;

    public FactorSet() {
        this(new HashMap<>());
    }

    private FactorSet(Map<String, Factor> factorsByType) {
        this.factorsByType = factorsByType;
    }

    public FactorSet add(Factor factor) {
        /*Preconditions.checkArgument(!factorsByType.containsKey(factor.getType()));
        Sadly this does sometimes happen.
         */
        factorsByType.put(factor.getType(), factor);
        return this;
    }

    @Override
    public Iterator<Factor> iterator() {
        return Iterators.unmodifiableIterator(factorsByType.values().iterator());
    }


    @Override
    public Factor factorOfType(String type) {
        return factorsByType.get(type);
    }

    @Override
    public int compareTo(FactorGroup other) {
        if (this.size() != other.size()) {
            return Integer.compare(this.size(), other.size());
        }

        Iterator<Factor> thisIterator = this.iterator();
        Iterator<Factor> otherIterator = other.iterator();

        while (thisIterator.hasNext()) {
            Factor thisFactor = thisIterator.next();
            Factor otherFactor = otherIterator.next();
            int c = thisFactor.compareTo(otherFactor);
            if (c != 0) {
                return c;
            }
        }

        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        //equality on hashmaps or hashmap values is broken, for this reason better to compare brand new Lists
        List<Factor> thisFactors = Lists.newArrayList(factorsByType.values());
        List<Factor> otherFactors =  Lists.newArrayList(((FactorSet) other).factorsByType.values());

        return Objects.equal(thisFactors, otherFactors);
    }

    @Override
    public int hashCode() {
        // hashcode on factorsByType.values() doesn't work
        return Objects.hashCode(factorsByType.entrySet());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("factors", factorsByType.values())
                .toString();
    }

    @Override
    public List<Factor> without(Collection<Factor> factors) {
        ArrayList<Factor> allFactors = Lists.newArrayList(factorsByType.values());

        allFactors.removeAll(factors);

        return allFactors;
    }

    @Override
    public FactorGroup withoutTypes(Collection<String> factorTypes) {
        HashMap<String, Factor> result = new HashMap<>();
        factorsByType.keySet().stream().filter(type -> !factorTypes.contains(type)).forEach(type -> {
            result.put(type, factorsByType.get(type));
        });
        return new FactorSet(result);
    }


    // Map <type, value>
    public static FactorSet create(Map<String, String> factorValueByType) {
        FactorSet factorSet = new FactorSet();
        for (String factorType : factorValueByType.keySet()) {
            Factor factor = new Factor(factorType, factorValueByType.get(factorType));
            factorSet.add(factor);
        }

        return factorSet;
    }

    public int size() {
        return factorsByType.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsOnlyOrganism() {
        return size() == 1 && factorOfType("ORGANISM") != null;
    }

}
