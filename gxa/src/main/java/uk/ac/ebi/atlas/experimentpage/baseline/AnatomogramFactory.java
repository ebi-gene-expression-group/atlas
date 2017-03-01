package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.gson.*;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.species.Species;

public class AnatomogramFactory {

    public JsonElement get(String queryFactorType, Species species, Iterable<OntologyTerm> ontologyTerms) {
        if ("ORGANISM_PART".equalsIgnoreCase(queryFactorType)) {
            return getAnatomogramProperties(species.getReferenceName(), ontologyTerms);
        } else {
            return JsonNull.INSTANCE;
        }
    }

    private JsonObject getAnatomogramProperties(String species, Iterable<OntologyTerm> ontologyTerms) {
        JsonObject anatomogramProperties = new JsonObject();

        anatomogramProperties.addProperty("species",species);
        anatomogramProperties.add("allSvgPathIds", ontologyTermsAsJson(ontologyTerms));

        return anatomogramProperties;
    }

    private JsonElement ontologyTermsAsJson(Iterable<OntologyTerm> ontologyTerms) {
        JsonArray result = new JsonArray();

        for(OntologyTerm ontologyTerm : ontologyTerms){
            result.add(new JsonPrimitive(ontologyTerm.accession()));
        }

        return result.size() == 0 ? JsonNull.INSTANCE : result;
    }

}
