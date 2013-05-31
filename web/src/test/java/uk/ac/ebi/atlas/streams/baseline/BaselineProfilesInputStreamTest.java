/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.streams.baseline;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilePreconditionBackedBuilder;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesInputStreamTest {

    public static final String RUN_ACCESSION_1 = "RUN_ACCESSION_1";
    public static final String RUN_ACCESSION_2 = "RUN_ACCESSION_2";

    @Mock
    private CSVReader csvReaderMock;

    @Mock
    private BaselineExpressionsBufferBuilder expressionsBufferBuilderMock;

    @Mock
    private BaselineExpressionsBuffer expressionsBufferMock;

    private String[] expressionLevels = new String[]{"GENE_ID", "2.22222", "0.11111"};
    private String[] expressionLevelsWithoutGeneIdColumn = new String[]{"2.22222", "0.11111"};

    private BaselineProfilesInputStream subject;


    @Before
    public void initSubject() throws Exception {

        ExperimentRun experimentRuns1Mock = mock(ExperimentRun.class);
        ExperimentRun experimentRuns2Mock = mock(ExperimentRun.class);

        given(experimentRuns1Mock.getAccession()).willReturn(RUN_ACCESSION_1);
        given(experimentRuns2Mock.getAccession()).willReturn(RUN_ACCESSION_2);

        String[] headers = new String[]{"", RUN_ACCESSION_1, RUN_ACCESSION_2};
        String[] headersWithoutGeneIdColumn = new String[]{RUN_ACCESSION_1, RUN_ACCESSION_2};

        given(csvReaderMock.readNext())
                .willReturn(headers)
                .willReturn(expressionLevels)
                .willReturn(null);

        given(expressionsBufferBuilderMock.forExperiment(anyString())).willReturn(expressionsBufferBuilderMock);
        given(expressionsBufferBuilderMock.withHeaders(headersWithoutGeneIdColumn)).willReturn(expressionsBufferBuilderMock);
        given(expressionsBufferBuilderMock.build()).willReturn(expressionsBufferMock);

        BaselineProfilePreconditionBackedBuilder geneProfileBuilderMock = mock(BaselineProfilePreconditionBackedBuilder.class);
        when(geneProfileBuilderMock.addExpression(any(BaselineExpression.class))).thenReturn(geneProfileBuilderMock);

        subject = new BaselineProfilesInputStream(csvReaderMock, "AN_ACCESSION", expressionsBufferBuilderMock, geneProfileBuilderMock);

    }

    @Test
    public void readNextShouldPollTheBuffer() throws Exception {

        BaselineExpression expression = mock(BaselineExpression.class);

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
        inOrder.verify(expressionsBufferMock).reload(expressionLevelsWithoutGeneIdColumn);
        inOrder.verify(expressionsBufferMock).poll();
    }


    @Test
    public void givenAllDataHasBeenRead_ReadNextShouldReturnNull() throws Exception {
        //given
        given(expressionsBufferMock.poll()).willReturn(null);
        //and
        given(csvReaderMock.readNext()).willReturn(null);
        //when
        Profile profile = subject.readNext();
        //then
        verify(csvReaderMock, times(2)).readNext();
        //and
        assertThat(profile, is(nullValue()));
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
