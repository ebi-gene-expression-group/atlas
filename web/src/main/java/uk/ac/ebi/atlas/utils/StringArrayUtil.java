package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.strip;
import static uk.ac.ebi.atlas.utils.StringUtil.quoteIfMoreThanOneWord;

public class StringArrayUtil {

    public static String[] filterBySubstring(String[] strings, String substring) {
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

        for (int i = 0; i < strings.length; i++) {
            result[i] = StringUtils.substringBefore(strings[i], separator);
        }

        return result;
    }


    public static int[] indicesOf(String[] strings, String substring) {
        int[] result = new int[strings.length];

        int count = 0;
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            if (s.contains(substring)) {
                result[count++] = i;
            }
        }

        return Arrays.copyOf(result, count);
    }

    public static String[] filterByIndices(String[] strings, int[] indices) {
        String[] result = new String[indices.length];

        for (int i = 0; i < indices.length; i++) {
            result[i] = strings[indices[i]];
        }

        return result;
    }

    public static String[] removeSurroundingQuotes(String[] strings) {
        String[] result = new String[strings.length];

        for (int i = 0; i < strings.length; i++) {
            result[i] = strip(strings[i], "\"");
        }

        return result;
    }

    public static String[] quotePhrases(String[] strings) {
        String[] result = new String[strings.length];

        for (int i = 0; i < strings.length; i++) {
            result[i] = quoteIfMoreThanOneWord(strings[i]);
        }

        return result;
    }
}
