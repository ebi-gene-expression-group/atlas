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
}
