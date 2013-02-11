package uk.ac.ebi.atlas.model.readers;

import com.google.common.base.Predicate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class AnalysisMethodsTsvReaderTest {

    private static final String PATH_TEMPLATE = "A_PATH_TEMPLATE";

    private AnalysisMethodsTsvReader subject = new AnalysisMethodsTsvReader(PATH_TEMPLATE);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTrim() throws Exception {
        assertThat(subject.trim(new String[]{" hello", "   boy   "}), is(new String[]{"hello", "boy"}));
    }

    @Test
    public void testIsComment(){
        AbstractTsvReader.IsComment isCommentPredicate = new AnalysisMethodsTsvReader.IsComment();
        assertThat(isCommentPredicate.apply("  #  Xyz"),is(true));
        assertThat(isCommentPredicate.apply(" #Xyz"),is(true));
        assertThat(isCommentPredicate.apply("#Xyz"),is(true));
    }
}
