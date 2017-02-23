package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentRun;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesTsvInputStreamTest {

    private static final String RUN_ACCESSION_1 = "RUN_ACCESSION_1";
    private static final String RUN_ACCESSION_2 = "RUN_ACCESSION_2";
    private static final OldBaselineProfile EMPTY_BASELINE_PROFILE = new OldBaselineProfile("gene_id", "gene_name");

    @Mock
    private RnaSeqBaselineExpressionsRowDeserializerBuilder expressionsBufferBuilderMock;

    @Mock
    private ExpressionsRowTsvDeserializerBaseline expressionsBufferMock;

    @Mock
    private BaselineProfileReusableBuilder baselineProfileReusableBuilder;

    private String[] expressionLevels = {"A GENE ID", "A GENE NAME", "2.22222", "0.11111"};

    @Spy
    private StringReader blah = new StringReader(
            Joiner.on('\t').join("A Gene Id", "A Gene Name", RUN_ACCESSION_1, RUN_ACCESSION_2) + "\n" +
            Joiner.on('\t').join(expressionLevels));

    @Mock
    private Reader readerMock;

    private BaselineProfilesTsvInputStream subject;


    @Before
    public void setUp() throws Exception {

        ExperimentRun experimentRuns1Mock = mock(ExperimentRun.class);
        ExperimentRun experimentRuns2Mock = mock(ExperimentRun.class);

        given(experimentRuns1Mock.getAccession()).willReturn(RUN_ACCESSION_1);
        given(experimentRuns2Mock.getAccession()).willReturn(RUN_ACCESSION_2);

        String[] headersWithoutGeneIdColumn = new String[]{RUN_ACCESSION_1, RUN_ACCESSION_2};

        given(expressionsBufferBuilderMock.build(headersWithoutGeneIdColumn)).willReturn(expressionsBufferMock);

        when(baselineProfileReusableBuilder.addExpression(any(BaselineExpression.class))).thenReturn(baselineProfileReusableBuilder);
        when(baselineProfileReusableBuilder.create()).thenReturn(EMPTY_BASELINE_PROFILE);

        subject = new BaselineProfilesTsvInputStream(blah, expressionsBufferBuilderMock, baselineProfileReusableBuilder);

    }

    @Test
    public void emptyBaselineProfileReturnsNull() {
        assertThat(subject.createProfile(), is(nullValue()));
    }

    @Test
    public void readNextShouldPollTheBuffer() throws Exception {

        BaselineExpression expression = mock(BaselineExpression.class);

        //given
        given(expressionsBufferMock.next())
                .willReturn(expression)
                .willReturn(null);
        //when
        subject.readNext();
        //then next will be invoked three times

        verify(expressionsBufferMock, times(2)).next();
    }

    @Test
    public void givenBufferIsEmptyReadNextShouldReadANewLineAndReloadTheBuffer() throws Exception {
        InOrder inOrder = inOrder(expressionsBufferMock);
        //given
        given(expressionsBufferMock.next()).willReturn(null);
        //when
        subject.readNext();
        //then
        inOrder.verify(expressionsBufferMock).reload(ArrayUtils.subarray(expressionLevels, 2, 4));
        inOrder.verify(expressionsBufferMock).next();
    }


    @Test
    public void givenAllDataHasBeenRead_ReadNextShouldReturnNull() throws Exception {
        //given
        given(expressionsBufferMock.next()).willReturn(null);
        //when
        Profile profile = subject.readNext();
        //then
        assertThat(profile, is(nullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void readCsvLineShouldThrowIllegalStateExceptionWhenThereIsAnIOProblem() throws Exception {
        //given
        given(readerMock.read(any(char[].class), anyInt(), anyInt())).willThrow(new IOException(""));
        subject = new BaselineProfilesTsvInputStream(readerMock, expressionsBufferBuilderMock, baselineProfileReusableBuilder);
        //when
        subject.readNext();
        //then expect IllegalStateException
    }


    @Test
    public void closeShouldCloseNestedResources() throws Exception {
        //given
        subject.close();
        //then
        verify(blah).close();
    }

}
