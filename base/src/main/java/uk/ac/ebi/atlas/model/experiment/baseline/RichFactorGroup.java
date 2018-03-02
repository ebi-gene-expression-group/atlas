package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/*
I provide additional behaviour to factorGroup without refactoring.

TODO get rid of FactorSet and me, make FactorGroup a class around an immutable map - potentially with a builder -  and put all the methods there!
 */
public class RichFactorGroup {

    private final FactorGroup factorGroup;

    public RichFactorGroup(FactorGroup factorGroup) {
        this.factorGroup = factorGroup;
    }

    public Set<OntologyTerm> getOntologyTerms() {
        return StreamSupport.stream(factorGroup.spliterator(), false)
                .flatMap(factor -> factor.getValueOntologyTerms().stream())
                .collect(Collectors.toSet());
    }

    public String asUrlEncodedJson() {
        try {
            return URLEncoder.encode(new Gson().toJson(asJson()), "UTF-8");
        } catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    //Coupled to the experiment page client code - over there we can have multiple factor values per type
    JsonObject asJson() {
        JsonObject result = new JsonObject();
        for(Factor factor: factorGroup){
            JsonArray values = new JsonArray();
            values.add(new JsonPrimitive(factor.getValue()));
            result.add(factor.getType(), values);
        }
        return result;
    }


    public static Set<String> typesWithCommonValues(Iterable<FactorGroup> factorGroups) {
        Multimap<String, String> allValuesPerType = LinkedListMultimap.create();
        for(FactorGroup factorGroup: factorGroups){
            for(Factor factor: factorGroup){
                allValuesPerType.put(factor.getType(), factor.getValue());
            }
        }

        return allValuesPerType.asMap().entrySet().stream()
                .filter(stringCollectionEntry -> stringCollectionEntry.getValue().size() >= 2 && ImmutableSet.copyOf(stringCollectionEntry.getValue()).size() < 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static List<String> filterOutTypesWithCommonValues(List<String> types, Iterable<FactorGroup> factorGroups) {
        return removeAll(types, typesWithCommonValues(factorGroups));
    }

    //apache commons' ListUtils.removeAll but with generic types
    private static <T>  List<T> removeAll(Collection<T> collection, Collection<T> remove) {
        ArrayList<T> list = new ArrayList<>();

        for (T obj : collection) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }

        return list;
    }

    public static boolean isSubgroup(FactorGroup moreSpecificGroup, FactorGroup moreGeneralGroup) {
        for(Factor factor: moreGeneralGroup){
            if(! moreSpecificGroup.factorOfType(factor.getType()).equals(factor)){
                return false;
            }
        }
        return true;
    }
}
