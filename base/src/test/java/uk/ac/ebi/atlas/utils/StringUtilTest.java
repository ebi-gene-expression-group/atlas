package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {
    private static final String UBERON_2000098_URL = "http://purl.obolibrary.org/obo/UBERON_2000098";

    @Test
    public void splitAtLastSlash() {
        assertThat(StringUtil.splitAtLastSlash(UBERON_2000098_URL))
                .isEqualTo(new String[]{"http://purl.obolibrary.org/obo/", "UBERON_2000098"});
        assertThat(StringUtil.splitAtLastSlash("foobar")).isEqualTo(new String[]{"foobar"});
    }

    @Test
    public void quoteIfMoreThanOneWord() {
        assertThat(StringUtil.quoteIfMoreThanOneWord("foo bar")).isEqualTo("\"foo bar\"");
        assertThat(StringUtil.quoteIfMoreThanOneWord("foobar")).isEqualTo("foobar");
    }

    @Test
    public void escapeDoubleQuotes() {
        String s;
        do {
            s = RandomStringUtils.random(20);
        } while (!s.contains("\""));

        String[] split = StringUtil.escapeDoubleQuotes(s).split("\"");
        for (int i = 0; i < split.length - 1; i++) {
            assertThat(split[i]).endsWith("\\");
        }
    }
}
