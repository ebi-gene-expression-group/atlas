package uk.ac.ebi.atlas.model;

import javax.annotation.Nullable;

public class OntologyTerm {

    private String id;
    private String source;

    public OntologyTerm(String id) {
        this(id, null);
    }

    public OntologyTerm(String id, String source) {
        this.id = id;
        this.source = source;
    }

    public static OntologyTerm createFromUri(String uri) {
        String[] uriSplit = splitAtFinalSlash(uri);
        return new OntologyTerm(uriSplit[1], uriSplit[0]);
    }

    public static String[] splitAtFinalSlash(String uri) {
        int finalSlash = uri.lastIndexOf('/');

        if (finalSlash == -1) {
            return new String[] {null, uri};
        }

        return new String[] {uri.substring(0, finalSlash + 1), uri.substring(finalSlash + 1)};
    }

    public String id() {
        return id;
    }

    @Nullable
    public String source() {
        return source;
    }

    public String uri() {
        return (source() == null || source().isEmpty()) ? id() : addTrailingSlashIfAbsent(source()) + id();
    }

    String addTrailingSlashIfAbsent(String s) {
        return (s.charAt(s.length() - 1) == '/' ? s : s + "/");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof OntologyTerm) {
            OntologyTerm that = (OntologyTerm) o;
            return (this.source == that.source && this.id == that.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() ^ this.source.hashCode();
    }
}
