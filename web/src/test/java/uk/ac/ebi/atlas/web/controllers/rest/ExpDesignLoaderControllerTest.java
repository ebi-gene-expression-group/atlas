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
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.expdesign.*;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpDesignLoaderControllerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";

    @Mock
    private ApplicationProperties applicationPropertiesMock;

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

    private ExpDesignLoaderController subject;

    @Before
    public void setUp() throws Exception {
        expDesignWriterBuilder = new ExpDesignWriterBuilder(applicationPropertiesMock,
                rnaSeqExpDesignWriterMock,
                microArrayExpDesignWriterMock,
                twoColourExpDesignWriterMock);

        subject = new ExpDesignLoaderController(
                expDesignTsvWriterMock, expDesignWriterBuilder);

        when(expDesignTsvWriterMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(csvWriterMock);
        when(expDesignTsvWriterMock.getFileAbsolutePath()).thenReturn("UNIT_TEST");
    }

    @Test
    public void testLoadExpDesignForBaseline() throws Exception {
        when(applicationPropertiesMock.getBaselineExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignForDifferential() throws Exception {
        when(applicationPropertiesMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(rnaSeqExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignMicroArray() throws Exception {
        when(applicationPropertiesMock.getMicroarrayExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(microArrayExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignTwoColour() throws Exception {
        when(applicationPropertiesMock.getTwoColourExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(twoColourExpDesignWriterMock).forExperimentAccession(EXPERIMENT_ACCESSION, csvWriterMock);
        verify(csvWriterMock).flush();
    }

    @Test(expected = IllegalStateException.class)
    public void testUnknownExperiment() throws Exception {
        subject.loadExpDesign(EXPERIMENT_ACCESSION);
    }
}