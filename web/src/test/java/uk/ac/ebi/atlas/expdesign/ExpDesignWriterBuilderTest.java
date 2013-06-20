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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExpDesignWriterBuilderTest {

    @Mock
    private RnaSeqExpDesignWriter rnaSeqExpDesignWriterMock;

    @Mock
    private MicroArrayExpDesignWriter microArrayExpDesignWriterMock;

    @Mock
    private TwoColourExpDesignWriter twoColourExpDesignWriterMock;

    private ExpDesignWriterBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExpDesignWriterBuilder(
                rnaSeqExpDesignWriterMock,
                microArrayExpDesignWriterMock,
                twoColourExpDesignWriterMock);
    }

    @Test
    public void testForExperimentType() throws Exception {
        assertThat(subject.forExperimentType(ExperimentType.BASELINE), is(subject));
    }

    @Test
    public void testBuildRnaSeq() throws Exception {
        assertThat(subject.forExperimentType(ExperimentType.BASELINE).build(),
                is((ExpDesignWriter) rnaSeqExpDesignWriterMock));

        assertThat(subject.forExperimentType(ExperimentType.DIFFERENTIAL).build(),
                is((ExpDesignWriter) rnaSeqExpDesignWriterMock));
    }

    @Test
    public void testBuildMicroArray() throws Exception {
        assertThat(subject.forExperimentType(ExperimentType.MICROARRAY).build(),
                is((ExpDesignWriter) microArrayExpDesignWriterMock));
    }

    @Test
    public void testBuildTwoColour() throws Exception {
        assertThat(subject.forExperimentType(ExperimentType.TWOCOLOUR).build(),
                is((ExpDesignWriter) twoColourExpDesignWriterMock));
    }

    @Test(expected = IllegalStateException.class)
    public void testNullType() throws Exception {
        subject.forExperimentType(null).build();
    }

}