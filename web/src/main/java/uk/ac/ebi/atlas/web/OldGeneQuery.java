package uk.ac.ebi.atlas.web;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@AutoValue
public abstract class OldGeneQuery implements Iterable<String> {

    public static final OldGeneQuery EMPTY = new AutoValue_OldGeneQuery(ImmutableList.<String>of());

    public abstract ImmutableList<String> terms();

    public static OldGeneQuery create(String... geneQuery) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        return create(builder.add(geneQuery).build());
    }

    public static OldGeneQuery create(Set<String> terms) {
        return create(ImmutableList.copyOf(terms));
    }

    public static OldGeneQuery create(ImmutableList<String> terms) {
        return new AutoValue_OldGeneQuery(terms);
    }

    public boolean isEmpty() {
        return terms().isEmpty();
    }

    public int size() {
        return terms().size();
    }

    @Override
    public Iterator<String> iterator() {
        return terms().iterator();
    }

    @Deprecated // use terms() instead
    public String asString() {
        return BioentityPropertyValueTokenizer.joinQuotingPhrases(terms());
    }

    public OldGeneQuery extend(String term, String... termsToAppend){
        ImmutableList.Builder<String> b = ImmutableList.builder();
        for(String t: terms()) {
            b.add(t);
            if (t.equals(term)) {
                b.add(termsToAppend);
            }
        }
        return OldGeneQuery.create(b.build());
    }

    public OldGeneQuery subtract(Collection<String> termsToSubtract){
        ImmutableList.Builder<String> b = ImmutableList.builder();
        for(String t: terms()) {
            if(!termsToSubtract.contains(t)){
                b.add(t);
            }
        }
        return OldGeneQuery.create(b.build());
    }

    public String as1DNF() {
        return "\"" + StringUtils.join(terms(), "\" OR \"") + "\"";
    }

    public String description() {
        return BioentityPropertyValueTokenizer.joinQuotingPhrases(terms());
    }

    public String asUrlQueryParameter() {
        try {
            return URLEncoder.encode(asTags(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String asTags() {
        return TagEditorConverter.queryStringToTags(BioentityPropertyValueTokenizer.joinQuotingPhrases(this));
    }

}
