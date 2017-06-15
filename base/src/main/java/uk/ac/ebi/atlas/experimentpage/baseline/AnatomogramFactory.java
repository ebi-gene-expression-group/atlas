package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.gson.*;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.species.Species;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnatomogramFactory {

    public static final String factorTypeWithAnatomogram = "ORGANISM_PART";

    public Optional<JsonElement> get(String queryFactorType, Species species, Iterable<OntologyTerm> ontologyTerms) {
        if (factorTypeWithAnatomogram.equalsIgnoreCase(queryFactorType)) {
            return Optional.of(getAnatomogramProperties(species, ontologyTerms));
        } else {
            return Optional.absent();
        }
    }

    public Optional<JsonElement> get(List<AssayGroup> selectedDataColumns, final BaselineExperiment baselineExperiment) {
        Set<String> s = new HashSet<>();
        for (AssayGroup assayGroup : selectedDataColumns) {
            s.add(safeFactorValue(baselineExperiment.getFactors(assayGroup).factorOfType(factorTypeWithAnatomogram)));
        }
        if(s.size() > 1){
            return Optional.of(getAnatomogramProperties(baselineExperiment.getSpecies(), FluentIterable.from(selectedDataColumns).transformAndConcat(
                    assayGroup -> baselineExperiment.getFactors(assayGroup).factorOfType(factorTypeWithAnatomogram).getValueOntologyTerms()
            )));
        } else {
            return Optional.absent();
        }
    }

    private String safeFactorValue(@Nullable Factor factor) {
        if(factor == null){
            return "";
        } else {
            return factor.getValue();
        }
    }

    private JsonElement getAnatomogramProperties(Species species, Iterable<OntologyTerm> ontologyTerms) {
        JsonObject anatomogramProperties = new JsonObject();

        anatomogramProperties.addProperty("species", species.getReferenceName());
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
