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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileComparatorTest {

    private DifferentialProfileComparator subject;

    @Mock
    private Contrast contrastMock1;
    @Mock
    private Contrast contrastMock2;
    @Mock
    private Contrast contrastMock3;

    private Set<Contrast> allContrasts;
    private Set<Contrast> selectedContrasts;
    private Set<Contrast> nonSelectedContrasts;

    @Mock
    private DifferentialProfile profileMock1;

    @Mock
    private DifferentialProfile profileMock2;

    @Before
    public void init() {
        allContrasts = Sets.newHashSet(contrastMock1, contrastMock2, contrastMock3);
        selectedContrasts = Sets.newHashSet(contrastMock1);
        nonSelectedContrasts = Sets.newHashSet(contrastMock2, contrastMock3);

        subject = new DifferentialProfileComparator(true, selectedContrasts, allContrasts, Regulation.UP);
    }

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        //when
        subject = new DifferentialProfileComparator(true, null, allContrasts, Regulation.UP);

        when(profileMock1.getSpecificity(Regulation.UP)).thenReturn(1);
        when(profileMock2.getSpecificity(Regulation.UP)).thenReturn(2);

        //then
        int comparison = subject.compare(profileMock1, profileMock2);

        assertThat(comparison, lessThan(0));
    }

    @Test
    public void lowerAverageAcrossSelectedContrasts() {

         //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.01);
        when(profileMock1.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3), Regulation.UP)).thenReturn(0.02);
        //and
         //when
        when(profileMock2.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.01);
        when(profileMock2.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3), Regulation.UP)).thenReturn(0.04);


        int comparison = subject.compare(profileMock1, profileMock2);
        // then
        assertThat(comparison, is(greaterThanOrEqualTo(0)));

    }

    @Test
    public void testGetExpressionLevelFoldChangeOn() throws Exception {
        //when
        when(profileMock1.getMinExpressionLevelOn(nonSelectedContrasts, Regulation.UP)).thenReturn(0.04);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.025);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(1.5999999999999999));

        //when
        when(profileMock1.getMinExpressionLevelOn(nonSelectedContrasts, Regulation.UP)).thenReturn(0.05D);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.025);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));

        //when
        when(profileMock1.getMinExpressionLevelOn(nonSelectedContrasts, Regulation.UP)).thenReturn(0.02);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.01);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOnWhenAllContrastsAreSelected() throws Exception {
        subject = new DifferentialProfileComparator(true, selectedContrasts, allContrasts, Regulation.UP);

        //when
        when(profileMock1.getMinExpressionLevelOn(nonSelectedContrasts, Regulation.UP)).thenReturn(0.05);
        //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.025);
        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }

}
