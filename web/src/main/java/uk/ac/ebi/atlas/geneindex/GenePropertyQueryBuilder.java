package uk.ac.ebi.atlas.geneindex;

import com.google.common.base.Joiner;

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

    protected Collection<String> parseSearchText(String searchText) {
        HashSet<String> result = new HashSet<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < searchText.length(); i++) {
            char cur = searchText.charAt(i);

            // this is used as a delimiter
            if (cur == ',') {
                if (sb.length() > 0)
                    result.add(sb.toString());
                // clear StringBuffer
                sb.delete(0, sb.length());
            }

            // this is used as a delimiter
            else if (cur == ' ' || cur == '\n' || cur == '\r') {
                if (sb.length() > 0)
                    result.add(sb.toString());
                // clear StringBuffer
                sb.delete(0, sb.length());
            }

            // this is used to specify a String
            else if (cur == '"') {
                // find closing double quote
                while (i < searchText.length() && (cur = searchText.charAt(++i)) != '"') {
                    sb.append(cur);
                }
                result.add(sb.toString());
                // clear StringBuffer
                sb.delete(0, sb.length());
            }

            // extend current entity
            else {
                sb.append(cur);
            }
        }
        // don't forget the last entity
        if (sb.length() > 0)
            result.add(sb.toString());

        return result;
    }

}
