package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringUtilTest {
    private static final String UBERON_2000098_URL = "http://purl.obolibrary.org/obo/UBERON_2000098";

    @Test
    public void splitAtLastSlash() {
        assertThat(StringUtil.splitAtLastSlash(UBERON_2000098_URL), is(new String[]{"http://purl.obolibrary.org/obo/", "UBERON_2000098"}));
        assertThat(StringUtil.splitAtLastSlash("foobar"), is(new String[]{"foobar"}));
    }

    @Test
    public void quoteIfMoreThanOneWord() {
        assertThat(StringUtil.quoteIfMoreThanOneWord("foo bar"), is("\"foo bar\""));
        assertThat(StringUtil.quoteIfMoreThanOneWord("foobar"), is("foobar"));
    }

    @Test
    public void escapeDoubleQuotes() {
        String s;
        do {
            s = RandomStringUtils.random(20);
        } while(!s.contains("\""));

        Arrays.stream(StringUtil.escapeDoubleQuotes(s).split("\""))
                .forEach(str -> assertThat(str.charAt(str.length() - 1), is('\\')));
    }
}