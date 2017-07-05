package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    OrganismPartGroupingService organismPartGroupingService;

    @Inject
    public FactorGroupingService(OrganismPartGroupingService organismPartGroupingService){
        this.organismPartGroupingService = organismPartGroupingService;
    }

    public JsonArray group(String defaultQueryFactorType,
                           Collection<FactorAcrossExperiments> factorsAcrossExperiments){
        JsonArray result = new JsonArray();
        if("ORGANISM_PART".equalsIgnoreCase(defaultQueryFactorType)){

            Collection<OntologyTerm> ontologyTermsInAllFactors =
                    FluentIterable.from(factorsAcrossExperiments)
                    .transformAndConcat(
                            factorAcrossExperiments -> factorAcrossExperiments.getValueOntologyTerms()
                    ).toSet();


            JsonObject anatomicalSystems = new JsonObject();
            anatomicalSystems.addProperty("name", "Anatomical Systems");
            anatomicalSystems.addProperty("memberName", "Anatomical system");

            JsonArray anatomicalSystemGroups = new JsonArray();
            for(Map.Entry<ColumnGroup, Set<OntologyTerm>> e : organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsInAllFactors).entrySet()){
                anatomicalSystemGroups.add(oneGroup(e));
            }
            anatomicalSystems.add("groups", anatomicalSystemGroups);

            result.add(anatomicalSystems);

            JsonObject organs = new JsonObject();
            organs.addProperty("name", "Organs");
            organs.addProperty("memberName", "Organ");
            JsonArray organGroups = new JsonArray();
            for(Map.Entry<ColumnGroup, Set<OntologyTerm>> e : organismPartGroupingService.getOrgansGrouping(ontologyTermsInAllFactors).entrySet()){
                organGroups.add(oneGroup(e));
            }
            organs.add("groups", organGroups);

            result.add(organs);
        }
        return result;
    }

    JsonObject oneGroup(Map.Entry<ColumnGroup, Set<OntologyTerm>> e){
        JsonObject thisSystemAndItsValues = e.getKey().toJson();
        JsonArray factorValues = new JsonArray();
        for(OntologyTerm f: e.getValue()){
            factorValues.add(new JsonPrimitive(f.accession()));
        }
        thisSystemAndItsValues.add("values", factorValues);
        return thisSystemAndItsValues;
    }

}
