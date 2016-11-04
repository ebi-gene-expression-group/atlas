package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringUtilTest {
    private static final String UBERON_2000098_URL = "http://purl.obolibrary.org/obo/UBERON_2000098";

    @Test
    public void splitAtLastSlash() throws Exception {
        assertThat(StringUtil.splitAtLastSlash(UBERON_2000098_URL), is(new String[]{"http://purl.obolibrary.org/obo/", "UBERON_2000098"}));
        assertThat(StringUtil.splitAtLastSlash("foobar"), is(new String[]{"foobar"}));
    }

    @Test
    public void quoteIfMoreThanOneWord() throws Exception {
        assertThat(StringUtil.quoteIfMoreThanOneWord("foo bar"), is("\"foo bar\""));
        assertThat(StringUtil.quoteIfMoreThanOneWord("foobar"), is("foobar"));
    }

}