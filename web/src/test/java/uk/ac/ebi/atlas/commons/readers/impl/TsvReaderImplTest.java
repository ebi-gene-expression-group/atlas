package uk.ac.ebi.atlas.commons.readers.impl;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class TsvReaderImplTest {

    private static final String PATH_TEMPLATE = "A_PATH_TEMPLATE";

    private TsvReaderImpl subject;

    @Before
    public void setUp() throws Exception {
        subject = new TsvReaderImpl();
        subject.setPathTemplate(PATH_TEMPLATE);
    }

    @Test
    public void testIsComment() {
        TsvReaderImpl.IsComment isCommentPredicate = new TsvReaderImpl.IsComment();
        assertThat(isCommentPredicate.apply("  #  Xyz"), is(true));
        assertThat(isCommentPredicate.apply(" #Xyz"), is(true));
        assertThat(isCommentPredicate.apply("#Xyz"), is(true));
    }
}
