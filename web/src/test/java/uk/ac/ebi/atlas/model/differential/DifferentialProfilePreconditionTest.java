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

package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfilePreconditionTest {

    DifferentialProfilePrecondition subject;

    @Mock
    Contrast contrastMock1;

    @Mock
    Contrast contrastMock2;

    @Mock
    DifferentialProfile profileMock;

    Set<Contrast> allQueryFactors = Sets.newHashSet(contrastMock1, contrastMock2);

    Set<Contrast> selectedQueryFactors = Sets.newHashSet(contrastMock1);

    @Before
    public void setUp() throws Exception {
        subject = new DifferentialProfilePrecondition();
        subject.setAllQueryFactors(allQueryFactors);
        subject.setRegulation(Regulation.UP_DOWN);
        subject.setSelectedQueryContrasts(selectedQueryFactors);
    }

    @Test
    public void testApply() throws Exception {
        assertThat(subject.apply(profileMock), is(true));

        when(profileMock.isEmpty()).thenReturn(true);
        assertThat(subject.apply(profileMock), is(false));

        when(profileMock.isEmpty()).thenReturn(false);
        when(profileMock.getAverageExpressionLevelOn(selectedQueryFactors, Regulation.UP_DOWN)).thenReturn(10.0);
        assertThat(subject.apply(profileMock), is(true));

        when(profileMock.isEmpty()).thenReturn(false);
        when(profileMock.getAverageExpressionLevelOn(selectedQueryFactors, Regulation.UP_DOWN)).thenReturn(-10.0);
        assertThat(subject.apply(profileMock), is(true));
    }
}