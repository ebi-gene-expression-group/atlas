package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class FactorGroupingService {
    private final OrganismPartGroupingService organismPartGroupingService;

    public FactorGroupingService(OrganismPartGroupingService organismPartGroupingService) {
        this.organismPartGroupingService = organismPartGroupingService;
    }

    public JsonArray group(String defaultQueryFactorType,
                           Collection<FactorAcrossExperiments> factorsAcrossExperiments) {
        return "ORGANISM_PART".equalsIgnoreCase(defaultQueryFactorType) ?
                groupOrganismPartOntologyTerms(
                        factorsAcrossExperiments.stream()
                                .flatMap(FactorAcrossExperiments::getValueOntologyTerms)
                                .collect(toList())) :
                new JsonArray();
    }

    JsonArray groupOrganismPartOntologyTerms(Collection<OntologyTerm> ontologyTermsInAllFactors) {
        JsonArray result = new JsonArray();

        groupingAsJson(
                organismPartGroupingService.getOrgansGrouping(ontologyTermsInAllFactors),
                "Organs",
                "Organ")
                .ifPresent(result::add);

        groupingAsJson(
                organismPartGroupingService.getAnatomicalSystemsGrouping(ontologyTermsInAllFactors),
                "Anatomical Systems",
                "Anatomical system")
                .ifPresent(result::add);

        return result;
    }

    boolean hasOntologyTerms(Map<ColumnGroup, Set<OntologyTerm>> m) {
        return m.values().stream().map(Set::size).reduce(0, (integer, integer2) -> integer + integer2) > 0;
    }

    private Optional<JsonObject> groupingAsJson(Map<ColumnGroup, Set<OntologyTerm>> grouping,
                                                String name,
                                                String memberName) {
        return groupsAsJson(grouping).map(a -> {
            JsonObject result = new JsonObject();
            result.addProperty("name", name);
            result.addProperty("memberName", memberName);
            result.add("groups", a);
            return result;
        });
    }

    private Optional<JsonArray> groupsAsJson(Map<ColumnGroup, Set<OntologyTerm>> groups) {
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

    private JsonObject oneGroup(Map.Entry<ColumnGroup, Set<OntologyTerm>> e) {
        JsonObject thisSystemAndItsValues = e.getKey().toJson();
        JsonArray factorValues = new JsonArray();
        for (OntologyTerm f : e.getValue()) {
            factorValues.add(new JsonPrimitive(f.accession()));
        }
        thisSystemAndItsValues.add("values", factorValues);
        return thisSystemAndItsValues;
    }

}
