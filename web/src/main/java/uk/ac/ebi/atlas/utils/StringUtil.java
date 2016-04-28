package uk.ac.ebi.atlas.utils;

import static org.apache.commons.lang3.StringUtils.isAllUpperCase;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.wrap;

public class StringUtil {

    public String lowerCaseIfNotAllUpperCase(String s) {
        return isAllUpperCase(s) ? s : lowerCase(s);
    }

    public static String[] splitAtLastSlash(String uri) {
        int finalSlashIndex = uri.lastIndexOf('/');
        return finalSlashIndex == -1 ? new String[] {uri} :  new String[] { uri.substring(0, finalSlashIndex + 1), uri.substring(finalSlashIndex + 1)};
    }

    public static String quoteIfMoreThanOneWord(String s) {
        return s.trim().contains(" ") ? wrap(s, '"') : s;
    }

}
