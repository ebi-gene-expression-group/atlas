package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.util.*;

/*
I provide additional behaviour to factorGroup without refactoring.

TODO get rid of FactorSet and me, make FactorGroup a class around an immutable map - potentially with a builder -  and put all the methods there!
 */
public class RichFactorGroup {

    private final FactorGroup factorGroup;

    public RichFactorGroup(FactorGroup factorGroup){
        this.factorGroup = factorGroup;
    }

    public Set<OntologyTerm> getOntologyTerms(){
        return FluentIterable.from(factorGroup).transformAndConcat(new Function<Factor, Iterable<OntologyTerm>>() {
            @Override
            public Iterable<OntologyTerm> apply(Factor factor) {
                return factor.getValueOntologyTerms();
            }
        }).toSet();
    }

    public static Set<String> typesWithCommonValues(Iterable<FactorGroup> factorGroups){
        Multimap<String, String> allValuesPerType = LinkedListMultimap.create();
        for(FactorGroup factorGroup: factorGroups){
            for(Factor factor: factorGroup){
                allValuesPerType.put(factor.getType(), factor.getValue());
            }
        }
       return FluentIterable.from(allValuesPerType.asMap().entrySet()).filter(new Predicate<Map.Entry<String, Collection<String>>>() {
            @Override
            public boolean apply(Map.Entry<String, Collection<String>> stringCollectionEntry) {
                return ImmutableSet.copyOf(stringCollectionEntry.getValue()).size() < 2;
            }
        }).transform(new Function<Map.Entry<String,Collection<String>>, String>() {
            @Override
            public String apply(Map.Entry<String, Collection<String>> stringCollectionEntry) {
                return stringCollectionEntry.getKey();
            }
        }).toSet();
    }

    public static List<String> filterOutTypesWithCommonValues(List<String> types, Iterable<FactorGroup> factorGroups){
        return removeAll(types, typesWithCommonValues(factorGroups));
    }

    //apache commons' ListUtils.removeAll but with generic types
    private static <T>  List<T> removeAll(Collection<T> collection, Collection<T> remove) {
        ArrayList<T> list = new ArrayList<>();
        Iterator<T> iter = collection.iterator();

        while(iter.hasNext()) {
            T obj = iter.next();
            if(!remove.contains(obj)) {
                list.add(obj);
            }
        }

        return list;
    }

    public static boolean isSubgroup(FactorGroup moreSpecificGroup, FactorGroup moreGeneralGroup){
        for(Factor factor: moreGeneralGroup){
            if(! moreSpecificGroup.factorOfType(factor.getType()).equals(factor)){
                return false;
            }
        }
        return true;
    }
}
