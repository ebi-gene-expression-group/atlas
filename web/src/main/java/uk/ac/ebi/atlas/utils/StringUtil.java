package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named
@Scope("singleton")
public class StringUtil {

    public String lowerCaseIfNotAllUpperCase(String s) {
        return StringUtils.isAllUpperCase(s) ? s : StringUtils.lowerCase(s);
    }

    public static String trimSurroundingQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }

        return s;
    }
}
