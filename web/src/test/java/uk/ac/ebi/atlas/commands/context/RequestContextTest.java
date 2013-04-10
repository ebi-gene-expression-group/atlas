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

package uk.ac.ebi.atlas.commands.context;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestContextTest {

    RequestContext<String, ExperimentPageRequestPreferences> subject;

    @Mock
    ExperimentPageRequestPreferences preferencesMock;

    @Before
    public void setUp() throws Exception {
        subject = new RequestContext<>();
        subject.setRequestPreferences(preferencesMock);

        when(preferencesMock.getGeneQuery()).thenReturn("GENE_QUERY");
        when(preferencesMock.getHeatmapMatrixSize()).thenReturn(42);
        when(preferencesMock.getCutoff()).thenReturn(0.05);
        when(preferencesMock.isSpecific()).thenReturn(true);
        when(preferencesMock.isExactMatch()).thenReturn(false);
    }

    @Test
    public void testGetGeneQuery() throws Exception {
        assertThat(subject.getGeneQuery(), is("GENE_QUERY"));
    }

    @Test
    public void testGetHeatmapMatrixSize() throws Exception {
        assertThat(subject.getHeatmapMatrixSize(), is(42));
    }

    @Test
    public void testGetSelectedQueryFactors() throws Exception {
        assertThat(subject.getSelectedQueryFactors(), is(nullValue()));
    }

    @Test
    public void testGetFilteredBySpecies() throws Exception {
        assertThat(subject.getFilteredBySpecies(), is(nullValue()));
    }

    @Test
    public void testGetCutoff() throws Exception {
        assertThat(subject.getCutoff(), is(0.05));
    }

    @Test
    public void testIsSpecific() throws Exception {
        assertThat(subject.isSpecific(), is(true));
    }

    @Test
    public void testIsExactMatch() throws Exception {
        assertThat(subject.isExactMatch(), is(false));
    }

    @Test
    public void testGetAllQueryFactors() throws Exception {
        assertThat(subject.getAllQueryFactors(), is(nullValue()));
    }

    @Test
    public void testSetAllQueryFactors() throws Exception {
        SortedSet<String> set = Sets.newTreeSet();
        set.add("b");
        set.add("a");
        set.add("c");
        subject.setAllQueryFactors(set);
        assertThat(subject.getAllQueryFactors(), contains("a", "b", "c"));
    }

    @Test
    public void testSetSelectedQueryFactors() throws Exception {
        subject.setSelectedQueryFactors(Sets.newHashSet("a", "b", "c"));
        assertThat(subject.getSelectedQueryFactors(), hasItems("a", "b", "c"));
    }

    @Test
    public void testSetFilteredBySpecies() throws Exception {
        subject.setFilteredBySpecies("homo sapiens");
        assertThat(subject.getFilteredBySpecies(), is("homo sapiens"));
    }

    @Test(expected = NullPointerException.class)
    public void testSetRequestPreferences() throws Exception {
        subject.setRequestPreferences(null);
        subject.getCutoff();
    }

    @Test
    public void testToString() throws Exception {
        assertThat(subject.toString(), containsString("requestPreferences"));
        assertThat(subject.toString(), containsString("filteredBySpecies"));
    }
}