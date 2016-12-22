package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import uk.ac.ebi.atlas.model.experiment.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.species.Species;

public class AnatomogramFactory {

    public JsonElement get(String queryFactorType, Species species, Iterable<AssayGroupFactor>
            filteredAssayGroupFactors) {
        if ("ORGANISM_PART".equals(queryFactorType)) {
            return getAnatomogramProperties(species.getReferenceName(), filteredAssayGroupFactors);
        } else {
            return JsonNull.INSTANCE;
        }
    }

    private JsonObject getAnatomogramProperties(String species, Iterable<AssayGroupFactor> filteredAssayGroupFactors) {
        JsonObject anatomogramProperties = new JsonObject();

        anatomogramProperties.addProperty("species",species);
        anatomogramProperties.add("allSvgPathIds", extractOntologyTerm(filteredAssayGroupFactors));

        return anatomogramProperties;
    }

    private JsonElement extractOntologyTerm(Iterable<AssayGroupFactor> filteredAssayGroupFactors) {
        JsonArray ontologyTerms = new JsonArray();

        for (AssayGroupFactor assayGroupFactor : filteredAssayGroupFactors) {
            String valueOntologyTermId = assayGroupFactor.getValueOntologyTermId();
            if (valueOntologyTermId != null) {
                ontologyTerms.add(new JsonPrimitive(valueOntologyTermId));
            }
        }
        return ontologyTerms.size() == 0 ? JsonNull.INSTANCE : ontologyTerms;
    }

}
