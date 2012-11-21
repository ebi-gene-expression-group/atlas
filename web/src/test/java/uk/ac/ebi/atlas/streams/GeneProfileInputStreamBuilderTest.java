/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.streams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileInputStreamBuilderTest {


    @Mock
    private InputStream inputStreamMock;

    @Mock
    private ExpressionsBuffer.Builder expressionsBufferBuilderMock;

    @Mock
    private ExpressionsBuffer expressionsBufferMock;

    @Mock
    private GeneProfilesInputStream geneProfilesInputStreamMock;

    private String[] headersMock = new String[]{"", "header_value_1", "header_value_2"};

    private GeneProfilesInputStream.Builder subject;

    @Before
    public void initSubject() {
        when(geneProfilesInputStreamMock.readCsvLine()).thenReturn(headersMock);
        when(expressionsBufferBuilderMock.forExperiment(anyString())).thenReturn(expressionsBufferBuilderMock);
        when(expressionsBufferBuilderMock.withHeaders(headersMock)).thenReturn(expressionsBufferBuilderMock);
        when(expressionsBufferBuilderMock.create()).thenReturn(expressionsBufferMock);

        subject = new GeneProfilesInputStream
                        .Builder(geneProfilesInputStreamMock, expressionsBufferBuilderMock)
                        .forExperiment("AN_EXPERIMENT", inputStreamMock);
    }



    @Test
    public void createShouldSucceedAfterExperimentAccessionHasBeenSet() {
        //when
        subject.forExperiment("AN_EXPERIMENT_ACCESSION", inputStreamMock);

        ObjectInputStream<GeneProfile> geneProfilesInputStream = subject.create();
        //then
        assertThat(geneProfilesInputStream, is(not(nullValue())));
        //and
        verify(geneProfilesInputStreamMock).setExpressionBuffer(expressionsBufferMock);

    }

    @Test
    public void shouldReadTheHeaderLineFromTheDataFile() throws IOException {
        //then
        verify(geneProfilesInputStreamMock).readCsvLine();
    }

    @Test
    public void shouldInitializeTheExpressionBufferWhenWeSetTheExperimentAccession() throws IOException {

        //then
        verify(expressionsBufferBuilderMock).withHeaders(headersMock);
        verify(expressionsBufferBuilderMock).create();
    }



}
