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

package uk.ac.ebi.atlas.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankDifferentialProfilesCommandTest {

    private RankDifferentialProfilesExecutor subject;

    @Mock
    private DifferentialRequestContext requestContext;

    @Mock
    private DifferentialProfile differentialProfileMock1;

    @Mock
    private DifferentialProfile differentialProfileMock2;

    @Mock
    private DifferentialProfile differentialProfileMock3;

    @Before
    public void setUp() throws Exception {
        when(requestContext.getRegulation()).thenReturn(Regulation.UP_DOWN);

        subject = new RankDifferentialProfilesExecutor(requestContext);

        when(differentialProfileMock1.getAverageExpressionLevelOn(new HashSet<Contrast>(), Regulation.UP_DOWN)).thenReturn(3D);
        when(differentialProfileMock1.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);
        when(differentialProfileMock2.getAverageExpressionLevelOn(new HashSet<Contrast>(), Regulation.UP_DOWN)).thenReturn(5D);
        when(differentialProfileMock2.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);
        when(differentialProfileMock3.getAverageExpressionLevelOn(new HashSet<Contrast>(), Regulation.UP_DOWN)).thenReturn(2D);
        when(differentialProfileMock3.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);

    }

    //ToDo: move this test to comparator test
    @Test
    public void comparatorShouldSortByLowestPValueFirst() throws Exception {
        //given
        Comparator comparator = subject.buildGeneProfileComparator();

        //when
        SortedSet<DifferentialProfile> profiles = new TreeSet<DifferentialProfile>(comparator);
        profiles.add(differentialProfileMock1);
        profiles.add(differentialProfileMock2);
        profiles.add(differentialProfileMock3);

        //then
        Iterator<DifferentialProfile> profilesIterator = profiles.iterator();

        assertThat(profilesIterator.next(), is(differentialProfileMock3));
        assertThat(profilesIterator.next(), is(differentialProfileMock1));
        assertThat(profilesIterator.next(), is(differentialProfileMock2));
    }
}
