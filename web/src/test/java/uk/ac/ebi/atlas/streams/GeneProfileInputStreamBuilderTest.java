package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileInputStreamBuilderTest {

    @Mock
    private CSVReader csvReaderMock;

    @Mock
    private InputStream inputStreamMock;

    @Mock
    private ExpressionsBuffer.Builder expressionsBufferBuilderMock;

    @Mock
    private ExpressionsBuffer expressionsBufferMock;

    private GeneProfilesInputStream.Builder subject;

    @Before
    public void initMocks() throws IOException {
        String[] headers = new String[]{"", "header_value_1", "header_value_2"};
        when(csvReaderMock.readNext()).thenReturn(headers);
        when(expressionsBufferBuilderMock.forExperiment(anyString())).thenReturn(expressionsBufferBuilderMock);
        when(expressionsBufferBuilderMock.withHeaders(headers)).thenReturn(expressionsBufferBuilderMock);
        when(expressionsBufferBuilderMock.create()).thenReturn(expressionsBufferMock);
    }

    @Before
    public void initSubject() {
        subject = new GeneProfilesInputStream.Builder(expressionsBufferBuilderMock).forDataFileInputStream(inputStreamMock)
                .injectCsvReader(csvReaderMock);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenExperimentRunsHaventBeenSet() {
        //when
        subject.create();
    }

    @Test
    public void shouldBeOkWhenExperimentRunsHaventBeenSet() {
        //given
        //geneProfileInputStream.expressionBuffer
        //when
        subject.withExperimentAccession("AN_EXPERIMENT_ACCESSION");
        //then
        assertThat(subject.create(), is(not(nullValue())));
    }

    @Test
    public void shouldHaveReadHeaderLineOnCreate() throws IOException {
        //when
        subject.withExperimentAccession("AN_EXPERIMENT_ACCESSION");
        //then
        verify(csvReaderMock).readNext();
    }


}
