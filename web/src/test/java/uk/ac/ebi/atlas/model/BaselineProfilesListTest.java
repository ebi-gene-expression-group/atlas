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

package uk.ac.ebi.atlas.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesListTest {

    BaselineProfilesList subject;

    @Mock
    BaselineProfile profileMock1;

    @Mock
    BaselineProfile profileMock2;

    @Mock
    Factor factorMock1;

    @Mock
    Factor factorMock2;

    Set<Factor> allQueryFactors;

    @Before
    public void setUp() throws Exception {


        allQueryFactors = Sets.newHashSet(factorMock1, factorMock2);

        when(profileMock1.getMaxExpressionLevel()).thenReturn(10.0);
        when(profileMock1.getExpressionLevel(factorMock1)).thenReturn(5.0);

        when(profileMock2.getMinExpressionLevel()).thenReturn(-10.0);
        when(profileMock2.getExpressionLevel(factorMock2)).thenReturn(2.0);

        subject = new BaselineProfilesList(Lists.newArrayList(profileMock1, profileMock2));
        subject.setTotalResultCount(2);
    }

    @Test
    public void testGetMaxExpressionLevel() throws Exception {
        assertThat(subject.getMaxExpressionLevel(), is(10.0));
    }

    @Test
    public void testGetMinExpressionLevel() throws Exception {
        assertThat(subject.getMinExpressionLevel(), is(-10.0));
    }

    @Test
    public void testTotalResultCount() throws Exception {
        assertThat(subject.getTotalResultCount(), is(2));
    }

    @Test
    public void testCollapsedProfilesList() throws Exception {
        BaselineProfilesList baselineProfiles = subject.collapsedProfilesList(allQueryFactors);
        assertThat(baselineProfiles.getMaxExpressionLevel(), is(3.0));
        assertThat(baselineProfiles.getMinExpressionLevel(), is(1.0));
        assertThat(baselineProfiles.size(), is(1));
    }

    @Test
    public void testCalculateAverageExpressionProfile() throws Exception {
        BaselineProfile profile = subject.calculateAverageExpressionProfile(allQueryFactors);
        assertThat(profile.getExpressionLevel(factorMock1), is(3.0));
        assertThat(profile.getExpressionLevel(factorMock2), is(1.0));
        assertThat(profile.getGeneId(), is("averages"));
    }

    @Test
    public void testCalculateCountsExpressionProfile() throws Exception {
        BaselineProfile profile = subject.calculateCountsExpressionProfile(allQueryFactors);
        assertThat(profile.getExpressionLevel(factorMock1), is(1.0));
        assertThat(profile.getExpressionLevel(factorMock2), is(1.0));
        assertThat(profile.getGeneId(), is("counts"));
    }

    @Test
    public void testCalculateMinExpressionProfile() throws Exception {
        BaselineProfile profile = subject.calculateMinExpressionProfile(allQueryFactors);
        assertThat(profile.getExpressionLevel(factorMock1), is(5.0));
        assertThat(profile.getExpressionLevel(factorMock2), is(2.0));
        assertThat(profile.getGeneId(), is("min"));
    }

    @Test
    public void testCalculateMaxExpressionProfile() throws Exception {
        BaselineProfile profile = subject.calculateMaxExpressionProfile(allQueryFactors);
        assertThat(profile.getExpressionLevel(factorMock1), is(5.0));
        assertThat(profile.getExpressionLevel(factorMock2), is(2.0));
        assertThat(profile.getGeneId(), is("max"));
    }

    @Test
    public void testTransformMapIntoProfile() throws Exception {
        BaselineProfile profile = subject.transformMapIntoProfile("test",
                Maps.asMap(allQueryFactors, new Function<Factor, Number>() {
                    @Override
                    public Number apply(Factor factor) {
                        return 1.0;
                    }
                }));
        assertThat(profile.getExpressionLevel(factorMock1), is(1.0));
        assertThat(profile.getExpressionLevel(factorMock2), is(1.0));
        assertThat(profile.getGeneId(), is("test"));
    }

}