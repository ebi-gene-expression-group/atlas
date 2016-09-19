package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class FactorGroupingService {

    AnatomicalSystemTrader anatomicalSystemTrader;

    @Inject
    public FactorGroupingService(AnatomicalSystemTrader anatomicalSystemTrader){
        this.anatomicalSystemTrader = anatomicalSystemTrader;
    }


    public JsonArray groupIntoAnatomicalSystems(Collection<Factor> factors) {
        Map<AnatomicalSystem, Set<OntologyTerm>> groupings = new HashMap<>();
        for (Factor factor : factors) {
            for (OntologyTerm ontologyTerm : factor.getValueOntologyTerms())
                for (AnatomicalSystem a :
                        anatomicalSystemTrader.getAnatomicalSystemsIncluding(ontologyTerm)) {
                    if (!groupings.containsKey(a)) {
                        groupings.put(a, new HashSet<OntologyTerm>());
                    }
                    groupings.get(a).add(ontologyTerm);
                }
        }


        JsonArray result = new JsonArray();
        for(Map.Entry<AnatomicalSystem, Set<OntologyTerm>> e: groupings.entrySet()){
            JsonObject thisSystemAndItsValues = e.getKey().toJson();

            JsonArray factorValues = new JsonArray();
            for(OntologyTerm f: e.getValue()){
                factorValues.add(new JsonPrimitive(f.accession()));
            }
            thisSystemAndItsValues.add("values", factorValues);
            result.add(thisSystemAndItsValues);
        }

        return result;
    }

    public JsonArray group(Collection<AssayGroupFactor> assayGroupFactors){
        return groupFactors(Collections2.transform(assayGroupFactors, new Function<AssayGroupFactor, Factor>() {
            @Nullable
            @Override
            public Factor apply(@Nullable AssayGroupFactor assayGroupFactor) {
                return assayGroupFactor.getFactor();
            }
        }));
    }

    JsonArray groupFactors(Collection<Factor> factors){
        JsonArray result = new JsonArray();

        JsonObject anatomicalSystems = new JsonObject();
        Collection<Factor> organismPartFactors = Collections2.filter(factors, new Predicate<Factor>() {
            @Override
            public boolean apply(@Nullable Factor factor) {
                return "ORGANISM_PART".equals(factor.getType());
            }
        });
        if(organismPartFactors.size()>0){
            anatomicalSystems.addProperty("name", "Anatomical Systems");
            anatomicalSystems.add("groups",groupIntoAnatomicalSystems(organismPartFactors));
        }

        result.add(anatomicalSystems);

        return result;
    }

}
