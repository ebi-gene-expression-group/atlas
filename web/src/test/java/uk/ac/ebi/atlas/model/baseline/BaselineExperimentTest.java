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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentTest {

    public static final String RUN_ACCESSION1 = "run1";
    public static final String RUN_ACCESSION2 = "run2";
    public static final String DEFAULT_QUERY_FACTOR_TYPE = "defaultQueryFactorType";

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    @Mock
    ExperimentRun runMock1;

    @Mock
    ExperimentRun runMock2;

    @Mock
    FactorGroup factorGroupMock;

    @Mock
    Factor factorMock;

    Map<String, String> speciesMapping = Maps.newHashMap();

    BaselineExperiment subject;

    @Before
    public void setUp() throws Exception {
        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);

        subject = new BaselineExperiment("accession", experimentalFactorsMock,
                Sets.newHashSet(runMock1, runMock2), "description",
                "displayName", Sets.newHashSet("species"), speciesMapping, DEFAULT_QUERY_FACTOR_TYPE,
                Sets.newHashSet(factorMock), true);


    }

    @Test
    public void testGetFactorGroup() throws Exception {
        assertThat(subject.getFactorGroup(RUN_ACCESSION1), is(factorGroupMock));
    }

    @Test
    public void testGetExperimentRunAccessions() throws Exception {
        assertThat(subject.getExperimentRunAccessions(), hasItems(RUN_ACCESSION1, RUN_ACCESSION2));
    }

    @Test
    public void testGetDefaultQueryFactorType() throws Exception {
        assertThat(subject.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
    }

    @Test
    public void testGetDefaultFilterFactors() throws Exception {
        assertThat(subject.getDefaultFilterFactors(), hasItem(factorMock));
    }

    @Test
    public void testGetExperimentalFactors() throws Exception {
        assertThat(subject.getExperimentalFactors(), is(experimentalFactorsMock));
    }
}