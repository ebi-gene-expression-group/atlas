package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.species.Species;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AnatomogramFactory {

    public static final String factorTypeWithAnatomogram = "ORGANISM_PART";

    public Optional<JsonElement> get(String queryFactorType, Species species, Iterable<OntologyTerm> ontologyTerms) {
        if (factorTypeWithAnatomogram.equalsIgnoreCase(queryFactorType)) {
            return Optional.of(getAnatomogramProperties(species, ontologyTerms));
        } else {
            return Optional.empty();
        }
    }

    public Optional<JsonElement> get(List<AssayGroup> selectedDataColumns, final BaselineExperiment baselineExperiment) {
        Set<String> s =
                selectedDataColumns.stream()
                        .map(assayGroup -> safeFactorValue(baselineExperiment.getFactors(assayGroup).factorOfType(factorTypeWithAnatomogram)))
                        .collect(Collectors.toSet());
        if (s.size() > 0) {
            return Optional.of(
                    getAnatomogramProperties(
                            baselineExperiment.getSpecies(),
                            selectedDataColumns.stream()
                                    .flatMap(assayGroup -> baselineExperiment.getFactors(assayGroup).factorOfType(factorTypeWithAnatomogram).getValueOntologyTerms().stream())
                                    .collect(Collectors.toList())));
        } else {
            return Optional.empty();
        }
    }

    private String safeFactorValue(@Nullable Factor factor) {
        if (factor == null) {
            return "";
        } else {
            return factor.getValue();
        }
    }

    private JsonElement getAnatomogramProperties(Species species, Iterable<OntologyTerm> ontologyTerms) {
        JsonObject anatomogramProperties = new JsonObject();

        anatomogramProperties.addProperty("species", species.getReferenceName().replace(" ", "_"));
        anatomogramProperties.add("allSvgPathIds", ontologyTermsAsJson(ontologyTerms));

        return anatomogramProperties;
    }

    private JsonElement ontologyTermsAsJson(Iterable<OntologyTerm> ontologyTerms) {
        JsonArray result = new JsonArray();

        for (OntologyTerm ontologyTerm : ontologyTerms) {
            result.add(new JsonPrimitive(ontologyTerm.accession()));
        }

        return result.size() == 0 ? JsonNull.INSTANCE : result;
    }

}
