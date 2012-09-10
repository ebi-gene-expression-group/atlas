package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptExpressionLevel;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionLevelsInputStreamTest {

    public static final String RUN_ACCESSION_1 = "RUN_ACCESSION_1";
    public static final String RUN_ACCESSION_2 = "RUN_ACCESSION_2";

    @Mock
    private CSVReader csvReaderMock;

    @Mock
    private ExpressionLevelsBuffer expressionLevelsBufferMock;


    private String[] rpkmLine = new String[]{"TRANSCRIPT_ID", "2.22222", "0.11111"};

    private List<ExperimentRun> experimentRunsMock;

    private ExpressionLevelInputStream subject;


    @Before
    public void initSubject() throws Exception {

        ExperimentRun experimentRuns1Mock = mock(ExperimentRun.class);
        ExperimentRun experimentRuns2Mock = mock(ExperimentRun.class);


        given(experimentRuns1Mock.getRunAccession()).willReturn(RUN_ACCESSION_1);
        given(experimentRuns2Mock.getRunAccession()).willReturn(RUN_ACCESSION_2);

        experimentRunsMock = Lists.newArrayList(experimentRuns1Mock, experimentRuns2Mock);

        given(csvReaderMock.readNext())
                .willReturn(new String[]{"", RUN_ACCESSION_1, RUN_ACCESSION_2})
                .willReturn(rpkmLine);

        subject = new ExpressionLevelInputStream(csvReaderMock, experimentRunsMock);

        subject.setExpressionLevelBuffer(expressionLevelsBufferMock);
    }

    @Test
    public void readNextShouldPollTheBuffer() throws Exception {

        TranscriptExpressionLevel transcriptExpressionLevelMock = mock(TranscriptExpressionLevel.class);

        //given
        given(expressionLevelsBufferMock.poll()).willReturn(transcriptExpressionLevelMock);
        //when
        subject.readNext();
        //then
        verify(expressionLevelsBufferMock).poll();
    }

    @Test
    public void givenBufferIsEmptyReadNextShouldReadANewLineAndReloadTheBuffer() throws Exception {
        InOrder inOrder = inOrder(expressionLevelsBufferMock);
        //given
        given(expressionLevelsBufferMock.poll()).willReturn(null);
        //when
        subject.readNext();
        //then
        verify(csvReaderMock, times(2)).readNext();
        //and
        inOrder.verify(expressionLevelsBufferMock).reload(rpkmLine);
        inOrder.verify(expressionLevelsBufferMock).poll();
    }


    @Test
    public void givenAllDataHasBeenRead_ReadNextShouldReturnNull() throws Exception {
        InOrder inOrder = inOrder(expressionLevelsBufferMock);
        //given
        given(expressionLevelsBufferMock.poll()).willReturn(null);
        //and
        given(csvReaderMock.readNext()).willReturn(null);
        //when
        TranscriptExpressionLevel transcriptExpressionLevel = subject.readNext();
        //then
        String[] values = verify(csvReaderMock, times(2)).readNext();
        //and
        assertThat(transcriptExpressionLevel, is(nullValue()));
    }

    @Test
    public void readCsvLineShouldUseCsvReader() throws Exception {
        //given
        subject.readNext();
        //then
        verify(csvReaderMock, times(2)).readNext();
    }

    @Test(expected = IllegalStateException.class)
    public void readCsvLineShouldThrowIllegalStateExceptionWhenThereIsAnIOProblem() throws Exception {
        //given
        given(csvReaderMock.readNext()).willThrow(new IOException(""));
        //when
        subject.readNext();
        //then expect IllegalStateException
    }


    @Test
    public void closeShouldCloseNestedResources() throws Exception {
        //given
        subject.close();
        //then
        verify(csvReaderMock).close();
    }

    @Test(expected = IOException.class)
    public void closeShouldThrowIOExceptionWhenNestedResourcesThrowIOException() throws Exception {
        //given
        willThrow(new IOException("")).given(csvReaderMock).close();
        //when
        subject.close();
        //then expect exception to be thrown
    }


}
