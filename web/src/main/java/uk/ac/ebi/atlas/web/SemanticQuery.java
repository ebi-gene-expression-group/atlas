package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

public class SemanticQuery implements Iterable<SemanticQueryTerm> {

    private ImmutableSet<SemanticQueryTerm> terms;

    @Override
    public Iterator<SemanticQueryTerm> iterator() {
        return terms.iterator();
    }

    public SemanticQuery() {
        terms = ImmutableSet.of();
    }

    public SemanticQuery(String value) {
        terms = ImmutableSet.of(SemanticQueryTerm.create(value));
    }

    public SemanticQuery(String value, String source) {
        terms = ImmutableSet.of(SemanticQueryTerm.create(value, source));
    }

    public SemanticQuery(SemanticQueryTerm... terms) {
        this.terms = ImmutableSet.copyOf(terms);
    }

    public SemanticQuery(Collection<SemanticQueryTerm> terms) {
        this.terms = ImmutableSet.copyOf(terms);
    }

    public ImmutableSet<SemanticQueryTerm> terms() {
        return terms;
    }

    public boolean isEmpty() {
        for (SemanticQueryTerm term : terms) {
            if (term.hasValue()) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return terms.size();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(terms);
    }

    public String toUrlEncodedJson() throws UnsupportedEncodingException{
        Gson gson = new Gson();
        return URLEncoder.encode(gson.toJson(terms), "UTF-8");
    }

    public static SemanticQuery fromJson(String json) {
        Gson gson = new Gson();
        return new SemanticQuery(gson.fromJson(json, AutoValue_SemanticQueryTerm[].class));
    }

    public static SemanticQuery fromUrlEncodedJson(String json) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        return new SemanticQuery(gson.fromJson(URLDecoder.decode(json, "UTF-8"), AutoValue_SemanticQueryTerm[].class));
    }

    @Override
    public int hashCode() {
        return terms.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SemanticQuery) {
            SemanticQuery other = (SemanticQuery) obj;
            return other.terms().equals(this.terms);
        }

        return false;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
