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
import uk.ac.ebi.atlas.expdesign.ExpDesignTsvWriter;
import uk.ac.ebi.atlas.expdesign.MicroArrayMageTabLimpopoExpDesignParser;
import uk.ac.ebi.atlas.expdesign.RnaSeqMageTabLimpopoExpDesignParser;
import uk.ac.ebi.atlas.expdesign.TwoColourMageTabLimpopoExpDesignParser;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpDesignLoaderControllerTest {

    public static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    @Mock
    ApplicationProperties applicationPropertiesMock;

    @Mock
    RnaSeqMageTabLimpopoExpDesignParser rnaSeqMageTabLimpopoParserMock;

    @Mock
    MicroArrayMageTabLimpopoExpDesignParser microArrayMageTabLimpopoExpDesignParserMock;

    @Mock
    TwoColourMageTabLimpopoExpDesignParser twoColourMageTabLimpopoExpDesignParserMock;

    @Mock
    ExpDesignTsvWriter expDesignTsvWriterMock;

    @Mock
    CSVWriter csvWriterMock;

    ExpDesignLoaderController subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExpDesignLoaderController(applicationPropertiesMock,
                rnaSeqMageTabLimpopoParserMock,
                microArrayMageTabLimpopoExpDesignParserMock,
                twoColourMageTabLimpopoExpDesignParserMock,
                expDesignTsvWriterMock);

        when(expDesignTsvWriterMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(csvWriterMock);
        when(rnaSeqMageTabLimpopoParserMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(rnaSeqMageTabLimpopoParserMock);
        when(rnaSeqMageTabLimpopoParserMock.build()).thenReturn(rnaSeqMageTabLimpopoParserMock);
        when(microArrayMageTabLimpopoExpDesignParserMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(microArrayMageTabLimpopoExpDesignParserMock);
        when(microArrayMageTabLimpopoExpDesignParserMock.build()).thenReturn(microArrayMageTabLimpopoExpDesignParserMock);
        when(twoColourMageTabLimpopoExpDesignParserMock.forExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(twoColourMageTabLimpopoExpDesignParserMock);
        when(twoColourMageTabLimpopoExpDesignParserMock.build()).thenReturn(twoColourMageTabLimpopoExpDesignParserMock);
    }

    @Test
    public void testLoadExpDesignForBaseline() throws Exception {
        when(applicationPropertiesMock.getBaselineExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(rnaSeqMageTabLimpopoParserMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignForDifferential() throws Exception {
        when(applicationPropertiesMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(rnaSeqMageTabLimpopoParserMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignMicroArray() throws Exception {
        when(applicationPropertiesMock.getMicroarrayExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(microArrayMageTabLimpopoExpDesignParserMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testLoadExpDesignTwoColour() throws Exception {
        when(applicationPropertiesMock.getTwoColourExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));

        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("ExperimentDesign for " + EXPERIMENT_ACCESSION + " loaded."));
        verify(twoColourMageTabLimpopoExpDesignParserMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(csvWriterMock).flush();
    }

    @Test
    public void testUnknownExperiment() throws Exception {
        assertThat(subject.loadExpDesign(EXPERIMENT_ACCESSION), is("Not a known experiment accession: " + EXPERIMENT_ACCESSION));
    }
}