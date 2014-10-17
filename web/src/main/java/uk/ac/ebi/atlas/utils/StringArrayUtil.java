package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class StringArrayUtil {

    public static String[] contains(String[] strings, String substring) {
        ArrayList<String> result = new ArrayList<>(strings.length);

        int count = 0;
        for (String s : strings) {
            if (s.contains(substring)) {
                result.add(s);
                count++;
            }
        }

        return result.toArray(new String[count]);
    }

    public static String[] substringBefore(String[] strings, String separator) {
        String[] result = new String[strings.length];

        for (int i = 0; i< strings.length; i++) {
            result[i] = StringUtils.substringBefore(strings[i], separator);
        }

        return result;
    }
}
