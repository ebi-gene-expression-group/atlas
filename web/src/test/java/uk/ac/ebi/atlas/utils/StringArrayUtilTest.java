package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringArrayUtilTest {

    @Test
    public void contains() {
        assertThat(StringArrayUtil.contains(new String[]{"aa", "ab", "ba", "aba", "cc"}, "b"), is(new String[] {"ab", "ba", "aba"}));
    }

    @Test
    public void substringBefore() {
        assertThat(StringArrayUtil.substringBefore(new String[]{"foo", "g1.one", "g2.two", "bar"}, "."), is(new String[] {"foo", "g1", "g2", "bar"}));
    }
}
