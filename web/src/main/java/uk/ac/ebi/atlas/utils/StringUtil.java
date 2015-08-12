package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named
@Scope("singleton")
public class StringUtil {

    public String lowerCaseIfNotAllUpperCase(String s) {
        return StringUtils.isAllUpperCase(s) ? s : StringUtils.lowerCase(s);
    }

    public static String removeSurroundingQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }

        return s;
    }

    public static String quote(String s) {
        return "\"" + s + "\"";
    }

    public static String quoteIfMoreThanOneWord(String s) {
        return moreThanOneWord(s) ? quote(s) : s;
    }

    private static boolean moreThanOneWord(String s) {
        return s.trim().contains(" ");
    }

    public static ImmutableList<String> quotePhrases(Iterable<String> strings) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (String s : strings) {
            builder.add(quoteIfMoreThanOneWord(s));
        }

        return builder.build();
    }

}
