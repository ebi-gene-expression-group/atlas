package uk.ac.ebi.atlas.geneindex;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;

@Named
public class GenePropertyQueryBuilder {

    public String buildQueryString(String searchText) {
        return buildQueryString(parseSearchText(searchText));
    }

    protected String buildQueryString(Collection<String> queryValues) {
        StringBuilder stringBuilder = new StringBuilder("(alltext:\"");
        return Joiner.on("\" OR alltext:\"").appendTo(stringBuilder, queryValues).append("\")").toString();
    }

    private boolean endsWithQuote(String value) {
        return value.lastIndexOf("\"") == value.length() - 1;
    }

    private boolean startsFromQuote(String value) {
        return value.indexOf("\"") == 0;
    }

    private Collection<String> parseSearchText(String searchText) {
        HashSet<String> result = new HashSet<>();

        Iterable<String> values = Splitter.on(CharMatcher.anyOf(", ")).omitEmptyStrings().split(searchText);

        StringBuilder sb = new StringBuilder();
        boolean building = false;
        for (String value : values) {
            if (building) {
                sb.append(value);
                if (endsWithQuote(value)) {
                    building = false;
                    String s = sb.toString().trim();
                    result.add(s.substring(1, s.length() - 1));
                }
            } else {
                if (startsFromQuote(value) && !endsWithQuote(value)) {
                    building = true;
                    sb = new StringBuilder();
                    sb.append(value).append(" ");
                } else {
                    result.add(value);
                }
            }
        }
        return result;
    }

}
