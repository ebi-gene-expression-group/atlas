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

package uk.ac.ebi.atlas.experimentpage.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqRequestContextBuilderTest {

    private static final String SPECIES = "species";
    
    @Mock
    DifferentialExperiment experimentMock;

    @Mock
    DifferentialRequestPreferences preferencesMock;

    RnaSeqRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(experimentMock.getFirstOrganism()).thenReturn(SPECIES);
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.EMPTY);
        subject = new RnaSeqRequestContextBuilder(new RnaSeqRequestContext());
    }

    @Test
    public void testBuild() throws Exception {
        RnaSeqRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context, is(not(nullValue())));
        assertThat(context.getFilteredBySpecies(), is(SPECIES.toLowerCase()));
    }
}