package uk.ac.ebi.atlas.web;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@AutoValue
public abstract class SemanticQuery implements Iterable<SemanticQueryTerm> {

    public abstract ImmutableSet<SemanticQueryTerm> terms();

    public static SemanticQuery create(Collection<SemanticQueryTerm> queryTerms) {
        return new AutoValue_SemanticQuery(ImmutableSet.copyOf(queryTerms));
    }

    public static SemanticQuery create() {
        return new AutoValue_SemanticQuery(ImmutableSet.<SemanticQueryTerm>of());
    }

    public static SemanticQuery create(SemanticQueryTerm... queryTerms) {
        ImmutableSet.Builder<SemanticQueryTerm> builder = ImmutableSet.builder();
        return new AutoValue_SemanticQuery(builder.add(queryTerms).build());
    }

    @Override
    public Iterator<SemanticQueryTerm> iterator() {
        return terms().iterator();
    }

//    public SemanticQuery(String value) {
//        terms = ImmutableSet.of(SemanticQueryTerm.create(value));
//    }
//
//    public SemanticQuery(String value, String source) {
//        terms = ImmutableSet.of(SemanticQueryTerm.create(value, source));
//    }

    public boolean isEmpty() {
        for (SemanticQueryTerm term : terms()) {
            if (term.hasValue()) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return terms().size();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(terms());
    }

    public String toUrlEncodedJson() throws UnsupportedEncodingException{
        Gson gson = new Gson();
        return URLEncoder.encode(gson.toJson(terms()), "UTF-8");
    }

    public static SemanticQuery fromJson(String json) {
        Gson gson = new Gson();
        return create(ImmutableSet.<SemanticQueryTerm>copyOf(gson.fromJson(json, AutoValue_SemanticQueryTerm[].class)));
    }

    public static SemanticQuery fromUrlEncodedJson(String json) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        return create(ImmutableSet.<SemanticQueryTerm>copyOf(gson.fromJson(URLDecoder.decode(json, "UTF-8"), AutoValue_SemanticQueryTerm[].class)));
    }

    @Override
    public String toString() {
        return toJson();
    }
}
