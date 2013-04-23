package uk.ac.ebi.atlas.commons.readers.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvInputStreamReaderBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class TsvReaderImplTest {

    private static final String PATH_TEMPLATE = "A_PATH_TEMPLATE";
    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    private TsvReaderImpl subject;

    @Mock
    private TsvInputStreamReaderBuilder tsvInputStreamReaderBuilderMock;

    @Before
    public void setUp() throws Exception {
        subject = new TsvReaderImpl(tsvInputStreamReaderBuilderMock);
        subject.setPathTemplate(PATH_TEMPLATE);
    }

    @Test
    public void testIsComment() {
        TsvReaderImpl.IsComment isCommentPredicate = new TsvReaderImpl.IsComment();
        assertThat(isCommentPredicate.apply("  #  Xyz"), is(true));
        assertThat(isCommentPredicate.apply(" #Xyz"), is(true));
        assertThat(isCommentPredicate.apply("#Xyz"), is(true));
    }

    @Test
    public void testIsNotComment() {
        TsvReaderImpl.IsNotComment isNotCommentPredicate = new TsvReaderImpl.IsNotComment();
        assertThat(isNotCommentPredicate.apply("  #  Xyz"), is(false));
        assertThat(isNotCommentPredicate.apply(" #Xyz"), is(false));
        assertThat(isNotCommentPredicate.apply("#Xyz"), is(false));
        assertThat(isNotCommentPredicate.apply("Xyz"), is(true));
        assertThat(isNotCommentPredicate.apply("Xy#z"), is(true));
        assertThat(isNotCommentPredicate.apply("Xyz # "), is(true));
    }
}
