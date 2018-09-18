package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class StringUtilTest {
    private static final String UBERON_2000098_URL = "http://purl.obolibrary.org/obo/UBERON_2000098";

    @Test
    public void splitAtLastSlash() {
        assertThat(StringUtil.splitAtLastSlash(UBERON_2000098_URL))
                .isEqualTo(new String[]{"http://purl.obolibrary.org/obo/", "UBERON_2000098"});
        assertThat(StringUtil.splitAtLastSlash("foobar")).isEqualTo(new String[]{"foobar"});
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

    @Test
    public void suffixAfterLastSlashTrimsEverythingBeforeTheLastSlash() {
        String randomWord = randomAlphanumeric(20);
        assertThat(StringUtil.suffixAfterLastSlash(randomWord))
                .isEqualTo(StringUtil.suffixAfterLastSlash("/" + randomWord))
                .isEqualTo(StringUtil.suffixAfterLastSlash(randomAlphanumeric(20) + "/" + randomWord))
                .isEqualTo(randomWord);

        assertThat(StringUtil.suffixAfterLastSlash(""))
                .isEmpty();

        assertThatNullPointerException().isThrownBy(() -> StringUtil.suffixAfterLastSlash(null));
    }
}
