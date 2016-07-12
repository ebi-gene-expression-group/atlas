package uk.ac.ebi.atlas.web;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

@AutoValue
public abstract class GeneQuery implements Iterable<SemanticQueryTerm> {

    private static final String OR_OPERATOR = " OR ";

    public abstract ImmutableSet<SemanticQueryTerm> terms();

    public static GeneQuery create(Collection<SemanticQueryTerm> queryTerms) {
        return new AutoValue_GeneQuery(ImmutableSet.copyOf(queryTerms));
    }

    public static GeneQuery create() {
        return new AutoValue_GeneQuery(ImmutableSet.<SemanticQueryTerm>of());
    }

    public static GeneQuery create(SemanticQueryTerm... queryTerms) {
        ImmutableSet.Builder<SemanticQueryTerm> builder = ImmutableSet.builder();
        return new AutoValue_GeneQuery(builder.add(queryTerms).build());
    }

    public static GeneQuery create(String queryTermValue) {
        return new AutoValue_GeneQuery(ImmutableSet.of(SemanticQueryTerm.create(queryTermValue)));
    }

    @Override
    public Iterator<SemanticQueryTerm> iterator() {
        return terms().iterator();
    }

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

    public static GeneQuery fromJson(String json) {
        Gson gson = new Gson();
        return create(ImmutableSet.<SemanticQueryTerm>copyOf(gson.fromJson(json, AutoValue_SemanticQueryTerm[].class)));
    }

    public static GeneQuery fromUrlEncodedJson(String json) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        try {
            return create(ImmutableSet.<SemanticQueryTerm>copyOf(gson.fromJson(URLDecoder.decode(json, "UTF-8"), AutoValue_SemanticQueryTerm[].class)));
        } catch (JsonSyntaxException e1) {
            String geneQueryString = gson.fromJson(URLDecoder.decode(StringUtils.wrap(json, "\""), "UTF-8"), String.class);
            return create(ImmutableSet.of(SemanticQueryTerm.create(geneQueryString)));
        }
    }

    public String prettyString() {
        StringBuilder prettyStringBuilder = new StringBuilder();

        for (SemanticQueryTerm queryTerm : terms()) {
            prettyStringBuilder.append(String.format("Query term: \"%s\" - Category: \"%s\"", queryTerm.value(), queryTerm.category()));
            prettyStringBuilder.append("\n");
        }

        return prettyStringBuilder.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }

    public String asSolr1DNF() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SemanticQueryTerm queryTerm : terms()) {
            stringBuilder.append(String.format("\"%s:{%s}\"",queryTerm.category(), queryTerm.value())).append(OR_OPERATOR);
        }
        stringBuilder.delete(stringBuilder.lastIndexOf(OR_OPERATOR), stringBuilder.length());
        return stringBuilder.toString();
    }

}
