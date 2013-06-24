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

package uk.ac.ebi.atlas.configuration;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.expdesign.*;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementGeneMappingLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentManagerTest {

    private static final ExperimentType BASELINE_TYPE = ExperimentType.BASELINE;
    private static final ExperimentType DIFFERENTIAL_TYPE = ExperimentType.DIFFERENTIAL;
    private static final ExperimentType MICROARRAY_TYPE = ExperimentType.MICROARRAY;
    private static final ExperimentType TWOCOLOUR_TYPE = ExperimentType.TWOCOLOUR;
    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String TEST_EXCEPTION = "TEST_EXCEPTION";
    private static final String ARRAY_DESIGN = "ARRAY_DESIGN";

    @Mock
    private RnaSeqExpDesignWriter rnaSeqExpDesignWriterMock;

    @Mock
    private MicroArrayExpDesignWriter microArrayExpDesignWriterMock;

    @Mock
    private TwoColourExpDesignWriter twoColourExpDesignWriterMock;

    @Mock
    private ExpDesignTsvWriter expDesignTsvWriterMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private TranscriptProfilesLoader transcriptProfileLoaderMock;

    @Mock
    private ArrayDesignDao arrayDesignDaoMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private DesignElementGeneMappingLoader designElementLoaderMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    private ExpDesignWriterBuilder expDesignWriterBuilder;

    private ExperimentManager subject;

    @Before
    public void setUp() throws Exception {
        expDesignWriterBuilder = new ExpDesignWriterBuilder(
                rnaSeqExpDesignWriterMock,
                microArrayExpDesignWriterMock,
                twoColourExpDesignWriterMock);

        when(expDesignTsvWriterMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(csvWriterMock);
        when(expDesignTsvWriterMock.getFileAbsolutePath()).thenReturn("UNIT_TEST");

        when(transcriptProfileLoaderMock.load(EXPERIMENT_ACCESSION)).thenReturn(0);
        when(configurationTraderMock.getMicroarrayExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentConfigurationMock);
        when(microarrayExperimentConfigurationMock.getArrayDesignNames()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN)));

        subject = new ExperimentManager(expDesignTsvWriterMock, expDesignWriterBuilder, transcriptProfileLoaderMock,
                arrayDesignDaoMock, configurationTraderMock, designElementLoaderMock);
    }

    @Test
    public void testGenerateExpDesign() throws Exception {
        subject.generateExpDesign(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        verify(expDesignTsvWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testGenerateExpDesignForBaseline() throws Exception {
        subject.generateExpDesign(EXPERIMENT_ACCESSION, BASELINE_TYPE);
        verify(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testGenerateExpDesignForDifferential() throws Exception {
        subject.generateExpDesign(EXPERIMENT_ACCESSION, DIFFERENTIAL_TYPE);
        verify(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testGenerateExpDesignForMicroArray() throws Exception {
        subject.generateExpDesign(EXPERIMENT_ACCESSION, MICROARRAY_TYPE);
        verify(microArrayExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testGenerateExpDesignForTwoColour() throws Exception {
        subject.generateExpDesign(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE);
        verify(twoColourExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test(expected = IllegalStateException.class)
    public void testCsvWriterIOException() throws Exception {
        Mockito.doThrow(new IOException(TEST_EXCEPTION)).when(csvWriterMock).close();
        subject.generateExpDesign(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE);
    }

    @Test(expected = IllegalStateException.class)
    public void testParseExceptionForBaseLine() throws Exception {
        Mockito.doThrow(new ParseException(TEST_EXCEPTION)).when(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        subject.generateExpDesign(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
    }

    @Test(expected = IllegalStateException.class)
    public void testParseExceptionForTwoColour() throws Exception {
        Mockito.doThrow(new ParseException(TEST_EXCEPTION)).when(twoColourExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        subject.generateExpDesign(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE);
    }

    @Test
    public void testLoadTranscripts() throws Exception {
        subject.loadTranscripts(EXPERIMENT_ACCESSION);
        verify(transcriptProfileLoaderMock).load(EXPERIMENT_ACCESSION);
    }

    @Test(expected = IllegalStateException.class)
    public void testTranscriptLoaderException() throws Exception {
        Mockito.doThrow(new IOException(TEST_EXCEPTION)).when(transcriptProfileLoaderMock).load(EXPERIMENT_ACCESSION);
        subject.loadTranscripts(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testLoadArrayDesignPresent() throws Exception {
        when(arrayDesignDaoMock.isArrayDesignPresent(ARRAY_DESIGN)).thenReturn(true);
        subject.loadArrayDesign(EXPERIMENT_ACCESSION);
        verify(designElementLoaderMock, times(0)).loadMappings(ARRAY_DESIGN);
    }

    @Test
    public void testLoadArrayDesignNotPresent() throws Exception {
        when(arrayDesignDaoMock.isArrayDesignPresent(ARRAY_DESIGN)).thenReturn(false);
        subject.loadArrayDesign(EXPERIMENT_ACCESSION);
        verify(designElementLoaderMock, times(1)).loadMappings(ARRAY_DESIGN);
    }
}