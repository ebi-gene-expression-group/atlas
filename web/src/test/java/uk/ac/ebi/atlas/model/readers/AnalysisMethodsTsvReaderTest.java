package uk.ac.ebi.atlas.model.readers;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class AnalysisMethodsTsvReaderTest {

    private AnalysisMethodsTsvReader subject = new AnalysisMethodsTsvReader();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTrim() throws Exception {
        assertThat(subject.trim(new String[]{" hello", "   boy   "}), is(new String[]{"hello", "boy"}));
    }
}
