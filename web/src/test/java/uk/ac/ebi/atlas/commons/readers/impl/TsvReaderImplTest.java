package uk.ac.ebi.atlas.commons.readers.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TsvReaderImplTest {

    private static final String PATH_TEMPLATE = "A_PATH_TEMPLATE";
    private static final String EXPERIMENT_ACCESSION = "experimentAccession";

    private TsvReaderImpl subject;

    @Mock
    private TsvInputStreamReaderBuilder tsvInputStreamReaderBuilderMock;

    @Before
    public void setUp() throws Exception {

        String data = "#Comment\nColumn1\tColumn2\tColumn3\nData1\tData2\tData3";

        when(tsvInputStreamReaderBuilderMock.withPathTemplate(PATH_TEMPLATE)).thenReturn(tsvInputStreamReaderBuilderMock);
        when(tsvInputStreamReaderBuilderMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(tsvInputStreamReaderBuilderMock);
        when(tsvInputStreamReaderBuilderMock.build()).thenReturn(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));

        subject = new TsvReaderImpl(tsvInputStreamReaderBuilderMock);
        subject.setPathTemplate(PATH_TEMPLATE);
    }

    @Test
    public void testReadAll() {
        List<String[]> result = subject.readAll(EXPERIMENT_ACCESSION);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), hasItemInArray("Column1"));
        assertThat(result.get(0), hasItemInArray("Column2"));
        assertThat(result.get(0), hasItemInArray("Column3"));
        assertThat(result.get(1), hasItemInArray("Data1"));
        assertThat(result.get(1), hasItemInArray("Data2"));
        assertThat(result.get(1), hasItemInArray("Data3"));
    }

    @Test
    public void testReadLine() {
        String[] result = subject.readLine(EXPERIMENT_ACCESSION, 2);
        assertThat(result.length, is(3));
        assertThat(result, hasItemInArray("Data1"));
        assertThat(result, hasItemInArray("Data2"));
        assertThat(result, hasItemInArray("Data3"));
    }

    @Test
    public void testReadAndFilter() {
        TsvReaderImpl.IsComment isCommentPredicate = new TsvReaderImpl.IsComment();
        List<String[]> result = subject.readAndFilter(EXPERIMENT_ACCESSION, isCommentPredicate);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), hasItemInArray("#Comment"));
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
