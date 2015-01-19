package uk.ac.ebi.atlas.web;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import java.util.List;

public class TagEditorConverter {

    private final static String TAG_DELIMITER = "\t";

    public static String tagsToQueryString(String geneQuery) {
        String trimmedQuery = geneQuery.trim();

        String[] tags = trimmedQuery.split(TAG_DELIMITER);
        String resQuery = "";

        if (tags.length == 1) {
            if (StringUtils.startsWith(trimmedQuery, "\"") || onlyOneWord(trimmedQuery)) {
                return trimmedQuery;
            } else {
                return "\"" + trimmedQuery + "\"";
            }
        }

        for (String tag : tags) { //for any tag find single word or multiple words
            String[] words = tag.trim().split(" ");

            if (words.length > 1) {  //multi-term
                String res = "";

                for (String word : words) {
                    res = res + word + " ";
                }
                res = res.trim();

                if (!StringUtils.startsWith(res, "\"")) {
                    res = "\"" + res;
                }

                if (!StringUtils.endsWith(res, "\"")) {
                    res = res + "\"";
                }

                resQuery = resQuery + res + " ";
            } else {  //single term
                resQuery = resQuery + tag.trim() + " ";
            }
        }

        return resQuery.trim();
    }

    private static boolean onlyOneWord(String geneQuery) {
        return StringUtils.countMatches(geneQuery, " ") == 0;
    }

    public static String queryStringToTags(String geneQuery) {
        List<String> tags = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(geneQuery);
        return Joiner.on(TAG_DELIMITER).join(tags).replace("\"", "");
    }
}
