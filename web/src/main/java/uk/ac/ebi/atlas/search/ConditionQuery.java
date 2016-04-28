package uk.ac.ebi.atlas.search;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static uk.ac.ebi.atlas.utils.StringArrayUtil.removeSurroundingQuotes;

//TODO: rename to SamplePropertiesQuery
@AutoValue
public abstract class ConditionQuery implements Iterable<String> {

    public abstract ImmutableList<String> terms();

    //TODO: change to use same interface as GeneQuery ie, String...
    public static ConditionQuery create(String conditionQueryString) {
        List<String> terms = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(conditionQueryString);
        return new AutoValue_ConditionQuery(ImmutableList.copyOf(removeSurroundingQuotes(terms.toArray(new String[0]))));
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
