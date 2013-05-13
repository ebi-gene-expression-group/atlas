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
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AverageBaselineProfileBuilderTest {

    @Mock
    private BaselineProfile profileMock1;

    @Mock
    private BaselineProfile profileMock2;

    @Mock
    private Factor factorMock1;

    @Mock
    private Factor factorMock2;

    @Mock
    private BaselineRequestContext baselineRequestContextMock;

    private BaselineProfilesList baselineProfilesListMock;

    private AverageBaselineProfileBuilder subject;

    @Before
    public void initSubject(){

        when(factorMock1.getType()).thenReturn("type1");
        when(factorMock2.getType()).thenReturn("type2");

        when(baselineRequestContextMock.getSelectedFilterFactors()).thenReturn(new TreeSet<Factor>());

        SortedSet<Factor> allQueryFactorsMock = Sets.newTreeSet(Sets.newHashSet(factorMock1, factorMock2));

        when(baselineRequestContextMock.getAllQueryFactors()).thenReturn(allQueryFactorsMock);
        when(profileMock1.getExpressionLevel(factorMock1)).thenReturn(5.0);
        when(profileMock2.getExpressionLevel(factorMock2)).thenReturn(2.0);

        baselineProfilesListMock = new BaselineProfilesList(Lists.newArrayList(profileMock1, profileMock2));

        subject = new AverageBaselineProfileBuilder(baselineRequestContextMock);
    }



    @Test
    public void testCalculateAverageExpressionProfile() throws Exception {
        BaselineProfile profile = subject.forProfileId("anId").withBaselineProfiles(baselineProfilesListMock).build();

        assertThat(profile.getId(), is("anId"));

        assertThat(profile.getExpressionLevel(factorMock1), is(3.0));
        assertThat(profile.getExpressionLevel(factorMock2), is(1.0));
    }

}
