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

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialRequestContextBuilderTest {

    public static final String ORGANISM = "homo sapiens";
    public static final String CONTRAST_NAME1 = "a";
    public static final String CONTRAST_NAME2 = "b";

    @Mock
    MicroarrayExperiment experimentMock;

    @Mock
    MicroarrayRequestPreferences preferencesMock;

    @Mock
    Contrast contrastMock1;

    @Mock
    Contrast contrastMock2;

    MicroarrayRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayRequestContextBuilder(new MicroarrayRequestContext());

        when(experimentMock.getFirstOrganism()).thenReturn(ORGANISM);

        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_NAME1);
        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_NAME2);
        SortedSet<Contrast> sortedSet = new TreeSet();
        sortedSet.add(contrastMock1);
        sortedSet.add(contrastMock2);

        when(experimentMock.getContrasts()).thenReturn(sortedSet);
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("a")));
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.EMPTY);
        when(experimentMock.getContrast(CONTRAST_NAME1)).thenReturn(contrastMock1);

    }

    @Test
    public void testBuild() throws Exception {
        MicroarrayRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getSelectedQueryFactors(), hasItem(contrastMock1));
        assertThat(context.getFilteredBySpecies(), is(ORGANISM));
        assertThat(context.getAllQueryFactors(), hasItems(contrastMock1, contrastMock2));
    }
}