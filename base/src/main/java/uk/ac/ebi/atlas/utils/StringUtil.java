package uk.ac.ebi.atlas.utils;

public class StringUtil {
    protected StringUtil() {
        throw new UnsupportedOperationException();
    }

    public static String[] splitAtLastSlash(String uri) {
        int finalSlashIndex = uri.lastIndexOf('/');
        return finalSlashIndex == -1 ?
                new String[] {uri} :
                new String[] {uri.substring(0, finalSlashIndex + 1), uri.substring(finalSlashIndex + 1)};
    }

    public static String suffixAfterLastSlash(String uri) {
        int finalSlashIndex = uri.lastIndexOf('/');
        return finalSlashIndex == -1 ?
                uri : uri.substring(finalSlashIndex + 1);
    }

    public static String escapeDoubleQuotes(String s) {
        return s.replace("\"", "\\\"");
    }
}
