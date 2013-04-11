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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialRequestContextTest {

    DifferentialRequestContext subject;

    @Mock
    DifferentialRequestPreferences preferencesMock;

    Regulation regulation = Regulation.UP;

    Regulation regulation1 = Regulation.DOWN;

    @Before
    public void setUp() throws Exception {
        subject = new DifferentialRequestContext();

        when(preferencesMock.getRegulation()).thenReturn(regulation);

        subject.setRequestPreferences(preferencesMock);
    }

    @Test
    public void testGetRegulation() throws Exception {
        assertThat(subject.getRegulation(), is(regulation));
    }

    @Test
    public void testSetRegulation() throws Exception {
        subject.setRegulation(regulation1);
        assertThat(subject.getRegulation(), is(regulation1));
    }

    @Test
    public void testToString() throws Exception {
        assertThat(subject.toString(), containsString("regulation"));
    }
}