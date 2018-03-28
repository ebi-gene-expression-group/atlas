package uk.ac.ebi.atlas.search;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@AutoValue
public abstract class SemanticQuery implements Iterable<SemanticQueryTerm> {
    // According to Google Gson is thread-safe:
    // https://github.com/google/gson/blob/master/UserGuide.md#using-gson
    // Remove static if Google is lying!
    public abstract ImmutableSet<SemanticQueryTerm> terms();

    public static SemanticQuery create(Collection<SemanticQueryTerm> queryTerms) {
        return new AutoValue_SemanticQuery(ImmutableSet.copyOf(queryTerms));
    }

    public static SemanticQuery create() {
        return new AutoValue_SemanticQuery(ImmutableSet.of());
    }

    public static SemanticQuery create(SemanticQueryTerm... queryTerms) {
        ImmutableSet.Builder<SemanticQueryTerm> builder = ImmutableSet.builder();
        return new AutoValue_SemanticQuery(builder.add(queryTerms).build());
    }

    public static SemanticQuery create(String queryTermValue) {
        return new AutoValue_SemanticQuery(ImmutableSet.of(SemanticQueryTerm.create(queryTermValue)));
    }

    public Map<String, Set<String>> groupValuesByCategory() {
        return terms().stream()
                .collect(groupingBy(SemanticQueryTerm::category)).entrySet().stream()
                .collect(toMap(
                        categorySemanticQueryTerm -> categorySemanticQueryTerm.getKey().orElse(""),
                        categorySemanticQueryTerm ->
                                categorySemanticQueryTerm.getValue().stream()
                                        .filter(SemanticQueryTerm::hasValue)
                                        .map(SemanticQueryTerm::value)
                                        .collect(toSet())));
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

    // Fail-fast version of !isEmpty()
    public boolean isNotEmpty() {
        for (SemanticQueryTerm term : terms()) {
            if (term.hasValue()) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return terms().size();
    }

    public String toJson() {
        return GSON.toJson(terms());
    }

    public String toUrlEncodedJson() {
        try {
            return URLEncoder.encode(toJson(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            // Unreachable , UTF-8 will always be supported, even on an infinite time scale :P
            throw new UncheckedIOException(e);
        }
    }

    public static SemanticQuery fromJson(String json) {
        if (isBlank(json)) {
            return create();
        }

        return create(ImmutableSet.copyOf(GSON.fromJson(json, AutoValue_SemanticQueryTerm[].class)));
    }

    public static SemanticQuery fromUrlEncodedJson(String json) throws UnsupportedEncodingException {
        if (isBlank(json)) {
            return create();
        }

        String decoded = URLDecoder.decode(json, "UTF-8");
         try {
             return create(ImmutableSet.copyOf(GSON.fromJson(decoded, AutoValue_SemanticQueryTerm[].class)));
        } catch (NullPointerException | JsonSyntaxException e) {
            String geneQueryString = GSON.fromJson(StringUtils.wrap(decoded, "\""), String.class);

            ImmutableSet.Builder<SemanticQueryTerm> builder = ImmutableSet.builder();
            for (String geneQueryTerm : geneQueryString.split(" ")) {
                builder.add(SemanticQueryTerm.create(geneQueryTerm));
            }
            return create(builder.build());
        }
    }

//    @Override
//    public String toString() {
//        return toJson();
//    }

    public String description() {
        return terms().stream()
                .map(SemanticQueryTerm::description)
                .collect(Collectors.joining(" OR "));
    }
}
