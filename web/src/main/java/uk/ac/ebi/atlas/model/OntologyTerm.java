package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Strings;

import javax.annotation.Nullable;

@AutoValue
public abstract class OntologyTerm {

    private final static int DEFAULT_DEPTH = 1;

    public abstract String accession();
    public abstract String name();
    public abstract String source();
    public abstract int depth();

    public static OntologyTerm create(String accession) {
        return create(accession, "", "", DEFAULT_DEPTH);
    }

    public static OntologyTerm create(String accession, String name) {
        return create(accession, name, "", DEFAULT_DEPTH);
    }

    public static OntologyTerm create(String accession, String name, String source) {
        return create(accession, name, source, DEFAULT_DEPTH);
    }

    public static OntologyTerm create(String accession, String name, String source, int depth) {
        return new AutoValue_OntologyTerm(accession, name, source, depth);
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
        return (Strings.isNullOrEmpty(source()) ? accession() : addTrailingSlashIfAbsent(source()) + accession());
    }

    private String addTrailingSlashIfAbsent(String s) {
        return (s.charAt(s.length() - 1) == '/' ? s : s + "/");
    }

}
