package uk.ac.ebi.atlas.utils;

import javax.inject.Named;

import static org.apache.commons.lang3.StringUtils.wrap;

@Named
public class StringUtil {
    public static String[] splitAtLastSlash(String uri) {
        int finalSlashIndex = uri.lastIndexOf('/');
        return finalSlashIndex == -1 ?
                new String[] {uri} :
                new String[] { uri.substring(0, finalSlashIndex + 1), uri.substring(finalSlashIndex + 1)};
    }

    public static String quoteIfMoreThanOneWord(String s) {
        return s.trim().contains(" ") ? wrap(s, '"') : s;
    }

    public static String escapeDoubleQuotes(String s) {
        return s.replace("\"", "\\\"");
    }
}
