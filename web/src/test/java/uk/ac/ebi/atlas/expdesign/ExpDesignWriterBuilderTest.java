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

package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpDesignWriterBuilderTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";

    @Mock
    private ApplicationProperties applicationPropertiesMock;

    @Mock
    private RnaSeqExpDesignWriter rnaSeqExpDesignWriterMock;

    @Mock
    private MicroArrayExpDesignWriter microArrayExpDesignWriterMock;

    @Mock
    private TwoColourExpDesignWriter twoColourExpDesignWriterMock;

    private ExpDesignWriterBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExpDesignWriterBuilder(applicationPropertiesMock,
                rnaSeqExpDesignWriterMock,
                microArrayExpDesignWriterMock,
                twoColourExpDesignWriterMock);
    }

    @Test
    public void testForExperimentAccession() throws Exception {
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION), is(subject));
    }

    @Test
    public void testBuildRnaSeq() throws Exception {
        when(applicationPropertiesMock.getBaselineExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION).build(), is((ExpDesignWriter) rnaSeqExpDesignWriterMock));

        when(applicationPropertiesMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION).build(), is((ExpDesignWriter) rnaSeqExpDesignWriterMock));
    }

    @Test
    public void testBuildMicroArray() throws Exception {
        when(applicationPropertiesMock.getMicroarrayExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION).build(), is((ExpDesignWriter) microArrayExpDesignWriterMock));
    }

    @Test
    public void testBuildTwoColour() throws Exception {
        when(applicationPropertiesMock.getTwoColourExperimentsIdentifiers()).thenReturn(Sets.newHashSet(EXPERIMENT_ACCESSION));
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION).build(), is((ExpDesignWriter) twoColourExpDesignWriterMock));
    }

    @Test(expected = IllegalStateException.class)
    public void testUnknownAccession() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION).build();
    }
}