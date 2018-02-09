package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringArrayUtilTest {
    @Test
    public void filterBySubstring() {
        assertThat(
                StringArrayUtil.filterBySubstring(new String[]{"aa", "ab", "ba", "aba", "cc"}, "b"),
                is(new String[] {"ab", "ba", "aba"}));
    }

    @Test
    public void substringBefore() {
        assertThat(
                StringArrayUtil.substringBefore(new String[]{"foo", "g1.one", "g2.two", "bar"}, "."),
                is(new String[] {"foo", "g1", "g2", "bar"}));
    }

    @Test
    public void indicesOf() {
        assertThat(
                StringArrayUtil.indicesOf(new String[]{"foo", "g1.one", "g2.two", "bar"}, "."),
                is(new int[]{1, 2}));
    }

    @Test
    public void indicesOfNoMatch() {
        assertThat(
                StringArrayUtil.indicesOf(new String[]{"foo", "g1.one", "g2.two", "bar"}, "z"),
                is(new int[] {}));
    }
}
