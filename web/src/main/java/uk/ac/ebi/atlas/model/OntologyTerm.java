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

    public static OntologyTerm createFromSourceAndId(String sourceAndId) {
        String[] sourceAndIdSplit = splitAtFinalSlash(sourceAndId);
        return create(sourceAndIdSplit[1], sourceAndIdSplit[0]);
    }

    static String[] splitAtFinalSlash(String sourceAndId) {
        int finalSlash = sourceAndId.lastIndexOf('/');

        if (finalSlash == -1) {
            return new String[] {null, sourceAndId};
        }

        return new String[] {sourceAndId.substring(0, finalSlash + 1), sourceAndId.substring(finalSlash + 1)};
    }

    public static Optional<OntologyTerm> createOptional(String id, String source) {
        return Strings.isNullOrEmpty(id) ? Optional.<OntologyTerm>absent() : Optional.of(create(id, source));
    }

    public abstract String id();

    @Nullable
    public abstract String source();

    public String sourceAndId() {
        return (source() == null) ? id() : addTrailingSlashIfAbsent(source()) + id();
    }

    String addTrailingSlashIfAbsent(String s) {
        return (s.charAt(s.length() - 1) == '/' ? s : s + "/");
    }

}
