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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayRequestContextTest {

    private MicroarrayRequestContext subject;

    @Mock
    private DifferentialRequestPreferences requestPreferencesMock;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayRequestContext();
        subject.setRequestPreferences(requestPreferencesMock);
        when(requestPreferencesMock.getRegulation()).thenReturn(Regulation.UP);
    }

    @Test
    public void testToString() throws Exception {
        assertThat(subject.toString(), containsString("arrayDesignAccession"));
        assertThat(subject.toString(), containsString("UP"));
    }

    @Test
    public void testSetArrayDesignAccession() throws Exception {
        subject.setArrayDesignAccession("ARRAY_DESIGN");
        assertThat(subject.getArrayDesignAccession(), is("ARRAY_DESIGN"));
    }

    @Test
    public void testGetArrayDesignAccession() throws Exception {
        assertThat(subject.getArrayDesignAccession(), is(nullValue()));
    }
}