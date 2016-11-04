package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.ImmutableList;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uk.ac.ebi.atlas.utils.StringArrayUtil.quotePhrases;

public class BioentityPropertyValueTokenizer {

    private BioentityPropertyValueTokenizer(){}

    private static final String SPLIT_BY_SPACE_PRESERVING_DOUBLE_QUOTES_REGEXP = "\"([^\"]*)\"|(\\S+)";

    private static final Pattern SPLITTING_PATTERN = Pattern.compile(SPLIT_BY_SPACE_PRESERVING_DOUBLE_QUOTES_REGEXP);

    public static List<String> splitBySpacePreservingQuotes(String geneQuery) {
        List<String> results = Lists.newArrayList();

        if (geneQuery == null) {
            return results;
        }

        Matcher m = SPLITTING_PATTERN.matcher(geneQuery);
        while (m.find()) {
            if (m.group(1) != null) {
                // quoted
                results.add("\"" + m.group(1) + "\"");
            } else {
                // plain
                results.add(m.group(2));
            }
        }

        return results;
    }

    public static String joinQuotingPhrases(Iterable<String> strings) {
        return Joiner.on(" ").join(quotePhrases(ImmutableList.copyOf(strings).toArray(new String[0])));
    }
}