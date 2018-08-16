package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

import static uk.ac.ebi.atlas.utils.StringUtil.splitAtLastSlash;

@AutoValue
public abstract class OntologyTerm {
    private static final int DEFAULT_DEPTH = 1;

    public abstract String accession();
    public abstract String name();
    public abstract String source();
    public abstract int depth();

    public static OntologyTerm create(String accession) {
        return create(accession, "");
    }

    public static OntologyTerm create(String accession, String name) {
        return create(accession, name, "");
    }

    public static OntologyTerm create(String accession, String name, String source) {
        return create(accession, name, source, DEFAULT_DEPTH);
    }

    public static OntologyTerm create(String accession, String name, String source, int depth) {
        return new AutoValue_OntologyTerm(accession, name, source, depth);
    }

    public static OntologyTerm createFromURI(String uri) {
        return uri.contains("/") ? create(splitAtLastSlash(uri)[1], "", splitAtLastSlash(uri)[0]) : create(uri);
    }

    public String uri() {
        return source().isEmpty() ? accession() : StringUtils.appendIfMissing(source(), "/") + accession();
    }

    public static JsonElement jsonForHeaders(Set<OntologyTerm> ontologyTerms) {
        return ontologyTerms.isEmpty() ? JsonNull.INSTANCE : new JsonPrimitive(ontologyTerms.iterator().next()
                .accession());
    }
}
