package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;
import com.google.common.base.Strings;

import javax.annotation.Nullable;

@AutoValue
public abstract class OntologyTerm {

    public static OntologyTerm create(String id) {
        return create(id, null);
    }

    public static OntologyTerm create(String id, String source) {
        return new AutoValue_OntologyTerm(id, source);
    }

    public static OntologyTerm createFromUri(String uri) {
        String[] uriSplit = splitAtFinalSlash(uri);
        return create(uriSplit[1], uriSplit[0]);
    }

    public static String[] splitAtFinalSlash(String uri) {
        int finalSlash = uri.lastIndexOf('/');

        if (finalSlash == -1) {
            return new String[] {null, uri};
        }

        return new String[] {uri.substring(0, finalSlash + 1), uri.substring(finalSlash + 1)};
    }

    public static Optional<OntologyTerm> createOptional(String id, String source) {
        return Strings.isNullOrEmpty(id) ? Optional.<OntologyTerm>absent() : Optional.of(create(id, source));
    }

    public abstract String id();

    @Nullable
    public abstract String source();

    public String uri() {
        return (source() == null) ? id() : addTrailingSlashIfAbsent(source()) + id();
    }

    String addTrailingSlashIfAbsent(String s) {
        return (s.charAt(s.length() - 1) == '/' ? s : s + "/");
    }

}
