package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Strings;

import javax.annotation.Nullable;

@AutoValue
public abstract class OntologyTerm {

    public abstract String id();

    @Nullable
    public abstract String source();

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

    public String uri() {
        return (Strings.isNullOrEmpty(source()) ? id() : addTrailingSlashIfAbsent(source()) + id());
    }

    private String addTrailingSlashIfAbsent(String s) {
        return (s.charAt(s.length() - 1) == '/' ? s : s + "/");
    }

}
