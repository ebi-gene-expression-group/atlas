package uk.ac.ebi.atlas.search;

import com.google.auto.value.AutoValue;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

import static org.apache.commons.lang3.StringUtils.isBlank;

@AutoValue
public abstract class SemanticQuery implements Iterable<SemanticQueryTerm> {

    private static final String OR_OPERATOR = " OR ";

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

    public static SemanticQuery create(String queryTermValue) {
        return new AutoValue_SemanticQuery(ImmutableSet.of(SemanticQueryTerm.create(queryTermValue)));
    }

    public static boolean isEmpty(SemanticQuery query) {
        return query == null || query.isEmpty();
    }

    public static boolean isNotEmpty(SemanticQuery query) {
        return query != null && !query.isEmpty();
    }

    @Override
    public Iterator<SemanticQueryTerm> iterator() {
        return terms().iterator();
    }

    private boolean isEmpty() {
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

    //Don't rename me. experiment.jsp: var geneQueryTagsPopulatedInTemplate = ${preferences.geneQuery.json};
    public String getJson(){
        return toJson();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(terms());
    }

    public String toUrlEncodedJson(){
        try {
            return URLEncoder.encode(toJson(), "UTF-8");
        } catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    public static SemanticQuery fromJson(String json) {
        if (isBlank(json)) {
            return create();
        }

        Gson gson = new Gson();
        return create(ImmutableSet.<SemanticQueryTerm>copyOf(gson.fromJson(json, AutoValue_SemanticQueryTerm[].class)));
    }

    public static SemanticQuery fromUrlEncodedJson(String json) throws MalformedJsonException {
        if (isBlank(json)) {
            return create();
        }

        Gson gson = new Gson();
        String decoded;
        try {
            decoded = URLDecoder.decode(json, "UTF-8");
        }  catch(UnsupportedEncodingException e){
                throw new RuntimeException(e);
        }
         try {
             return create(ImmutableSet.<SemanticQueryTerm>copyOf(gson.fromJson(decoded, AutoValue_SemanticQueryTerm[].class)));
        } catch (NullPointerException | JsonSyntaxException e) {
            String geneQueryString = gson.fromJson(StringUtils.wrap(decoded, "\""), String.class);

            ImmutableSet.Builder<SemanticQueryTerm> builder = ImmutableSet.builder();
            for (String geneQueryTerm : geneQueryString.split(" ")) {
                builder.add(SemanticQueryTerm.create(geneQueryTerm));
            }
            return create(builder.build());
        }
    }

    @Override
    public String toString() {
        return toJson();
    }

    public String description() {
        return Joiner.on(OR_OPERATOR).join(Collections2.transform(terms(), new Function<SemanticQueryTerm, String>() {
            @Nullable
            @Override
            public String apply(SemanticQueryTerm semanticQueryTerm) {
                return semanticQueryTerm.description();
            }
        }));
    }
}
