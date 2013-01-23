package uk.ac.ebi.atlas.geneindex;

import com.google.common.base.Joiner;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Named
public class GenePropertyQueryBuilder {

    public String buildQueryString(String searchText) {
        return buildQueryString(parseSearchText(searchText));
    }

    protected String buildQueryString(Collection<String> queryValues) {
        StringBuilder stringBuilder = new StringBuilder("(alltext:\"");
        return Joiner.on("\" OR alltext:\"").appendTo(stringBuilder, queryValues).append("\")").toString();
    }

    // this method had a too high complexity score by Sonar, major violation
    protected Collection<String> parseSearchText(String searchText) {
        Set<String> result = new HashSet<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < searchText.length(); i++) {
            char cur = searchText.charAt(i);

            // this is used as a delimiter
            if (cur == ',') {
                addCurrentStringBufferToResult(result, sb);
            }

            // this is used as a delimiter
            else if (cur == ' ' || cur == '\n' || cur == '\r') {
                addCurrentStringBufferToResult(result, sb);
            }

            // this is used to specify a String
            else if (cur == '"') {
                i = addQuotedStringToResult(searchText, result, sb, i);
            }

            // extend current entity
            else {
                sb.append(cur);
            }
        }
        // don't forget the last entity
        addCurrentStringBufferToResult(result, sb);

        return result;
    }

    private int addQuotedStringToResult(String searchText, Set<String> result, StringBuilder sb, int i) {
        // find closing double quote
        char cur;
        while (i < searchText.length() && (cur = searchText.charAt(++i)) != '"') {
            sb.append(cur);
        }
        result.add(sb.toString());
        // clear StringBuffer
        sb.delete(0, sb.length());
        return i;
    }

    private void addCurrentStringBufferToResult(Set<String> result, StringBuilder sb) {
        if (sb.length() > 0) {
            result.add(sb.toString());
        }
        // clear StringBuffer
        sb.delete(0, sb.length());
    }

}
