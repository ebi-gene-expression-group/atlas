package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.collect.FluentIterable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Named
public class FactorGroupingService {

    OrganismPartGroupingService organismPartGroupingService;

    @Inject
    public FactorGroupingService(OrganismPartGroupingService organismPartGroupingService) {
        this.organismPartGroupingService = organismPartGroupingService;
    }

    public JsonArray group(String defaultQueryFactorType,
                           Collection<FactorAcrossExperiments> factorsAcrossExperiments) {
        return "ORGANISM_PART".equalsIgnoreCase(defaultQueryFactorType)
                ? groupOrganismPartOntologyTerms(FluentIterable.from(factorsAcrossExperiments)
                .transformAndConcat(
                        factorAcrossExperiments -> factorAcrossExperiments.getValueOntologyTerms()
                ).toSet())
                : new JsonArray();
    }

    JsonArray groupOrganismPartOntologyTerms(Collection<OntologyTerm> ontologyTermsInAllFactors) {
        JsonArray result = new JsonArray();

        groupingAsJson(organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsInAllFactors), "Anatomical Systems", "Anatomical system")
                .ifPresent(o -> result.add(o));

        groupingAsJson(organismPartGroupingService.getOrgansGrouping(ontologyTermsInAllFactors), "Organs", "Organ")
                .ifPresent(o -> result.add(o));
        return result;
    }

    boolean hasOntologyTerms(Map<ColumnGroup, Set<OntologyTerm>> m) {
        return m.values().stream().map(s -> s.size()).reduce(0, (integer, integer2) -> integer + integer2) > 0;
    }

    Optional<JsonObject> groupingAsJson(Map<ColumnGroup, Set<OntologyTerm>> grouping, String name, String memberName) {
        return groupsAsJson(grouping).map(a -> {
            JsonObject result = new JsonObject();
            result.addProperty("name", name);
            result.addProperty("memberName", memberName);
            result.add("groups", a);
            return result;
        });
    }

    Optional<JsonArray> groupsAsJson(Map<ColumnGroup, Set<OntologyTerm>> groups) {
        if (hasOntologyTerms(groups)) {
            JsonArray result = new JsonArray();
            for (Map.Entry<ColumnGroup, Set<OntologyTerm>> e : groups.entrySet()) {
                result.add(oneGroup(e));
            }
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    JsonObject oneGroup(Map.Entry<ColumnGroup, Set<OntologyTerm>> e) {
        JsonObject thisSystemAndItsValues = e.getKey().toJson();
        JsonArray factorValues = new JsonArray();
        for (OntologyTerm f : e.getValue()) {
            factorValues.add(new JsonPrimitive(f.accession()));
        }
        thisSystemAndItsValues.add("values", factorValues);
        return thisSystemAndItsValues;
    }

}
