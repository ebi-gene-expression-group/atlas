package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.OntologyTerm;

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

    public JsonArray group(String defaultQueryFactorType,
                           Collection<FactorAcrossExperiments> factorsAcrossExperiments){
        JsonArray result = new JsonArray();
        if("ORGANISM_PART".equalsIgnoreCase(defaultQueryFactorType)){
            JsonObject anatomicalSystems = new JsonObject();
            anatomicalSystems.addProperty("name", "Anatomical Systems");
            anatomicalSystems.addProperty("memberName", "Anatomical system");
            anatomicalSystems.add("groups", groupIntoAnatomicalSystems(FluentIterable.from(factorsAcrossExperiments)
                    .transformAndConcat(new Function<FactorAcrossExperiments, Iterable<OntologyTerm>>() {
                        @Nullable
                        @Override
                        public Iterable<OntologyTerm> apply(@Nullable FactorAcrossExperiments factorAcrossExperiments) {
                            return factorAcrossExperiments.getValueOntologyTerms();
                        }
                    }).toSet())
            );
            result.add(anatomicalSystems);
        }
        return result;
    }

    private JsonArray groupIntoAnatomicalSystems(Set<OntologyTerm> ontologyTerms) {
        Map<AnatomicalSystem, Set<OntologyTerm>> groupings = new HashMap<>();

        for (OntologyTerm ontologyTerm : ontologyTerms) {
            for (AnatomicalSystem a : anatomicalSystemTrader.getAnatomicalSystemsIncluding(ontologyTerm.accession())) {
                if (!groupings.containsKey(a)) {
                    groupings.put(a, new HashSet<>());
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


}
