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

package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsDifferentialProfileSpecificTest {

    IsDifferentialProfileSpecific subject;

    @Mock
    private AssayGroup referenceAssayGroup;

    @Mock
    private AssayGroup testAssayGroup;

    Contrast contrast1 = new Contrast("c1", "a1", referenceAssayGroup, testAssayGroup, "c1");

    Contrast contrast2 = new Contrast("c2", "a1", referenceAssayGroup, testAssayGroup, "c2");

    @Mock
    DifferentialProfile profileMock;

    Set<Contrast> allQueryFactors = Sets.newHashSet(contrast1, contrast2);

    Set<Contrast> selectedQueryFactors = Sets.newHashSet(contrast1);

    Set<Contrast> nonSelectedQueryContrasts = Sets.newHashSet(contrast2);

    @Before
    public void setUp() throws Exception {
        subject = new IsDifferentialProfileSpecific(selectedQueryFactors, allQueryFactors);
    }

    @Test
    public void testApply() throws Exception {
        assertThat(subject.apply(profileMock), is(false));

        when(profileMock.getAverageExpressionLevelOn(selectedQueryFactors)).thenReturn(0.01);
        when(profileMock.getStrongestExpressionLevelOn(nonSelectedQueryContrasts)).thenReturn(0.5);
        assertThat(subject.apply(profileMock), is(false));

        when(profileMock.getAverageExpressionLevelOn(selectedQueryFactors)).thenReturn(0.5);
        when(profileMock.getStrongestExpressionLevelOn(nonSelectedQueryContrasts)).thenReturn(0.01);
        assertThat(subject.apply(profileMock), is(true));
    }
}