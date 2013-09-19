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

package uk.ac.ebi.atlas.experimentloader;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.solr.admin.index.conditions.IndexCommand;
import uk.ac.ebi.atlas.solr.admin.index.conditions.IndexCommandTrader;
import uk.ac.ebi.atlas.solr.admin.index.conditions.IndexOperation;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDAO;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCRUDTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String TEST_EXCEPTION = "TEST_EXCEPTION";
    private static final String ARRAY_DESIGN = "ARRAY_DESIGN";
    private static final String ACCESS_KEY = "AN_UUID";

    @Mock
    private ExperimentDesignFileWriter experimentDesignFileWriterMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private TranscriptProfilesLoader transcriptProfileLoaderMock;

    @Mock
    private ArrayDesignDao arrayDesignDaoMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private DesignElementMappingLoader designElementLoaderMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    private ExperimentCRUD subject;

    @Mock
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilderMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private TranscriptProfileDAO transcriptProfileDAOMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    DifferentialExperiment differentialExperimentMock;

    @Mock
    private IndexCommandTrader indexCommandTraderMock;

    @Mock
    private IndexCommand indexCommandMock;

    @Before
    public void setUp() throws Exception {

        when(transcriptProfileLoaderMock.load(EXPERIMENT_ACCESSION)).thenReturn(0);
        when(configurationTraderMock.getMicroarrayExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentConfigurationMock);
        when(microarrayExperimentConfigurationMock.getArrayDesignNames()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN)));

        given(experimentDesignFileWriterBuilderMock.forExperimentAccession(EXPERIMENT_ACCESSION)).willReturn(experimentDesignFileWriterBuilderMock);
        given(experimentDesignFileWriterBuilderMock.withExperimentType(ExperimentType.BASELINE)).willReturn(experimentDesignFileWriterBuilderMock);
        given(experimentDesignFileWriterBuilderMock.withExperimentType(ExperimentType.DIFFERENTIAL)).willReturn(experimentDesignFileWriterBuilderMock);
        given(experimentDesignFileWriterBuilderMock.build()).willReturn(experimentDesignFileWriterMock);

        given(indexCommandTraderMock.getIndexCommand(eq(EXPERIMENT_ACCESSION), any(IndexOperation.class))).willReturn(indexCommandMock);
        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(differentialExperimentMock);


        subject = new ExperimentCRUD(transcriptProfileLoaderMock,
                arrayDesignDaoMock, configurationTraderMock, designElementLoaderMock, experimentDAOMock, transcriptProfileDAOMock,
                experimentDesignFileWriterBuilderMock, experimentTraderMock, indexCommandTraderMock);
    }

    @Test
    public void generateExperimentDesignShouldUseTheExperimentDesignWriter() throws Exception {

        subject.generateExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        verify(experimentDesignFileWriterBuilderMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(experimentDesignFileWriterBuilderMock).withExperimentType(ExperimentType.BASELINE);
        verify(experimentDesignFileWriterBuilderMock).build();
        verify(experimentDesignFileWriterMock).write(EXPERIMENT_ACCESSION);
    }

    @Test(expected = IOException.class)
    public void shouldThrowIllegalStateExceptionWhenWritingExperimentDesignFails() throws Exception {
        willThrow(new IOException()).given(experimentDesignFileWriterMock).write(EXPERIMENT_ACCESSION);
        subject.generateExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
    }

    @Test
    public void testLoadTranscripts() throws Exception {
        subject.loadTranscripts(EXPERIMENT_ACCESSION);
        verify(transcriptProfileLoaderMock).load(EXPERIMENT_ACCESSION);
    }

    @Test(expected = IllegalStateException.class)
    public void testTranscriptLoaderException() throws Exception {
        willThrow(new IOException(TEST_EXCEPTION)).given(transcriptProfileLoaderMock).load(EXPERIMENT_ACCESSION);
        subject.loadTranscripts(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testLoadArrayDesignPresent() throws Exception {
        when(arrayDesignDaoMock.isArrayDesignPresent(ARRAY_DESIGN)).thenReturn(true);
        subject.loadArrayDesign(EXPERIMENT_ACCESSION, ArrayDesignType.MICRO_ARRAY);
        verify(designElementLoaderMock, times(0)).loadMappings(ARRAY_DESIGN, ArrayDesignType.MICRO_ARRAY);
    }

    @Test
    public void testLoadArrayDesignNotPresent() throws Exception {
        when(arrayDesignDaoMock.isArrayDesignPresent(ARRAY_DESIGN)).thenReturn(false);
        subject.loadArrayDesign(EXPERIMENT_ACCESSION, ArrayDesignType.MICRO_ARRAY);
        verify(designElementLoaderMock).loadMappings(ARRAY_DESIGN, ArrayDesignType.MICRO_ARRAY);
    }

    @Test
    public void updateExperimentShouldDelegateToDAO() throws Exception {
        subject.updateExperiment(EXPERIMENT_ACCESSION, true);
        verify(experimentDAOMock).updateExperiment(EXPERIMENT_ACCESSION, true);
    }

    @Test
    public void updateExperimentDesignShouldRemoveExperimentFromCache() throws Exception {
        subject.updateExperimentDesign(new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, null, false, ACCESS_KEY));
        verify(experimentTraderMock).removeExperimentFromCache(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        verify(indexCommandMock).execute();
    }
}