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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.GeneProfilesList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesListTest {

    @Mock
    BaselineProfile profile_1;
    @Mock
    BaselineProfile profile_2;
    @Mock
    BaselineProfile profile_3;
    @Mock
    BaselineProfile profile_4;
    @Mock
    BaselineProfile profile_5;

    private GeneProfilesList<BaselineProfile> subject;

    @Before
    public void setUp() throws Exception {

        subject = new GeneProfilesList(Lists.newArrayList(profile_5, profile_3, profile_4, profile_1, profile_2));

    }

    @Test
    public void testList() throws Exception {
        assertThat(subject, contains(profile_5, profile_3, profile_4, profile_1, profile_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sublistShouldThrowIllegalArguemntExceptionWhenUpperIndexIsLessThanZero() throws Exception {
        //when
        GeneProfilesList e = subject.subList(0, -1);
        assertThat(e, is(nullValue()));
    }

    public void sublistTest() throws Exception {
        //when
        GeneProfilesList<BaselineProfile> geneProfiles = subject.subList(0, 3);
        //then
        assertThat(geneProfiles, contains(profile_5, profile_3, profile_4));
    }

    public void sublistShouldReturnEntireListWhenTopIndexLargerThanListSize() throws Exception {
        //when
        GeneProfilesList<BaselineProfile> geneProfiles = subject.subList(0, 7);
        //then
        assertThat(geneProfiles, hasSize(5));
    }

    @Test
    public void getMaxExpressionLevelTest() {
        //given
        when(profile_1.getMaxExpressionLevel()).thenReturn(55d);
        when(profile_2.getMaxExpressionLevel()).thenReturn(15d);
        when(profile_3.getMaxExpressionLevel()).thenReturn(25d);
        when(profile_4.getMaxExpressionLevel()).thenReturn(115d);
        when(profile_5.getMaxExpressionLevel()).thenReturn(35d);
        //then
        assertThat(subject.getMaxExpressionLevel(), is(115d));
    }

    @Test
    public void getMinExpressionLevelTest() {
        //given
        when(profile_1.getMinExpressionLevel()).thenReturn(55d);
        when(profile_2.getMinExpressionLevel()).thenReturn(15d);
        when(profile_3.getMinExpressionLevel()).thenReturn(25d);
        when(profile_4.getMinExpressionLevel()).thenReturn(115d);
        when(profile_5.getMinExpressionLevel()).thenReturn(35d);
        //then
        assertThat(subject.getMinExpressionLevel(), is(15d));
    }


}
