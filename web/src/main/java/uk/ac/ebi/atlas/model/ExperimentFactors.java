package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

import java.util.*;

public class ExperimentFactors {

    //we want keys to be ordered alphabetically
    private SortedSetMultimap<String, Factor> factorsByName = TreeMultimap.create();

    private String defaultQueryFactorType;

    private Map<String, String> factorNamesByType;

    private Set<Factor> defaultFilterFactors = new HashSet<>();

    protected ExperimentFactors(Collection<Factor> factors, String defaultQueryFactorType, Factor... defaultFilterFactors){
        for (Factor factor: factors){
            factorsByName.put(factor.getName(), factor);
            factorNamesByType.put(factor.getType(), factor.getName());
        }
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.defaultFilterFactors = Sets.newHashSet(defaultFilterFactors);
    }

    public SortedSet<String> getAllFactorNames(){
        return ImmutableSortedSet.copyOf(factorsByName.keySet());
    }

    public SortedSet<Factor> getFactorsByName(String name){
        return ImmutableSortedSet.copyOf(factorsByName.get(name));
    }

    public Set<Factor> getFactorsByType(String type){
        return ImmutableSortedSet.copyOf(factorsByName.get(factorNamesByType.get(type)));
    }
}
