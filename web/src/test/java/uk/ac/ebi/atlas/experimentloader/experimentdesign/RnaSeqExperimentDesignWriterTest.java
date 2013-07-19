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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqExperimentDesignWriterTest {

    private RnaSeqExperimentDesignWriter subject;

    @Mock
    private RnaSeqExperimentDesignMageTabParser rnaSeqExperimentDesignMageTabParserMock;
    @Mock
    private ExperimentDesign experimentDesignMock;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqExperimentDesignWriter(rnaSeqExperimentDesignMageTabParserMock);
    }

    @Test
    public void testComposeHeader() throws Exception {
        given(experimentDesignMock.getFactorHeaders()).willReturn(Sets.newTreeSet(Sets.newHashSet("F1", "F2")));
        given(experimentDesignMock.getSampleHeaders()).willReturn(Sets.newTreeSet(Sets.newHashSet("S1", "S2")));

        String[] header = subject.buildColumnHeaders(experimentDesignMock);
        assertThat(header, arrayContaining("Run", "Sample Characteristics[S1]", "Sample Characteristics[S2]", "Factor Values[F1]", "Factor Values[F2]" ));

    }
}
