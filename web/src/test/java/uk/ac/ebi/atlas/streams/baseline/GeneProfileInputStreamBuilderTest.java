///*
// * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *
// *
// * For further details of the Gene Expression Atlas project, including source code,
// * downloads and documentation, please see:
// *
// * http://gxa.github.com/gxa
// */
//
//package uk.ac.ebi.atlas.streams;
//
//import au.com.bytecode.opencsv.CSVReader;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
//import uk.ac.ebi.atlas.model.GeneProfile;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//ToDo: find test cases and fix test
//@RunWith(MockitoJUnitRunner.class)
//public class GeneProfileInputStreamBuilderTest {
//
//    @Mock
//    private CSVReader csvReaderMock;
//
//    @Mock
//    private InputStream inputStreamMock;
//
//    @Mock
//    private BaselineExpressionsBuffer.TsvInputStreamBuilder expressionsBufferBuilderMock;
//
//    @Mock
//    private BaselineExpressionsBuffer expressionsBufferMock;
//
//
//    private String[] headersMock = new String[]{"", "header_value_1", "header_value_2"};
//
//    private GeneProfilesInputStream.TsvInputStreamBuilder subject;
//
//    @Before
//    public void initSubject() throws IOException {
//
//        given(csvReaderMock.readNext()).willReturn(headersMock);
//
//        when(expressionsBufferBuilderMock.forExperiment(anyString())).thenReturn(expressionsBufferBuilderMock);
//        when(expressionsBufferBuilderMock.withHeaders(headersMock)).thenReturn(expressionsBufferBuilderMock);
//        when(expressionsBufferBuilderMock.create()).thenReturn(expressionsBufferMock);
//
//
//        subject = new GeneProfilesInputStream
//                .TsvInputStreamBuilder(expressionsBufferBuilderMock, null)
//                .forExperiment("AN_EXPERIMENT", inputStreamMock);
//    }
//
//
//    @Test
//    public void createShouldSucceedAfterExperimentAccessionHasBeenSet() {
//        //when
//        subject.forExperiment("AN_EXPERIMENT_ACCESSION", inputStreamMock);
//
//        ObjectInputStream<GeneProfile> geneProfilesInputStream = subject.create();
//        //then
//        assertThat(geneProfilesInputStream, is(not(nullValue())));
//
//
//    }
//
//
//    @Test
//    public void shouldInitializeTheExpressionBufferWhenWeSetTheExperimentAccession() throws IOException {
//
//        //then
//        verify(expressionsBufferBuilderMock).withHeaders(headersMock);
//        verify(expressionsBufferBuilderMock).create();
//    }
//
//
//}
