///*
// * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
//package uk.ac.ebi.atlas.experimentloader;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import uk.ac.ebi.atlas.model.ExperimentType;
//
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.util.UUID;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ExperimentDTORowMapperTest {
//
//    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
//    private static final String RNASEQ_MRNA_BASELINE = "RNASEQ_MRNA_BASELINE";
//
//    @Mock
//    private ResultSet resultSetMock;
//
//    @Mock
//    private ResultSetMetaData metadataMock;
//
//    private ExperimentDTORowMapper subject;
//
//    @Before
//    public void setUp() throws Exception {
//        subject = new ExperimentDTORowMapper();
//        when(resultSetMock.getString("accession")).thenReturn(EXPERIMENT_ACCESSION);
//        when(resultSetMock.getString("type")).thenReturn(RNASEQ_MRNA_BASELINE);
//        when(resultSetMock.getObject("access_key")).thenReturn(UUID.randomUUID());
//    }
//
//    @Test
//    public void buildExperimentDTOShouldSucceed() throws Exception {
//        given(resultSetMock.getMetaData()).willReturn(metadataMock);
//        ExperimentDTO experimentDTO = subject.buildExperimentDTO(resultSetMock);
//        assertThat(experimentDTO.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
//        assertThat(experimentDTO.getExperimentType(), is(ExperimentType.valueOf(RNASEQ_MRNA_BASELINE)));
//    }
//}