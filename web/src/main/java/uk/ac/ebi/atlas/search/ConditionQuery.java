package uk.ac.ebi.atlas.search;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import uk.ac.ebi.atlas.utils.StringUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

//TODO: rename to SamplePropertiesQuery
@AutoValue
public abstract class ConditionQuery implements Iterable<String> {

    public abstract ImmutableList<String> terms();

    public static ConditionQuery create(String conditionQueryString) {
        List<String> terms = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(conditionQueryString);
        return new AutoValue_ConditionQuery(removeSurroundingQuotes(terms));
    }

    static ImmutableList<String> removeSurroundingQuotes(List<String> strings) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (String s : strings) {
            builder.add(StringUtil.removeSurroundingQuotes(s));

        }

        return builder.build();
    }

    public static ConditionQuery create(Set<String> terms) {
        return new AutoValue_ConditionQuery(ImmutableList.copyOf(terms));
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

    public boolean containsIgnoreCase(String testTerm) {
        for (String term : terms()) {
            if (term.equalsIgnoreCase(testTerm)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated // use terms() instead
    public String asString() {
        return BioentityPropertyValueTokenizer.joinQuotingPhrases(terms());
    }

}
