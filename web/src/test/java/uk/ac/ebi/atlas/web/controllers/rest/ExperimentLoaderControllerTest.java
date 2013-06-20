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

package uk.ac.ebi.atlas.web.controllers.rest;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.configuration.ExperimentConfiguration;
import uk.ac.ebi.atlas.expdesign.*;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentLoaderControllerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String BASELINE_TYPE = "BASELINE";
    private static final String TEST_EXCEPTION = "TEST_EXCEPTION";
    public static final String DIFFERENTIAL_TYPE = "DIFFERENTIAL";
    public static final String MICROARRAY_TYPE = "MICROARRAY";
    public static final String TWOCOLOUR_TYPE = "TWOCOLOUR";

    @Mock
    private ConfigurationDao configurationDaoMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

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

    private ExpDesignWriterBuilder expDesignWriterBuilder;

    private ExperimentLoaderController subject;

    @Before
    public void setUp() throws Exception {
        expDesignWriterBuilder = new ExpDesignWriterBuilder(
                rnaSeqExpDesignWriterMock,
                microArrayExpDesignWriterMock,
                twoColourExpDesignWriterMock);

        when(expDesignTsvWriterMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(csvWriterMock);
        when(expDesignTsvWriterMock.getFileAbsolutePath()).thenReturn("UNIT_TEST");

        subject = new ExperimentLoaderController(configurationDaoMock, expDesignTsvWriterMock, expDesignWriterBuilder);

        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(null);
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(BASELINE_TYPE))).thenReturn(1);
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(1);
        when(configurationDaoMock.getExperimentConfigurations()).thenReturn(
                Lists.newArrayList(new ExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(BASELINE_TYPE))));
    }

    @Test
    public void testLoadExpDesignForBaseline() throws Exception {
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignForDifferential() throws Exception {
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(DIFFERENTIAL_TYPE))).thenReturn(1);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, DIFFERENTIAL_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignMicroArray() throws Exception {
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(MICROARRAY_TYPE))).thenReturn(1);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, MICROARRAY_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(microArrayExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignTwoColour() throws Exception {
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(TWOCOLOUR_TYPE))).thenReturn(1);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(twoColourExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testIOException() throws Exception {

        Mockito.doThrow(new IOException(TEST_EXCEPTION)).when(csvWriterMock).close();

        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE), is(TEST_EXCEPTION));
    }

    @Test
    public void testParseException() throws Exception {

        Mockito.doThrow(new ParseException(TEST_EXCEPTION)).when(twoColourExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);

        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE), is(TEST_EXCEPTION));
    }

    @Test
    public void testLoadExperiment() throws Exception {
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
    }

    @Test
    public void testLoadExperimentEmptyAccession() throws Exception {
        assertThat(subject.loadExperiment("", BASELINE_TYPE), is("Experiment accession cannot be empty."));
    }

    @Test
    public void testLoadExperimentExistingAccession() throws Exception {
        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(experimentConfigurationMock);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is("Experiment with accession " + EXPERIMENT_ACCESSION + " already exists."));
    }

    @Test
    public void testLoadExperimentWrongType() throws Exception {
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, "NON-EXISTING-TYPE"), is("An unknown experiment type has been specified."));
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadExperimentIllegalState() throws Exception {
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(BASELINE_TYPE))).thenReturn(0);
        subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE);
    }

    @Test
    public void testDeleteExperiment() throws Exception {
        assertThat(subject.deleteExperiment(EXPERIMENT_ACCESSION), is("Experiment " + EXPERIMENT_ACCESSION + " deleted."));
    }

    @Test
    public void testDeleteExperimentEmpty() throws Exception {
        assertThat(subject.deleteExperiment(""), is("Experiment accession cannot be empty."));
    }

    @Test
    public void testDeleteExperimentNonExisting() throws Exception {
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(0);
        assertThat(subject.deleteExperiment(EXPERIMENT_ACCESSION), is("Experiment " + EXPERIMENT_ACCESSION + " not present."));
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteExperimentIllegalState() throws Exception {
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(-1);
        subject.deleteExperiment(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testListExperiments() throws Exception {
        assertThat(subject.listExperiments(), is("[{\"experimentAccession\":\"EXPERIMENT_ACCESSION\",\"experimentType\":\"BASELINE\"}]"));
    }
}