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
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayRequestContextBuilderTest {

    public static final String ACCESSION = "ACCESSION";
    private static final String SPECIES = "SPECIES";
    @Mock
    MicroarrayExperiment experimentMock;

    @Mock
    MicroarrayRequestPreferences preferencesMock;

    MicroarrayRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(preferencesMock.getArrayDesignAccession()).thenReturn(ACCESSION);
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.EMPTY);
        when(experimentMock.getFirstOrganism()).thenReturn(SPECIES);
        subject = new MicroarrayRequestContextBuilder(new MicroarrayRequestContext());
    }

    @Test
    public void testBuild() throws Exception {
        MicroarrayRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getArrayDesignAccession(), is(ACCESSION));
        assertThat(context.getFilteredBySpecies(), is(SPECIES.toLowerCase()));
    }
}