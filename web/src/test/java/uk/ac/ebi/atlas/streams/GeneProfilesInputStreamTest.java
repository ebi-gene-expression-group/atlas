package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.Expression;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesInputStreamTest {

    public static final String RUN_ACCESSION_1 = "RUN_ACCESSION_1";
    public static final String RUN_ACCESSION_2 = "RUN_ACCESSION_2";

    @Mock
    private CSVReader csvReaderMock;

    @Mock
    private ExpressionsBuffer.Builder expressionsBufferBuilderMock;

    @Mock
    private ExpressionsBuffer expressionsBufferMock;

    private String[] expressionLevels = new String[]{"GENE_ID", "2.22222", "0.11111"};

    private GeneProfilesInputStream subject;


    @Before
    public void initSubject() throws Exception {

        ExperimentRun experimentRuns1Mock = mock(ExperimentRun.class);
        ExperimentRun experimentRuns2Mock = mock(ExperimentRun.class);

        given(experimentRuns1Mock.getAccession()).willReturn(RUN_ACCESSION_1);
        given(experimentRuns2Mock.getAccession()).willReturn(RUN_ACCESSION_2);

        String [] headers = new String[]{"", RUN_ACCESSION_1, RUN_ACCESSION_2};

        given(csvReaderMock.readNext())
                .willReturn(headers)
                .willReturn(expressionLevels)
                .willReturn(null);

        given(expressionsBufferBuilderMock.forExperiment(anyString())).willReturn(expressionsBufferBuilderMock);
        given(expressionsBufferBuilderMock.withHeaders(headers)).willReturn(expressionsBufferBuilderMock);
        given(expressionsBufferBuilderMock.create()).willReturn(expressionsBufferMock);

        GeneProfile.Builder geneProfileBuilderMock = mock(GeneProfile.Builder.class);
        when(geneProfileBuilderMock.addExpression(any(Expression.class))).thenReturn(geneProfileBuilderMock);

        subject = new GeneProfilesInputStream(csvReaderMock, "AN_ACCESSION", expressionsBufferBuilderMock, geneProfileBuilderMock);

    }

    @Test
    public void readNextShouldPollTheBuffer() throws Exception {

        Expression expression = mock(Expression.class);

        //given
        given(expressionsBufferMock.poll())
                .willReturn(expression)
                .willReturn(null);
        //when
        subject.readNext();
        //then poll will be invoked three times

        verify(expressionsBufferMock, times(2)).poll();
    }

    @Test
    public void givenBufferIsEmptyReadNextShouldReadANewLineAndReloadTheBuffer() throws Exception {
        InOrder inOrder = inOrder(expressionsBufferMock);
        //given
        given(expressionsBufferMock.poll()).willReturn(null);
        //when
        subject.readNext();
        //then
        verify(csvReaderMock, times(3)).readNext();
        //and
        inOrder.verify(expressionsBufferMock).reload(expressionLevels);
        inOrder.verify(expressionsBufferMock).poll();
    }


    @Test
    public void givenAllDataHasBeenRead_ReadNextShouldReturnNull() throws Exception {
        //given
        given(expressionsBufferMock.poll()).willReturn(null);
        //and
        given(csvReaderMock.readNext()).willReturn(null);
        //when
        GeneProfile geneProfile = subject.readNext();
        //then
        verify(csvReaderMock, times(2)).readNext();
        //and
        assertThat(geneProfile, is(nullValue()));
    }

    @Test
    public void readCsvLineShouldUseCsvReader() throws Exception {
        //given
        subject.readNext();
        //then
        verify(csvReaderMock, times(3)).readNext();
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
